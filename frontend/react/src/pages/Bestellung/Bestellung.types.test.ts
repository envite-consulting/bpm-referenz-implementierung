import {
  bestellungsabfrageSchema,
  bestellungserfassungSchema,
} from './Bestellung.types.ts';
import { ZodError } from 'zod';

describe('bestellungsabfrageSchema', () => {
  it('should parse valid data', () => {
    const validData = {
      id: '123',
      antragstellerreferenz: 'ref-1',
      fahrzeugreferenz: 'ref-2',
      bestelldatum: new Date(),
      status: 'GENEHMIGT',
    };

    const result = bestellungsabfrageSchema.parse(validData);

    expect(result).toEqual(validData);
  });

  it('should throw if status is invalid', () => {
    const invalidData = {
      id: '123',
      antragstellerreferenz: 'ref-1',
      fahrzeugreferenz: 'ref-2',
      bestelldatum: new Date(),
      status: 'INVALID',
    };

    expect(() => bestellungsabfrageSchema.parse(invalidData)).toThrow(ZodError);
  });

  it('should throw if date is invalid', () => {
    const invalidData = {
      id: '123',
      antragstellerreferenz: 'ref-1',
      fahrzeugreferenz: 'ref-2',
      bestelldatum: 'invalid',
      status: 'GENEHMIGT',
    };

    expect(() => bestellungsabfrageSchema.parse(invalidData)).toThrow(ZodError);
  });
});

describe('bestellungserfassungSchema', () => {
  it('should parse valid data', () => {
    const validData = {
      antragstellerreferenz: 'ref-1',
      fahrzeugreferenz: 'ref-2',
    };

    const result = bestellungserfassungSchema.parse(validData);

    expect(result).toEqual(validData);
  });

  it('should throw if antragstellerreferenz is missing', () => {
    const invalidData = {
      fahrzeugreferenz: 'ref-2',
    };

    expect(() => bestellungserfassungSchema.parse(invalidData)).toThrow();
  });

  it('should throw if fahrzeugreferenz is missing', () => {
    const invalidData = {
      antragstellerreferenz: 'ref-1',
    };

    expect(() => bestellungserfassungSchema.parse(invalidData)).toThrow();
  });
});
