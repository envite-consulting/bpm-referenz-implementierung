import { vorgangAbfrageSchema } from './Vorgang.types.ts';

describe('vorgangAbfrageSchema', () => {
  it('should parse a valid Vorgang object', () => {
    const validData = {
      id: 'v1',
      fachlicherSchluessel: 'SCHLUESSEL-123',
      fachdaten: {
        antragstellerVorname: 'Max',
        antragstellerNachname: 'Mustermann',
        fahrzeugHersteller: 'VW',
        fahrzeugModell: 'Golf',
      },
    };

    const result = vorgangAbfrageSchema.safeParse(validData);

    expect(result.success).toBeTruthy();
    expect(result.data).toEqual(validData);
  });

  it('should throw if a required top-level field is missing', () => {
    const invalidData = {
      fachlicherSchluessel: 'SCHLUESSEL-123',
      fachdaten: {
        antragstellerVorname: 'Max',
        antragstellerNachname: 'Mustermann',
        fahrzeugHersteller: 'VW',
        fahrzeugModell: 'Golf',
      },
    };

    const result = vorgangAbfrageSchema.safeParse(invalidData);

    expect(result.success).toBeFalsy();
    expect(result.error?.issues).toMatchObject([
      {
        path: ['id'],
        code: 'invalid_type',
      },
    ]);
  });

  it('should throw if a required nested field is missing', () => {
    const invalidData = {
      id: 'v1',
      fachlicherSchluessel: 'SCHLUESSEL-123',
      fachdaten: {
        antragstellerNachname: 'Mustermann',
        fahrzeugHersteller: 'VW',
        fahrzeugModell: 'Golf',
      },
    };

    const result = vorgangAbfrageSchema.safeParse(invalidData);

    expect(result.success).toBeFalsy();
    expect(result.error?.issues).toMatchObject([
      {
        path: ['fachdaten', 'antragstellerVorname'],
        code: 'invalid_type',
      },
    ]);
  });

  it('should throw if a nested field has wrong type', () => {
    const invalidData = {
      id: 'v1',
      fachlicherSchluessel: 'SCHLUESSEL-123',
      fachdaten: {
        antragstellerVorname: 'Max',
        antragstellerNachname: 'Mustermann',
        fahrzeugHersteller: 123,
        fahrzeugModell: 'Golf',
      },
    };

    const result = vorgangAbfrageSchema.safeParse(invalidData);

    expect(result.success).toBeFalsy();
    expect(result.error?.issues).toMatchObject([
      {
        path: ['fachdaten', 'fahrzeugHersteller'],
        code: 'invalid_type',
      },
    ]);
  });
});
