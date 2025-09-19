import { Bestellung } from '@bestellung/Bestellung.tsx';
import { Aufgabenliste } from '@aufgabenliste/Aufgabenliste.tsx';
import { AufgabenFormular } from '@aufgabenFormular/AufgabenFormular.tsx';
import { Route, Routes } from 'react-router-dom';

export function AppRoutes() {
  return (
    <Routes>
      <Route path='/bestellung' element={<Bestellung />} />

      <Route path='/aufgabenliste' element={<Aufgabenliste />}>
        <Route path='aufgabe/:id' element={<AufgabenFormular />} />
      </Route>

      <Route path='*' element={<Aufgabenliste />} />
    </Routes>
  );
}
