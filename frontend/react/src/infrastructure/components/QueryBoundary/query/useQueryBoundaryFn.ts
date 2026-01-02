import { type QueryKey, useQuery } from '@tanstack/react-query';

export type QueryBoundaryFnProps<
  TData,
  TQueryKey extends QueryKey = QueryKey,
> = {
  queryKey: TQueryKey;
  queryFn: () => TData | Promise<TData>;
  defaultQueryDataResult: TData;
  customErrorMessage?: string;
  discardErrorReason?: boolean;
};

export type QueryBoundaryFnResult<TData> = {
  getData: () => TData;
  getErrorMessage: () => string;
  isFetching: boolean;
  isError: boolean;
  isSuccess: boolean;
};

export const useQueryBoundaryFn = <TData>({
  queryKey,
  queryFn,
  defaultQueryDataResult,
  customErrorMessage,
  discardErrorReason = false,
}: QueryBoundaryFnProps<TData>): QueryBoundaryFnResult<TData> => {
  const query = useQuery<TData>({ queryKey, queryFn });

  const isFetching = query.isFetching;
  const isError = query.isError;
  const isSuccess = query.isSuccess;

  const getData = () => query.data ?? defaultQueryDataResult;

  const getErrorMessage = () => {
    if (!isError) return '';
    const queryErrorReason = query.error?.message;

    const errrorMessage = customErrorMessage ?? 'Fehler beim Lesen der Daten.';
    const reason =
      !discardErrorReason && queryErrorReason
        ? ` Grund: ${queryErrorReason}`
        : '';
    return `${errrorMessage}${reason}`;
  };

  return {
    getData,
    isFetching,
    isError,
    isSuccess,
    getErrorMessage,
  };
};
