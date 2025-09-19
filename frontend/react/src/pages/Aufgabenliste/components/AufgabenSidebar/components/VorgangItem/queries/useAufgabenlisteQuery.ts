import { useQuery } from '@tanstack/react-query';
import { getAufgabenlisteByVorgang } from '@aufgabenSidebar/components/VorgangItem/queries/api/fetchAufgabenliste.ts';

export const QUERY_KEYS = {
  aufgabenListe: ['aufgaben-liste'] as const,
} as const;

export function useAufgabenlisteQuery(vorgangId: string) {
  const query = useQuery({
    queryKey: [QUERY_KEYS.aufgabenListe, vorgangId],
    queryFn: () => getAufgabenlisteByVorgang(vorgangId),
  });
  return {
    ...query,
    aufgaben: query.data ?? [],
  };
}
