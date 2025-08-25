import { aufgabeAbfrageSchema } from './Aufgabe.types.ts';

describe('aufgabenItemAbfrageSchema', () => {
  it('should parse a valid AufgabenItemAbfrage object', () => {
    const validData = {
      id: '1',
      name: 'Aufgabe 1',
      bearbeiter: 'Assignee',
      erstelldatum: new Date(),
      formularreferenz: 'Ref1',
    };

    const result = aufgabeAbfrageSchema.safeParse(validData);

    expect(result.success).toBeTruthy();
    expect(result.data).toEqual(validData);
  });

  it('should throw if a required field is missing', () => {
    const invalidData = {
      name: 'Aufgabe 1',
      bearbeiter: 'Assignee',
      erstelldatum: new Date(),
      formularreferenz: 'Ref1',
    };

    const result = aufgabeAbfrageSchema.safeParse(invalidData);
    expect(result.success).toBeFalsy();
    expect(result.error?.issues).toMatchObject([
      {
        path: ['id'],
        code: 'invalid_type',
      },
    ]);
  });

  it('should throw if a field has wrong type', () => {
    const invalidData = {
      id: 1,
      name: 'Aufgabe 1',
      bearbeiter: 'Assignee',
      erstelldatum: new Date(),
      formularreferenz: 'Ref1',
    };

    const result = aufgabeAbfrageSchema.safeParse(invalidData);
    expect(result.success).toBeFalsy();
    expect(result.error?.issues).toMatchObject([
      {
        path: ['id'],
        code: 'invalid_type',
      },
    ]);
  });
});
