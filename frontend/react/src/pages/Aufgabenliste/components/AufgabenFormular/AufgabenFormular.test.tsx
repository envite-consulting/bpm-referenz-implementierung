import { fireEvent, render } from '@testing-library/react';
import { useAufgabeUpdate } from '@aufgabenFormular/queries/useAufgabeUpdate.ts';
import type { Aufgabe } from '@aufgabenliste/Aufgabe.types.ts';
import { useAufgabeQuery } from '@aufgabenFormular/queries/useAufgabeQuery.ts';
import { AufgabenFormular } from './AufgabenFormular';

jest.mock('@aufgabenFormular/queries/useAufgabeUpdate.ts');
jest.mock('@aufgabenFormular/queries/useAufgabeQuery.ts');
jest.mock(
  '@aufgabenFormular/components/AufgabenFormularHeader/AufgabenFormularHeader.tsx',
);
jest.mock(
  '@aufgabenFormular/components/AufgabenFormularFooter/AufgabenFormularFooter.tsx',
);
jest.mock('@aufgabenFormular/formwrapper/FormWrapper.tsx');

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useParams: () => ({ id: '123' }),
  useNavigate: () => jest.fn(),
}));

describe('AufgabenFormular', () => {
  const mockUebernehmen = {
    mutate: jest.fn(),
    isError: false,
    isPending: false,
  };

  const mockAbgeben = {
    mutate: jest.fn(),
    isError: false,
    isPending: false,
  };

  const mockAbschliessenMitVariablen = {
    mutate: jest.fn(),
    isError: false,
    isPending: false,
  };

  const defaultAufgabe: Aufgabe = {
    id: '123',
    name: 'Test',
    formularreferenz: 'formular-1',
    bearbeiter: null,
    erstelldatum: new Date(),
  };

  beforeEach(() => {
    jest.clearAllMocks();
    (useAufgabeUpdate as jest.Mock).mockReturnValue({
      uebernehmen: mockUebernehmen,
      abgeben: mockAbgeben,
      abschliessenMitVariablen: mockAbschliessenMitVariablen,
    });
  });

  describe('Rendering', () => {
    it('should render Aufgabe übernehmen warning when no assignee exists', () => {
      (useAufgabeQuery as jest.Mock).mockReturnValue({
        aufgabe: defaultAufgabe,
        isLoading: false,
        isError: false,
      });

      const { asFragment } = render(<AufgabenFormular />);

      expect(asFragment()).toMatchSnapshot();
    });

    it('should render header, footer and FormWrapper when assignee exists', () => {
      (useAufgabeQuery as jest.Mock).mockReturnValue({
        aufgabe: { ...defaultAufgabe, bearbeiter: 'admin' },
        isLoading: false,
        isError: false,
      });

      const { asFragment } = render(<AufgabenFormular />);

      expect(asFragment()).toMatchSnapshot();
    });
  });

  describe('Event handling', () => {
    it('should call uebernehmen.mutate when no assignee exists', () => {
      (useAufgabeQuery as jest.Mock).mockReturnValue({
        aufgabe: defaultAufgabe,
        isLoading: false,
        isError: false,
      });

      const { getByTestId } = render(<AufgabenFormular />);

      fireEvent.click(getByTestId('header-button'));

      expect(mockUebernehmen.mutate).toHaveBeenCalledWith('admin');
    });

    it('should call abgeben.mutate when assignee exists', () => {
      (useAufgabeQuery as jest.Mock).mockReturnValue({
        aufgabe: { ...defaultAufgabe, bearbeiter: 'admin' },
        isLoading: false,
        isError: false,
      });

      const { getByTestId } = render(<AufgabenFormular />);

      fireEvent.click(getByTestId('header-button'));

      expect(mockAbgeben.mutate).toHaveBeenCalledWith(undefined);
    });

    it('should call abschliessenMitVariablen.mutate on submit', () => {
      (useAufgabeQuery as jest.Mock).mockReturnValue({
        aufgabe: { ...defaultAufgabe, bearbeiter: 'admin' },
        isLoading: false,
        isError: false,
      });

      const { container } = render(<AufgabenFormular />);

      const form = container.querySelector('form')!;
      fireEvent.submit(form);

      expect(mockAbschliessenMitVariablen.mutate).toHaveBeenCalledWith(
        {},
        expect.objectContaining({
          onSuccess: expect.any(Function),
        }),
      );
    });
  });
});
