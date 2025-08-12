import { act, render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { createBestellung } from '@bestellung/queries/api/createBestellung.ts';
import { StatusEnum } from '@bestellung/Bestellung.types.ts';
import { Bestellung } from './Bestellung.tsx';

jest.mock('@bestellung/queries/api/createBestellung.ts', () => ({
  createBestellung: jest.fn(),
}));

jest.mock('@bestellung/components/Antragsteller/Antragsteller.tsx');

jest.mock('@ui/Button/Button.tsx');

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
    const { getByTestId, getByRole } = render(<Bestellung />);

    expect(
      getByRole('heading', { name: 'Mitarbeiter Firmenwagen Formular' }),
    ).toBeInTheDocument();
    expect(getByTestId('antragsteller-mock')).toBeInTheDocument();
    expect(getByTestId('primary-button-mock')).toBeInTheDocument();
  });

  it('should show validation error if Antragsteller is not selected and form submitted', async () => {
    const { getByTestId, getByText } = render(<Bestellung />);

    const submitButton = getByTestId('primary-button-mock');
    await act(async () => {
      userEvent.click(submitButton);
    });

    await waitFor(() => {
      expect(getByText('Mitarbeiter ist erforderlich')).toBeInTheDocument();
    });

    expect(mockCreateBestellung).not.toHaveBeenCalled();
  });

  it('should call createBestellung and reset form on successful submit', async () => {
    mockCreateBestellung.mockResolvedValueOnce(mockData);

    window.alert = jest.fn();

    const { getByTestId } = render(<Bestellung />);

    await act(async () => {
      userEvent.click(getByTestId('antragsteller-mock'));
      userEvent.click(getByTestId('primary-button-mock'));
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

    const { getByTestId } = render(<Bestellung />);

    await act(async () => {
      userEvent.click(getByTestId('antragsteller-mock'));
      userEvent.click(getByTestId('primary-button-mock'));
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

    const { getByTestId } = render(<Bestellung />);

    await act(async () => {
      userEvent.click(getByTestId('antragsteller-mock'));
      userEvent.click(getByTestId('primary-button-mock'));
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
