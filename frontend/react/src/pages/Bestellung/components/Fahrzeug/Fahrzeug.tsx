import { DropdownMenu } from '@ui/DropDownMenu/DropdownMenu.tsx';
import { useFahrzeugQuery } from '@fahrzeug/queries/useFahrzeugQuery.ts';
import { QueryBoundary } from '@ui/QueryBoundary';

type FahrzeugauflistungProps = {
  onSelectId: (id: string) => void;
};

export function Fahrzeug({ onSelectId }: Readonly<FahrzeugauflistungProps>) {
  const queryResult = useFahrzeugQuery();

  const handleChange = (value: string) => {
    onSelectId(value);
  };

  return (
    <QueryBoundary queryResult={queryResult}>
      <DropdownMenu
        required={true}
        options={queryResult.getData()}
        onChange={handleChange}
        label='Auswahl Fahrzeug'
      />
    </QueryBoundary>
  );
}
