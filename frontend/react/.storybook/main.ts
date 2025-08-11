import type { StorybookConfig } from '@storybook/react-vite';
import UnoCSS from 'unocss/vite';
import unocssConfig from '../unocss.config';
import { UserConfig } from 'vite';

const config: StorybookConfig = {
  stories: ['../src/**/*.mdx', '../src/**/*.stories.@(js|jsx|mjs|ts|tsx)'],
  addons: [
    '@chromatic-com/storybook',
    '@storybook/addon-docs',
    '@storybook/addon-onboarding',
    '@storybook/addon-a11y',
    '@storybook/addon-vitest',
  ],
  framework: {
    name: '@storybook/react-vite',
    options: {},
  },
  staticDirs: ['../public'],
  core: {
    builder: '@storybook/builder-vite',
  },
  async viteFinal(config: UserConfig) {
    config.plugins = config.plugins ?? [];
    config.plugins.push(UnoCSS(unocssConfig));

    return config;
  },
};
export default config;
