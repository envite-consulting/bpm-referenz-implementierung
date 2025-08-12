import { render, screen } from '@testing-library/react';
import { useAntragstellerQuery } from '@/pages/Bestellung/components/Antragsteller/queries/useAntragstellerQuery.ts';
import { DropdownMenu } from '@/infrastructure/components/DropDownMenu/DropdownMenu.tsx';
import { Antragsteller } from './Antragsteller.tsx';

jest.mock(
  '@/pages/Bestellung/components/Antragsteller/queries/useAntragstellerQuery.ts',
);

jest.mock('@/infrastructure/components/DropDownMenu/DropdownMenu.tsx');

describe('Antragsteller', () => {
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

      render(<Antragsteller onSelectId={jest.fn()} />);

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

      render(<Antragsteller onSelectId={jest.fn()} />);

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

      render(<Antragsteller onSelectId={jest.fn()} />);

      expect(mockDropdownMenu).toHaveBeenCalledWith(
        expect.objectContaining({
          required: true,
          options: mockOptions,
          label: 'Auswahl Antragsteller',
          onChange: expect.any(Function),
        }),
        undefined,
      );

      expect(screen.getByTestId('dropdown-menu-mock')).toBeInTheDocument();
    });

    describe('Event handling', () => {
      it('should call onSelectId when DropdownMenu onChange is triggered', () => {
        const mockOnSelectId = jest.fn();

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
