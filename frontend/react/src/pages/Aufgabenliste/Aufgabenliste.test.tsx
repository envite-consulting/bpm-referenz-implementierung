import { render } from '@testing-library/react';
import { Aufgabenliste } from './Aufgabenliste.tsx';
import React from 'react';

jest.mock('@aufgabenSidebar/AufgabenSidebar.tsx');

jest.mock('@aufgabenFormular/AufgabenFormular.tsx');

jest.mock('@ui/Badge/Badge.tsx');

describe('Aufgabenliste', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe('Rendering', () => {
    it('should render badge when no Aufgabe is selected', () => {
      const { asFragment } = render(<Aufgabenliste />);

      expect(asFragment()).toMatchSnapshot();
    });

    it('should render AufgabenFormular when an Aufgabe is selected', () => {
      const mockAufgabe = {
        id: '1',
        name: 'Test Aufgabe',
        bearbeiter: null,
        erstelldatum: new Date(),
        formularreferenz: 'X',
      };

      const mockSetSelectedAufgabe = jest.fn();

      jest
        .spyOn(React, 'useState')
        .mockImplementationOnce(() => [mockAufgabe, mockSetSelectedAufgabe]);

      const { asFragment } = render(<Aufgabenliste />);

      expect(asFragment()).toMatchSnapshot();
    });
  });
});
