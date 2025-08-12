export const Button = jest.fn(({ label, ...props }) => (
  <button {...props} data-testid='primary-button-mock'>
    {label}
  </button>
));
