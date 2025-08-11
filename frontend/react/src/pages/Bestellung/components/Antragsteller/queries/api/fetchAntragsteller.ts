import axios from 'axios';
import { z } from 'zod';
import { antragstellerSchema } from '@/pages/Bestellung/components/Antragsteller/Antragsteller.types.ts';

export async function getAntragsteller() {
  try {
    const response = await axios.get('/antragsteller');
    return z.array(antragstellerSchema).parse(response.data);
  } catch (error) {
    console.error(error);
    throw error;
  }
}
