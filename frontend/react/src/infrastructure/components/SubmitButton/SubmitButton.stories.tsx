import type { Meta, StoryObj } from '@storybook/react-vite';
import { SubmitButton } from '@/infrastructure/components/SubmitButton/SubmitButton.tsx';

const meta: Meta<typeof SubmitButton> = {
  title: 'Infrastructure/SubmitButton',
  component: SubmitButton,
  tags: ['autodocs'],
  argTypes: {
    label: { control: 'text' },
  },
};
export default meta;

type Story = StoryObj<typeof SubmitButton>;

export const Default: Story = {
  args: {
    label: 'Beispiel Label',
  },
};
