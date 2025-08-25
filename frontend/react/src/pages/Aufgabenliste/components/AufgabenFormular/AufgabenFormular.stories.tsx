import type { Meta, StoryObj } from '@storybook/react-vite';
import { AufgabenFormular } from '@aufgabenFormular/AufgabenFormular.tsx';

const meta: Meta<typeof AufgabenFormular> = {
  title: 'Aufgabenliste/AufgabenFormular',
  component: AufgabenFormular,
  tags: ['autodocs'],
  argTypes: {
    onAufgabeCompleted: { action: 'completed' },
  },
  args: {
    onAufgabeCompleted: () => {},
  },
};
export default meta;

type Story = StoryObj<typeof AufgabenFormular>;

const mockAufgabe = {
  id: 'task-1',
  name: 'Daten prüfen',
  bearbeiter: null,
  erstelldatum: new Date(),
  formularreferenz: 'Test',
};

export const NotAssigned: Story = {
  args: {
    aufgabe: { ...mockAufgabe, bearbeiter: null },
  },
};

export const Assigned: Story = {
  args: {
    aufgabe: { ...mockAufgabe, bearbeiter: 'admin' },
  },
};
