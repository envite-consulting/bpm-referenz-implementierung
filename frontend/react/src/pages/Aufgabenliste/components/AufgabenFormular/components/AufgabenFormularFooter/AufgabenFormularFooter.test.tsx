import { render, screen } from '@testing-library/react';
import { AufgabenFormularFooter } from './AufgabenFormularFooter.tsx';

jest.mock('@ui/Button/Button.tsx');
jest.mock('@ui/Badge/Badge.tsx');

describe('AufgabenFormularFooter', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should render footer with submit button when no error', () => {
    const { asFragment } = render(
      <AufgabenFormularFooter isLoading={false} isError={false} />,
    );

    expect(asFragment()).toMatchSnapshot();
  });

  it('should render submit button disabled when loading', () => {
    render(<AufgabenFormularFooter isError={false} isLoading={true} />);
    const button = screen.getByTestId('button-mock');

    expect(button).toBeDisabled();
  });

  it('should render footer with warning when error', () => {
    const { asFragment } = render(
      <AufgabenFormularFooter isError={true} isLoading={false} />,
    );

    expect(asFragment()).toMatchSnapshot();
  });
});
