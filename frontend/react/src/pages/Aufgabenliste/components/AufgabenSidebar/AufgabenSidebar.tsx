import { useVorganglisteQuery } from '@aufgabenSidebar/queries/useVorganglisteQuery.ts';
import { LoadingSpin } from '@ui/LoadingSpin/LoadingSpin.tsx';
import { VorgangItem } from '@vorgangItem/VorgangItem.tsx';

export function AufgabenSidebar() {
  const { vorgaenge, isLoading, isError } = useVorganglisteQuery();

  return (
    <div className='flex flex-col h-full border-r border-gray-200'>
      <h2 className='p-4 text-xl font-bold'>Aufgabenliste</h2>

      <div className='overflow-y-auto'>
        {isLoading && <LoadingSpin />}
        {isError && <div className='p-4'>Fehler beim Laden</div>}

        {vorgaenge.map((vorgang) => (
          <VorgangItem key={vorgang.fachlicherSchluessel} vorgang={vorgang} />
        ))}
      </div>
    </div>
  );
}
