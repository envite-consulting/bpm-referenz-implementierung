import { useAufgabeUpdate } from '@aufgabenliste/components/AufgabenFormular/queries/useAufgabeUpdate.ts';
import { useNavigate, useParams } from 'react-router-dom';

import { Badge } from '@ui/Badge/Badge.tsx';
import { AufgabenFormularHeader } from '@aufgabenFormular/components/AufgabenFormularHeader/AufgabenFormularHeader.tsx';
import { AufgabenFormularFooter } from '@aufgabenFormular/components/AufgabenFormularFooter/AufgabenFormularFooter.tsx';
import { FormWrapper } from '@aufgabenFormular/formwrapper/FormWrapper.tsx';
import { useAufgabeQuery } from '@aufgabenFormular/queries/useAufgabeQuery.ts';
import { LoadingSpin } from '@ui/LoadingSpin/LoadingSpin.tsx';

export function AufgabenFormular() {
  const { id } = useParams<{ id: string }>();

  const navigate = useNavigate();
  const { aufgabe, isLoading, isError } = useAufgabeQuery(id!);
  const { uebernehmen, abgeben, abschliessenMitVariablen } = useAufgabeUpdate(
    id!,
  );

  if (isLoading) return <LoadingSpin />;
  if (isError || !aufgabe)
    return <Badge label='Fehler beim Laden der Aufgabe' type='danger' />;

  // TODO: später aus Auth-Kontext holen
  const currentUser = 'admin';
  const bearbeiter = aufgabe.bearbeiter ?? null;

  const onAssignUser = () => {
    if (bearbeiter) {
      abgeben.mutate(undefined);
    } else {
      uebernehmen.mutate(currentUser);
    }
  };

  return (
    <form
      className='p-6 bg-white shadow-md border border-gray-200 space-y-6'
      onSubmit={(e) => {
        e.preventDefault();
        abschliessenMitVariablen.mutate(
          {},
          { onSuccess: () => navigate('/aufgabenliste') },
        );
      }}
    >
      <AufgabenFormularHeader
        titel={aufgabe.name ?? aufgabe.formularreferenz}
        bearbeiter={bearbeiter}
        isErrorAssigneeChange={uebernehmen.isError || abgeben.isError}
        isLoading={uebernehmen.isPending || abgeben.isPending}
        onAssignUser={onAssignUser}
      />

      {bearbeiter === currentUser ? (
        <div className='py-4'>
          <FormWrapper
            key={aufgabe.id}
            formularreferenz={aufgabe.formularreferenz}
            taskId={aufgabe.id}
          />
          <AufgabenFormularFooter
            isLoading={abschliessenMitVariablen.isPending}
            isError={abschliessenMitVariablen.isError}
          />
        </div>
      ) : (
        <Badge
          label='Aufgabe übernehmen, um Formular bearbeiten zu können'
          type='warning'
        />
      )}
    </form>
  );
}
