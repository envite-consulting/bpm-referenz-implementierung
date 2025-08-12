import { renderHook, waitFor } from '@testing-library/react';
import { getAntragstellerOptions } from '@antragsteller/queries/api/fetchAntragsteller.ts';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import { useAntragstellerQuery } from './useAntragstellerQuery.ts';
import type { DropdownOption } from '@ui/DropDownMenu/DropdownMenu.tsx';

jest.mock(
  '@bestellung/components/Antragsteller/queries/api/fetchAntragsteller.ts',
);

const mockGetAntragstellerOptions =
  getAntragstellerOptions as jest.MockedFunction<
    typeof getAntragstellerOptions
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
    mockGetAntragstellerOptions.mockReturnValue(new Promise(() => {}));

    const { result } = renderHook(() => useAntragstellerQuery(), {
      wrapper: createWrapper(),
    });

    expect(result.current.isLoading).toBe(true);
    expect(result.current.antragstellerOptions).toEqual([]);
  });

  it('should return error state if query fails', async () => {
    const error = new Error('Failed to fetch');
    mockGetAntragstellerOptions.mockRejectedValue(error);

    const { result } = renderHook(() => useAntragstellerQuery(), {
      wrapper: createWrapper(),
    });

    await waitFor(() => {
      expect(result.current.isError).toBe(true);
    });

    expect(result.current.error).toBe(error);
    expect(result.current.antragstellerOptions).toEqual([]);
  });

  it('should return dropdown options on success', async () => {
    const mockOptions: DropdownOption<string>[] = [
      { label: 'Max Mustermann (Vertrieb)', value: '1' },
      { label: 'Anna Müller (Marketing)', value: '2' },
    ];

    mockGetAntragstellerOptions.mockResolvedValue(mockOptions);

    const { result } = renderHook(() => useAntragstellerQuery(), {
      wrapper: createWrapper(),
    });

    await waitFor(() => {
      expect(result.current.isSuccess).toBe(true);
    });

    expect(result.current.data).toEqual(mockOptions);
    expect(result.current.antragstellerOptions).toEqual(mockOptions);
  });

  it('should return empty array when no data', async () => {
    mockGetAntragstellerOptions.mockResolvedValue([]);

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
