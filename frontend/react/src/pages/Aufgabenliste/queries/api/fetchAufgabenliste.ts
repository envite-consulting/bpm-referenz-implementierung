import axios from 'axios';
import z from 'zod/v4';
import {
  type Aufgabe,
  aufgabeAbfrageSchema,
} from '@aufgabenliste/Aufgabe.types.ts';

export async function getAufgabenliste() {
  try {
    const response = await axios.get('/api/aufgabe');

    const aufgabenList: Aufgabe[] = z
      .array(aufgabeAbfrageSchema)
      .parse(response.data);
    return aufgabenList;
  } catch (error) {
    console.error(error);
    throw error;
  }
}
