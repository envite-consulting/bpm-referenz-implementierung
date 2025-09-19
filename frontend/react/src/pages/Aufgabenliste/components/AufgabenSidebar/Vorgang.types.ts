import * as z from 'zod';

export const fachdatenAbfrageSchema = z.object({
  antragstellerVorname: z.string(),
  antragstellerNachname: z.string(),
  fahrzeugHersteller: z.string(),
  fahrzeugModell: z.string(),
});

export const vorgangAbfrageSchema = z.object({
  id: z.string(),
  fachlicherSchluessel: z.string(),
  fachdaten: fachdatenAbfrageSchema,
});

export type Vorgang = z.infer<typeof vorgangAbfrageSchema>;
