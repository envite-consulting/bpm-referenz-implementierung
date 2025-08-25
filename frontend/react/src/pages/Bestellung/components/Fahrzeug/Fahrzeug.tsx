import { DropdownMenu } from '@ui/DropDownMenu/DropdownMenu.tsx';
import { useFahrzeugQuery } from '@fahrzeug/queries/useFahrzeugQuery.ts';
import { Badge } from '@ui/Badge/Badge.tsx';
import { LoadingSpin } from '@ui/LoadingSpin/LoadingSpin.tsx';

type FahrzeugauflistungProps = {
  onSelectId: (id: string) => void;
};

export function Fahrzeug({ onSelectId }: FahrzeugauflistungProps) {
  const { fahrzeugOptions, isLoading, error, isError } = useFahrzeugQuery();

  if (isLoading) return <LoadingSpin />;
  if (isError) return <Badge label={error.message} type={'warning'} />;

  const handleChange = (value: string) => {
    onSelectId(value);
  };

  return (
    <DropdownMenu
      required={true}
      options={fahrzeugOptions}
      onChange={handleChange}
      label='Auswahl Fahrzeug'
    />
  );
}
