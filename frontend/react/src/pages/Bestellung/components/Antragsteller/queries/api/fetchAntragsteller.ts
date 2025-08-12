import axios from 'axios';
import { z } from 'zod';
import {
  type Antragsteller,
  antragstellerSchema,
} from '@antragsteller/Antragsteller.types.ts';
import type { DropdownOption } from '@ui/DropDownMenu/DropdownMenu.tsx';

export async function getAntragsteller() {
  try {
    const response = await axios.get('/api/antragsteller');
    const antragstellerList: Antragsteller[] = z
      .array(antragstellerSchema)
      .parse(response.data);
    return antragstellerList;
  } catch (error) {
    console.error(error);
    throw error;
  }
}

export async function getAntragstellerOptions(): Promise<
  DropdownOption<string>[]
> {
  const antragstellerList = await getAntragsteller();

  return antragstellerList.map((antragsteller) => ({
    label: `${antragsteller.vorname} ${antragsteller.nachname} (${antragsteller.abteilung})`,
    value: antragsteller.id,
  }));
}
