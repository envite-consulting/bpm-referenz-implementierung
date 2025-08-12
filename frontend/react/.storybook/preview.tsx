import 'virtual:uno.css';
import '@public-ui/components/dist/kolibri/kolibri.esm.js';
import type { Preview } from '@storybook/react-vite';
import { register } from '@public-ui/components';
import { DEFAULT } from '@public-ui/themes';
import { defineCustomElements } from '@public-ui/components/dist/loader';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { initialize, mswLoader } from 'msw-storybook-addon';

register(DEFAULT, defineCustomElements);
initialize();

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 1000,
      retry: 1,
    },
  },
});

const preview: Preview = {
  parameters: {
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/i,
      },
    },
  },
  decorators: [
    (Story) => (
      <QueryClientProvider client={queryClient}>
        <Story />
      </QueryClientProvider>
    ),
  ],
  loaders: [mswLoader],
};

export default preview;
