import axios from 'axios';
import { getAntragsteller } from '@/pages/Bestellung/components/Antragsteller/queries/api/fetchAntragsteller.ts';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('getAntragsteller', () => {
  it('should fetch and parse Antragsteller data successfully', async () => {
    const mockData = [
      {
        id: '1',
        vorname: 'Max',
        nachname: 'Mustermann',
        abteilung: 'Vertrieb',
      },
      { id: '2', vorname: 'Anna', nachname: 'Müller', abteilung: 'Marketing' },
    ];

    mockedAxios.get.mockResolvedValue({ data: mockData });

    const result = await getAntragsteller();

    expect(result).toEqual(mockData);
    expect(mockedAxios.get).toHaveBeenCalledWith('/antragsteller');
  });

  it('should throw an error when the request fails', async () => {
    const error = new Error('Network Error');

    const consoleErrorSpy = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});

    mockedAxios.get.mockRejectedValue(error);

    await expect(getAntragsteller()).rejects.toThrow('Network Error');

    expect(consoleErrorSpy).toHaveBeenCalledWith(error);
    expect(consoleErrorSpy).toHaveBeenCalledTimes(1);

    consoleErrorSpy.mockRestore();
  });

  it('should throw if response data does not match schema', async () => {
    const invalidData = [{ foo: 'bar' }];

    mockedAxios.get.mockResolvedValue({ data: invalidData });

    await expect(getAntragsteller()).rejects.toThrow('Invalid input');

    expect(mockedAxios.get).toHaveBeenCalledWith('/antragsteller');
  });
});
