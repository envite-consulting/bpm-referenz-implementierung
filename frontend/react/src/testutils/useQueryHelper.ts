import { useQuery, type UseQueryResult } from '@tanstack/react-query';

const defaultTanstackQueryResult: UseQueryResult = {
  data: undefined,
  dataUpdatedAt: 0,
  error: null,
  errorUpdateCount: 0,
  errorUpdatedAt: 0,
  failureCount: 0,
  failureReason: null,
  fetchStatus: 'idle',
  isEnabled: false,
  isError: false,
  isFetched: false,
  isFetchedAfterMount: false,
  isFetching: false,
  isInitialLoading: false,
  isLoading: false,
  isLoadingError: false,
  isPaused: false,
  isPending: false,
  isPlaceholderData: false,
  isRefetchError: false,
  isRefetching: false,
  isStale: false,
  isSuccess: true,
  promise: Promise.resolve(undefined),
  refetch: jest.fn(),
  status: 'success',
};

export const tanstackSuccessQueryResult: UseQueryResult =
  defaultTanstackQueryResult;

export const tanstackFetchingQueryResult: UseQueryResult = {
  ...defaultTanstackQueryResult,
  isFetching: true,
};

export const tanstackErrorQueryResult: UseQueryResult = {
  ...defaultTanstackQueryResult,
  isSuccess: false,
  isError: true,
  isRefetchError: true,
  status: 'error',
  error: new Error(),
};

export const tanstackErrorWithMessageQueryResult = (
  errorMessage: string,
): UseQueryResult => {
  return { ...tanstackErrorQueryResult, error: new Error(errorMessage) };
};

export const tanstackUseQueryMock = jest.mocked(useQuery);
