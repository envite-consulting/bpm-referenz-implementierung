import { DropdownMenu } from '@ui/DropDownMenu/DropdownMenu.tsx';
import { useAntragstellerQuery } from '@antragsteller/queries/useAntragstellerQuery.ts';

type AntragstellerauflistungProps = {
  onSelectId: (id: string) => void;
};

export function Antragsteller({ onSelectId }: AntragstellerauflistungProps) {
  const { antragstellerOptions, isLoading, error, isError } =
    useAntragstellerQuery();

  if (isLoading) return <p>Loading...</p>;
  if (isError) return <p>Error: {error.message}</p>;

  const handleChange = (value: string) => {
    onSelectId(value);
  };

  return (
    <DropdownMenu
      required={true}
      options={antragstellerOptions}
      onChange={handleChange}
      label='Auswahl Antragsteller'
    />
  );
}
