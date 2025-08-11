import { render, screen } from '@testing-library/react';
import { useAntragstellerQuery } from '@/pages/Bestellung/components/Antragsteller/queries/useAntragstellerQuery.ts';
import { DropdownMenu } from '@/infrastructure/components/DropDownMenu/DropdownMenu.tsx';
import Antragsteller from '@/pages/Bestellung/components/Antragsteller/Antragsteller.tsx';

jest.mock(
  '@/pages/Bestellung/components/Antragsteller/queries/useAntragstellerQuery.ts',
  () => ({
    useAntragstellerQuery: jest.fn(),
  }),
);

jest.mock('@/infrastructure/components/DropDownMenu/DropdownMenu.tsx', () => ({
  DropdownMenu: jest.fn(({ options, onChange, label, required }) => {
    return (
      <div
        data-testid='dropdown-menu-mock'
        data-options={JSON.stringify(options)}
        data-label={label}
        data-required={required}
        onClick={() => onChange && onChange('mockValue')}
      >
        DropdownMenu Mock
      </div>
    );
  }),
}));

describe('Antragsteller', () => {
  const mockOnSelectId = jest.fn();
  const mockDropdownMenu = DropdownMenu as jest.MockedFunction<
    typeof DropdownMenu
  >;

  const mockOptions = [
    { label: 'Antragsteller 1', value: '1' },
    { label: 'Antragsteller 2', value: '2' },
  ];

  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe('Rendering', () => {
    it('should render loading state', () => {
      (useAntragstellerQuery as jest.Mock).mockReturnValue({
        antragstellerOptions: [],
        isLoading: true,
        error: null,
        isError: false,
      });

      render(<Antragsteller onSelectId={mockOnSelectId} />);

      expect(screen.getByText('Loading...')).toBeInTheDocument();
      expect(mockDropdownMenu).not.toHaveBeenCalled();
    });

    it('should render error state', () => {
      (useAntragstellerQuery as jest.Mock).mockReturnValue({
        antragstellerOptions: [],
        isLoading: false,
        error: { message: 'Failed to fetch' },
        isError: true,
      });

      render(<Antragsteller onSelectId={mockOnSelectId} />);

      expect(screen.getByText('Error: Failed to fetch')).toBeInTheDocument();
      expect(mockDropdownMenu).not.toHaveBeenCalled();
    });

    it('should render DropdownMenu with correct props', () => {
      (useAntragstellerQuery as jest.Mock).mockReturnValue({
        antragstellerOptions: mockOptions,
        isLoading: false,
        error: null,
        isError: false,
      });

      render(<Antragsteller onSelectId={mockOnSelectId} />);

      expect(mockDropdownMenu).toHaveBeenCalled();
      const firstCall = mockDropdownMenu.mock.calls[0];
      const firstCallArguments = firstCall[0];
      expect(firstCallArguments).toEqual(
        expect.objectContaining({
          required: true,
          options: mockOptions,
          label: 'Auswahl Antragsteller',
          onChange: expect.any(Function),
        }),
      );

      expect(screen.getByTestId('dropdown-menu-mock')).toBeInTheDocument();
    });

    describe('Event handling', () => {
      it('should call onSelectId when DropdownMenu onChange is triggered', () => {
        (useAntragstellerQuery as jest.Mock).mockReturnValue({
          antragstellerOptions: mockOptions,
          isLoading: false,
          error: null,
          isError: false,
        });

        render(<Antragsteller onSelectId={mockOnSelectId} />);

        const lastCall =
          mockDropdownMenu.mock.calls[mockDropdownMenu.mock.calls.length - 1];
        const lastCallArguments = lastCall[0];
        const onChangeCallback = lastCallArguments.onChange;

        onChangeCallback('1');
        expect(mockOnSelectId).toHaveBeenCalledWith('1');

        onChangeCallback('2');
        expect(mockOnSelectId).toHaveBeenCalledWith('2');

        onChangeCallback('');
        expect(mockOnSelectId).toHaveBeenCalledWith('');

        expect(mockOnSelectId).toHaveBeenCalledTimes(3);
      });
    });
  });
});
