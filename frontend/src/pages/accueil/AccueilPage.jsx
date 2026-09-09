import { Link } from 'react-router-dom';
import { useAuth } from '../../auth/AuthProvider';

const roleContent = {
	ADMIN: { label: 'Administration', title: 'Pilotez votre établissement.', description: 'Gérez les élèves, les équipes, les finances et les paramètres depuis un seul espace.', shortcuts: [{ title: 'Gérer les élèves', detail: 'Dossiers et inscriptions', to: '/gestion-scolaire' }, { title: 'Suivre les absences', detail: 'Présences et justificatifs', to: '/absences' }, { title: 'Ouvrir la comptabilité', detail: 'Paiements et échéances', to: '/comptabilite' }] },
	ENSEIGNANT: { label: 'Espace enseignant', title: 'Votre journée pédagogique, en un coup d’œil.', description: 'Retrouvez vos évaluations, vos absences et votre emploi du temps.', shortcuts: [{ title: 'Mes évaluations', detail: 'Créer et suivre les évaluations', to: '/evaluations' }, { title: 'Saisir les absences', detail: 'Suivi de vos classes', to: '/absences' }, { title: 'Mon emploi du temps', detail: 'Cours et créneaux', to: '/emploi-du-temps' }] },
	PARENT: { label: 'Espace parent', title: 'Suivez la scolarité de votre enfant.', description: 'Consultez les absences, les résultats, les paiements et les communications.', shortcuts: [{ title: 'Voir les absences', detail: 'Suivi de vos enfants', to: '/absences' }, { title: 'Consulter les résultats', detail: 'Évaluations et notes', to: '/evaluations' }, { title: 'Suivre les paiements', detail: 'Échéances et règlements', to: '/comptabilite' }] },
	ELEVE: { label: 'Espace élève', title: 'Retrouvez votre parcours scolaire.', description: 'Consultez vos résultats, vos absences et votre emploi du temps.', shortcuts: [{ title: 'Mes résultats', detail: 'Évaluations et notes', to: '/evaluations' }, { title: 'Mes absences', detail: 'Historique et justificatifs', to: '/absences' }, { title: 'Mon emploi du temps', detail: 'Cours de la semaine', to: '/emploi-du-temps' }] },
};

export default function AccueilPage() {
	const { user } = useAuth();
	const displayRole = user?.role || 'Utilisateur';
	const content = roleContent[displayRole] || roleContent.ELEVE;
	return (
		<section className="page-shell dashboard-page">
			<div className="page-header dashboard-header">
				<div><p className="eyebrow">{content.label}</p><h1>{content.title}</h1><p className="page-lead">{content.description}</p></div>
				<div className="date-card"><span>Aujourd’hui</span><strong>{new Intl.DateTimeFormat('fr-FR', { dateStyle: 'long' }).format(new Date())}</strong></div>
			</div>
			<div className="stats-grid dashboard-stats">
				<article className="stat-card stat-card-accent"><span>Votre espace</span><strong>{displayRole}</strong><small>Accès actif</small></article>
				<article className="stat-card"><span>Année scolaire</span><strong>2025 - 2026</strong><small>Configuration en cours</small></article>
				<article className="stat-card"><span>État du service</span><strong className="status-text"><i /> Opérationnel</strong><small>Dernière vérification à l’instant</small></article>
			</div>
			<div className="dashboard-columns">
				<section className="card-panel"><div className="section-heading"><div><p className="eyebrow">Accès rapide</p><h2>Que souhaitez-vous faire ?</h2></div></div>
					<div className="shortcut-list">{content.shortcuts.map((shortcut) => <Link className="shortcut-item" key={shortcut.to} to={shortcut.to}><span className="shortcut-icon" aria-hidden="true">↗</span><span><strong>{shortcut.title}</strong><small>{shortcut.detail}</small></span><span className="shortcut-arrow" aria-hidden="true">→</span></Link>)}</div>
				</section>
				<section className="card-panel notice-panel"><p className="eyebrow">À retenir</p><h2>Votre espace est prêt.</h2><p>Le menu est limité aux fonctionnalités correspondant à votre rôle : {displayRole}.</p><Link className="text-link" to="/communication">Voir les communications <span aria-hidden="true">→</span></Link></section>
			</div>
		</section>
	);
}
