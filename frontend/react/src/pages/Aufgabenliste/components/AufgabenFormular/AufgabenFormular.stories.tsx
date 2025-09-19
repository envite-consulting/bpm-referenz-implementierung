import type { Meta, StoryObj } from '@storybook/react-vite';
import { AufgabenFormular } from '@aufgabenFormular/AufgabenFormular.tsx';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import { http, HttpResponse } from 'msw';

const meta: Meta<typeof AufgabenFormular> = {
  title: 'Aufgabenliste/AufgabenFormular',
  component: AufgabenFormular,
  tags: ['autodocs'],
};
export default meta;

type Story = StoryObj<typeof AufgabenFormular>;

const baseAufgabe = {
  id: 'task-1',
  name: 'Daten prüfen',
  bearbeiter: null as string | null,
  erstelldatum: new Date(),
  formularreferenz: 'Test',
};

const renderWithRoute = () => (
  <MemoryRouter initialEntries={['/aufgabenliste/aufgabe/task-1']}>
    <Routes>
      <Route path='/aufgabenliste/aufgabe/:id' element={<AufgabenFormular />} />
    </Routes>
  </MemoryRouter>
);

export const NotAssigned: Story = {
  parameters: {
    msw: {
      handlers: [
        http.get('/api/aufgabe/task-1', () =>
          HttpResponse.json({ ...baseAufgabe, bearbeiter: null }),
        ),
        http.put('/api/aufgabe/task-1/uebernehmen', () =>
          HttpResponse.json({}),
        ),
        http.put('/api/aufgabe/task-1/abgeben', () => HttpResponse.json({})),
        http.put('/api/aufgabe/task-1/abschliessenMitVariablen', () =>
          HttpResponse.json({}),
        ),
      ],
    },
  },
  render: renderWithRoute,
};

export const Assigned: Story = {
  parameters: {
    msw: {
      handlers: [
        http.get('/api/aufgabe/task-1', () =>
          HttpResponse.json({ ...baseAufgabe, bearbeiter: 'admin' }),
        ),
        http.put('/api/aufgabe/task-1/uebernehmen', () =>
          HttpResponse.json({}),
        ),
        http.put('/api/aufgabe/task-1/abgeben', () => HttpResponse.json({})),
        http.put('/api/aufgabe/task-1/abschliessenMitVariablen', () =>
          HttpResponse.json({}),
        ),
      ],
    },
  },
  render: renderWithRoute,
};
