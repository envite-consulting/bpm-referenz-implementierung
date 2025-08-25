import { createBestellung } from '@bestellung/queries/api/createBestellung.ts';
import {
  type Bestellungserfassung,
  bestellungserfassungSchema,
} from '@bestellung/Bestellung.types.ts';
import { Button } from '@ui/Button/Button.tsx';
import { Antragsteller } from '@antragsteller/Antragsteller.tsx';
import { Controller, useForm } from 'react-hook-form';
import { Badge } from '@ui/Badge/Badge.tsx';

export function Bestellung() {
  const {
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    defaultValues: {
      antragstellerreferenz: '',
      fahrzeugreferenz: 'b6122856-f08a-4454-b5bd-a3d232065b91',
    },
  });

  const onSubmit = async (data: Bestellungserfassung) => {
    try {
      bestellungserfassungSchema.parse(data);

      await createBestellung({
        antragstellerreferenz: data.antragstellerreferenz,
        fahrzeugreferenz: data.fahrzeugreferenz,
      });

      alert('Bestellung erfolgreich erstellt!');

      reset();
    } catch (error) {
      console.error('Fehler beim Erstellen der Bestellung:', error);
      alert('Fehler beim Erstellen der Bestellung.');
    }
  };

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className='p-6 max-w-md mx-auto bg-white rounded-xl shadow-md flex flex-col items-center space-y-4'
    >
      <h2 className='text-xl font-bold'>Mitarbeiter Firmenwagen Formular</h2>

      <Controller
        name='antragstellerreferenz'
        control={control}
        rules={{ required: 'Mitarbeiter ist erforderlich' }}
        render={({ field }) => <Antragsteller onSelectId={field.onChange} />}
      />

      {errors.antragstellerreferenz && (
        <Badge
          type={'warning'}
          label={
            errors.antragstellerreferenz.message ??
            'Fehler bei der Auswahl des Mitarbeiters'
          }
        />
      )}
      )}

      <Button label={'Absenden'} type={'submit'} />
    </form>
  );
}
