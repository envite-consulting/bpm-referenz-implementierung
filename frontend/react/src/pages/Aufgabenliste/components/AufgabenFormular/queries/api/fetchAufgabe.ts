import axios from 'axios';
import z from 'zod/v4';
import {
  type Aufgabe,
  aufgabeAbfrageSchema,
} from '@aufgabenliste/Aufgabe.types.ts';

export async function getAufgabe(id: string) {
  try {
    const response = await axios.get(`/api/aufgabe/${id}`);

    const aufgabe: Aufgabe = z.parse(aufgabeAbfrageSchema, response.data);
    return aufgabe;
  } catch (error) {
    console.error(error);
    throw error;
  }
}
