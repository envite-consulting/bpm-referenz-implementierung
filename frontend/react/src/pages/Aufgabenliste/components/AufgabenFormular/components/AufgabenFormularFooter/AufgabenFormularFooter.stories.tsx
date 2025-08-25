import type { Meta, StoryObj } from '@storybook/react-vite';
import { AufgabenFormularFooter } from '@aufgabenFormular/components/AufgabenFormularFooter/AufgabenFormularFooter.tsx';

const meta: Meta<typeof AufgabenFormularFooter> = {
  title: 'Aufgabenliste/AufgabenFormular/Footer',
  component: AufgabenFormularFooter,
  tags: ['autodocs'],
};
export default meta;

type Story = StoryObj<typeof AufgabenFormularFooter>;

export const Default: Story = {
  args: {
    isLoading: false,
    isError: false,
  },
};

export const Loading: Story = {
  args: {
    isLoading: true,
    isError: false,
  },
};

export const Error: Story = {
  args: {
    isLoading: false,
    isError: true,
  },
};
