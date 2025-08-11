import z from 'zod/v4';

export const StatusEnum = z.enum([
  'ANGELEGT',
  'GENEHMIGT',
  'BESTELLT',
  'AUSGELIEFERT',
  'STORNIERT',
]);

export const bestellungsabfrageSchema = z.object({
  id: z.string(),
  antragstellerreferenz: z.string(),
  fahrzeugreferenz: z.string(),
  bestelldatum: z.coerce.date(),
  status: StatusEnum,
});

export type Bestellungsabfrage = z.infer<typeof bestellungsabfrageSchema>;

export const bestellungserfassungSchema = z.object({
  antragstellerreferenz: z.string(),
  fahrzeugreferenz: z.string(),
});

export type Bestellungserfassung = z.infer<typeof bestellungserfassungSchema>;
