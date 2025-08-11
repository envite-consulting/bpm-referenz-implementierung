import { render } from '@testing-library/react';
import { SubmitButton } from '@/infrastructure/components/SubmitButton/SubmitButton.tsx';

describe('SubmitButton', () => {
  describe('Rendering', () => {
    it('should render with required props', () => {
      render(<SubmitButton label='Click me' />);

      const buttonElement = document.querySelector('kol-button');

      expect(buttonElement).toBeInTheDocument();
      expect(buttonElement).toHaveAttribute('_label', 'Click me');
      expect(buttonElement).toHaveAttribute('_type', 'submit');
    });
  });
});
