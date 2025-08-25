import axios from 'axios';
import { z } from 'zod';
import { type Fahrzeug, fahrzeugSchema } from '@fahrzeug/Fahrzeug.types.ts';
import type { DropdownOption } from '@ui/DropDownMenu/DropdownMenu.tsx';

export async function getFahrzeug() {
  try {
    const response = await axios.get('/api/fahrzeug');

    const fahrzeugList: Fahrzeug[] = z
      .array(fahrzeugSchema)
      .parse(response.data);
    return fahrzeugList;
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export async function getFahrzeugOptions(): Promise<DropdownOption<string>[]> {
  const fahrzeugList = await getFahrzeug();

  return fahrzeugList.map((fahrzeug) => ({
    label: `${fahrzeug.hersteller} ${fahrzeug.modell} (${fahrzeug.jahr})`,
    value: fahrzeug.id,
  }));
}
