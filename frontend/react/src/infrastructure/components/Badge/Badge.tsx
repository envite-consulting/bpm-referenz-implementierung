import { KolBadge } from '@public-ui/react';

export type BadgeType = 'success' | 'info' | 'warning' | 'danger';

type BadgeProps = {
  label: string;
  type: BadgeType;
};

export function Badge({ label, type }: BadgeProps) {
  const variantStyles: Record<BadgeType, string> = {
    success: '#22bb33',
    info: '#5bc0de',
    warning: '#f0ad4e',
    danger: '#bb2124',
  };

  return <KolBadge _label={label} _color={`${variantStyles[type]}`} />;
}
