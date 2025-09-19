import axios from 'axios';
import { getAufgabe } from './fetchAufgabe.ts';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('getAufgabe', () => {
  const id = '123';

  it('should fetch and parse Aufgabe data successfully', async () => {
    const mockData = {
      id: '123',
      name: 'Aufgabe 1',
      bearbeiter: 'Assignee',
      formularreferenz: 'Ref1',
      erstelldatum: new Date(),
    };

    mockedAxios.get.mockResolvedValue({ data: mockData });

    const result = await getAufgabe(id);

    expect(result).toEqual(mockData);
    expect(mockedAxios.get).toHaveBeenCalledWith(`/api/aufgabe/${id}`);
  });

  it('should throw an error when the request fails', async () => {
    const error = new Error('Network Error');
    const consoleErrorSpy = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});

    mockedAxios.get.mockRejectedValue(error);

    await expect(getAufgabe(id)).rejects.toThrow('Network Error');

    expect(consoleErrorSpy).toHaveBeenCalledWith(error);
    expect(consoleErrorSpy).toHaveBeenCalledTimes(1);

    consoleErrorSpy.mockRestore();
  });

  it('should throw if response data does not match schema', async () => {
    const invalidData = { foo: 'bar' };

    mockedAxios.get.mockResolvedValue({ data: invalidData });

    await expect(getAufgabe(id)).rejects.toThrow('Invalid input');
    expect(mockedAxios.get).toHaveBeenCalledWith(`/api/aufgabe/${id}`);
  });
});
