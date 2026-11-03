import { fireEvent, render } from '@testing-library/react';
import { useAufgabeUpdate } from '@aufgabenFormular/queries/useAufgabeUpdate.ts';
import type { Aufgabe } from '@aufgabenliste/Aufgabe.types.ts';
import { useAufgabeQuery } from '@aufgabenFormular/queries/useAufgabeQuery.ts';
import { useFolgeaufgabeVorschlag } from '@aufgabenFormular/queries/useFolgeaufgabeVorschlag.ts';
import { AufgabenFormular } from './AufgabenFormular';

jest.mock('@aufgabenFormular/queries/useAufgabeUpdate.ts');
jest.mock('@aufgabenFormular/queries/useAufgabeQuery.ts');
jest.mock('@aufgabenFormular/queries/useFolgeaufgabeVorschlag.ts');
jest.mock(
  '@aufgabenFormular/components/AufgabenFormularHeader/AufgabenFormularHeader.tsx',
);
jest.mock(
  '@aufgabenFormular/components/AufgabenFormularFooter/AufgabenFormularFooter.tsx',
);
jest.mock('@aufgabenFormular/formwrapper/FormWrapper.tsx');

const mockNavigate = jest.fn();

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useParams: () => ({ id: '123' }),
  useNavigate: () => mockNavigate,
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

  const mockFolgeaufgabeVorschlag = {
    mutate: jest.fn(),
    isPending: false,
  };

  const defaultAufgabe: Aufgabe = {
    id: '123',
    name: 'Test',
    formularreferenz: 'formular-1',
    vorgangId: 'vorgang-1',
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
    (useFolgeaufgabeVorschlag as jest.Mock).mockReturnValue(
      mockFolgeaufgabeVorschlag,
    );
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

  describe('Folgeaufgabe', () => {
    const aufgabeMitArbeitspaket: Aufgabe = {
      ...defaultAufgabe,
      bearbeiter: 'admin',
      arbeitspaket: 'FAHRZEUG',
    };

    const abschliessen = (
      ergebnis: (optionen: {
        onSuccess: (vorschlag: unknown) => void;
        onError: () => void;
      }) => void,
    ) => {
      (useAufgabeQuery as jest.Mock).mockReturnValue({
        aufgabe: aufgabeMitArbeitspaket,
        isLoading: false,
        isError: false,
      });
      mockAbschliessenMitVariablen.mutate.mockImplementation(
        (_variablen, optionen) => optionen.onSuccess(),
      );
      mockFolgeaufgabeVorschlag.mutate.mockImplementation(
        (_parameter, optionen) => ergebnis(optionen),
      );

      const { container } = render(<AufgabenFormular />);
      fireEvent.submit(container.querySelector('form')!);
    };

    it('should request the Vorschlag with Vorgang and Arbeitspaket after completing', () => {
      abschliessen(() => {});

      expect(mockFolgeaufgabeVorschlag.mutate).toHaveBeenCalledWith(
        { vorgangId: 'vorgang-1', letztesArbeitspaket: 'FAHRZEUG' },
        expect.anything(),
      );
    });

    it('should navigate to the suggested Aufgabe', () => {
      abschliessen(({ onSuccess }) => onSuccess({ aufgabeId: 'aufgabe-2' }));

      expect(mockNavigate).toHaveBeenCalledWith(
        '/aufgabenliste/aufgabe/aufgabe-2',
      );
    });

    it('should return to the list with a hint when there is no Vorschlag', () => {
      abschliessen(({ onSuccess }) => onSuccess(null));

      expect(mockNavigate).toHaveBeenCalledWith('/aufgabenliste', {
        state: { hinweis: expect.objectContaining({ type: 'info' }) },
      });
    });

    it('should return to the list with an error message on failure', () => {
      abschliessen(({ onError }) => onError());

      expect(mockNavigate).toHaveBeenCalledWith('/aufgabenliste', {
        state: { hinweis: expect.objectContaining({ type: 'danger' }) },
      });
    });

    it('should show a loading state instead of the form while waiting', () => {
      (useFolgeaufgabeVorschlag as jest.Mock).mockReturnValue({
        ...mockFolgeaufgabeVorschlag,
        isPending: true,
      });
      (useAufgabeQuery as jest.Mock).mockReturnValue({
        aufgabe: undefined,
        isLoading: false,
        isError: true,
      });

      const { container } = render(<AufgabenFormular />);

      expect(container.querySelector('form')).toBeNull();
    });
  });
});
