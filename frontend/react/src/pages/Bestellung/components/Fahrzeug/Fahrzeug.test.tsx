import { render } from '@testing-library/react';
import { useFahrzeugQuery } from '@fahrzeug/queries/useFahrzeugQuery.ts';
import { DropdownMenu } from '@ui/DropDownMenu/DropdownMenu.tsx';
import { Fahrzeug } from '@fahrzeug/Fahrzeug.tsx';
import { QueryBoundary, type QueryBoundaryFnResult } from '@ui/QueryBoundary';

jest.mock('@fahrzeug/queries/useFahrzeugQuery.ts');
jest.mock('@ui/DropDownMenu/DropdownMenu.tsx');
jest.mock('@ui/QueryBoundary');

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
    it('should render correct loading state', () => {
      const queryResult: QueryBoundaryFnResult<string[]> = {
        isFetching: true,
        isError: true,
        isSuccess: true,
        getData: () => ['Das wäre z.B. ein Datenstring'],
        getErrorMessage: () => 'Das wäre ein etwaiger Fehler',
      };
      (useFahrzeugQuery as jest.Mock).mockReturnValue(queryResult);

      const { asFragment } = render(<Fahrzeug onSelectId={jest.fn()} />);

      expect(QueryBoundary).toHaveBeenCalledWith(
        expect.objectContaining({ queryResult }),
        undefined,
      );
      expect(asFragment()).toMatchSnapshot();
    });

    describe('Event handling', () => {
      it('should call onSelectId when DropdownMenu onChange is triggered', () => {
        const mockOnSelectId = jest.fn();

        (useFahrzeugQuery as jest.Mock).mockReturnValue({
          getData: () => mockOptions,
          isFetching: () => false,
          getErrorMessage: jest.fn(),
          isError: () => false,
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
