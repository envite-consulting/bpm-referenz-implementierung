import { render } from '@testing-library/react';
import { LoadingSpin } from './LoadingSpin.tsx';

describe('LoadingSpin', () => {
  it('should render KolSpin with show attribute', () => {
    const { container } = render(<LoadingSpin />);
    const kolBadge = container.querySelector(
      'kol-spin',
    ) as HTMLKolButtonElement;

    expect(kolBadge).toBeInTheDocument();
    expect(kolBadge).toHaveAttribute('_show', '');
  });
});
