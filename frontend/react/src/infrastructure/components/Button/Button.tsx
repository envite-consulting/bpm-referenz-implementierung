import { KolButton } from '@public-ui/react';

export type ButtonType = 'submit' | 'button' | 'reset';

type ButtonProps = {
  label: string;
  type: ButtonType;
  onClick?: () => void;
  disabled?: boolean;
};

export function Button({
  label,
  type,
  onClick,
  disabled = false,
}: ButtonProps) {
  const baseStyles =
    'px-3 rounded-xl shadow-lg transition duration-300 hover:shadow-xl';

  const variantStyles: Record<ButtonType, string> = {
    submit: 'bg-emerald-600 hover:bg-emerald-500',
    button: '',
    reset: '',
  };

  const handleClick = () => {
    if (onClick && !disabled) {
      onClick();
    }
  };

  return (
    <KolButton
      _label={label}
      _type={type}
      _variant='custom'
      _disabled={disabled}
      _on={{ onClick: handleClick }}
      className={`${baseStyles} ${variantStyles[type]}`}
    />
  );
}
