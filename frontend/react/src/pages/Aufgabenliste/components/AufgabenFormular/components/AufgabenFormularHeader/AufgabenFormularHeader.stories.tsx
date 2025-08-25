import type { Meta, StoryObj } from '@storybook/react-vite';
import { AufgabenFormularHeader } from '@aufgabenFormular/components/AufgabenFormularHeader/AufgabenFormularHeader.tsx';

const meta: Meta<typeof AufgabenFormularHeader> = {
  title: 'Aufgabenliste/AufgabenFormular/Header',
  component: AufgabenFormularHeader,
  tags: ['autodocs'],
  argTypes: {
    onAssignUser: { action: 'assignUser' },
  },
  args: {
    onAssignUser: () => {},
  },
};
export default meta;

type Story = StoryObj<typeof AufgabenFormularHeader>;

export const NotAssigned: Story = {
  args: {
    titel: 'Aufgabe bearbeiten',
    bearbeiter: null,
    isErrorAssigneeChange: false,
    isLoading: false,
  },
};

export const Assigned: Story = {
  args: {
    titel: 'Aufgabe bearbeiten',
    bearbeiter: 'Max',
    isErrorAssigneeChange: false,
    isLoading: false,
  },
};

export const Loading: Story = {
  args: {
    titel: 'Aufgabe bearbeiten',
    bearbeiter: null,
    isErrorAssigneeChange: false,
    isLoading: true,
  },
};

export const Error: Story = {
  args: {
    titel: 'Aufgabe bearbeiten',
    bearbeiter: null,
    isErrorAssigneeChange: true,
    isLoading: false,
  },
};
