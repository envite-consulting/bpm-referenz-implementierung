import { render } from '@testing-library/react';
import { Aufgabenliste } from './Aufgabenliste.tsx';
import { useLocation as useLocationOriginal } from 'react-router-dom';

jest.mock('@aufgabenSidebar/AufgabenSidebar.tsx');

jest.mock('@aufgabenFormular/AufgabenFormular.tsx');

jest.mock('@ui/Badge/Badge.tsx');

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useLocation: jest.fn(),
}));

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useLocation: jest.fn(),
  Outlet: () => <div data-testid='outlet-mock'>Outlet Content</div>,
}));

const useLocation = jest.mocked(useLocationOriginal);

describe('Aufgabenliste', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe('Rendering', () => {
    it('should render badge when no Aufgabe is selected', () => {
      useLocation.mockReturnValue({ pathname: '/aufgabenliste' } as never);

      const { asFragment } = render(<Aufgabenliste />);

      expect(asFragment()).toMatchSnapshot();
    });

    it('should render AufgabenFormular when an Aufgabe is selected', () => {
      useLocation.mockReturnValue({
        pathname: '/aufgabenliste/aufgabe/123',
      } as never);

      const { asFragment } = render(<Aufgabenliste />);

      expect(asFragment()).toMatchSnapshot();
    });
  });
});
