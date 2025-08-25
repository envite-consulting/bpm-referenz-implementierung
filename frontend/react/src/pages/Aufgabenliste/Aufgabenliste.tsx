import { useState } from 'react';
import { Badge } from '@ui/Badge/Badge.tsx';
import { AufgabenFormular } from '@aufgabenFormular/AufgabenFormular.tsx';
import { AufgabenSidebar } from '@aufgabenSidebar/AufgabenSidebar.tsx';
import type { Aufgabe } from '@aufgabenliste/Aufgabe.types.ts';

export function Aufgabenliste() {
  const [selectedAufgabe, setSelectedAufgabe] = useState<Aufgabe | null>(null);

  const onAufgabeCompleted = () => setSelectedAufgabe(null);

  return (
    <div className='flex h-full'>
      <div className='w-1/3'>
        <AufgabenSidebar
          selected={selectedAufgabe}
          setSelected={setSelectedAufgabe}
        />
      </div>

      <div className='flex-1 h-full overflow-auto'>
        {selectedAufgabe ? (
          <AufgabenFormular
            aufgabe={selectedAufgabe}
            onAufgabeCompleted={onAufgabeCompleted}
          />
        ) : (
          <Badge label='Wähle eine Aufgabe aus' type='info' />
        )}
      </div>
    </div>
  );
}
