import { useQuery } from '@tanstack/react-query';
import { getAntragstellerOptions } from '@antragsteller/queries/api/fetchAntragsteller.ts';

const QUERY_KEYS = {
  antragsteller: ['antragsteller'] as const,
} as const;

export function useAntragstellerQuery() {
  const query = useQuery({
    queryKey: QUERY_KEYS.antragsteller,
    queryFn: getAntragstellerOptions,
  });

  return {
    ...query,
    antragstellerOptions: query.data ?? [],
  };
}
