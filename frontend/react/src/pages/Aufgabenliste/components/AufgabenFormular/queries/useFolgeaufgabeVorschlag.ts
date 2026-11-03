import { useMutation, useQueryClient } from '@tanstack/react-query';
import { getFolgeaufgabeVorschlag } from '@aufgabenFormular/queries/api/fetchFolgeaufgabeVorschlag.ts';
import { QUERY_KEYS as QUERY_KEYS_VORGANG } from '@aufgabenSidebar/queries/useVorganglisteQuery.ts';
import { QUERY_KEYS as QUERY_KEYS_AUFGABEN } from '@vorgangItem/queries/useAufgabenlisteQuery.ts';

type FolgeaufgabeVorschlagParameter = {
  vorgangId: string;
  letztesArbeitspaket?: string | null;
};

export function useFolgeaufgabeVorschlag() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      vorgangId,
      letztesArbeitspaket,
    }: FolgeaufgabeVorschlagParameter) =>
      getFolgeaufgabeVorschlag(vorgangId, letztesArbeitspaket),
    onSettled: () => {
      queryClient.invalidateQueries({
        queryKey: QUERY_KEYS_VORGANG.vorgangListe,
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS_AUFGABEN.aufgabenListe],
      });
    },
  });
}
