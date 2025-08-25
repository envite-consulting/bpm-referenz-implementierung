import type { Meta, StoryObj } from '@storybook/react-vite';
import { Header } from '@ui/Header/Header.tsx';

const meta: Meta<typeof Header> = {
  title: 'Infrastructure/Header',
  component: Header,
  tags: ['autodocs'],
};
export default meta;

type Story = StoryObj<typeof Header>;

export const Default: Story = {
  args: {
    currentPath: '/',
  },
};

export const Aufgabenliste: Story = {
  args: {
    currentPath: '/aufgabenliste',
  },
};

export const Bestellung: Story = {
  args: {
    currentPath: '/bestellung',
  },
};
