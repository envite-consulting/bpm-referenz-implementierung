export const Badge = jest.fn(({ label, ...props }) => (
  <p {...props} data-testid='badge-mock'>
    {label}
  </p>
));
