import type { Aufgabe } from '@aufgabenliste/Aufgabe.types.ts';
import { Badge, type BadgeType } from '@ui/Badge/Badge.tsx';

export type AufgabeProps = {
  aufgabe: Aufgabe;
  selected?: boolean;
  onClick?: (id: string) => void;
};

// Camunda vergibt standardmäßig die Priorität 50. Höhere Zahl = wichtiger.
const STANDARD_PRIORITAET = 50;

function prioritaetsStufe(prioritaet: number): {
  type: BadgeType;
  titel: string;
} {
  if (prioritaet > STANDARD_PRIORITAET) {
    return { type: 'danger', titel: 'Hohe Priorität' };
  }
  if (prioritaet < STANDARD_PRIORITAET) {
    return { type: 'success', titel: 'Niedrige Priorität' };
  }
  return { type: 'info', titel: 'Normale Priorität' };
}

function PrioritaetBadge({ prioritaet }: { prioritaet: number }) {
  const { type, titel } = prioritaetsStufe(prioritaet);
  return (
    <span className='shrink-0' title={`${titel} (${prioritaet})`}>
      <Badge label={String(prioritaet)} type={type} />
    </span>
  );
}

export function AufgabenItem({ aufgabe, selected, onClick }: AufgabeProps) {
  const { id, name, bearbeiter, erstelldatum, prioritaet } = aufgabe;
  return (
    <div
      role='button'
      onClick={() => onClick?.(id)}
      className={`p-3 cursor-pointer border-b border-gray-200 hover:bg-gray-50 ${
        selected ? 'bg-gray-100' : ''
      }`}
      data-testid='aufgabe-item'
    >
      <div className='flex items-start justify-between gap-2'>
        <div className='font-medium'>{name}</div>
        {prioritaet !== undefined && (
          <PrioritaetBadge prioritaet={prioritaet} />
        )}
      </div>
      <div className='text-sm text-gray-600'>
        {bearbeiter ?? 'Nicht zugewiesen'} ·{' '}
        {new Date(erstelldatum).toLocaleDateString('de-DE')}
      </div>
    </div>
  );
}
