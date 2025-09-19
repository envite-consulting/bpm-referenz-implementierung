import axios from 'axios';
import { getVorgangliste } from './fetchVorgangliste.ts';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('getVorganliste', () => {
  it('should fetch and parse Vorgangliste data successfully', async () => {
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

    mockedAxios.get.mockResolvedValue({ data: mockData });

    const result = await getVorgangliste();

    expect(result).toEqual(mockData);
    expect(mockedAxios.get).toHaveBeenCalledWith('/api/vorgang');
  });

  it('should throw an error when the request fails', async () => {
    const error = new Error('Network Error');

    const consoleErrorSpy = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});

    mockedAxios.get.mockRejectedValue(error);

    await expect(getVorgangliste()).rejects.toThrow('Network Error');

    expect(consoleErrorSpy).toHaveBeenCalledWith(error);
    expect(consoleErrorSpy).toHaveBeenCalledTimes(1);

    consoleErrorSpy.mockRestore();
  });

  it('should throw if response data does not match schema', async () => {
    const invalidData = [{ foo: 'bar' }];

    mockedAxios.get.mockResolvedValue({ data: invalidData });

    await expect(getVorgangliste()).rejects.toThrow('Invalid input');

    expect(mockedAxios.get).toHaveBeenCalledWith('/api/vorgang');
  });
});
