import { getFahrzeugOptions } from '@fahrzeug/queries/api/fetchFahrzeug.ts';
import { useQueryBoundaryFn } from '@ui/QueryBoundary';
import type { DropdownOption } from '@ui/DropDownMenu/DropdownMenu.tsx';

const QUERY_KEYS = {
  fahrzeug: ['fahrzeug'] as const,
} as const;

export function useFahrzeugQuery() {
  return useQueryBoundaryFn({
    queryKey: QUERY_KEYS.fahrzeug,
    queryFn: getFahrzeugOptions,
    defaultQueryDataResult: [] as DropdownOption<string>[],
  });
}
