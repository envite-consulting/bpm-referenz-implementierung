import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { AppRoutes } from './AppRoutes';

jest.mock('@bestellung/Bestellung.tsx');
jest.mock('@aufgabenliste/Aufgabenliste.tsx');
jest.mock('@aufgabenFormular/AufgabenFormular.tsx');

describe('AppRoutes', () => {
  it('should render Bestellung when navigating to /bestellung', () => {
    render(
      <MemoryRouter initialEntries={['/bestellung']}>
        <AppRoutes />
      </MemoryRouter>,
    );

    expect(screen.getByTestId('bestellung-mock')).toBeInTheDocument();
  });

  it('should render Aufgabenliste when navigating to /aufgabenliste', () => {
    render(
      <MemoryRouter initialEntries={['/aufgabenliste']}>
        <AppRoutes />
      </MemoryRouter>,
    );

    expect(screen.getByTestId('aufgabenliste-mock')).toBeInTheDocument();
  });

  it('should render AufgabenFormular when navigating to /aufgabenliste/aufgabe/42', () => {
    render(
      <MemoryRouter initialEntries={['/aufgabenliste/aufgabe/42']}>
        <AppRoutes />
      </MemoryRouter>,
    );

    expect(screen.getByTestId('aufgaben-formular-mock')).toBeInTheDocument();
  });

  it('should fallback to Aufgabenliste for unknown route', () => {
    render(
      <MemoryRouter initialEntries={['/invalid']}>
        <AppRoutes />
      </MemoryRouter>,
    );

    expect(screen.getByTestId('aufgabenliste-mock')).toBeInTheDocument();
  });
});
