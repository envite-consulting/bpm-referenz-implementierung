import type { Config } from 'jest';

const config: Config = {
  preset: 'ts-jest',

  testEnvironment: 'jest-environment-jsdom',

  clearMocks: true,

  collectCoverage: true,
  coverageDirectory: 'coverage',
  coverageProvider: 'v8',

  moduleNameMapper: {
    '^@ui/(.*)$': '<rootDir>/src/infrastructure/components/$1',
    '^@bestellung/(.*)$': '<rootDir>/src/pages/Bestellung/$1',
    '^@antragsteller/(.*)$':
      '<rootDir>/src/pages/Bestellung/components/Antragsteller/$1',
    '^@root/(.*)$': '<rootDir>/src/$1',
  },

  setupFilesAfterEnv: ['<rootDir>/jest.setup.ts'],

  transform: {
    '^.+\\.tsx?$': [
      'ts-jest',
      {
        tsconfig: 'tsconfig.app.json',
      },
    ],
  },
};

export default config;
