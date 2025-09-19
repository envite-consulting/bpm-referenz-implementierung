import type { Meta, StoryObj } from '@storybook/react-vite';
import type { Vorgang } from '@aufgabenSidebar/Vorgang.types.ts';
import type { Aufgabe } from '@aufgabenliste/Aufgabe.types.ts';
import { VorgangItem } from '@vorgangItem/VorgangItem.tsx';
import { http, HttpResponse } from 'msw';
import { MemoryRouter } from 'react-router-dom';

const meta: Meta<typeof VorgangItem> = {
  title: 'Aufgabenliste/AufgabenSidebar/VorgangItem',
  component: VorgangItem,
  tags: ['autodocs'],
};
export default meta;

type Story = StoryObj<typeof VorgangItem>;

const baseVorgang: Vorgang = {
  id: '1',
  fachlicherSchluessel: 'V2024-001',
  fachdaten: {
    antragstellerVorname: 'Max',
    antragstellerNachname: 'Mustermann',
    fahrzeugHersteller: 'Volkswagen',
    fahrzeugModell: 'Golf GTI',
  },
};

const sampleAufgaben: Aufgabe[] = [
  {
    id: '1',
    name: 'Antrag prüfen',
    formularreferenz: 'formular-1',
    bearbeiter: 'admin',
    erstelldatum: new Date('2024-01-15'),
  },
  {
    id: '2',
    name: 'Dokumente nachreichen',
    formularreferenz: 'formular-2',
    bearbeiter: null,
    erstelldatum: new Date('2024-01-16'),
  },
  {
    id: '3',
    name: 'Genehmigung erteilen',
    formularreferenz: 'formular-3',
    bearbeiter: 'supervisor',
    erstelldatum: new Date('2024-01-17'),
  },
];

export const Default: Story = {
  args: {
    vorgang: baseVorgang,
  },
  parameters: {
    msw: {
      handlers: [
        http.get('/api/aufgabe', () => HttpResponse.json(sampleAufgaben)),
      ],
    },
  },
  render: (args) => (
    <MemoryRouter initialEntries={['/']}>
      <VorgangItem {...args} />
    </MemoryRouter>
  ),
};

export const Loading: Story = {
  args: {
    vorgang: baseVorgang,
  },
  parameters: {
    msw: {
      handlers: [
        http.get(
          '/api/aufgabe',
          () =>
            new Promise((resolve) =>
              setTimeout(
                () => resolve(HttpResponse.json(sampleAufgaben)),
                2000,
              ),
            ),
        ),
      ],
    },
  },
  render: (args) => (
    <MemoryRouter initialEntries={['/']}>
      <VorgangItem {...args} />
    </MemoryRouter>
  ),
};

export const Error: Story = {
  args: {
    vorgang: baseVorgang,
  },
  parameters: {
    msw: {
      handlers: [
        http.get('/api/aufgabe', () =>
          HttpResponse.json({ message: 'Fehler' }, { status: 500 }),
        ),
      ],
    },
  },
  render: (args) => (
    <MemoryRouter initialEntries={['/']}>
      <VorgangItem {...args} />
    </MemoryRouter>
  ),
};

export const Empty: Story = {
  args: {
    vorgang: baseVorgang,
  },
  parameters: {
    msw: {
      handlers: [http.get('/api/aufgabe', () => HttpResponse.json([]))],
    },
  },
  render: (args) => (
    <MemoryRouter initialEntries={['/']}>
      <VorgangItem {...args} />
    </MemoryRouter>
  ),
};
