import { KolSelect } from '@public-ui/react';

type DropdownProps<T> = {
  options: DropdownOption<T>[];
  onChange: (value: string) => void;
  required?: boolean;
  placeholderText?: string;
  label?: string;
};

export type DropdownOption<T> = { label: string; value: T };

export function DropdownMenu<T>({
  options,
  onChange,
  required = false,
  placeholderText = 'Bitte auswählen...',
  label = '',
}: DropdownProps<T>) {
  const placeholderOption: DropdownOption<string> = {
    label: placeholderText,
    value: '',
  };

  const optionsWithPlaceholder = [placeholderOption, ...options];

  const handleChange = (_event: Event, value: unknown) => {
    if (typeof value === 'string') {
      onChange(value);
    }
  };

  return (
    <div className='w-full'>
      <KolSelect
        class='w-full rounded-lg border border-gray-200 shadow-sm transition-all hover:border-gray-300 focus:border-emerald-500 focus:ring-2 focus:ring-emerald-100 box-border px-3 py-2'
        _label={label}
        _options={JSON.stringify(optionsWithPlaceholder)}
        _on={{ onChange: handleChange }}
        _required={required}
      />
    </div>
  );
}
