import { useQuery } from '@tanstack/react-query';
import { getAufgabe } from '@aufgabenFormular/queries/api/fetchAufgabe.ts';
import type { Aufgabe } from '@aufgabenliste/Aufgabe.types.ts';

export const QUERY_KEYS = {
  aufgabe: (id: string) => ['aufgabe', id] as const,
} as const;

export function useAufgabeQuery(id: string) {
  const query = useQuery<Aufgabe, unknown>({
    queryKey: QUERY_KEYS.aufgabe(id),
    queryFn: () => getAufgabe(id),
  });
  return {
    ...query,
    aufgabe: query.data,
  };
}
