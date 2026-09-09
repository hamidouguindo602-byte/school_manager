import { Route } from 'react-router-dom';
import ConnexionPage from '../pages/auth/ConnexionPage';
import PasswordPage from '../pages/auth/PasswordPage';
export function publicRoutes() { return <><Route path="/login" element={<ConnexionPage />} /><Route path="/mot-de-passe-oublie" element={<PasswordPage mode="forgot" />} /><Route path="/reinitialiser-mot-de-passe" element={<PasswordPage mode="reset" />} /></>; }
