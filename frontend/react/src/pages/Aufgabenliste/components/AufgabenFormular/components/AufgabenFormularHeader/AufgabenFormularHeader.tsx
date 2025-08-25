import { Button } from '@ui/Button/Button.tsx';
import { Badge } from '@ui/Badge/Badge.tsx';

type Props = {
  titel: string;
  bearbeiter: string | null;
  isErrorAssigneeChange: boolean;
  isLoading: boolean;
  onAssignUser: () => void;
};

export function AufgabenFormularHeader({
  titel,
  bearbeiter,
  isErrorAssigneeChange,
  isLoading,
  onAssignUser,
}: Props) {
  return (
    <div className='flex items-center justify-between border-b border-gray-200 pb-4'>
      <h3 className='text-xl font-semibold text-gray-800'>{titel}</h3>

      <Button
        label={bearbeiter ?? 'Übernehmen'}
        type='button'
        disabled={isLoading}
        onClick={onAssignUser}
      />
      {isErrorAssigneeChange && (
        <Badge
          label={'Fehler beim Übernehmen oder Abgeben der Aufgabe'}
          type={'danger'}
        />
      )}
    </div>
  );
}
