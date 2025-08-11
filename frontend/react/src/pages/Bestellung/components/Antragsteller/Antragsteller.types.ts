import * as z from 'zod';

export const antragstellerSchema = z.object({
  id: z.string(),
  vorname: z.string(),
  nachname: z.string(),
  abteilung: z.string(),
});

export type Antragsteller = z.infer<typeof antragstellerSchema>;
