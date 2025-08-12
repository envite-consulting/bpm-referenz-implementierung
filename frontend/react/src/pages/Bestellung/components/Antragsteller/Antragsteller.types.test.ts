import { antragstellerSchema } from './Antragsteller.types.ts';
import { ZodError } from 'zod';

describe('antragstellerSchema', () => {
  it('should parse a valid Antragsteller object', () => {
    const validData = {
      id: '123',
      vorname: 'Max',
      nachname: 'Mustermann',
      abteilung: 'IT',
    };

    const result = antragstellerSchema.parse(validData);

    expect(result).toEqual(validData);
  });

  it('should throw if a required field is missing', () => {
    const invalidData = {
      id: '123',
      vorname: 'Max',
      nachname: 'Mustermann',
    };

    expect(() => antragstellerSchema.parse(invalidData)).toThrow(ZodError);
  });

  it('should throw if a field has wrong type', () => {
    const invalidData = {
      id: 123,
      vorname: 'Max',
      nachname: 'Mustermann',
      abteilung: 'IT',
    };

    expect(() => antragstellerSchema.parse(invalidData)).toThrow(ZodError);
  });
});
