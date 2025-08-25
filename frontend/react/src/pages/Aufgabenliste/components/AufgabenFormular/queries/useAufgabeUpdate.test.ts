import { renderHook, waitFor } from '@testing-library/react';
import {
  uebernehmen,
  abgeben,
  abschliessenMitVariablen,
} from '@aufgabenliste/components/AufgabenFormular/queries/api/updateAufgabe.ts';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import { QUERY_KEYS } from '@aufgabenliste/queries/useAufgabenlisteQuery.ts';
import { useAufgabeUpdate } from './useAufgabeUpdate.ts';

jest.mock(
  '@aufgabenliste/components/AufgabenFormular/queries/api/updateAufgabe.ts',
);

const mockUebernehmen = uebernehmen as jest.MockedFunction<typeof uebernehmen>;
const mockAbgeben = abgeben as jest.MockedFunction<typeof abgeben>;
const mockAbschliessenMitVariablen =
  abschliessenMitVariablen as jest.MockedFunction<
    typeof abschliessenMitVariablen
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

  jest.spyOn(queryClient, 'invalidateQueries');
});

describe('useAufgabeUpdate', () => {
  const aufgabenId = 'test-aufgaben-id';

  describe('uebernehmen', () => {

    it('should return error state if mutation fails', async () => {
      const error = new Error('Failed to uebernehmen');
      mockUebernehmen.mockRejectedValue(error);

      const { result } = renderHook(() => useAufgabeUpdate(aufgabenId), {
        wrapper: createWrapper(),
      });

      result.current.uebernehmen.mutate('user-123');

      await waitFor(() => {
        expect(result.current.uebernehmen.isError).toBe(true);
      });

      expect(result.current.uebernehmen.error).toBe(error);
    });

    it('should return success state and invalidate queries on success', async () => {
      const userId = 'user-123';
      mockUebernehmen.mockResolvedValue(undefined);

      const { result } = renderHook(() => useAufgabeUpdate(aufgabenId), {
        wrapper: createWrapper(),
      });

      result.current.uebernehmen.mutate(userId);

      await waitFor(() => {
        expect(result.current.uebernehmen.isSuccess).toBe(true);
      });

      expect(mockUebernehmen).toHaveBeenCalledWith(aufgabenId, userId);
      expect(queryClient.invalidateQueries).toHaveBeenCalledWith({
        queryKey: QUERY_KEYS.aufgabenListe,
      });
    });
  });

  describe('abgeben', () => {

    it('should return error state if mutation fails', async () => {
      const error = new Error('Failed to abgeben');
      mockAbgeben.mockRejectedValue(error);

      const { result } = renderHook(() => useAufgabeUpdate(aufgabenId), {
        wrapper: createWrapper(),
      });

      result.current.abgeben.mutate();

      await waitFor(() => {
        expect(result.current.abgeben.isError).toBe(true);
      });

      expect(result.current.abgeben.error).toBe(error);
    });

    it('should return success state and invalidate queries on success', async () => {
      mockAbgeben.mockResolvedValue(undefined);

      const { result } = renderHook(() => useAufgabeUpdate(aufgabenId), {
        wrapper: createWrapper(),
      });

      result.current.abgeben.mutate();

      await waitFor(() => {
        expect(result.current.abgeben.isSuccess).toBe(true);
      });

      expect(mockAbgeben).toHaveBeenCalledWith(aufgabenId);
      expect(queryClient.invalidateQueries).toHaveBeenCalledWith({
        queryKey: QUERY_KEYS.aufgabenListe,
      });
    });
  });

  describe('abschliessenMitVariablen', () => {

    it('should return error state if mutation fails', async () => {
      const error = new Error('Failed to abschliessen');
      mockAbschliessenMitVariablen.mockRejectedValue(error);

      const { result } = renderHook(() => useAufgabeUpdate(aufgabenId), {
        wrapper: createWrapper(),
      });

      const variablen = { key: 'value' };
      result.current.abschliessenMitVariablen.mutate(variablen);

      await waitFor(() => {
        expect(result.current.abschliessenMitVariablen.isError).toBe(true);
      });

      expect(result.current.abschliessenMitVariablen.error).toBe(error);
    });

    it('should return success state and invalidate queries on success', async () => {
      const variablen = { key: 'value', number: 42 };
      mockAbschliessenMitVariablen.mockResolvedValue(undefined);

      const { result } = renderHook(() => useAufgabeUpdate(aufgabenId), {
        wrapper: createWrapper(),
      });

      result.current.abschliessenMitVariablen.mutate(variablen);

      await waitFor(() => {
        expect(result.current.abschliessenMitVariablen.isSuccess).toBe(true);
      });

      expect(mockAbschliessenMitVariablen).toHaveBeenCalledWith(
        aufgabenId,
        variablen,
      );
      expect(queryClient.invalidateQueries).toHaveBeenCalledWith({
        queryKey: QUERY_KEYS.aufgabenListe,
      });
    });

    it('should use empty object as default when no variables provided', async () => {
      mockAbschliessenMitVariablen.mockResolvedValue(undefined);

      const { result } = renderHook(() => useAufgabeUpdate(aufgabenId), {
        wrapper: createWrapper(),
      });

      result.current.abschliessenMitVariablen.mutate({});

      await waitFor(() => {
        expect(result.current.abschliessenMitVariablen.isSuccess).toBe(true);
      });

      expect(mockAbschliessenMitVariablen).toHaveBeenCalledWith(aufgabenId, {});
      expect(queryClient.invalidateQueries).toHaveBeenCalledWith({
        queryKey: QUERY_KEYS.aufgabenListe,
      });
    });
  });
});
