import type { Meta, StoryObj } from '@storybook/react-vite';
import { http, HttpResponse } from 'msw';
import { AufgabenSidebar } from '@aufgabenSidebar/AufgabenSidebar.tsx';
import { MemoryRouter } from 'react-router-dom';
import type { Aufgabe } from '@aufgabenliste/Aufgabe.types.ts';

const meta: Meta<typeof AufgabenSidebar> = {
  title: 'Aufgabenliste/AufgabenSidebar',
  component: AufgabenSidebar,
  tags: ['autodocs'],
};
export default meta;

type Story = StoryObj<typeof AufgabenSidebar>;

const vorgaenge = [
  {
    id: '1',
    fachlicherSchluessel: 'V1',
    fachdaten: {
      antragstellerVorname: 'Max',
      antragstellerNachname: 'Mustermann',
      fahrzeugHersteller: 'VW',
      fahrzeugModell: 'Golf',
    },
  },
  {
    id: '2',
    fachlicherSchluessel: 'V2',
    fachdaten: {
      antragstellerVorname: 'Erika',
      antragstellerNachname: 'Musterfrau',
      fahrzeugHersteller: 'BMW',
      fahrzeugModell: 'X3',
    },
  },
];

const sampleAufgaben: Aufgabe[] = [
  {
    id: '1',
    name: 'Antrag prüfen',
    formularreferenz: 'formular-1',
    vorgangId: 'vorgang-1',
    bearbeiter: 'admin',
    erstelldatum: new Date('2024-01-15'),
  },
  {
    id: '2',
    name: 'Dokumente nachreichen',
    formularreferenz: 'formular-2',
    vorgangId: 'vorgang-1',
    bearbeiter: null,
    erstelldatum: new Date('2024-01-16'),
  },
  {
    id: '3',
    name: 'Genehmigung erteilen',
    formularreferenz: 'formular-3',
    vorgangId: 'vorgang-1',
    bearbeiter: 'supervisor',
    erstelldatum: new Date('2024-01-17'),
  },
];

export const Default: Story = {
  parameters: {
    msw: {
      handlers: [
        http.get('/api/vorgang', () => HttpResponse.json(vorgaenge)),
        http.get('/api/aufgabe', () => HttpResponse.json(sampleAufgaben)),
      ],
    },
  },
  render: () => (
    <MemoryRouter initialEntries={['/']}>
      <AufgabenSidebar />
    </MemoryRouter>
  ),
};

export const Loading: Story = {
  parameters: {
    msw: {
      handlers: [
        http.get(
          '/api/vorgang',
          () =>
            new Promise((resolve) =>
              setTimeout(() => resolve(HttpResponse.json(vorgaenge)), 2000),
            ),
        ),
        http.get('/api/aufgabe', () => HttpResponse.json(sampleAufgaben)),
      ],
    },
  },
  render: () => (
    <MemoryRouter initialEntries={['/']}>
      <AufgabenSidebar />
    </MemoryRouter>
  ),
};

export const Error: Story = {
  parameters: {
    msw: {
      handlers: [
        http.get('/api/vorgang', () =>
          HttpResponse.json({ message: 'Fehler' }, { status: 500 }),
        ),
      ],
    },
  },
  render: () => (
    <MemoryRouter initialEntries={['/']}>
      <AufgabenSidebar />
    </MemoryRouter>
  ),
};

export const Empty: Story = {
  parameters: {
    msw: {
      handlers: [http.get('/api/vorgang', () => HttpResponse.json([]))],
    },
  },
  render: () => (
    <MemoryRouter initialEntries={['/']}>
      <AufgabenSidebar />
    </MemoryRouter>
  ),
};
