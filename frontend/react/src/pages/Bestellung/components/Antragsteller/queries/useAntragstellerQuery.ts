import { useQuery } from '@tanstack/react-query';
import { getAntragsteller } from '@/pages/Bestellung/components/Antragsteller/queries/api/fetchAntragsteller.ts';
import type { Antragsteller } from '@/pages/Bestellung/components/Antragsteller/Antragsteller.types.ts';
import type { DropdownOption } from '@/infrastructure/components/DropDownMenu/DropdownMenu.tsx';

const QUERY_KEYS = {
  antragsteller: ['antragsteller'] as const,
} as const;

export function useAntragstellerQuery() {
  const query = useQuery({
    queryKey: QUERY_KEYS.antragsteller,
    queryFn: getAntragsteller,
  });

  const antragstellerOptions =
    query.data?.map(
      (antragsteller: Antragsteller): DropdownOption<string> => ({
        label: `${antragsteller.vorname} ${antragsteller.nachname} (${antragsteller.abteilung})`,
        value: antragsteller.id,
      }),
    ) ?? [];

  return {
    ...query,
    antragstellerOptions,
  };
}
