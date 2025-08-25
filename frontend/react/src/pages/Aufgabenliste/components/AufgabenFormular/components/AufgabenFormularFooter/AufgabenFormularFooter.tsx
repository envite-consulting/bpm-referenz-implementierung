import { Button } from '@ui/Button/Button.tsx';
import { Badge } from '@ui/Badge/Badge.tsx';

type AufgabenFormularFooterProps = {
  isError: boolean;
  isLoading: boolean;
};

export function AufgabenFormularFooter({
  isError,
  isLoading,
}: AufgabenFormularFooterProps) {
  return (
    <div className='pt-2 flex flex-col items-start space-y-2'>
      <Button label={'Abschließen'} type='submit' disabled={isLoading} />
      {isError && (
        <Badge label='Fehler beim Abschließen der Aufgabe' type='danger' />
      )}
    </div>
  );
}
