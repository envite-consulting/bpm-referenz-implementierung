import { useMutation, useQueryClient } from '@tanstack/react-query';
import {
  abgeben,
  abschliessenMitVariablen,
  uebernehmen,
} from '@aufgabenliste/components/AufgabenFormular/queries/api/updateAufgabe.ts';
import { QUERY_KEYS } from '@aufgabenliste/queries/useAufgabenlisteQuery.ts';

export function useAufgabeUpdate(aufgabenId: string) {
  const queryClient = useQueryClient();

  const uebernehmenMutation = useMutation({
    mutationFn: (userId: string) => uebernehmen(aufgabenId, userId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: QUERY_KEYS.aufgabenListe });
    },
  });

  const abgebenMutation = useMutation({
    mutationFn: () => abgeben(aufgabenId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: QUERY_KEYS.aufgabenListe });
    },
  });

  const abschliessenMutation = useMutation({
    mutationFn: (variablen: Record<string, unknown> = {}) =>
      abschliessenMitVariablen(aufgabenId, variablen),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: QUERY_KEYS.aufgabenListe });
    },
  });

  return {
    uebernehmen: uebernehmenMutation,
    abgeben: abgebenMutation,
    abschliessenMitVariablen: abschliessenMutation,
  };
}
