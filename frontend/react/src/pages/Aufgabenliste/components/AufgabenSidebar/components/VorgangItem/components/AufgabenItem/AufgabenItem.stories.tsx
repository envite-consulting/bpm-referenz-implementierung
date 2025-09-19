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
    },
    selected: true,
  },
};
