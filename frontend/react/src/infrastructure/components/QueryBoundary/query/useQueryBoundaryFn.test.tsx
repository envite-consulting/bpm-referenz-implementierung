import { renderHook } from '@testing-library/react';
import {
  type QueryBoundaryFnProps,
  useQueryBoundaryFn,
} from './useQueryBoundaryFn.ts';
import {
  tanstackErrorQueryResult,
  tanstackErrorWithMessageQueryResult,
  tanstackFetchingQueryResult,
  tanstackSuccessQueryResult,
  tanstackUseQueryMock,
} from '@root/testutils/useQueryHelper.ts';

jest.mock('@tanstack/react-query');

describe('useQueryBoundary', () => {
  const defaultQueryFnProps: QueryBoundaryFnProps<string> = {
    defaultQueryDataResult: '',
    queryFn: jest.fn(),
    queryKey: ['myQueryKey'],
  };

  beforeEach(() => jest.clearAllMocks());

  it('Should return fetching', () => {
    tanstackUseQueryMock.mockReturnValue(tanstackFetchingQueryResult);

    const result = renderHook(() => useQueryBoundaryFn(defaultQueryFnProps))
      .result.current;

    expect(result.isFetching).toBeTruthy();
    expect(result.isSuccess).toBeTruthy();
    expect(result.isError).toBeFalsy();
    expect(result.getData()).toEqual(
      defaultQueryFnProps.defaultQueryDataResult,
    );
    expect(result.getErrorMessage()).toEqual('');
  });

  it('Should return success', () => {
    tanstackUseQueryMock.mockReturnValue(tanstackSuccessQueryResult);

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
    tanstackUseQueryMock.mockReturnValue(tanstackErrorQueryResult);

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
    tanstackUseQueryMock.mockReturnValue(tanstackErrorQueryResult);

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
    tanstackUseQueryMock.mockReturnValue(
      tanstackErrorWithMessageQueryResult(additionalErrorInfo),
    );

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
