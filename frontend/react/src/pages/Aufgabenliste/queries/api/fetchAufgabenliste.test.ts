import axios from 'axios';
import { getAufgabenliste } from './fetchAufgabenliste.ts';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('getAufgabenliste', () => {
  it('should fetch and parse Aufgabenliste data successfully', async () => {
    const mockData = [
      {
        id: '1',
        name: 'Aufgabe 1',
        bearbeiter: 'Assignee',
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

    mockedAxios.get.mockResolvedValue({ data: mockData });

    const result = await getAufgabenliste();

    expect(result).toEqual(mockData);
    expect(mockedAxios.get).toHaveBeenCalledWith('/api/aufgabe');
  });

  it('should throw an error when the request fails', async () => {
    const error = new Error('Network Error');

    const consoleErrorSpy = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});

    mockedAxios.get.mockRejectedValue(error);

    await expect(getAufgabenliste()).rejects.toThrow('Network Error');

    expect(consoleErrorSpy).toHaveBeenCalledWith(error);
    expect(consoleErrorSpy).toHaveBeenCalledTimes(1);

    consoleErrorSpy.mockRestore();
  });

  it('should throw if response data does not match schema', async () => {
    const invalidData = [{ foo: 'bar' }];

    mockedAxios.get.mockResolvedValue({ data: invalidData });

    await expect(getAufgabenliste()).rejects.toThrow('Invalid input');

    expect(mockedAxios.get).toHaveBeenCalledWith('/api/aufgabe');
  });
});
