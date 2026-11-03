import { Outlet, useLocation } from 'react-router-dom';

import { Badge, type BadgeType } from '@ui/Badge/Badge.tsx';
import { AufgabenSidebar } from '@aufgabenSidebar/AufgabenSidebar.tsx';

export function Aufgabenliste() {
  const { pathname, state } = useLocation();
  const hinweis: { label: string; type: BadgeType } | undefined =
    state?.hinweis;
  return (
    <div className='flex h-full'>
      <div className='w-1/3'>
        <AufgabenSidebar />
      </div>

      <div className='flex-1 h-full overflow-auto'>
        <Outlet />
        {!pathname.includes('/aufgabe/') && (
          <>
            {hinweis && (
              <div className='mb-2'>
                <Badge label={hinweis.label} type={hinweis.type} />
              </div>
            )}
            <Badge label='Wähle eine Aufgabe aus' type='info' />
          </>
        )}
      </div>
    </div>
  );
}
