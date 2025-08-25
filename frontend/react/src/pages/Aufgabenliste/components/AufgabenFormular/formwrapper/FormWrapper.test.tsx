import { render } from '@testing-library/react';
import { AnfragenGenehmigung } from '@aufgabenFormular/formwrapper/AnfragenGenehmigung/AnfragenGenehmigung.tsx';
import { FormWrapper } from './FormWrapper.tsx';

jest.mock('@ui/Badge/Badge.tsx');
jest.mock(
  '@aufgabenFormular/formwrapper/AnfragenGenehmigung/AnfragenGenehmigung.tsx',
);

describe('FormWrapper', () => {
  const mockAnfragenGenehmigung = AnfragenGenehmigung as jest.MockedFunction<
    typeof AnfragenGenehmigung
  >;

  beforeEach(() => {
    jest.clearAllMocks();
    mockAnfragenGenehmigung.mockClear();
  });

  it('should render AnfragenGenehmigung component when formularreferenz is "Test"', () => {
    const { getByTestId } = render(
      <FormWrapper formularreferenz='Test' taskId='123' />,
    );
    const component = getByTestId('anfragen-mock');

    expect(component).toBeInTheDocument();
    expect(component).toHaveAttribute('data-taskid', '123');
  });

  it('should render error when formularreferenz is undefined', () => {
    const { getByTestId } = render(<FormWrapper taskId='123' />);
    const badge = getByTestId('badge-mock');

    expect(badge).toHaveTextContent('Kein passendes Formular gefunden');
  });

  it('should render error when formularreferenz is unknown', () => {
    const { getByTestId } = render(
      <FormWrapper formularreferenz='Unbekannt' taskId='123' />,
    );
    const badge = getByTestId('badge-mock');

    expect(badge).toHaveTextContent('Kein passendes Formular gefunden');
  });
});
