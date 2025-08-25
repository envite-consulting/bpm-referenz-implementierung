import { renderHook, waitFor } from '@testing-library/react';
import { getFahrzeugOptions } from '@fahrzeug/queries/api/fetchFahrzeug.ts';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import { useFahrzeugQuery } from './useFahrzeugQuery.ts';
import type { DropdownOption } from '@ui/DropDownMenu/DropdownMenu.tsx';

jest.mock('@bestellung/components/Fahrzeug/queries/api/fetchFahrzeug.ts');

const mockGetFahrzeugOptions = getFahrzeugOptions as jest.MockedFunction<
  typeof getFahrzeugOptions
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

describe('useFahrzeugQuery', () => {
  it('should return loading state initially', () => {
    mockGetFahrzeugOptions.mockReturnValue(new Promise(() => {}));

    const { result } = renderHook(() => useFahrzeugQuery(), {
      wrapper: createWrapper(),
    });

    expect(result.current.isLoading).toBe(true);
    expect(result.current.fahrzeugOptions).toEqual([]);
  });

  it('should return error state if query fails', async () => {
    const error = new Error('Failed to fetch');
    mockGetFahrzeugOptions.mockRejectedValue(error);

    const { result } = renderHook(() => useFahrzeugQuery(), {
      wrapper: createWrapper(),
    });

    await waitFor(() => {
      expect(result.current.isError).toBe(true);
    });

    expect(result.current.error).toBe(error);
    expect(result.current.fahrzeugOptions).toEqual([]);
  });

  it('should return dropdown options on success', async () => {
    const mockOptions: DropdownOption<string>[] = [
      { label: 'Audi A5 (1990)', value: '1' },
      { label: 'VW ID4 (2023)', value: '2' },
    ];

    mockGetFahrzeugOptions.mockResolvedValue(mockOptions);

    const { result } = renderHook(() => useFahrzeugQuery(), {
      wrapper: createWrapper(),
    });

    await waitFor(() => {
      expect(result.current.isSuccess).toBe(true);
    });

    expect(result.current.data).toEqual(mockOptions);
    expect(result.current.fahrzeugOptions).toEqual(mockOptions);
  });

  it('should return empty array when no data', async () => {
    mockGetFahrzeugOptions.mockResolvedValue([]);

    const { result } = renderHook(() => useFahrzeugQuery(), {
      wrapper: createWrapper(),
    });

    await waitFor(() => {
      expect(result.current.isSuccess).toBe(true);
    });

    expect(result.current.data).toEqual([]);
    expect(result.current.fahrzeugOptions).toEqual([]);
  });
});
