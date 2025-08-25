import type { Meta, StoryObj } from '@storybook/react-vite';
import { LoadingSpin } from '@ui/LoadingSpin/LoadingSpin.tsx';

const meta: Meta<typeof LoadingSpin> = {
  title: 'Infrastructure/LoadingSpin',
  component: LoadingSpin,
  tags: ['autodocs'],
  argTypes: {
    label: { control: 'text' },
  },
};
export default meta;

type Story = StoryObj<typeof LoadingSpin>;

export const Default: Story = {};
