import { render } from '@testing-library/react';
import { AufgabenSidebar } from './AufgabenSidebar';
import { useVorganglisteQuery as useVorganglisteQueryOriginal } from '@aufgabenSidebar/queries/useVorganglisteQuery';

jest.mock('@aufgabenSidebar/queries/useVorganglisteQuery.ts');

jest.mock('@vorgangItem/VorgangItem.tsx');

jest.mock('@ui/LoadingSpin/LoadingSpin.tsx');

const useVorganglisteQuery =
  useVorganglisteQueryOriginal as jest.MockedFunction<
    typeof useVorganglisteQueryOriginal
  >;

describe('AufgabenSidebar', () => {
  const baseVorgaenge = [
    {
      id: '1',
      fachlicherSchluessel: 'V1',
      fachdaten: {
        antragstellerVorname: 'Max',
        antragstellerNachname: 'Mustermann',
        fahrzeugHersteller: 'VW',
        fahrzeugModell: 'Golf',
      },
    },
    {
      id: '2',
      fachlicherSchluessel: 'V2',
      fachdaten: {
        antragstellerVorname: 'Erika',
        antragstellerNachname: 'Musterfrau',
        fahrzeugHersteller: 'BMW',
        fahrzeugModell: 'X3',
      },
    },
  ];

  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe('Rendering', () => {
    it('should show loading spinner when loading', () => {
      (useVorganglisteQuery as jest.Mock).mockReturnValue({
        vorgaenge: [],
        isLoading: true,
        isError: false,
      });

      const { getByTestId } = render(<AufgabenSidebar />);

      expect(getByTestId('loading-spin-mock')).toBeInTheDocument();
    });

    it('should show error message when error', () => {
      (useVorganglisteQuery as jest.Mock).mockReturnValue({
        vorgaenge: [],
        isLoading: false,
        isError: true,
      });

      const { asFragment } = render(<AufgabenSidebar />);

      expect(asFragment()).toMatchSnapshot();
    });

    it('should render list of Vorgaenge', () => {
      (useVorganglisteQuery as jest.Mock).mockReturnValue({
        vorgaenge: baseVorgaenge,
        isLoading: false,
        isError: false,
      });

      const { asFragment } = render(<AufgabenSidebar />);

      expect(asFragment()).toMatchSnapshot();
    });
  });
});
