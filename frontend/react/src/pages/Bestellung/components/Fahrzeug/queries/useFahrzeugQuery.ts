import { useQuery } from '@tanstack/react-query';
import { getFahrzeugOptions } from '@fahrzeug/queries/api/fetchFahrzeug.ts';

const QUERY_KEYS = {
  fahrzeug: ['fahrzeug'] as const,
} as const;

export function useFahrzeugQuery() {
  const query = useQuery({
    queryKey: QUERY_KEYS.fahrzeug,
    queryFn: getFahrzeugOptions,
  });

  return {
    ...query,
    fahrzeugOptions: query.data ?? [],
  };
}
