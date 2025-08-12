import axios from 'axios';
import {
  type Bestellungsabfrage,
  type Bestellungserfassung,
} from '@bestellung/Bestellung.types.ts';
import { createBestellung } from './createBestellung.ts';
import { ZodError } from 'zod';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('createBestellung', () => {
  const validBestellung: Bestellungserfassung = {
    fahrzeugreferenz: 'ref-1',
    antragstellerreferenz: 'ref-2',
  };

  const validResponseData: Bestellungsabfrage = {
    id: '123',
    fahrzeugreferenz: 'ref-1',
    antragstellerreferenz: 'ref-2',
    bestelldatum: new Date(),
    status: 'ANGELEGT',
  };

  it('should successfully create a Bestellung and parse response', async () => {
    mockedAxios.post.mockResolvedValue({ data: validResponseData });

    const result = await createBestellung(validBestellung);

    expect(result).toEqual(validResponseData);
    expect(mockedAxios.post).toHaveBeenCalledWith(
      '/api/bestellung',
      validBestellung,
    );
  });

  it('should throw and log error if axios.post fails', async () => {
    const error = new Error('Network Error');
    const consoleErrorSpy = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});

    mockedAxios.post.mockRejectedValue(error);

    await expect(createBestellung(validBestellung)).rejects.toThrow(
      'Network Error',
    );
    expect(consoleErrorSpy).toHaveBeenCalledWith(
      'Fehler beim Erstellen der Bestellung:',
      error,
    );

    consoleErrorSpy.mockRestore();
  });

  it('should throw if response data does not match schema', async () => {
    const invalidResponseData = { foo: 'bar' };

    mockedAxios.post.mockResolvedValue({ data: invalidResponseData });

    await expect(createBestellung(validBestellung)).rejects.toThrow(ZodError);

    expect(mockedAxios.post).toHaveBeenCalledWith(
      '/api/bestellung',
      validBestellung,
    );
  });
});
