import type { Meta, StoryObj } from '@storybook/react-vite';
import { Badge } from '@ui/Badge/Badge.tsx';

const meta: Meta<typeof Badge> = {
  title: 'Infrastructure/Badge',
  component: Badge,
  tags: ['autodocs'],
  argTypes: {
    label: { control: 'text' },
  },
};
export default meta;

type Story = StoryObj<typeof Badge>;

export const Success: Story = {
  args: {
    label: 'Beispiel Label',
    type: 'success',
  },
};

export const Info: Story = {
  args: {
    label: 'Beispiel Label',
    type: 'info',
  },
};

export const Warning: Story = {
  args: {
    label: 'Beispiel Label',
    type: 'warning',
  },
};

export const Danger: Story = {
  args: {
    label: 'Beispiel Label',
    type: 'danger',
  },
};
