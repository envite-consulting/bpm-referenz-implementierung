import type { Meta, StoryObj } from '@storybook/react-vite';
import { http, HttpResponse } from 'msw';
import { AufgabenSidebar } from '@aufgabenSidebar/AufgabenSidebar.tsx';

const meta: Meta<typeof AufgabenSidebar> = {
  title: 'Aufgabenliste/AufgabenSidebar',
  component: AufgabenSidebar,
  tags: ['autodocs'],
  argTypes: {
    setSelected: { action: 'setSelected' },
  },
  args: {
    selected: null,
    setSelected: () => {},
  },
};
export default meta;

type Story = StoryObj<typeof AufgabenSidebar>;

const aufgaben = [
  {
    id: '1',
    name: 'Rechnung prüfen',
    bearbeiter: null,
    erstelldatum: new Date().toISOString(),
    formularreferenz: 'Test',
  },
  {
    id: '2',
    name: 'Lieferung bestätigen',
    bearbeiter: 'Max',
    erstelldatum: new Date().toISOString(),
    formularreferenz: 'Test',
  },
];

export const Default: Story = {
  parameters: {
    msw: {
      handlers: [http.get('/api/aufgabe', () => HttpResponse.json(aufgaben))],
    },
  },
};

export const Loading: Story = {
  parameters: {
    msw: {
      handlers: [
        http.get(
          '/api/aufgabe',
          () =>
            new Promise((resolve) =>
              setTimeout(() => resolve(HttpResponse.json(aufgaben)), 2000),
            ),
        ),
      ],
    },
  },
};

export const Error: Story = {
  parameters: {
    msw: {
      handlers: [
        http.get('/api/aufgabe', () =>
          HttpResponse.json({ message: 'Fehler' }, { status: 500 }),
        ),
      ],
    },
  },
};

export const Empty: Story = {
  parameters: {
    msw: {
      handlers: [http.get('/api/aufgabe', () => HttpResponse.json([]))],
    },
  },
};
