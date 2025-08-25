import { render } from '@testing-library/react';
import { Button, type ButtonType } from './Button.tsx';

describe('Button', () => {
  describe('Rendering', () => {
    const baseStyles =
      'px-3 rounded-xl shadow-lg transition duration-300 hover:shadow-xl';
    const submitStyles = 'bg-emerald-600 hover:bg-emerald-500';

    it.each([
      ['button', `${baseStyles} `],
      ['submit', `${baseStyles} ${submitStyles}`],
      ['reset', `${baseStyles} `],
    ])('should render with type=%s', (typeValue, expectedClass) => {
      const { container } = render(
        <Button label='Click me' type={typeValue as ButtonType} />,
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

  describe('Event handling', () => {
    it('should call onClick when handler is triggered', () => {
      const onClickMock = jest.fn();
      const { container } = render(
        <Button label='Click me' type='button' onClick={onClickMock} />,
      );

      const kolButton = container.querySelector(
        'kol-button',
      ) as HTMLKolButtonElement;

      kolButton._on?.onClick?.(new MouseEvent('click'), '');

      expect(onClickMock).toHaveBeenCalledTimes(1);
    });

    it('should not call onClick when disabled', () => {
      const onClickMock = jest.fn();
      const { container } = render(
        <Button
          label='Click me'
          type='button'
          onClick={onClickMock}
          disabled
        />,
      );

      const kolButton = container.querySelector(
        'kol-button',
      ) as HTMLKolButtonElement;

      kolButton._on?.onClick?.(new MouseEvent('click'), '');

      expect(onClickMock).not.toHaveBeenCalled();
    });
  });
});
