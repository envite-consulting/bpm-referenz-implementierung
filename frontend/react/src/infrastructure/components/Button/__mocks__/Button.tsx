export const Button = jest.fn(({ label, ...props }) => (
  <button {...props} data-testid='button-mock'>
    {label}
  </button>
));
