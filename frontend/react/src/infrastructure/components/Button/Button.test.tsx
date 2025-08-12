import { render } from '@testing-library/react';
import { Button } from './Button.tsx';

describe('Button', () => {
  describe('Rendering', () => {
    const baseStyles =
      'rounded-xl shadow-lg transition duration-300 hover:shadow-xl';
    const submitStyles = `w-full bg-emerald-600 hover:bg-emerald-500`;

    it.each([
      ['button', `${baseStyles} `],
      ['submit', `${baseStyles} ${submitStyles}`],
      ['reset', `${baseStyles} `],
    ])('should render with type=%s', (typeValue, expectedClass) => {
      render(<Button label='Click me' type={typeValue as any} />);
      const buttonElement = document.querySelector('kol-button');

      expect(buttonElement).toBeInTheDocument();
      expect(buttonElement).toHaveAttribute('_label', 'Click me');
      expect(buttonElement).toHaveAttribute('_type', typeValue);
      expect(buttonElement).toHaveAttribute('class', expectedClass);
    });
  });
});
