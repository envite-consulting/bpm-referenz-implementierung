import { act, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { createBestellung } from '@/pages/Bestellung/queries/api/createBestellung.ts';
import { StatusEnum } from './Bestellung.types.ts';
import { Bestellung } from './Bestellung.tsx';

jest.mock('@/pages/Bestellung/queries/api/createBestellung.ts', () => ({
  createBestellung: jest.fn(),
}));

jest.mock('@/pages/Bestellung/components/Antragsteller/Antragsteller.tsx');

jest.mock('@/infrastructure/components/Button/Button.tsx');

const mockData = {
  id: 'test-id',
  antragstellerreferenz: 'mock-id',
  fahrzeugreferenz: 'b6122856-f08a-4454-b5bd-a3d232065b91',
  bestelldatum: new Date(),
  status: StatusEnum.enum.ANGELEGT,
};

describe('Bestellung', () => {
  const mockCreateBestellung = createBestellung as jest.MockedFunction<
    typeof createBestellung
  >;

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should render form with heading, Antragsteller and submit button', () => {
    render(<Bestellung />);

    expect(
      screen.getByRole('heading', { name: 'Mitarbeiter Firmenwagen Formular' }),
    ).toBeInTheDocument();
    expect(screen.getByTestId('antragsteller-mock')).toBeInTheDocument();
    expect(screen.getByTestId('primary-button-mock')).toBeInTheDocument();
  });

  it('should show validation error if Antragsteller is not selected and form submitted', async () => {
    render(<Bestellung />);

    const submitButton = screen.getByTestId('primary-button-mock');
    await act(async () => {
      userEvent.click(submitButton);
    });

    await waitFor(() => {
      expect(
        screen.getByText('Mitarbeiter ist erforderlich'),
      ).toBeInTheDocument();
    });

    expect(mockCreateBestellung).not.toHaveBeenCalled();
  });

  it('should call createBestellung and reset form on successful submit', async () => {
    mockCreateBestellung.mockResolvedValueOnce(mockData);

    window.alert = jest.fn();

    render(<Bestellung />);

    await act(async () => {
      userEvent.click(screen.getByTestId('antragsteller-mock'));
      userEvent.click(screen.getByTestId('primary-button-mock'));
    });

    await waitFor(() => {
      expect(mockCreateBestellung).toHaveBeenCalledWith({
        antragstellerreferenz: 'mock-id',
        fahrzeugreferenz: 'b6122856-f08a-4454-b5bd-a3d232065b91',
      });
    });
  });

  it('should alert success message on successful creation', async () => {
    mockCreateBestellung.mockResolvedValueOnce(mockData);

    window.alert = jest.fn();

    render(<Bestellung />);

    await act(async () => {
      userEvent.click(screen.getByTestId('antragsteller-mock'));
      userEvent.click(screen.getByTestId('primary-button-mock'));
    });

    await waitFor(() => {
      expect(window.alert).toHaveBeenCalledWith(
        'Bestellung erfolgreich erstellt!',
      );
    });
  });

  it('should alert error message on createBestellung failure', async () => {
    mockCreateBestellung.mockRejectedValueOnce(new Error('Fail'));

    window.alert = jest.fn();
    jest.spyOn(console, 'error').mockImplementation(() => {});

    render(<Bestellung />);

    await act(async () => {
      userEvent.click(screen.getByTestId('antragsteller-mock'));
      userEvent.click(screen.getByTestId('primary-button-mock'));
    });

    await waitFor(() => {
      expect(window.alert).toHaveBeenCalledWith(
        'Fehler beim Erstellen der Bestellung.',
      );
      expect(console.error).toHaveBeenCalledWith(
        'Fehler beim Erstellen der Bestellung:',
        expect.any(Error),
      );
    });
  });
});
