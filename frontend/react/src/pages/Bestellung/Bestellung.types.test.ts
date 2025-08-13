import {
  bestellungsabfrageSchema,
  bestellungserfassungSchema,
} from './Bestellung.types.ts';

describe('bestellungsabfrageSchema', () => {
  it('should parse valid data', () => {
    const validData = {
      id: '123',
      antragstellerreferenz: 'ref-1',
      fahrzeugreferenz: 'ref-2',
      bestelldatum: new Date(),
      status: 'GENEHMIGT',
    };

    const result = bestellungsabfrageSchema.safeParse(validData);

    expect(result.success).toBeTruthy();
    expect(result.data).toEqual(validData);
  });

  it('should throw if status is invalid', () => {
    const invalidData = {
      id: '123',
      antragstellerreferenz: 'ref-1',
      fahrzeugreferenz: 'ref-2',
      bestelldatum: new Date(),
      status: 'INVALID',
    };

    const result = bestellungsabfrageSchema.safeParse(invalidData);
    expect(result.success).toBeFalsy();
    expect(result.error?.issues).toMatchObject([
      {
        path: ['status'],
        code: 'invalid_value',
      },
    ]);
  });

  it('should throw if date is invalid', () => {
    const invalidData = {
      id: '123',
      antragstellerreferenz: 'ref-1',
      fahrzeugreferenz: 'ref-2',
      bestelldatum: 'invalid',
      status: 'GENEHMIGT',
    };

    const result = bestellungsabfrageSchema.safeParse(invalidData);
    expect(result.success).toBeFalsy();
    expect(result.error?.issues).toMatchObject([
      {
        path: ['bestelldatum'],
        code: 'invalid_type',
      },
    ]);
  });
});

describe('bestellungserfassungSchema', () => {
  it('should parse valid data', () => {
    const validData = {
      antragstellerreferenz: 'ref-1',
      fahrzeugreferenz: 'ref-2',
    };

    const result = bestellungserfassungSchema.safeParse(validData);

    expect(result.success).toBeTruthy();
    expect(result.data).toEqual(validData);
  });

  it('should throw if antragstellerreferenz is missing', () => {
    const invalidData = {
      fahrzeugreferenz: 'ref-2',
    };

    const result = bestellungserfassungSchema.safeParse(invalidData);
    expect(result.success).toBeFalsy();
    expect(result.error?.issues).toMatchObject([
      {
        path: ['antragstellerreferenz'],
        code: 'invalid_type',
      },
    ]);
  });

  it('should throw if fahrzeugreferenz is missing', () => {
    const invalidData = {
      antragstellerreferenz: 'ref-1',
    };

    const result = bestellungserfassungSchema.safeParse(invalidData);
    expect(result.success).toBeFalsy();
    expect(result.error?.issues).toMatchObject([
      {
        path: ['fahrzeugreferenz'],
        code: 'invalid_type',
      },
    ]);
  });
});
