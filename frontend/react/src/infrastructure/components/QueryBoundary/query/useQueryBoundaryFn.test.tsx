import { renderHook } from '@testing-library/react';
import {
  type QueryBoundaryFnProps,
  useQueryBoundaryFn,
} from './useQueryBoundaryFn.ts';
import { useQuery } from '@tanstack/react-query';

jest.mock('@tanstack/react-query', () => ({
  useQuery: jest.fn(),
}));

describe('useQueryBoundary', () => {
  const defaultTanstackQueryResult = {
    isError: false,
    isFetching: false,
    isSuccess: false,
    data: '',
  };
  const defaultQueryFnProps: QueryBoundaryFnProps<string> = {
    defaultQueryDataResult: '',
    queryFn: jest.fn(),
    queryKey: ['myQueryKey'],
  };

  const tanstackUseQuery = useQuery as jest.MockedFunction<typeof useQuery>;

  beforeEach(() => jest.clearAllMocks());

  it('Should return fetching', () => {
    tanstackUseQuery.mockReturnValue({
      ...defaultTanstackQueryResult,
      isFetching: true,
    } as never);

    const result = renderHook(() => useQueryBoundaryFn(defaultQueryFnProps))
      .result.current;

    expect(result.isFetching).toBeTruthy();
    expect(result.isSuccess).toBeFalsy();
    expect(result.isError).toBeFalsy();
    expect(result.getData()).toEqual(
      defaultQueryFnProps.defaultQueryDataResult,
    );
    expect(result.getErrorMessage()).toEqual('');
  });

  it('Should return success', () => {
    tanstackUseQuery.mockReturnValue({
      ...defaultTanstackQueryResult,
      isSuccess: true,
    } as never);

    const result = renderHook(() => useQueryBoundaryFn(defaultQueryFnProps))
      .result.current;

    expect(result.isFetching).toBeFalsy();
    expect(result.isSuccess).toBeTruthy();
    expect(result.isError).toBeFalsy();
    expect(result.getData()).toEqual(
      defaultQueryFnProps.defaultQueryDataResult,
    );
    expect(result.getErrorMessage()).toEqual('');
  });

  it('Should return error', () => {
    tanstackUseQuery.mockReturnValue({
      ...defaultTanstackQueryResult,
      isError: true,
    } as never);

    const result = renderHook(() => useQueryBoundaryFn(defaultQueryFnProps))
      .result.current;

    expect(result.isFetching).toBeFalsy();
    expect(result.isSuccess).toBeFalsy();
    expect(result.isError).toBeTruthy();
    expect(result.getData()).toEqual(
      defaultQueryFnProps.defaultQueryDataResult,
    );
    expect(result.getErrorMessage()).toEqual('Fehler beim Lesen der Daten.');
  });

  it('Should return error with custom error message', () => {
    const customErrorMessage = 'Da ist echt ewas komisch';
    tanstackUseQuery.mockReturnValue({
      ...defaultTanstackQueryResult,
      isError: true,
    } as never);

    const result = renderHook(() =>
      useQueryBoundaryFn({
        ...defaultQueryFnProps,
        customErrorMessage,
      }),
    ).result.current;

    expect(result.isFetching).toBeFalsy();
    expect(result.isSuccess).toBeFalsy();
    expect(result.isError).toBeTruthy();
    expect(result.getData()).toEqual(
      defaultQueryFnProps.defaultQueryDataResult,
    );
    expect(result.getErrorMessage()).toEqual(customErrorMessage);
  });

  it('Should return error with additional error info', () => {
    const additionalErrorInfo = 'Das Backend steht nicht zur Verfügung';
    tanstackUseQuery.mockReturnValue({
      ...defaultTanstackQueryResult,
      isError: true,
      error: new Error(additionalErrorInfo),
    } as never);

    const result = renderHook(() => useQueryBoundaryFn(defaultQueryFnProps))
      .result.current;

    expect(result.isFetching).toBeFalsy();
    expect(result.isSuccess).toBeFalsy();
    expect(result.isError).toBeTruthy();
    expect(result.getData()).toEqual(
      defaultQueryFnProps.defaultQueryDataResult,
    );
    expect(result.getErrorMessage()).toEqual(
      'Fehler beim Lesen der Daten. Grund: Das Backend steht nicht zur Verfügung',
    );
  });
});
