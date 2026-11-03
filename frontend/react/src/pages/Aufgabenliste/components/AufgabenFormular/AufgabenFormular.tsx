import { useAufgabeUpdate } from '@aufgabenliste/components/AufgabenFormular/queries/useAufgabeUpdate.ts';
import { useNavigate, useParams } from 'react-router-dom';

import { Badge } from '@ui/Badge/Badge.tsx';
import { AufgabenFormularHeader } from '@aufgabenFormular/components/AufgabenFormularHeader/AufgabenFormularHeader.tsx';
import { AufgabenFormularFooter } from '@aufgabenFormular/components/AufgabenFormularFooter/AufgabenFormularFooter.tsx';
import { FormWrapper } from '@aufgabenFormular/formwrapper/FormWrapper.tsx';
import { useAufgabeQuery } from '@aufgabenFormular/queries/useAufgabeQuery.ts';
import { useFolgeaufgabeVorschlag } from '@aufgabenFormular/queries/useFolgeaufgabeVorschlag.ts';
import { LoadingSpin } from '@ui/LoadingSpin/LoadingSpin.tsx';

export function AufgabenFormular() {
  const { id } = useParams<{ id: string }>();

  const navigate = useNavigate();
  const { aufgabe, isLoading, isError } = useAufgabeQuery(id!);
  const { uebernehmen, abgeben, abschliessenMitVariablen } = useAufgabeUpdate(
    id!,
  );

  const folgeaufgabeVorschlag = useFolgeaufgabeVorschlag();

  if (folgeaufgabeVorschlag.isPending) {
    return (
      <div className='p-6 space-y-4'>
        <LoadingSpin />
        <Badge label='Nächste Aufgabe wird ermittelt' type='info' />
      </div>
    );
  }

  if (isLoading) return <LoadingSpin />;
  if (isError || !aufgabe)
    return <Badge label='Fehler beim Laden der Aufgabe' type='danger' />;

  // TODO: später aus Auth-Kontext holen
  const currentUser = 'admin';
  const bearbeiter = aufgabe.bearbeiter ?? null;

  const zurFolgeaufgabe = () => {
    folgeaufgabeVorschlag.mutate(
      {
        vorgangId: aufgabe.vorgangId,
        letztesArbeitspaket: aufgabe.arbeitspaket,
      },
      {
        onSuccess: (vorschlag) => {
          if (vorschlag) {
            navigate(`/aufgabenliste/aufgabe/${vorschlag.aufgabeId}`);
          } else {
            navigate('/aufgabenliste', {
              state: {
                hinweis: {
                  label: 'Keine weitere Aufgabe zu diesem Vorgang',
                  type: 'info',
                },
              },
            });
          }
        },
        onError: () => {
          navigate('/aufgabenliste', {
            state: {
              hinweis: {
                label: 'Nächste Aufgabe konnte nicht ermittelt werden',
                type: 'danger',
              },
            },
          });
        },
      },
    );
  };

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
        abschliessenMitVariablen.mutate({}, { onSuccess: zurFolgeaufgabe });
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
