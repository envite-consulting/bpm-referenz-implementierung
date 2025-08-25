import { render } from '@testing-library/react';
import { useFahrzeugQuery } from '@fahrzeug/queries/useFahrzeugQuery.ts';
import { DropdownMenu } from '@ui/DropDownMenu/DropdownMenu.tsx';
import { Fahrzeug } from '@fahrzeug/Fahrzeug.tsx';

jest.mock('@fahrzeug/queries/useFahrzeugQuery.ts');
jest.mock('@ui/DropDownMenu/DropdownMenu.tsx');
jest.mock('@ui/LoadingSpin/LoadingSpin.tsx');

describe('Fahrzeug', () => {
  const mockDropdownMenu = DropdownMenu as jest.MockedFunction<
    typeof DropdownMenu
  >;

  const mockOptions = [
    { label: 'Fahrzeug 1', value: '1' },
    { label: 'Fahrzeug 2', value: '2' },
  ];

  beforeEach(() => {
    jest.clearAllMocks();
    mockDropdownMenu.mockClear();
  });

  describe('Rendering', () => {
    it('should render loading state', () => {
      (useFahrzeugQuery as jest.Mock).mockReturnValue({
        fahrzeugOptions: [],
        isLoading: true,
        error: null,
        isError: false,
      });

      const { getByTestId } = render(<Fahrzeug onSelectId={jest.fn()} />);

      expect(getByTestId('loading-spin-mock')).toBeInTheDocument();
      expect(mockDropdownMenu).not.toHaveBeenCalled();
    });

    it('should render error state', () => {
      (useFahrzeugQuery as jest.Mock).mockReturnValue({
        fahrzeugOptions: [],
        isLoading: false,
        error: { message: 'Failed to fetch' },
        isError: true,
      });

      const { asFragment } = render(<Fahrzeug onSelectId={jest.fn()} />);

      expect(asFragment()).toMatchSnapshot();
      expect(mockDropdownMenu).not.toHaveBeenCalled();
    });

    it('should render DropdownMenu with correct props', () => {
      (useFahrzeugQuery as jest.Mock).mockReturnValue({
        fahrzeugOptions: mockOptions,
        isLoading: false,
        error: null,
        isError: false,
      });

      const { getByTestId } = render(<Fahrzeug onSelectId={jest.fn()} />);

      expect(mockDropdownMenu).toHaveBeenCalledWith(
        expect.objectContaining({
          required: true,
          options: mockOptions,
          label: 'Auswahl Fahrzeug',
          onChange: expect.any(Function),
        }),
        undefined,
      );

      expect(getByTestId('dropdown-menu-mock')).toBeInTheDocument();
    });

    describe('Event handling', () => {
      it('should call onSelectId when DropdownMenu onChange is triggered', () => {
        const mockOnSelectId = jest.fn();

        (useFahrzeugQuery as jest.Mock).mockReturnValue({
          fahrzeugOptions: mockOptions,
          isLoading: false,
          error: null,
          isError: false,
        });

        render(<Fahrzeug onSelectId={mockOnSelectId} />);

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
