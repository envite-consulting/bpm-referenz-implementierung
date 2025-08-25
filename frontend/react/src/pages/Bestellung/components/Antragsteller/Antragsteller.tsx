import { DropdownMenu } from '@ui/DropDownMenu/DropdownMenu.tsx';
import { useAntragstellerQuery } from '@antragsteller/queries/useAntragstellerQuery.ts';
import { LoadingSpin } from '@ui/LoadingSpin/LoadingSpin.tsx';
import { Badge } from '@ui/Badge/Badge.tsx';

type AntragstellerauflistungProps = {
  onSelectId: (id: string) => void;
};

export function Antragsteller({ onSelectId }: AntragstellerauflistungProps) {
  const { antragstellerOptions, isLoading, error, isError } =
    useAntragstellerQuery();

  if (isLoading) return <LoadingSpin />;
  if (isError) return <Badge label={error.message} type={'warning'} />;

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
