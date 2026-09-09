import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { AuthProvider, useAuth } from './auth/AuthProvider';
import { ProtectedRoute } from './auth/ProtectedRoute';
import ApplicationLayout from './layouts/ApplicationLayout';
import PublicLayout from './layouts/PublicLayout';
import ConnexionPage from './pages/auth/ConnexionPage';
import PasswordPage from './pages/auth/PasswordPage';
import { privateRoutes } from './routes/privateRoutes';

function AppRoutes() {
  return (
    <Routes>
      <Route element={<PublicLayout />}>
        <Route path="/login" element={<ConnexionPage />} />
        <Route path="/mot-de-passe-oublie" element={<PasswordPage mode="forgot" />} />
        <Route path="/reinitialiser-mot-de-passe" element={<PasswordPage mode="reset" />} />
      </Route>

      <Route element={<ProtectedRoute />}>
        <Route element={<ApplicationLayout />}>
          {privateRoutes()}
        </Route>
      </Route>

    </Routes>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <AppRoutes />
      </BrowserRouter>
    </AuthProvider>
  );
}

