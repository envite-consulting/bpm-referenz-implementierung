import { useAufgabenListeQuery } from '@aufgabenliste/queries/useAufgabenlisteQuery.ts';
import { AufgabenItem } from '@aufgabenSidebar/components/AufgabenItem/AufgabenItem.tsx';
import type { Aufgabe } from '@aufgabenliste/Aufgabe.types.ts';
import { LoadingSpin } from '@ui/LoadingSpin/LoadingSpin.tsx';

type AufgabenSidebarProps = {
  selected: Aufgabe | null;
  setSelected: (aufgabe: Aufgabe) => void;
};

export function AufgabenSidebar({
  selected,
  setSelected,
}: AufgabenSidebarProps) {
  const { aufgaben, isLoading, isError } = useAufgabenListeQuery();

  return (
    <div className='flex flex-col h-full border-r border-gray-200'>
      <h2 className='p-4 text-xl font-bold'>Aufgabenliste</h2>

      <div className='overflow-y-auto'>
        {isLoading && <LoadingSpin />}
        {isError && <div className='p-4'>Fehler beim Laden</div>}

        {aufgaben.map((a) => (
          <AufgabenItem
            key={a.id}
            aufgabe={a}
            selected={selected?.id === a.id}
            onClick={() => setSelected(a)}
          />
        ))}
      </div>
    </div>
  );
}
