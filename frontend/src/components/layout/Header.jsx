import { Link } from 'react-router-dom';
import { useAuth } from '../../auth/AuthProvider';
export default function Header() { const { user, logout } = useAuth(); return <header className="app-header"><Link className="mobile-brand" to="/">School Manager</Link><div className="topbar-actions"><span className="user-badge">{user?.role || 'Utilisateur'}</span><Link className="text-link" to="/mon-compte/mot-de-passe">Mon compte</Link><button className="logout-button" onClick={logout}>Déconnexion</button></div></header>; }
