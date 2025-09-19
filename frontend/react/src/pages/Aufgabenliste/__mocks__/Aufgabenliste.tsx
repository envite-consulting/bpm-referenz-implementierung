import { Outlet } from 'react-router-dom';

export function Aufgabenliste() {
  return (
    <div>
      <p data-testid='aufgabenliste-mock'>Aufgabenliste</p>
      <Outlet />
    </div>
  );
}
