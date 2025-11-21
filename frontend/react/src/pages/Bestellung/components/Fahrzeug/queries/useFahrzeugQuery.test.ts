import { renderHook } from '@testing-library/react';
import { useFahrzeugQuery } from './useFahrzeugQuery.ts';
import { useQueryBoundaryFn } from '@ui/QueryBoundary/';
import { getFahrzeugOptions } from '@fahrzeug/queries/api/fetchFahrzeug.ts';

jest.mock('@ui/QueryBoundary/query/useQueryBoundaryFn.ts');
jest.mock('@bestellung/components/Fahrzeug/queries/api/fetchFahrzeug.ts');

describe('useFahrzeugQuery', () => {
  const getFahrzeugOptionsMock = getFahrzeugOptions as jest.MockedFunction<
    typeof getFahrzeugOptions
  >;

  const queryMock = useQueryBoundaryFn as jest.MockedFunction<
    typeof useQueryBoundaryFn
  >;

  it('should use query with the correct paramters', () => {
    renderHook(() => useFahrzeugQuery());

    expect(queryMock).toHaveBeenCalledWith({
      defaultQueryDataResult: [],
      queryKey: ['fahrzeug'],
      queryFn: expect.any(Function),
    });
    expect(queryMock.mock.calls[0][0].queryFn).toBe(getFahrzeugOptionsMock);
  });
});
