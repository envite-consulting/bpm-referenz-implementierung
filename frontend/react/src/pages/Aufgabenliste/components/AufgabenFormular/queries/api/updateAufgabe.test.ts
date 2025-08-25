import axios from 'axios';
import {
  abgeben,
  abschliessenMitVariablen,
  uebernehmen,
} from './updateAufgabe.ts';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('updateAufgabe', () => {
  const aufgabenId = 'aufg-123';
  const userId = 'user-456';

  describe('uebernehmen', () => {
    it('should call axios.put with correct URL and payload', async () => {
      mockedAxios.put.mockResolvedValue({});

      await uebernehmen(aufgabenId, userId);

      expect(mockedAxios.put).toHaveBeenCalledWith(
        `/api/aufgabe/${aufgabenId}/uebernehmen`,
        { userId },
      );
    });

    it('should throw error if axios.put fails', async () => {
      const error = new Error('Network Error');

      mockedAxios.put.mockRejectedValue(error);

      await expect(uebernehmen(aufgabenId, userId)).rejects.toThrow(
        'Network Error',
      );
    });
  });

  describe('abgeben', () => {
    it('should call axios.put with correct URL', async () => {
      mockedAxios.put.mockResolvedValue({});

      await abgeben(aufgabenId);

      expect(mockedAxios.put).toHaveBeenCalledWith(
        `/api/aufgabe/${aufgabenId}/abgeben`,
      );
    });

    it('should throw error if axios.put fails', async () => {
      const error = new Error('Network Error');

      mockedAxios.put.mockRejectedValue(error);

      await expect(abgeben(aufgabenId)).rejects.toThrow('Network Error');
    });
  });

  describe('abschliessenMitVariablen', () => {
    it('should call axios.put with correct URL and variables', async () => {
      const variablen = { foo: 'bar' };
      mockedAxios.put.mockResolvedValue({});

      await abschliessenMitVariablen(aufgabenId, variablen);

      expect(mockedAxios.put).toHaveBeenCalledWith(
        `/api/aufgabe/${aufgabenId}/abschliessenMitVariablen`,
        { variables: variablen },
      );
    });

    it('should default variables to empty object if not provided', async () => {
      mockedAxios.put.mockResolvedValue({});

      await abschliessenMitVariablen(aufgabenId);

      expect(mockedAxios.put).toHaveBeenCalledWith(
        `/api/aufgabe/${aufgabenId}/abschliessenMitVariablen`,
        { variables: {} },
      );
    });

    it('should throw error if axios.put fails', async () => {
      const error = new Error('Network Error');

      mockedAxios.put.mockRejectedValue(error);

      await expect(
        abschliessenMitVariablen(aufgabenId, { foo: 'bar' }),
      ).rejects.toThrow('Network Error');
    });
  });
});
