import * as z from 'zod';

export const aufgabeAbfrageSchema = z.object({
  id: z.string(),
  name: z.string(),
  bearbeiter: z.string().nullable().optional(),
  erstelldatum: z.coerce.date(),
  formularreferenz: z.string(),
});

export type Aufgabe = z.infer<typeof aufgabeAbfrageSchema>;
