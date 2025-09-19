import axios from 'axios';
import z from 'zod/v4';
import {
  type Vorgang,
  vorgangAbfrageSchema,
} from '@aufgabenSidebar/Vorgang.types.ts';

export async function getVorgangliste() {
  try {
    const response = await axios.get('/api/vorgang');

    const vorgangList: Vorgang[] = z
      .array(vorgangAbfrageSchema)
      .parse(response.data);
    return vorgangList;
  } catch (error) {
    console.error(error);
    throw error;
  }
}
