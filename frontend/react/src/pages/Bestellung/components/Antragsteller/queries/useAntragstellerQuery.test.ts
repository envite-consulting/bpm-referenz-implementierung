import { renderHook, waitFor } from '@testing-library/react';
import { getAntragsteller } from '@/pages/Bestellung/components/Antragsteller/queries/api/fetchAntragsteller.ts';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import { useAntragstellerQuery } from '@/pages/Bestellung/components/Antragsteller/queries/useAntragstellerQuery.ts';

jest.mock(
  '@/pages/Bestellung/components/Antragsteller/queries/api/fetchAntragsteller.ts',
);

const mockGetAntragsteller = getAntragsteller as jest.MockedFunction<
  typeof getAntragsteller
>;

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
    defaultOptions: {
      queries: {
        retry: false,
      },
    },
  });
  jest.clearAllMocks();
});

describe('useAntragstellerQuery', () => {
  it('should return loading state initially', () => {
    mockGetAntragsteller.mockReturnValue(new Promise(() => {}));

    const { result } = renderHook(() => useAntragstellerQuery(), {
      wrapper: createWrapper(),
    });

    expect(result.current.isLoading).toBe(true);
    expect(result.current.antragstellerOptions).toEqual([]);
  });

  it('should return error state if query fails', async () => {
    const error = new Error('Failed to fetch');
    mockGetAntragsteller.mockRejectedValue(error);

    const { result } = renderHook(() => useAntragstellerQuery(), {
      wrapper: createWrapper(),
    });

    await waitFor(() => {
      expect(result.current.isError).toBe(true);
    });

    expect(result.current.error).toBe(error);
    expect(result.current.antragstellerOptions).toEqual([]);
  });

  it('should return mapped options on success', async () => {
    const mockData = [
      {
        id: '1',
        vorname: 'Max',
        nachname: 'Mustermann',
        abteilung: 'Vertrieb',
      },
      { id: '2', vorname: 'Anna', nachname: 'Müller', abteilung: 'Marketing' },
    ];

    mockGetAntragsteller.mockResolvedValue(mockData);

    const { result } = renderHook(() => useAntragstellerQuery(), {
      wrapper: createWrapper(),
    });

    await waitFor(() => {
      expect(result.current.isSuccess).toBe(true);
    });

    expect(result.current.data).toEqual(mockData);
    expect(result.current.antragstellerOptions).toEqual([
      { label: 'Max Mustermann (Vertrieb)', value: '1' },
      { label: 'Anna Müller (Marketing)', value: '2' },
    ]);
  });

  it('should return empty array when no data', async () => {
    mockGetAntragsteller.mockResolvedValue([]);

    const { result } = renderHook(() => useAntragstellerQuery(), {
      wrapper: createWrapper(),
    });

    await waitFor(() => {
      expect(result.current.isSuccess).toBe(true);
    });

    expect(result.current.data).toEqual([]);
    expect(result.current.antragstellerOptions).toEqual([]);
  });
});
