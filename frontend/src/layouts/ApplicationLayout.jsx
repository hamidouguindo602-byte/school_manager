import { Link, Outlet, useNavigate } from 'react-router-dom';
import { useAuth } from '../auth/AuthProvider';
import Sidebar from '../components/layout/Sidebar';

export default function ApplicationLayout() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const initials = `${user?.prenom?.[0] || user?.role?.[0] || 'U'}${user?.nom?.[0] || ''}`.toUpperCase();

  return (
    <div className="layout-shell min-h-screen bg-slate-50 antialiased">
      <Sidebar />
      <main className="layout-main">
        <header className="app-header flex items-center">
          <label className="global-search"><span aria-hidden="true">⌕</span><input aria-label="Rechercher" placeholder="Rechercher..." /></label>
          <div className="topbar-actions"><button className="icon-button" type="button" aria-label="Notifications" onClick={() => navigate('/communication/notifications')}>♧<i /></button><span className="topbar-divider" /><Link className="profile-chip" to="/mon-compte/mot-de-passe"><span className="profile-avatar">{initials}</span><span className="profile-meta"><strong>{user?.prenom || user?.email || 'Utilisateur'}</strong><small>{user?.role || 'Utilisateur'}</small></span></Link><button className="logout-button" type="button" onClick={logout}>Déconnexion</button></div>
        </header>
        <Outlet />
      </main>
    </div>
  );
}
