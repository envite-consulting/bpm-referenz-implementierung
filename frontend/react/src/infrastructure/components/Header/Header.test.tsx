import { fireEvent, render } from '@testing-library/react';
import { Header } from './Header.tsx';

describe('Header', () => {
  const navigateMock = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe('Rendering', () => {
    it('should render dashboard title and render paths correct', () => {
      const { asFragment } = render(
        <Header navigate={navigateMock} currentPath='/aufgabenliste' />,
      );

      expect(asFragment()).toMatchSnapshot();
    });
  });

  describe('Event handling', () => {
    it('should call navigate with /aufgabenliste when Aufgabenliste is clicked', () => {
      const { getByText } = render(
        <Header navigate={navigateMock} currentPath='/bestellung' />,
      );
      const link = getByText('Aufgabenliste');

      fireEvent.click(link);

      expect(navigateMock).toHaveBeenCalledWith('/aufgabenliste');
    });

    it('should call navigate with /bestellung when Bestellung is clicked', () => {
      const { getByText } = render(
        <Header navigate={navigateMock} currentPath='/aufgabenliste' />,
      );
      const link = getByText('Bestellung erstellen');

      fireEvent.click(link);

      expect(navigateMock).toHaveBeenCalledWith('/bestellung');
    });
  });
});
