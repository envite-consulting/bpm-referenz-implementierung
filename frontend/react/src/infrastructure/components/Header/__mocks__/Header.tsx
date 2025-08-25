export const Header = jest.fn(
  ({
    navigate,
    currentPath,
  }: {
    navigate: (path: string) => void;
    currentPath?: string;
  }) => (
    <div data-testid='header-mock'>
      Header - {currentPath}
      <button
        onClick={() => navigate('/aufgabenliste')}
        data-testid='nav-aufgabenliste'
      >
        Go Aufgabenliste
      </button>
      <button
        onClick={() => navigate('/bestellung')}
        data-testid='nav-bestellung'
      >
        Go Bestellung
      </button>
    </div>
  ),
);
