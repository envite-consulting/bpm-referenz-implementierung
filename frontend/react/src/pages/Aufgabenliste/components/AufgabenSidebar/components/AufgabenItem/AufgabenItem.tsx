import type { Aufgabe } from '@aufgabenliste/Aufgabe.types.ts';

export type AufgabeProps = {
  aufgabe: Aufgabe;
  selected?: boolean;
  onClick?: (id: string) => void;
};

export function AufgabenItem({ aufgabe, selected, onClick }: AufgabeProps) {
  const { id, name, bearbeiter, erstelldatum } = aufgabe;
  return (
    <div
      role='button'
      onClick={() => onClick?.(id)}
      className={`p-3 cursor-pointer border-b border-gray-200 hover:bg-gray-50 ${
        selected ? 'bg-gray-100' : ''
      }`}
      data-testid='aufgabe-item'
    >
      <div className='font-medium'>{name}</div>
      <div className='text-sm text-gray-600'>
        {bearbeiter ?? 'Nicht zugewiesen'} ·{' '}
        {new Date(erstelldatum).toLocaleDateString('de-DE')}
      </div>
    </div>
  );
}
