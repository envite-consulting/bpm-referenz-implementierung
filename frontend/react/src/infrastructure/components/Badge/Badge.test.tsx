import { render } from '@testing-library/react';
import { Badge, type BadgeType } from './Badge.tsx';

describe('Badge', () => {
  it('should render with provided label', () => {
    const { container } = render(
      <Badge label={`Label`} type={'info' as BadgeType} />,
    );

    const kolBadge = container.querySelector(
      'kol-badge',
    ) as HTMLKolButtonElement;

    expect(kolBadge).toBeInTheDocument();
    expect(kolBadge).toHaveAttribute('_label', 'Label');
  });

  it.each([
    ['success', '#22bb33'],
    ['info', '#5bc0de'],
    ['warning', '#f0ad4e'],
    ['danger', '#bb2124'],
  ])('should render as type=%s', (typeValue, expectedColor) => {
    const { container } = render(
      <Badge label={`Label`} type={typeValue as BadgeType} />,
    );

    const kolBadge = container.querySelector(
      'kol-badge',
    ) as HTMLKolButtonElement;

    expect(kolBadge).toBeInTheDocument();
    expect(kolBadge).toHaveAttribute('_color', expectedColor);
  });
});
