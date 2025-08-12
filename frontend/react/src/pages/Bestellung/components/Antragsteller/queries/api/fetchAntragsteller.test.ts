import axios from 'axios';
import {
  getAntragsteller,
  getAntragstellerOptions,
} from './fetchAntragsteller.ts';

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
    expect(mockedAxios.get).toHaveBeenCalledWith('/api/antragsteller');
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

    expect(mockedAxios.get).toHaveBeenCalledWith('/api/antragsteller');
  });
});

describe('getAntragstellerOptions', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should transform Antragsteller data to dropdown options successfully', async () => {
    const mockData = [
      {
        id: '1',
        vorname: 'Max',
        nachname: 'Mustermann',
        abteilung: 'Vertrieb',
      },
      { id: '2', vorname: 'Anna', nachname: 'Müller', abteilung: 'Marketing' },
    ];
    const expectedOptions = [
      {
        label: 'Max Mustermann (Vertrieb)',
        value: '1',
      },
      {
        label: 'Anna Müller (Marketing)',
        value: '2',
      },
    ];

    mockedAxios.get.mockResolvedValue({ data: mockData });
    const result = await getAntragstellerOptions();

    expect(result).toEqual(expectedOptions);
    expect(mockedAxios.get).toHaveBeenCalledWith('/api/antragsteller');
  });

  it('should handle empty Antragsteller list', async () => {
    const mockData: [] = [];

    mockedAxios.get.mockResolvedValue({ data: mockData });
    const result = await getAntragstellerOptions();

    expect(result).toEqual([]);
    expect(mockedAxios.get).toHaveBeenCalledWith('/api/antragsteller');
  });

  it('should propagate error when getAntragsteller fails', async () => {
    const error = new Error('Network Error');
    const consoleErrorSpy = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});

    mockedAxios.get.mockRejectedValue(error);

    await expect(getAntragstellerOptions()).rejects.toThrow('Network Error');
    expect(consoleErrorSpy).toHaveBeenCalledWith(error);
    consoleErrorSpy.mockRestore();
  });
});
