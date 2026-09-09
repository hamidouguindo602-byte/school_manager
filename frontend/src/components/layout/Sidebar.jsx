import { NavLink } from 'react-router-dom';
import { useAuth } from '../../auth/AuthProvider';

const adminNavigation = [
	{ label: 'Vue d’ensemble', to: '/', end: true }, { label: 'Élèves', to: '/gestion-scolaire' }, { label: 'Enseignants', to: '/admin/enseignants' },
	{ label: 'Classes', to: '/admin/classes' }, { label: 'Parents', to: '/admin/parents' }, { label: 'Inscriptions', to: '/admin/inscriptions' },
	{ label: 'Formations', to: '/admin/formations' }, { label: 'Matières', to: '/admin/matieres' }, { label: 'Assignations', to: '/admin/assignations' },
	{ label: 'Évaluations', to: '/evaluations' }, { label: 'Notes', to: '/admin/notes' }, { label: 'Absences', to: '/absences' },
	{ label: 'Emploi du temps', to: '/emploi-du-temps' }, { label: 'Communications', to: '/communication' }, { label: 'Notifications', to: '/communication/notifications' },
	{ label: 'Paiements', to: '/comptabilite' }, { label: 'Échéances', to: '/admin/echeances' }, { label: 'Reçus PDF', to: '/comptabilite/recus' },
	{ label: 'Utilisateurs', to: '/admin/utilisateurs' }, { label: 'Permissions', to: '/admin/permissions' }, { label: 'Journaux', to: '/admin/journaux' },
];

const roleNavigation = {
	ENSEIGNANT: [{ label: 'Mon espace', to: '/', end: true }, { label: 'Mes évaluations', to: '/evaluations' }, { label: 'Absences', to: '/absences' }, { label: 'Emploi du temps', to: '/emploi-du-temps' }, { label: 'Communications', to: '/communication' }, { label: 'Notifications', to: '/communication/notifications' }],
	PARENT: [{ label: 'Mon espace', to: '/', end: true }, { label: 'Suivi de mes enfants', to: '/absences' }, { label: 'Évaluations', to: '/evaluations' }, { label: 'Emploi du temps', to: '/emploi-du-temps' }, { label: 'Paiements', to: '/comptabilite' }, { label: 'Communications', to: '/communication' }, { label: 'Notifications', to: '/communication/notifications' }],
	ELEVE: [{ label: 'Mon espace', to: '/', end: true }, { label: 'Mes évaluations', to: '/evaluations' }, { label: 'Mes absences', to: '/absences' }, { label: 'Mon emploi du temps', to: '/emploi-du-temps' }, { label: 'Communications', to: '/communication' }, { label: 'Notifications', to: '/communication/notifications' }],
};

export default function Sidebar() {
	const { user } = useAuth();
	const visibleNavigation = user?.role === 'ADMIN' ? adminNavigation : (roleNavigation[user?.role] || roleNavigation.ELEVE);
	return (
		<aside className="app-sidebar">
			<div className="sidebar-brand">
				<span className="brand-mark">S</span>
				<div><strong>School Manager</strong><span>Portail établissement</span></div>
			</div>
			<nav className="sidebar-nav" aria-label="Navigation principale">
				<span className="sidebar-label">Espace {user?.role === 'ADMIN' ? 'administrateur' : 'personnel'}</span>
				{visibleNavigation.map((item) => (
					<NavLink className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`} end={item.end} key={item.to} to={item.to}>
						<span className="sidebar-link-dot" aria-hidden="true" />
						{item.label}
					</NavLink>
				))}
			</nav>
			<div className="sidebar-footer"><span className="plan-avatar">{`${user?.prenom?.[0] || user?.role?.[0] || 'U'}${user?.nom?.[0] || ''}`.toUpperCase()}</span><span className="plan-meta"><strong>{user?.prenom || user?.email || 'Utilisateur'}</strong><small>{user?.role || 'Utilisateur'}</small></span></div>
		</aside>
	);
}
