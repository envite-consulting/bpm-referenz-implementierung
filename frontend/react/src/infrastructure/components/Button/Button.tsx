import { KolButton } from '@public-ui/react';
import type { ButtonTypePropType } from '@public-ui/components';

type ButtonProps = {
  label: string;
  type: ButtonTypePropType;
};

export function Button({ label, type }: ButtonProps) {
  const baseStyles =
    'rounded-xl shadow-lg transition duration-300 hover:shadow-xl';

  const variantStyles: Record<ButtonTypePropType, string> = {
    submit: 'w-full bg-emerald-600 hover:bg-emerald-500',
    button: '',
    reset: '',
  };

  return (
    <KolButton
      _label={label}
      _type={type}
      _variant='custom'
      className={`${baseStyles} ${variantStyles[type]}`}
    />
  );
}
