import React from 'react';
import { renderHook, waitFor } from '@testing-library/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { getAufgabenlisteByVorgang } from '@aufgabenSidebar/components/VorgangItem/queries/api/fetchAufgabenliste.ts';
import { useAufgabenlisteQuery } from './useAufgabenlisteQuery.ts';

jest.mock(
  '@aufgabenSidebar/components/VorgangItem/queries/api/fetchAufgabenliste.ts',
);

const mockGetAufgaben = getAufgabenlisteByVorgang as jest.MockedFunction<
  typeof getAufgabenlisteByVorgang
>;

let queryClient: QueryClient;

const createWrapper =
  () =>
  ({ children }: { children: React.ReactNode }) => {
    return React.createElement(
      QueryClientProvider,
      { client: queryClient },
      children,
    );
  };

beforeEach(() => {
  queryClient = new QueryClient({
    defaultOptions: { queries: { retry: false } },
  });
  jest.clearAllMocks();
});

describe('useAufgabenlisteQuery', () => {
  const vorgangId = '123';

  it('should return loading state initially', () => {
    mockGetAufgaben.mockReturnValue(new Promise(() => {}));

    const { result } = renderHook(() => useAufgabenlisteQuery(vorgangId), {
      wrapper: createWrapper(),
    });

    expect(result.current.isLoading).toBe(true);
    expect(result.current.aufgaben).toEqual([]);
  });

  it('should return error state if query fails', async () => {
    const error = new Error('fail');
    mockGetAufgaben.mockRejectedValue(error);

    const { result } = renderHook(() => useAufgabenlisteQuery(vorgangId), {
      wrapper: createWrapper(),
    });

    await waitFor(() => expect(result.current.isError).toBe(true));

    expect(result.current.error).toBe(error);
    expect(result.current.aufgaben).toEqual([]);
  });

  it('should return Aufgabenliste on success', async () => {
    const mockData = [
      {
        id: '1',
        name: 'Aufgabe 1',
        bearbeiter: 'Max',
        formularreferenz: 'Ref1',
        erstelldatum: new Date(),
      },
      {
        id: '2',
        name: 'Aufgabe 2',
        bearbeiter: null,
        formularreferenz: 'Ref2',
        erstelldatum: new Date(),
      },
    ];

    mockGetAufgaben.mockResolvedValue(mockData);

    const { result } = renderHook(() => useAufgabenlisteQuery(vorgangId), {
      wrapper: createWrapper(),
    });

    await waitFor(() => expect(result.current.isSuccess).toBe(true));

    expect(result.current.data).toEqual(mockData);
    expect(result.current.aufgaben).toEqual(mockData);
  });

  it('should return empty array when no data', async () => {
    mockGetAufgaben.mockResolvedValue([]);

    const { result } = renderHook(() => useAufgabenlisteQuery(vorgangId), {
      wrapper: createWrapper(),
    });

    await waitFor(() => {
      expect(result.current.isSuccess).toBe(true);
    });

    expect(result.current.data).toEqual([]);
    expect(result.current.aufgaben).toEqual([]);
  });
});
