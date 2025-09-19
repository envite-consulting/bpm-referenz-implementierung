import { render } from '@testing-library/react';
import { AufgabenItem } from './AufgabenItem.tsx';
import type { Aufgabe } from '@aufgabenliste/Aufgabe.types.ts';

describe('AufgabenItem', () => {
  const defaultAufgabe: Aufgabe = {
    id: '1',
    name: 'Testaufgabe',
    bearbeiter: null,
    erstelldatum: new Date('2024-01-01T12:00:00Z'),
    formularreferenz: 'formular-1',
  };

  it('should render default without assignee', () => {
    const { asFragment } = render(<AufgabenItem aufgabe={defaultAufgabe} />);

    expect(asFragment()).toMatchSnapshot();
  });

  it('should render assignee name with assignee', () => {
    const { asFragment } = render(
      <AufgabenItem
        aufgabe={{ ...defaultAufgabe, bearbeiter: 'Max' }}
        selected
      />,
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
