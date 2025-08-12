import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import UnoCSS from 'unocss/vite';
import tsconfigPaths from 'vite-tsconfig-paths';
import unocssConfig from './unocss.config.ts';

export default defineConfig(({ command }) => {
  const isDev: boolean = command === 'serve';

  return {
    ...(isDev && {
      server: {
        proxy: {
          '/api': {
            target: 'http://localhost:8080',
            changeOrigin: true,
          },
        },
      },
    }),

    plugins: [react(), UnoCSS(unocssConfig), tsconfigPaths()],
  };
});
