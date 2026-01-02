import { renderHook } from '@testing-library/react';
import { useFahrzeugQuery } from './useFahrzeugQuery.ts';
import { getFahrzeugOptions } from '@fahrzeug/queries/api/fetchFahrzeug.ts';
import { useQueryBoundaryFnMock } from '@ui/QueryBoundary/query/__mocks__/useQueryBoundaryFn.functions.ts';

jest.mock('@ui/QueryBoundary/query/useQueryBoundaryFn.ts');
jest.mock('@bestellung/components/Fahrzeug/queries/api/fetchFahrzeug.ts');

describe('useFahrzeugQuery', () => {
  const getFahrzeugOptionsMock = jest.mocked(getFahrzeugOptions);

  it('should use query with the correct paramters', () => {
    renderHook(() => useFahrzeugQuery());

    expect(useQueryBoundaryFnMock).toHaveBeenCalledWith({
      defaultQueryDataResult: [],
      queryKey: ['fahrzeug'],
      queryFn: expect.any(Function),
    });
    expect(useQueryBoundaryFnMock.mock.calls[0][0].queryFn).toBe(
      getFahrzeugOptionsMock,
    );
  });
});
