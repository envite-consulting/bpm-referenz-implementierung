import { useAufgabeUpdate } from '@aufgabenliste/components/AufgabenFormular/queries/useAufgabeUpdate.ts';
import type { Aufgabe } from '@aufgabenliste/Aufgabe.types.ts';

import * as React from 'react';
import { Badge } from '@ui/Badge/Badge.tsx';
import { AufgabenFormularHeader } from '@aufgabenFormular/components/AufgabenFormularHeader/AufgabenFormularHeader.tsx';
import { AufgabenFormularFooter } from '@aufgabenFormular/components/AufgabenFormularFooter/AufgabenFormularFooter.tsx';
import { FormWrapper } from '@aufgabenFormular/formwrapper/FormWrapper.tsx';

type Props = {
  aufgabe: Aufgabe;
  onAufgabeCompleted: () => void;
};

export function AufgabenFormular({ aufgabe, onAufgabeCompleted }: Props) {
  const { uebernehmen, abgeben, abschliessenMitVariablen } = useAufgabeUpdate(
    aufgabe.id,
  );

  // TODO: später aus Auth-Kontext holen
  const currentUser = 'admin';

  const [bearbeiter, setBearbeiter] = React.useState<string | null>(
    aufgabe.bearbeiter ?? null,
  );

  const onAssignUser = () => {
    if (bearbeiter) {
      abgeben.mutate(undefined, {
        onSuccess: () => setBearbeiter(null),
      });
    } else {
      uebernehmen.mutate(currentUser, {
        onSuccess: () => setBearbeiter(currentUser),
      });
    }
  };

  return (
    <form
      className='p-6 bg-white shadow-md border border-gray-200 space-y-6'
      onSubmit={(e) => {
        e.preventDefault();
        abschliessenMitVariablen.mutate(
          {},
          { onSuccess: () => onAufgabeCompleted() },
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
