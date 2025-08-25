import { fireEvent, render } from '@testing-library/react';
import { useAufgabeUpdate } from '@aufgabenFormular/queries/useAufgabeUpdate.ts';
import { AufgabenFormular } from './AufgabenFormular';
import type { Aufgabe } from '@aufgabenliste/Aufgabe.types.ts';

jest.mock('@aufgabenFormular/queries/useAufgabeUpdate.ts');
jest.mock(
  '@aufgabenFormular/components/AufgabenFormularHeader/AufgabenFormularHeader.tsx',
);
jest.mock(
  '@aufgabenFormular/components/AufgabenFormularFooter/AufgabenFormularFooter.tsx',
);
jest.mock('@aufgabenFormular/formwrapper/FormWrapper.tsx');

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
      const { asFragment } = render(
        <AufgabenFormular
          aufgabe={defaultAufgabe}
          onAufgabeCompleted={jest.fn()}
        />,
      );

      expect(asFragment()).toMatchSnapshot();
    });

    it('should render header, footer and FormWrapper when assignee exists', () => {
      const aufgabeMitBearbeiter = { ...defaultAufgabe, bearbeiter: 'admin' };

      const { asFragment } = render(
        <AufgabenFormular
          aufgabe={aufgabeMitBearbeiter}
          onAufgabeCompleted={jest.fn()}
        />,
      );

      expect(asFragment()).toMatchSnapshot();
    });
  });

  describe('Event handling', () => {
    it('should call uebernehmen.mutate when no assignee exists', () => {
      const { getByTestId } = render(
        <AufgabenFormular
          aufgabe={defaultAufgabe}
          onAufgabeCompleted={jest.fn()}
        />,
      );

      fireEvent.click(getByTestId('header-button'));

      expect(mockUebernehmen.mutate).toHaveBeenCalledWith(
        'admin',
        expect.objectContaining({
          onSuccess: expect.any(Function),
        }),
      );
    });

    it('should call abgeben.mutate when assignee exists', () => {
      const aufgabeMitBearbeiter = { ...defaultAufgabe, bearbeiter: 'admin' };

      const { getByTestId } = render(
        <AufgabenFormular
          aufgabe={aufgabeMitBearbeiter}
          onAufgabeCompleted={jest.fn()}
        />,
      );

      fireEvent.click(getByTestId('header-button'));

      expect(mockAbgeben.mutate).toHaveBeenCalledWith(
        undefined,
        expect.objectContaining({
          onSuccess: expect.any(Function),
        }),
      );
    });

    it('should call abschliessenMitVariablen.mutate on submit', () => {
      const onCompleted = jest.fn();
      const aufgabeMitBearbeiter = { ...defaultAufgabe, bearbeiter: 'admin' };

      const { container } = render(
        <AufgabenFormular
          aufgabe={aufgabeMitBearbeiter}
          onAufgabeCompleted={onCompleted}
        />,
      );

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
