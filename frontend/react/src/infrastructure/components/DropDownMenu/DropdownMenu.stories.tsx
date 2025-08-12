import {
  DropdownMenu,
  type DropdownOption,
} from '@ui/DropDownMenu/DropdownMenu.tsx';
import type { Meta, StoryObj } from '@storybook/react-vite';

const meta: Meta<typeof DropdownMenu> = {
  title: 'Infrastructure/DropdownMenu',
  component: DropdownMenu,
  tags: ['autodocs'],
  argTypes: {
    onChange: { action: 'value changed' },
    required: { control: 'boolean' },
    placeholderText: { control: 'text' },
    label: { control: 'text' },
  },
};
export default meta;

type Story = StoryObj<typeof DropdownMenu>;

const exampleOptions: DropdownOption<string>[] = [
  { label: 'Option 1', value: 'value1' },
  { label: 'Option 2', value: 'value2' },
  { label: 'Option 3', value: 'value3' },
];

export const Default: Story = {
  args: {
    options: exampleOptions,
    onChange: (value: string) => console.log('Selected value:', value),
  },
};

export const CustomLabel: Story = {
  args: {
    ...Default.args,
    label: 'Beispiel Label',
  },
};

export const CustomPlaceholder: Story = {
  args: {
    ...Default.args,
    placeholderText: 'Beispiel Placeholder',
  },
};
