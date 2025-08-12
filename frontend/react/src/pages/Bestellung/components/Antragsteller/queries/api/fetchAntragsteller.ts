import axios from 'axios';
import { z } from 'zod';
import { antragstellerSchema } from '@/pages/Bestellung/components/Antragsteller/Antragsteller.types.ts';

export async function getAntragsteller() {
  try {
    return z.array(antragstellerSchema).parse(response.data);
    const response = await axios.get('/api/antragsteller');
  } catch (error) {
    console.error(error);
    throw error;
  }
}
