import { fahrzeugSchema } from './Fahrzeug.types.ts';

describe('fahrzeugSchema', () => {
  it('should parse a valid Fahrzeug object', () => {
    const validData = {
      id: '123',
      hersteller: 'Audi',
      modell: 'A5',
      jahr: 1990,
    };

    const result = fahrzeugSchema.safeParse(validData);

    expect(result.success).toBeTruthy();
    expect(result.data).toEqual(validData);
  });

  it('should throw if a required field is missing', () => {
    const invalidData = {
      id: '123',
      hersteller: 'Audi',
      modell: 'A5',
    };

    const result = fahrzeugSchema.safeParse(invalidData);
    expect(result.success).toBeFalsy();
    expect(result.error?.issues).toMatchObject([
      {
        path: ['jahr'],
        code: 'invalid_type',
      },
    ]);
  });

  it('should throw if a field has wrong type', () => {
    const invalidData = {
      id: 123,
      hersteller: 'Audi',
      modell: 'A5',
      jahr: 1990,
    };

    const result = fahrzeugSchema.safeParse(invalidData);
    expect(result.success).toBeFalsy();
    expect(result.error?.issues).toMatchObject([
      {
        path: ['id'],
        code: 'invalid_type',
      },
    ]);
  });
});
