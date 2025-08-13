import { antragstellerSchema } from './Antragsteller.types.ts';

describe('antragstellerSchema', () => {
  it('should parse a valid Antragsteller object', () => {
    const validData = {
      id: '123',
      vorname: 'Max',
      nachname: 'Mustermann',
      abteilung: 'IT',
    };

    const result = antragstellerSchema.safeParse(validData);

    expect(result.success).toBeTruthy();
    expect(result.data).toEqual(validData);
  });

  it('should throw if a required field is missing', () => {
    const invalidData = {
      id: '123',
      vorname: 'Max',
      nachname: 'Mustermann',
    };

    const result = antragstellerSchema.safeParse(invalidData);
    expect(result.success).toBeFalsy();
    expect(result.error?.issues).toMatchObject([
      {
        path: ['abteilung'],
        code: 'invalid_type',
      },
    ]);
  });

  it('should throw if a field has wrong type', () => {
    const invalidData = {
      id: 123,
      vorname: 'Max',
      nachname: 'Mustermann',
      abteilung: 'IT',
    };

    const result = antragstellerSchema.safeParse(invalidData);
    expect(result.success).toBeFalsy();
    expect(result.error?.issues).toMatchObject([
      {
        path: ['id'],
        code: 'invalid_type',
      },
    ]);
  });
});
