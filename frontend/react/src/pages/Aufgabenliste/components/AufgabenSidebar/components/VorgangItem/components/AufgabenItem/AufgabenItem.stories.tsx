import type { Meta, StoryObj } from '@storybook/react-vite';
import { AufgabenItem } from '@vorgangItem/components/AufgabenItem/AufgabenItem.tsx';

const meta: Meta<typeof AufgabenItem> = {
  title: 'Aufgabenliste/AufgabenSidebar/VorgangItem/AufgabenItem',
  component: AufgabenItem,
  tags: ['autodocs'],
};
export default meta;

type Story = StoryObj<typeof AufgabenItem>;

export const Standard: Story = {
  args: {
    aufgabe: {
      id: '1',
      name: 'Register the passenger',
      bearbeiter: null,
      erstelldatum: new Date(),
      formularreferenz: 'Test',
      prioritaet: 50,
      vorgangId: 'vorgang-1',
    },
  },
};

export const WithAssignee: Story = {
  args: {
    aufgabe: {
      id: '2',
      name: 'Check payment',
      bearbeiter: 'Max',
      erstelldatum: new Date(),
      formularreferenz: 'Test',
      prioritaet: 20,
      vorgangId: 'vorgang-1',
    },
  },
};

export const Selected: Story = {
  args: {
    aufgabe: {
      id: '2',
      name: 'Check payment',
      bearbeiter: 'Max',
      erstelldatum: new Date(),
      formularreferenz: 'Test',
      prioritaet: 80,
      vorgangId: 'vorgang-1',
    },
    selected: true,
  },
};

export const HighPriority: Story = {
  args: {
    aufgabe: {
      id: '3',
      name: 'Anfragen genehmigen',
      bearbeiter: null,
      erstelldatum: new Date(),
      formularreferenz: 'Test',
      prioritaet: 100,
      vorgangId: 'vorgang-1',
    },
  },
};
