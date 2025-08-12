import { render } from '@testing-library/react';
import { DropdownMenu, type DropdownOption } from './DropdownMenu.tsx';

describe('DropdownMenu', () => {
  const defaultOptions: DropdownOption<string>[] = [
    { label: 'Option 1', value: 'option1' },
    { label: 'Option 2', value: 'option2' },
    { label: 'Option 3', value: 'option3' },
  ];

  describe('Rendering', () => {
    it('should render with required and default props', () => {
      const { container } = render(
        <DropdownMenu options={defaultOptions} onChange={jest.fn()} />,
      );

      const kolSelect = container.querySelector(
        'kol-select',
      ) as HTMLKolSelectElement;

      expect(kolSelect).toBeInTheDocument();

      expect(kolSelect).toHaveAttribute('_label', '');

      const options = kolSelect?.getAttribute('_options');
      expect(options).toContain('Bitte auswählen...');
      expect(options).toContain('Option 1');
      expect(options).toContain('Option 2');
      expect(options).toContain('Option 3');

      expect(kolSelect).not.toHaveAttribute('_required');
    });

    it('should render with optional props', () => {
      const { container } = render(
        <DropdownMenu
          options={defaultOptions}
          onChange={jest.fn()}
          required={true}
          placeholderText={'Placeholder Test'}
          label={'Label Test'}
        />,
      );

      const kolSelect = container.querySelector(
        'kol-select',
      ) as HTMLKolSelectElement;

      expect(kolSelect).toBeInTheDocument();

      expect(kolSelect).toHaveAttribute('_label', 'Label Test');
      expect(kolSelect).toHaveAttribute('_required');
      expect(kolSelect?.getAttribute('_options')).toContain('Placeholder Test');
    });
  });

  describe('Options handling', () => {
    it('should include placeholder as first option', () => {
      const { container } = render(
        <DropdownMenu options={defaultOptions} onChange={jest.fn()} />,
      );

      const kolSelect = container.querySelector(
        'kol-select',
      ) as HTMLKolSelectElement;

      const optionsAttribute = kolSelect?.getAttribute('_options');
      const options = JSON.parse(optionsAttribute || '[]');

      expect(options).toHaveLength(defaultOptions.length + 1);
      expect(options[0]).toEqual({
        label: 'Bitte auswählen...',
        value: '',
      });
    });

    it('should include all provided options after placeholder', () => {
      const { container } = render(
        <DropdownMenu options={defaultOptions} onChange={jest.fn()} />,
      );

      const kolSelect = container.querySelector(
        'kol-select',
      ) as HTMLKolSelectElement;

      const optionsAttribute = kolSelect.getAttribute('_options');
      const options = JSON.parse(optionsAttribute || '[]');

      defaultOptions.forEach((option, index) => {
        expect(options[index + 1]).toEqual(option);
      });
    });

    it('should handle empty options array', () => {
      const { container } = render(
        <DropdownMenu options={[]} onChange={jest.fn()} />,
      );

      const kolSelect = container.querySelector(
        'kol-select',
      ) as HTMLKolSelectElement;

      const optionsAttr = kolSelect?.getAttribute('_options');
      const options = JSON.parse(optionsAttr || '[]');

      expect(options).toHaveLength(1);
      expect(options[0]).toEqual({
        label: 'Bitte auswählen...',
        value: '',
      });
    });
  });

  describe('Event handling', () => {
    describe('DropdownMenu handleChange', () => {
      it('should call onChange when a value is selected', () => {
        const onChangeMock = jest.fn();

        const { container } = render(
          <DropdownMenu
            options={[
              { label: 'Option A', value: 'a' },
              { label: 'Option B', value: 'b' },
            ]}
            onChange={onChangeMock}
          />,
        );

        const kolSelect = container.querySelector(
          'kol-select',
        ) as HTMLKolSelectElement;

        kolSelect._on?.onChange?.(new Event('change'), 'b');

        expect(onChangeMock).toHaveBeenCalledWith('b');
      });

      it('should not call onChange when value is not a string', () => {
        const onChangeMock = jest.fn();

        const { container } = render(
          <DropdownMenu
            options={[{ label: 'Option 1', value: 123 }]}
            onChange={onChangeMock}
          />,
        );

        const kolSelect = container.querySelector(
          'kol-select',
        ) as HTMLKolSelectElement;

        kolSelect._on?.onChange?.(new Event('change'), 123);

        expect(onChangeMock).not.toHaveBeenCalled();
      });

      it('should handle empty string selection', () => {
        const onChangeMock = jest.fn();

        const { container } = render(
          <DropdownMenu
            options={[
              { label: 'Option A', value: 'a' },
              { label: 'Option B', value: 'b' },
            ]}
            onChange={onChangeMock}
          />,
        );

        const kolSelect = container.querySelector(
          'kol-select',
        ) as HTMLKolSelectElement;

        kolSelect._on?.onChange?.(new Event('change'), '');

        expect(onChangeMock).toHaveBeenCalledWith('');
      });
    });
  });
});
