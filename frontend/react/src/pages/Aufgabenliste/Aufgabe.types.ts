import * as z from 'zod';

export const aufgabeAbfrageSchema = z.object({
  id: z.string(),
  name: z.string(),
  bearbeiter: z.string().nullable().optional(),
  erstelldatum: z.coerce.date(),
  formularreferenz: z.string(),
  vorgangId: z.string(),
  arbeitspaket: z.string().nullable().optional(),
});

export type Aufgabe = z.infer<typeof aufgabeAbfrageSchema>;

export const folgeaufgabeVorschlagSchema = z.object({
  aufgabeId: z.string(),
  vorgangId: z.string(),
  arbeitspaket: z.string().nullable().optional(),
});

export type FolgeaufgabeVorschlag = z.infer<typeof folgeaufgabeVorschlagSchema>;
