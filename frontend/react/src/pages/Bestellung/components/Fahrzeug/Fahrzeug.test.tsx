import { render } from '@testing-library/react';
import { useFahrzeugQuery } from '@fahrzeug/queries/useFahrzeugQuery.ts';
import {
  DropdownMenu,
  type DropdownOption,
} from '@ui/DropDownMenu/DropdownMenu.tsx';
import { Fahrzeug } from '@fahrzeug/Fahrzeug.tsx';
import { QueryBoundary } from '@ui/QueryBoundary';
import { buildUseQueryBoundaryFnResult } from '@ui/QueryBoundary/query/__mocks__/useQueryBoundaryFn.functions.ts';

jest.mock('@fahrzeug/queries/useFahrzeugQuery.ts');
jest.mock('@ui/DropDownMenu/DropdownMenu.tsx');
jest.mock('@ui/QueryBoundary');

describe('Fahrzeug', () => {
  const mockDropdownMenu = jest.mocked(DropdownMenu);

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
      const queryResult = buildUseQueryBoundaryFnResult<
        DropdownOption<string>[]
      >({ type: 'success' });
      jest.mocked(useFahrzeugQuery).mockReturnValue(queryResult);

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

        jest.mocked(useFahrzeugQuery).mockReturnValue({
          getData: () => mockOptions,
          isFetching: false,
          getErrorMessage: jest.fn(),
          isError: false,
          isSuccess: true,
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
