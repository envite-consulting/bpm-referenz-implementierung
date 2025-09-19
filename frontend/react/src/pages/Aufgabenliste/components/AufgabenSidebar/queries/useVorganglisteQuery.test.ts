import React from 'react';
import { renderHook, waitFor } from '@testing-library/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { getVorgangliste } from '@aufgabenSidebar/queries/api/fetchVorgangliste.ts';
import { useVorganglisteQuery } from './useVorganglisteQuery.ts';

jest.mock('@aufgabenSidebar/queries/api/fetchVorgangliste.ts');

const mockGetVorgaenge = getVorgangliste as jest.MockedFunction<
  typeof getVorgangliste
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

describe('useVorganglisteQuery', () => {
  it('should return loading state initially', () => {
    mockGetVorgaenge.mockReturnValue(new Promise(() => {}));

    const { result } = renderHook(() => useVorganglisteQuery(), {
      wrapper: createWrapper(),
    });

    expect(result.current.isLoading).toBe(true);
    expect(result.current.vorgaenge).toEqual([]);
  });

  it('should return error state if query fails', async () => {
    const error = new Error('fail');
    mockGetVorgaenge.mockRejectedValue(error);

    const { result } = renderHook(() => useVorganglisteQuery(), {
      wrapper: createWrapper(),
    });

    await waitFor(() => expect(result.current.isError).toBe(true));

    expect(result.current.error).toBe(error);
    expect(result.current.vorgaenge).toEqual([]);
  });

  it('should return Vorgangliste on success', async () => {
    const mockData = [
      {
        id: '1',
        fachlicherSchluessel: 'businessKey1',
        fachdaten: {
          antragstellerVorname: 'Vorname1',
          antragstellerNachname: 'Nachname1',
          fahrzeugHersteller: 'Fahrzeughersteller1',
          fahrzeugModell: 'Fahrzeugmodell1',
        },
      },
      {
        id: '2',
        fachlicherSchluessel: 'businessKey2',
        fachdaten: {
          antragstellerVorname: 'Vorname2',
          antragstellerNachname: 'Nachname2',
          fahrzeugHersteller: 'Fahrzeughersteller2',
          fahrzeugModell: 'Fahrzeugmodell2',
        },
      },
    ];

    mockGetVorgaenge.mockResolvedValue(mockData);

    const { result } = renderHook(() => useVorganglisteQuery(), {
      wrapper: createWrapper(),
    });

    await waitFor(() => expect(result.current.isSuccess).toBe(true));

    expect(result.current.data).toEqual(mockData);
    expect(result.current.vorgaenge).toEqual(mockData);
  });

  it('should return empty array when no data', async () => {
    mockGetVorgaenge.mockResolvedValue([]);

    const { result } = renderHook(() => useVorganglisteQuery(), {
      wrapper: createWrapper(),
    });

    await waitFor(() => {
      expect(result.current.isSuccess).toBe(true);
    });

    expect(result.current.data).toEqual([]);
    expect(result.current.vorgaenge).toEqual([]);
  });
});
