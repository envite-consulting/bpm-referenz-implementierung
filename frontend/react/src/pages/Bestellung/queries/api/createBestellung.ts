import axios from 'axios';
import {
  bestellungsabfrageSchema,
  type Bestellungserfassung,
} from '@/pages/Bestellung/Bestellung.types.ts';

export async function createBestellung(bestellung: Bestellungserfassung) {
  try {
    const response = await axios.post('/bestellung', bestellung);

    return bestellungsabfrageSchema.parse(response.data);
  } catch (error) {
    console.error('Fehler beim Erstellen der Bestellung:', error);
    throw error;
  }
}
