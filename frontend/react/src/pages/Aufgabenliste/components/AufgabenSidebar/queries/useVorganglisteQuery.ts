import { useQuery, type UseQueryResult } from '@tanstack/react-query';
import { getVorgangliste } from '@aufgabenSidebar/queries/api/fetchVorgangliste.ts';
import type { Vorgang } from '@aufgabenSidebar/Vorgang.types.ts';

export const QUERY_KEYS = {
  vorgangListe: ['vorgang-liste'] as const,
  vorgang: (id: string) => ['vorgang', id] as const,
} as const;

export type UseVorganglisteQueryResult = UseQueryResult<Vorgang[], unknown> & {
  vorgaenge: Vorgang[];
};

export function useVorganglisteQuery(): UseVorganglisteQueryResult {
  const query = useQuery<Vorgang[], unknown>({
    queryKey: QUERY_KEYS.vorgangListe,
    queryFn: getVorgangliste,
  });
  return {
    ...query,
    vorgaenge: query.data ?? [],
  } as UseVorganglisteQueryResult;
}
