import { render } from '@testing-library/react';
import { Button } from './Button.tsx';
import type { ButtonTypePropType } from '@public-ui/components';

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
      const { container } = render(
        <Button label='Click me' type={typeValue as ButtonTypePropType} />,
      );

      const kolButton = container.querySelector(
        'kol-button',
      ) as HTMLKolButtonElement;

      expect(kolButton).toBeInTheDocument();
      expect(kolButton).toHaveAttribute('_label', 'Click me');
      expect(kolButton).toHaveAttribute('_type', typeValue);
      expect(kolButton).toHaveAttribute('class', expectedClass);
    });
  });
});
