export const DropdownMenu = jest.fn(
  ({ options, onChange, label, required }) => {
    return (
      <div
        data-testid='dropdown-menu-mock'
        data-options={JSON.stringify(options)}
        data-label={label}
        data-required={required ? 'true' : 'false'}
        onClick={() => onChange && onChange('mockValue')}
      >
        DropdownMenu Mock
      </div>
    );
  },
);
