type Props = {
  navigate: (path: string) => void;
  currentPath?: string;
};

export function Header({
  navigate,
  currentPath = window.location.pathname,
}: Props) {
  const link = (path: string, label: string) => (
    <a
      href={path}
      onClick={(e) => {
        e.preventDefault();
        navigate(path);
      }}
      className={`px-3 py-2 rounded ${currentPath === path ? 'bg-gray-200' : 'hover:bg-gray-100'}`}
    >
      {label}
    </a>
  );

  return (
    <header className='flex items-center justify-between p-4 border-b border-gray-200'>
      <div className='font-bold'>Dashboard</div>
      <nav className='flex gap-2'>
        {link('/aufgabenliste', 'Aufgabenliste')}
        {link('/bestellung', 'Bestellung erstellen')}
      </nav>
    </header>
  );
}
