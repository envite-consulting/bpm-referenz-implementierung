import { useMutation, useQueryClient } from '@tanstack/react-query';
import {
  abgeben,
  abschliessenMitVariablen,
  uebernehmen,
} from '@aufgabenliste/components/AufgabenFormular/queries/api/updateAufgabe.ts';
import { QUERY_KEYS as QUERY_KEYS_VORGANG } from '@aufgabenSidebar/queries/useVorganglisteQuery.ts';
import { QUERY_KEYS as QUERY_KEYS_AUFGABE } from '@aufgabenFormular/queries/useAufgabeQuery.ts';

export function useAufgabeUpdate(aufgabenId: string) {
  const queryClient = useQueryClient();

  const uebernehmenMutation = useMutation({
    mutationFn: (userId: string) => uebernehmen(aufgabenId, userId),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: QUERY_KEYS_VORGANG.vorgangListe,
      });
      queryClient.invalidateQueries({
        queryKey: QUERY_KEYS_AUFGABE.aufgabe(aufgabenId),
      });
    },
  });

  const abgebenMutation = useMutation({
    mutationFn: () => abgeben(aufgabenId),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: QUERY_KEYS_VORGANG.vorgangListe,
      });
      queryClient.invalidateQueries({
        queryKey: QUERY_KEYS_AUFGABE.aufgabe(aufgabenId),
      });
    },
  });

  const abschliessenMutation = useMutation({
    mutationFn: (variablen: Record<string, unknown> = {}) =>
      abschliessenMitVariablen(aufgabenId, variablen),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: QUERY_KEYS_VORGANG.vorgangListe,
      });
      queryClient.invalidateQueries({
        queryKey: QUERY_KEYS_AUFGABE.aufgabe(aufgabenId),
      });
    },
  });

  return {
    uebernehmen: uebernehmenMutation,
    abgeben: abgebenMutation,
    abschliessenMitVariablen: abschliessenMutation,
  };
}
