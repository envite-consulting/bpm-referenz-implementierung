import { LoadingSpin } from '@ui/LoadingSpin/LoadingSpin.tsx';
import { Badge, type BadgeType } from '@ui/Badge/Badge.tsx';

import type { QueryBoundaryFnResult } from './query/useQueryBoundaryFn.ts';

export type QueryBoundaryProps<TData> = {
  queryResult: QueryBoundaryFnResult<TData>;
  errorType?: BadgeType;
  children?: React.ReactNode;
};

export const QueryBoundary = <TData,>({
  queryResult,
  children,
  errorType,
}: QueryBoundaryProps<TData>): React.ReactNode => {
  const { isFetching, isError, getErrorMessage } = queryResult;

  if (isFetching) return <LoadingSpin />;
  if (isError)
    return <Badge label={getErrorMessage()} type={errorType ?? 'warning'} />;

  return children;
};
