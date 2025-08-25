import type { Meta, StoryObj } from '@storybook/react-vite';
import { Antragsteller } from '@antragsteller/Antragsteller.tsx';
import { http, HttpResponse } from 'msw';

const meta: Meta<typeof Antragsteller> = {
  title: 'Bestellung/Antragsteller',
  component: Antragsteller,
  tags: ['autodocs'],
  argTypes: {
    onSelectId: { action: 'selected' },
  },
  args: {
    onSelectId: () => {},
  },
};
export default meta;

type Story = StoryObj<typeof Antragsteller>;

const testData = [
  {
    id: '1',
    vorname: 'Max',
    nachname: 'Mustermann',
    abteilung: 'IT-Abteilung',
  },
  { id: '2', vorname: 'Anna', nachname: 'Schmidt', abteilung: 'Marketing' },
];

export const Default: Story = {
  parameters: {
    msw: {
      handlers: [
        http.get('/api/antragsteller', () => {
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
        http.get('/api/antragsteller', () => {
          return HttpResponse.json(
            { message: 'Fehler beim Laden der Antragsteller' },
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
        http.get('/api/antragsteller', () => {
          return HttpResponse.json([]);
        }),
      ],
    },
  },
};
