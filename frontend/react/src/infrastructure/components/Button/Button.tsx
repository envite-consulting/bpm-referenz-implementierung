import { KolButton } from '@public-ui/react';

export type ButtonType = 'submit' | 'button' | 'reset';

type ButtonProps = {
  label: string;
  type: ButtonType;
};

export function Button({ label, type }: ButtonProps) {
  const baseStyles =
    'rounded-xl shadow-lg transition duration-300 hover:shadow-xl';

  const variantStyles: Record<ButtonType, string> = {
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
