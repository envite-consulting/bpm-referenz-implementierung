import { useEffect, useMemo, useState } from 'react';
import { Bestellung } from '@bestellung/Bestellung.tsx';
import { Aufgabenliste } from '@aufgabenliste/Aufgabenliste.tsx';
import { Header } from '@ui/Header/Header.tsx';

function Dashboard() {
  const [path, setPath] = useState(
    window.location.pathname || '/aufgabenliste',
  );

  useEffect(() => {
    const onPop = () => setPath(window.location.pathname || '/aufgabenliste');
    window.addEventListener('popstate', onPop);
    return () => window.removeEventListener('popstate', onPop);
  }, []);

  const navigate = (to: string) => {
    if (to === path) return;
    window.history.pushState({}, '', to);
    setPath(to);
  };

  const content = useMemo(() => {
    switch (path) {
      case '/bestellung':
        return <Bestellung />;
      case '/aufgabenliste':
      default:
        return <Aufgabenliste />;
    }
  }, [path]);

  return (
    <div className='h-screen flex flex-col'>
      <Header navigate={navigate} currentPath={path} />
      <main className='flex-1 overflow-auto'>{content}</main>
    </div>
  );
}

function App() {
  return <Dashboard />;
}

export default App;
