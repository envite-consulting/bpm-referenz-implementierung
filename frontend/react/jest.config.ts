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
    '^@fahrzeug/(.*)$': '<rootDir>/src/pages/Bestellung/components/Fahrzeug/$1',
    '^@aufgabenliste/(.*)$': '<rootDir>/src/pages/Aufgabenliste/$1',
    '^@aufgabenSidebar/(.*)$':
      '<rootDir>/src/pages/Aufgabenliste/components/AufgabenSidebar/$1',
    '^@aufgabenFormular/(.*)$':
      '<rootDir>/src/pages/Aufgabenliste/components/AufgabenFormular/$1',
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
