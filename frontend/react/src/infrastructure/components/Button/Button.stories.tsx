import type { Meta, StoryObj } from '@storybook/react-vite';
import { Button } from '@/infrastructure/components/Button/Button.tsx';

const meta: Meta<typeof Button> = {
  title: 'Infrastructure/SubmitButton',
  component: Button,
  tags: ['autodocs'],
  argTypes: {
    label: { control: 'text' },
  },
};
export default meta;

type Story = StoryObj<typeof Button>;

export const Default: Story = {
  args: {
    label: 'Beispiel Label',
    type: 'button',
  },
};

export const Submit: Story = {
  args: {
    label: 'Beispiel Label',
    type: 'submit',
  },
};

export const Reset: Story = {
  args: {
    label: 'Beispiel Label',
    type: 'reset',
  },
};
