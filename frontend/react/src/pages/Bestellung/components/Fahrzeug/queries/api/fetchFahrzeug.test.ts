import axios from 'axios';
import { getFahrzeug, getFahrzeugOptions } from './fetchFahrzeug.ts';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('getFahrzeug', () => {
  it('should fetch and parse Fahrzeug data successfully', async () => {
    const mockData = [
      {
        id: '1',
        hersteller: 'Audi',
        modell: 'A5',
        jahr: 1990,
      },
      { id: '2', hersteller: 'VW', modell: 'ID4', jahr: 2024 },
    ];

    mockedAxios.get.mockResolvedValue({ data: mockData });

    const result = await getFahrzeug();

    expect(result).toEqual(mockData);
    expect(mockedAxios.get).toHaveBeenCalledWith('/api/fahrzeug');
  });

  it('should throw an error when the request fails', async () => {
    const error = new Error('Network Error');

    const consoleErrorSpy = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});

    mockedAxios.get.mockRejectedValue(error);

    await expect(getFahrzeug()).rejects.toThrow('Network Error');

    expect(consoleErrorSpy).toHaveBeenCalledWith(error);
    expect(consoleErrorSpy).toHaveBeenCalledTimes(1);

    consoleErrorSpy.mockRestore();
  });

  it('should throw if response data does not match schema', async () => {
    const invalidData = [{ foo: 'bar' }];

    mockedAxios.get.mockResolvedValue({ data: invalidData });

    await expect(getFahrzeug()).rejects.toThrow('Invalid input');

    expect(mockedAxios.get).toHaveBeenCalledWith('/api/fahrzeug');
  });
});

describe('getFahrzeugOptions', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should transform Fahrzeug data to dropdown options successfully', async () => {
    const mockData = [
      {
        id: '1',
        hersteller: 'Audi',
        modell: 'A5',
        jahr: 1990,
      },
      { id: '2', hersteller: 'Anna', modell: 'Müller', jahr: 2023 },
    ];
    const expectedOptions = [
      {
        label: 'Audi A5 (1990)',
        value: '1',
      },
      {
        label: 'Anna Müller (2023)',
        value: '2',
      },
    ];

    mockedAxios.get.mockResolvedValue({ data: mockData });
    const result = await getFahrzeugOptions();

    expect(result).toEqual(expectedOptions);
    expect(mockedAxios.get).toHaveBeenCalledWith('/api/fahrzeug');
  });

  it('should handle empty Fahrzeug list', async () => {
    const mockData: [] = [];

    mockedAxios.get.mockResolvedValue({ data: mockData });
    const result = await getFahrzeugOptions();

    expect(result).toEqual([]);
    expect(mockedAxios.get).toHaveBeenCalledWith('/api/fahrzeug');
  });

  it('should propagate error when getFahrzeug fails', async () => {
    const error = new Error('Network Error');
    const consoleErrorSpy = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});

    mockedAxios.get.mockRejectedValue(error);

    await expect(getFahrzeugOptions()).rejects.toThrow('Network Error');
    expect(consoleErrorSpy).toHaveBeenCalledWith(error);
    consoleErrorSpy.mockRestore();
  });
});
