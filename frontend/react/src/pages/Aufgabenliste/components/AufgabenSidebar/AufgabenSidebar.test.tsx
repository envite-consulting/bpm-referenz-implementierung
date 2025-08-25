import { fireEvent, render } from '@testing-library/react';
import { AufgabenSidebar } from './AufgabenSidebar.tsx';
import { useAufgabenListeQuery as useAufgabenListeQueryOriginal } from '@aufgabenliste/queries/useAufgabenlisteQuery.ts';

jest.mock('@aufgabenliste/queries/useAufgabenlisteQuery.ts', () => ({
  useAufgabenListeQuery: jest.fn(),
}));

jest.mock('@ui/LoadingSpin/LoadingSpin.tsx');

const useAufgabenListeQuery =
  useAufgabenListeQueryOriginal as jest.MockedFunction<
    typeof useAufgabenListeQueryOriginal
  >;

describe('AufgabenSidebar', () => {
  const baseAufgaben = [
    {
      id: '1',
      name: 'A1',
      bearbeiter: null,
      erstelldatum: new Date('2024-01-01T00:00:00Z'),
      formularreferenz: 'X',
    },
    {
      id: '2',
      name: 'A2',
      bearbeiter: 'Max',
      erstelldatum: new Date('2024-01-02T00:00:00Z'),
      formularreferenz: 'Y',
    },
  ];

  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe('Rendering', () => {
    it('should show loading spinner when loading', () => {
      (useAufgabenListeQuery as jest.Mock).mockReturnValue({
        aufgaben: [],
        isLoading: true,
        isError: false,
      });

      const { getByTestId } = render(
        <AufgabenSidebar selected={null} setSelected={jest.fn()} />,
      );

      expect(getByTestId('loading-spin-mock')).toBeInTheDocument();
    });

    it('should show error message when error', () => {
      (useAufgabenListeQuery as jest.Mock).mockReturnValue({
        aufgaben: [],
        isLoading: false,
        isError: true,
      });

      const { asFragment } = render(
        <AufgabenSidebar selected={null} setSelected={jest.fn()} />,
      );

      expect(asFragment()).toMatchSnapshot();
    });

    it('should render list of Aufgaben', () => {
      (useAufgabenListeQuery as jest.Mock).mockReturnValue({
        aufgaben: baseAufgaben,
        isLoading: false,
        isError: false,
      });

      const { asFragment } = render(
        <AufgabenSidebar selected={baseAufgaben[0]} setSelected={jest.fn()} />,
      );

      expect(asFragment()).toMatchSnapshot();
    });
  });

  describe('Event handling', () => {
    it('should call setSelected when clicking an item', () => {
      (useAufgabenListeQuery as jest.Mock).mockReturnValue({
        aufgaben: baseAufgaben,
        isLoading: false,
        isError: false,
      });

      const onSelectMock = jest.fn();
      const { getAllByTestId } = render(
        <AufgabenSidebar
          selected={baseAufgaben[0]}
          setSelected={onSelectMock}
        />,
      );

      const items = getAllByTestId('aufgabe-item');
      fireEvent.click(items[1]);

      expect(onSelectMock).toHaveBeenCalledWith(baseAufgaben[1]);
    });
  });
});
