import { createBestellung } from '@/pages/Bestellung/queries/api/createBestellung.ts';
import {
  type Bestellungserfassung,
  bestellungserfassungSchema,
} from '@/pages/Bestellung/Bestellung.types.ts';
import { SubmitButton } from '@/infrastructure/components/SubmitButton/SubmitButton.tsx';
import Antragsteller from '@/pages/Bestellung/components/Antragsteller/Antragsteller.tsx';
import { Controller, useForm } from 'react-hook-form';

export default function Bestellung() {
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
      className='p-6 max-w-md mx-auto bg-white rounded-xl shadow-md space-y-4'
    >
      <h2 className='text-xl font-bold'>Mitarbeiter Firmenwagen Formular</h2>

      <Controller
        name='antragstellerreferenz'
        control={control}
        rules={{ required: 'Mitarbeiter ist erforderlich' }}
        render={({ field }) => <Antragsteller onSelectId={field.onChange} />}
      />

      {errors.antragstellerreferenz && (
        <span className='text-red-500'>
          {errors.antragstellerreferenz.message}
        </span>
      )}

      <SubmitButton label={'Absenden'} />
    </form>
  );
}
