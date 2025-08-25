export const AufgabenFormularHeader = jest.fn(
  ({ bearbeiter, onAssignUser }) => (
    <button data-testid='header-button' onClick={onAssignUser}>
      {bearbeiter ?? 'Übernehmen'}
    </button>
  ),
);
