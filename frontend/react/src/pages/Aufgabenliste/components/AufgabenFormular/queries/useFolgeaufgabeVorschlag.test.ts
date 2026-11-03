import { renderHook, waitFor } from '@testing-library/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import { getFolgeaufgabeVorschlag } from '@aufgabenFormular/queries/api/fetchFolgeaufgabeVorschlag.ts';
import { QUERY_KEYS as QUERY_KEYS_VORGANG } from '@aufgabenSidebar/queries/useVorganglisteQuery.ts';
import { QUERY_KEYS as QUERY_KEYS_AUFGABEN } from '@vorgangItem/queries/useAufgabenlisteQuery.ts';
import { useFolgeaufgabeVorschlag } from './useFolgeaufgabeVorschlag.ts';

jest.mock('@aufgabenFormular/queries/api/fetchFolgeaufgabeVorschlag.ts');

const mockGetFolgeaufgabeVorschlag =
  getFolgeaufgabeVorschlag as jest.MockedFunction<
    typeof getFolgeaufgabeVorschlag
  >;

describe('useFolgeaufgabeVorschlag', () => {
  let queryClient: QueryClient;

  const wrapper = ({ children }: { children: React.ReactNode }) =>
    React.createElement(QueryClientProvider, { client: queryClient }, children);

  beforeEach(() => {
    queryClient = new QueryClient();
    jest.clearAllMocks();
    jest.spyOn(queryClient, 'invalidateQueries');
  });

  it('should return the Vorschlag and invalidate the sidebar queries', async () => {
    const vorschlag = {
      aufgabeId: 'aufgabe-2',
      vorgangId: 'vorgang-1',
      arbeitspaket: 'FAHRZEUG',
    };
    mockGetFolgeaufgabeVorschlag.mockResolvedValue(vorschlag);

    const { result } = renderHook(() => useFolgeaufgabeVorschlag(), {
      wrapper,
    });

    result.current.mutate({
      vorgangId: 'vorgang-1',
      letztesArbeitspaket: 'FAHRZEUG',
    });

    await waitFor(() => {
      expect(result.current.isSuccess).toBe(true);
    });

    expect(result.current.data).toEqual(vorschlag);
    expect(mockGetFolgeaufgabeVorschlag).toHaveBeenCalledWith(
      'vorgang-1',
      'FAHRZEUG',
    );
    expect(queryClient.invalidateQueries).toHaveBeenCalledWith({
      queryKey: QUERY_KEYS_VORGANG.vorgangListe,
    });
    expect(queryClient.invalidateQueries).toHaveBeenCalledWith({
      queryKey: [QUERY_KEYS_AUFGABEN.aufgabenListe],
    });
  });

  it('should return error state and still invalidate the sidebar queries', async () => {
    const error = new Error('Serverfehler');
    mockGetFolgeaufgabeVorschlag.mockRejectedValue(error);

    const { result } = renderHook(() => useFolgeaufgabeVorschlag(), {
      wrapper,
    });

    result.current.mutate({ vorgangId: 'vorgang-1' });

    await waitFor(() => {
      expect(result.current.isError).toBe(true);
    });

    expect(result.current.error).toBe(error);
    expect(queryClient.invalidateQueries).toHaveBeenCalledWith({
      queryKey: QUERY_KEYS_VORGANG.vorgangListe,
    });
  });
});
