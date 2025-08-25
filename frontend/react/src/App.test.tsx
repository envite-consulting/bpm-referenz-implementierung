import { fireEvent, render, screen } from '@testing-library/react';
import App from './App.tsx';

jest.mock('@ui/Header/Header.tsx');
jest.mock('@aufgabenliste/Aufgabenliste.tsx');
jest.mock('@bestellung/Bestellung.tsx');

describe('Dashboard', () => {
  beforeEach(() => {
    window.history.pushState({}, '', '/aufgabenliste');
  });

  describe('Rendering', () => {
    it('should render Aufgabenliste as default route', () => {
      render(<App />);
      expect(screen.getByTestId('aufgabenliste-mock')).toBeInTheDocument();
    });

    it('should render Bestellung when path is /bestellung', () => {
      window.history.pushState({}, '', '/bestellung');
      render(<App />);
      expect(screen.getByTestId('bestellung-mock')).toBeInTheDocument();
    });
  });

  describe('Navigation', () => {
    it('should navigate to Bestellung when header button is clicked', () => {
      const { getByTestId } = render(<App />);

      const navigationButton = getByTestId('nav-bestellung');
      fireEvent.click(navigationButton);

      expect(getByTestId('bestellung-mock')).toBeInTheDocument();
    });

    it('should navigate back to Aufgabenliste when header button is clicked', () => {
      window.history.pushState({}, '', '/bestellung');
      const { getByTestId } = render(<App />);

      const navigationButton = getByTestId('nav-aufgabenliste');
      fireEvent.click(navigationButton);

      expect(getByTestId('aufgabenliste-mock')).toBeInTheDocument();
    });

    it('should not navigate if target path equals current path', () => {
      const { getByTestId } = render(<App />);

      const navigationButton = getByTestId('nav-aufgabenliste');
      fireEvent.click(navigationButton);

      expect(screen.getByTestId('aufgabenliste-mock')).toBeInTheDocument();
      expect(screen.getByTestId('header-mock')).toHaveTextContent(
        '/aufgabenliste',
      );
    });
  });
});
