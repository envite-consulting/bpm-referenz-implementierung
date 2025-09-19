import React from 'react';
import { renderHook, waitFor } from '@testing-library/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { getAufgabe } from '@aufgabenFormular/queries/api/fetchAufgabe.ts';
import { useAufgabeQuery } from './useAufgabeQuery.ts';

jest.mock('@aufgabenFormular/queries/api/fetchAufgabe.ts');

const mockGetAufgabe = getAufgabe as jest.MockedFunction<typeof getAufgabe>;

let queryClient: QueryClient;

const createWrapper = () => {
  return ({ children }: { children: React.ReactNode }) => {
    return React.createElement(
      QueryClientProvider,
      { client: queryClient },
      children,
    );
  };
};

beforeEach(() => {
  queryClient = new QueryClient({
    defaultOptions: { queries: { retry: false } },
  });
  jest.clearAllMocks();
});

describe('useAufgabeQuery', () => {
  const id = '123';

  it('should return loading state initially', () => {
    mockGetAufgabe.mockReturnValue(new Promise(() => {}));

    const { result } = renderHook(() => useAufgabeQuery(id), {
      wrapper: createWrapper(),
    });

    expect(result.current.isLoading).toBe(true);
    expect(result.current.aufgabe).toBeUndefined();
  });

  it('should return error state if query fails', async () => {
    const error = new Error('fail');
    mockGetAufgabe.mockRejectedValue(error);

    const { result } = renderHook(() => useAufgabeQuery(id), {
      wrapper: createWrapper(),
    });

    await waitFor(() => expect(result.current.isError).toBe(true));

    expect(result.current.error).toBe(error);
    expect(result.current.aufgabe).toBeUndefined();
  });

  it('should return Aufgabe on success', async () => {
    const mockData = {
      id: '123',
      name: 'Aufgabe 1',
      bearbeiter: 'Max',
      formularreferenz: 'Ref1',
      erstelldatum: new Date(),
    };

    mockGetAufgabe.mockResolvedValue(mockData);

    const { result } = renderHook(() => useAufgabeQuery(id), {
      wrapper: createWrapper(),
    });

    await waitFor(() => expect(result.current.isSuccess).toBe(true));

    expect(result.current.data).toEqual(mockData);
    expect(result.current.aufgabe).toEqual(mockData);
  });
});
