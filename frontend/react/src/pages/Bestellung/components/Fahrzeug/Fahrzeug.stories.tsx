import type { Meta, StoryObj } from '@storybook/react-vite';
import { Fahrzeug } from '@fahrzeug/Fahrzeug.tsx';
import { http, HttpResponse } from 'msw';

const meta: Meta<typeof Fahrzeug> = {
  title: 'Bestellung/Fahrzeug',
  component: Fahrzeug,
  tags: ['autodocs'],
  argTypes: {
    onSelectId: { action: 'selected' },
  },
  args: {
    onSelectId: () => {},
  },
};
export default meta;

type Story = StoryObj<typeof Fahrzeug>;

const testData = [
  {
    id: '1',
    hersteller: 'Audi',
    modell: 'A5',
    jahr: 1990,
  },
  { id: '2', hersteller: 'BMW', modell: 'i5', jahr: 2023 },
];

export const Default: Story = {
  parameters: {
    msw: {
      handlers: [
        http.get('/api/fahrzeug', () => {
          return new Promise((resolve) => {
            setTimeout(() => {
              resolve(HttpResponse.json(testData));
            }, 2000);
          });
        }),
      ],
    },
  },
};

export const Error: Story = {
  parameters: {
    msw: {
      handlers: [
        http.get('/api/fahrzeug', () => {
          return HttpResponse.json(
            { message: 'Fehler beim Laden der Fahrzeug' },
            { status: 500 },
          );
        }),
      ],
    },
  },
};

export const EmptyOptions: Story = {
  parameters: {
    msw: {
      handlers: [
        http.get('/api/fahrzeug', () => {
          return HttpResponse.json([]);
        }),
      ],
    },
  },
};
