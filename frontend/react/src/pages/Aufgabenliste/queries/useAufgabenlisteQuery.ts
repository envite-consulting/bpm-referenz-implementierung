import { useQuery } from '@tanstack/react-query';
import { getAufgabenliste } from '@aufgabenliste/queries/api/fetchAufgabenliste.ts';

export const QUERY_KEYS = {
  aufgabenListe: ['aufgaben-liste'] as const,
  aufgabe: (id: string) => ['aufgabe', id] as const,
} as const;

export function useAufgabenListeQuery() {
  const query = useQuery({
    queryKey: QUERY_KEYS.aufgabenListe,
    queryFn: getAufgabenliste,
  });
  return {
    ...query,
    aufgaben: query.data ?? [],
  };
}
