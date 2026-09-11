import {
  type QueryBoundaryFnResult,
  useQueryBoundaryFn,
} from '@ui/QueryBoundary';

export const useQueryBoundaryFnMock = jest.mocked(useQueryBoundaryFn);

export const buildUseQueryBoundaryFnResult = <T>({
  type,
  data,
  errorMessage,
}: {
  type: 'error' | 'success' | 'fetching';
  data?: T;
  errorMessage?: string;
}): QueryBoundaryFnResult<T> => {
  return {
    isFetching: type === 'fetching',
    isError: type === 'error',
    isSuccess: type === 'success',
    getErrorMessage: () => errorMessage ?? '',
    getData: () => data ?? ({} as T),
  };
};
