import { Header } from '@ui/Header/Header.tsx';
import { BrowserRouter, useLocation, useNavigate } from 'react-router-dom';
import { AppRoutes } from '@root/AppRoutes.tsx';

function Dashboard() {
  const navigate = useNavigate();
  const location = useLocation();

  return (
    <div className='h-screen flex flex-col'>
      <Header navigate={navigate} currentPath={location.pathname} />
      <main className='flex-1 overflow-auto'>
        <AppRoutes />
      </main>
    </div>
  );
}

function App() {
  return (
    <BrowserRouter>
      <Dashboard />
    </BrowserRouter>
  );
}

export default App;
