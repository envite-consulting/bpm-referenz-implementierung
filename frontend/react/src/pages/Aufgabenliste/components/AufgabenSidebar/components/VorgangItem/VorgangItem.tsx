import type { Vorgang } from '@aufgabenSidebar/Vorgang.types.ts';
import { useState } from 'react';
import { LoadingSpin } from '@ui/LoadingSpin/LoadingSpin.tsx';
import { useNavigate } from 'react-router-dom';
import { AufgabenItem } from '@vorgangItem/components/AufgabenItem/AufgabenItem.tsx';
import { useAufgabenlisteQuery } from '@vorgangItem/queries/useAufgabenlisteQuery.ts';

export type VorgangProps = {
  vorgang: Vorgang;
};

export function VorgangItem({ vorgang }: VorgangProps) {
  const { fachdaten } = vorgang;
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const { aufgaben, isLoading, isError } = useAufgabenlisteQuery(
    vorgang.fachlicherSchluessel,
  );

  const handleVorgangSelected = () => {
    setOpen(!open);
  };

  const handleAufgabeSelected = (aufgabeId: string) => {
    navigate(`/aufgabenliste/aufgabe/${aufgabeId}`);
  };

  return (
    <div className='border-b border-gray-200'>
      <div
        role='button'
        onClick={handleVorgangSelected}
        className={`p-3 cursor-pointer hover:bg-gray-50`}
        data-testid='vorgang-item'
      >
        <div className='font-medium text-gray-900'>
          {`${fachdaten.antragstellerVorname} ${fachdaten.antragstellerNachname}`}
        </div>

        <div className='text-sm text-gray-500 mt-1'>
          {`${fachdaten.fahrzeugHersteller} ${fachdaten.fahrzeugModell}`}
        </div>
      </div>
      {open && (
        <div className='bg-gray-50 p-3 border-t border-gray-200'>
          {isLoading && <LoadingSpin />}
          {isError && <div className='p-4'>Fehler beim Laden</div>}
          {aufgaben.map((aufgabe) => (
            <AufgabenItem
              key={aufgabe.id}
              aufgabe={aufgabe}
              selected={false}
              onClick={() => handleAufgabeSelected(aufgabe.id)}
            />
          ))}
        </div>
      )}
    </div>
  );
}
