import axios from 'axios';
import z from 'zod/v4';
import {
  type FolgeaufgabeVorschlag,
  folgeaufgabeVorschlagSchema,
} from '@aufgabenliste/Aufgabe.types.ts';

export async function getFolgeaufgabeVorschlag(
  vorgangId: string,
  letztesArbeitspaket?: string | null,
): Promise<FolgeaufgabeVorschlag | null> {
  try {
    const response = await axios.get(
      `/api/vorgang/${vorgangId}/folgeaufgabe-vorschlag`,
      { params: { letztesArbeitspaket: letztesArbeitspaket ?? undefined } },
    );

    return z.parse(folgeaufgabeVorschlagSchema, response.data);
  } catch (error) {
    if (axios.isAxiosError(error) && error.response?.status === 404) {
      return null;
    }
    console.error(error);
    throw error;
  }
}
