import { render } from '@testing-library/react';
import App from './App.tsx';

jest.mock('@ui/Header/Header.tsx');
jest.mock('@root/AppRoutes.tsx');

describe('Dashboard', () => {
  describe('Rendering', () => {
    it('should render Dasboard with Header', () => {
      const { asFragment } = render(<App />);
      expect(asFragment()).toMatchSnapshot();
    });
  });
});
