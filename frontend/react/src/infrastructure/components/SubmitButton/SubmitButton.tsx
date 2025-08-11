import { KolButton } from '@public-ui/react';

type SubmitButtonProps = {
  label: string;
};

export function SubmitButton({ label }: SubmitButtonProps) {
  return (
    <KolButton
      _label={label}
      _type='submit'
      _variant='custom'
      className='w-full rounded-xl bg-emerald-600 shadow-lg transition duration-300 hover:bg-emerald-500 hover:shadow-xl'
    />
  );
}
