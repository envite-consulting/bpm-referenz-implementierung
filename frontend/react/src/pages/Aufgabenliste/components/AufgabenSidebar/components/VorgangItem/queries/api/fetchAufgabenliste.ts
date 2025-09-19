import axios from 'axios';
import z from 'zod/v4';
import {
  type Aufgabe,
  aufgabeAbfrageSchema,
} from '@aufgabenliste/Aufgabe.types.ts';

export async function getAufgabenlisteByVorgang(vorgangId: string) {
  try {
    const response = await axios.get(`/api/aufgabe?vorgangId=${vorgangId}`);

    const vorgangList: Aufgabe[] = z
      .array(aufgabeAbfrageSchema)
      .parse(response.data);
    return vorgangList;
  } catch (error) {
    console.error(error);
    throw error;
  }
}
