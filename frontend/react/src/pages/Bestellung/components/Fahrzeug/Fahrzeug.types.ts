import * as z from 'zod';

export const fahrzeugSchema = z.object({
  id: z.string(),
  hersteller: z.string(),
  modell: z.string(),
  jahr: z.int(),
});

export type Fahrzeug = z.infer<typeof fahrzeugSchema>;
