import { fireEvent, render } from '@testing-library/react';
import { AufgabenFormularHeader } from './AufgabenFormularHeader.tsx';

jest.mock('@ui/Button/Button.tsx');
jest.mock('@ui/Badge/Badge.tsx');

describe('AufgabenFormularHeader', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe('Rendering', () => {
    it('should render header with header and claim button', () => {
      const { asFragment } = render(
        <AufgabenFormularHeader
          titel='Meine Aufgabe'
          bearbeiter={'Max'}
          isErrorAssigneeChange={false}
          isLoading={false}
          onAssignUser={jest.fn()}
        />,
      );

      expect(asFragment()).toMatchSnapshot();
    });

    it('should render warning when error', () => {
      const { asFragment } = render(
        <AufgabenFormularHeader
          titel='Meine Aufgabe'
          bearbeiter={null}
          isErrorAssigneeChange={true}
          isLoading={false}
          onAssignUser={jest.fn()}
        />,
      );

      expect(asFragment()).toMatchSnapshot();
    });

    it('should render claim button disabled when loading', () => {
      const onAssign = jest.fn();
      const { getByTestId } = render(
        <AufgabenFormularHeader
          titel='Meine Aufgabe'
          bearbeiter={null}
          isErrorAssigneeChange={false}
          isLoading={true}
          onAssignUser={onAssign}
        />,
      );
      const button = getByTestId('button-mock');

      expect(button).toBeDisabled();
    });

    it('should render claim button with assignee when assignee exists', () => {
      const { getByTestId } = render(
        <AufgabenFormularHeader
          titel='Meine Aufgabe'
          bearbeiter={'My Assignee'}
          isErrorAssigneeChange={true}
          isLoading={false}
          onAssignUser={jest.fn()}
        />,
      );
      const button = getByTestId('button-mock');

      expect(button).toHaveTextContent('My Assignee');
    });

    it('should render claim button with default message when no assignee exists', () => {
      const { getByTestId } = render(
        <AufgabenFormularHeader
          titel='Meine Aufgabe'
          bearbeiter={null}
          isErrorAssigneeChange={true}
          isLoading={false}
          onAssignUser={jest.fn()}
        />,
      );
      const button = getByTestId('button-mock');

      expect(button).toHaveTextContent('Übernehmen');
    });
  });

  describe('Event handling', () => {
    it('should call onAssignUser when claim button is clicked', () => {
      const onAssign = jest.fn();
      const { getByTestId } = render(
        <AufgabenFormularHeader
          titel='Meine Aufgabe'
          bearbeiter={null}
          isErrorAssigneeChange={false}
          isLoading={false}
          onAssignUser={onAssign}
        />,
      );
      const button = getByTestId('button-mock');

      fireEvent.click(button);

      expect(onAssign).toHaveBeenCalledTimes(1);
    });
  });
});
