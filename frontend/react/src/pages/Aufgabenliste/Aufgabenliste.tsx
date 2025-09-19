import { Outlet, useLocation } from 'react-router-dom';

import { Badge } from '@ui/Badge/Badge.tsx';
import { AufgabenSidebar } from '@aufgabenSidebar/AufgabenSidebar.tsx';

export function Aufgabenliste() {
  const { pathname } = useLocation();
  return (
    <div className='flex h-full'>
      <div className='w-1/3'>
        <AufgabenSidebar />
      </div>

      <div className='flex-1 h-full overflow-auto'>
        <Outlet />
        {!pathname.includes('/aufgabe/') && (
          <Badge label='Wähle eine Aufgabe aus' type='info' />
        )}
      </div>
    </div>
  );
}
