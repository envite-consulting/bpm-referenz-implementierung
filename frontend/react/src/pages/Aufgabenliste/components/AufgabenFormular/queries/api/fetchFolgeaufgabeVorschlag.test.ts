import axios from 'axios';
import { getFolgeaufgabeVorschlag } from './fetchFolgeaufgabeVorschlag.ts';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('getFolgeaufgabeVorschlag', () => {
  const vorgangId = 'vorgang-1';
  const url = `/api/vorgang/${vorgangId}/folgeaufgabe-vorschlag`;

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should fetch and parse the Vorschlag with the last Arbeitspaket', async () => {
    const vorschlag = {
      aufgabeId: 'aufgabe-2',
      vorgangId,
      arbeitspaket: 'FAHRZEUG',
    };
    mockedAxios.get.mockResolvedValue({ data: vorschlag });

    const result = await getFolgeaufgabeVorschlag(vorgangId, 'FAHRZEUG');

    expect(result).toEqual(vorschlag);
    expect(mockedAxios.get).toHaveBeenCalledWith(url, {
      params: { letztesArbeitspaket: 'FAHRZEUG' },
    });
  });

  it('should omit the Arbeitspaket when the completed Aufgabe has none', async () => {
    mockedAxios.get.mockResolvedValue({
      data: { aufgabeId: 'aufgabe-2', vorgangId, arbeitspaket: null },
    });

    await getFolgeaufgabeVorschlag(vorgangId, null);

    expect(mockedAxios.get).toHaveBeenCalledWith(url, {
      params: { letztesArbeitspaket: undefined },
    });
  });

  it('should return null when there is no Vorschlag', async () => {
    mockedAxios.isAxiosError.mockReturnValue(true);
    mockedAxios.get.mockRejectedValue({ response: { status: 404 } });

    await expect(getFolgeaufgabeVorschlag(vorgangId)).resolves.toBeNull();
  });

  it('should throw on any other error', async () => {
    const error = { response: { status: 500 } };
    const consoleErrorSpy = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});
    mockedAxios.isAxiosError.mockReturnValue(true);
    mockedAxios.get.mockRejectedValue(error);

    await expect(getFolgeaufgabeVorschlag(vorgangId)).rejects.toBe(error);

    expect(consoleErrorSpy).toHaveBeenCalledWith(error);
    consoleErrorSpy.mockRestore();
  });
});
