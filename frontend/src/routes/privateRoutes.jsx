import { Navigate, Route } from 'react-router-dom';
import { RoleRoute } from '../auth/RoleRoute';
import AccueilPage from '../pages/accueil/AccueilPage';
import ElevesPage from '../pages/gestion-scolaire/ElevesPage';
import AbsencesPage from '../pages/absences/AbsencesPage';
import AbsencesClassePage from '../pages/absences/AbsencesClassePage';
import AbsencesEnseignantPage from '../pages/absences/AbsencesEnseignantPage';
import StatistiquesAbsencesPage from '../pages/absences/StatistiquesAbsencesPage';
import AnnoncesPage from '../pages/communication/AnnoncesPage';
import NotificationsPage from '../pages/communication/NotificationsPage';
import RecusPage from '../pages/comptabilite/RecusPage';
import InscriptionsPage from '../pages/gestion-scolaire/InscriptionsPage';
import EleveDetailPage from '../pages/gestion-scolaire/EleveDetailPage';
import EnseignantDetailPage from '../pages/gestion-scolaire/EnseignantDetailPage';
import FormationDetailPage from '../pages/gestion-scolaire/FormationDetailPage';
import ParentDetailPage from '../pages/gestion-scolaire/ParentDetailPage';
import AnnonceDetailPage from '../pages/communication/AnnonceDetailPage';
import PaiementDetailPage from '../pages/comptabilite/PaiementDetailPage';
import EvaluationDetailPage from '../pages/evaluations/EvaluationDetailPage';
import UtilisateurDetailPage from '../pages/administration/UtilisateurDetailPage';
import AdminDashboardPage from '../pages/administration/AdminDashboardPage';
import EmploiDuTempsPage from '../pages/emploi-du-temps/EmploiDuTempsPage';
import PasswordPage from '../pages/auth/PasswordPage';
import ResourcePage from '../components/common/ResourcePage';
import { activerAnneeScolaire, createAnneeScolaire, deleteAnneeScolaire, getAnneesScolaires, updateAnneeScolaire } from '../api/anneesScolaires.api';
import { createClasse, deleteClasse, getClasses, updateClasse } from '../api/classes.api';
import { createEnseignant, deleteEnseignant, getEnseignants, updateEnseignant } from '../api/enseignants.api';
import { createFormation, deleteFormation, getFormations, updateFormation } from '../api/formations.api';
import { createParent, deleteParent, getParents, updateParent } from '../api/parents.api';
import { createMatiere, deleteMatiere, getMatieres, updateMatiere } from '../api/matieres.api';
import { getAssignations } from '../api/assignations.api';
import { createNote, deleteNote, getNotes, updateNote } from '../api/notes.api';
import { activerUtilisateur, createUtilisateur, deleteUtilisateur, desactiverUtilisateur, getUtilisateurs, updateUtilisateur } from '../api/utilisateurs.api';
import { getLogs } from '../api/logs.api';
import { getNotifications } from '../api/notifications.api';
import { createEcheance, deleteEcheance, getEcheances, updateEcheance } from '../api/echeances.api';
import { getPasswordTokens } from '../api/passwordTokens.api';
import { getRecus } from '../api/recus.api';
import { createEvaluation, deleteEvaluation, getEvaluations, updateEvaluation } from '../api/evaluations.api';
import { cancelPaiement, createPaiement, deletePaiement, getPaiements, updatePaiement } from '../api/paiements.api';
import { createPermission, deletePermission, getPermissions, updatePermission } from '../api/permissions.api';

const roleRoute = (roles, element) => <RoleRoute allowedRoles={roles}>{element}</RoleRoute>;

export function privateRoutes() {
	return <>
		<Route path="/" element={<AccueilPage />} />
		<Route path="/mon-compte/mot-de-passe" element={<PasswordPage mode="change" />} />
		<Route path="/gestion-scolaire" element={roleRoute(['ADMIN'], <ElevesPage />)} />
		<Route path="/gestion-scolaire/eleves/:id" element={<EleveDetailPage />} />
		<Route path="/gestion-scolaire/enseignants/:id" element={<EnseignantDetailPage />} />
		<Route path="/gestion-scolaire/formations/:id" element={<FormationDetailPage />} />
		<Route path="/gestion-scolaire/parents/:id" element={<ParentDetailPage />} />
		<Route path="/evaluations" element={roleRoute(['ADMIN', 'ENSEIGNANT', 'PARENT', 'ELEVE'], <ResourcePage eyebrow="Suivi pédagogique" title="Évaluations" description="Consultez les évaluations correspondant à votre rôle." loader={getEvaluations} createAction={createEvaluation} updateAction={updateEvaluation} deleteAction={deleteEvaluation} formFields={[{ name: 'idMatiere', label: 'ID matière', type: 'number', required: true }, { name: 'idEnseignant', label: 'ID enseignant', type: 'number', required: true }, { name: 'idClasse', label: 'ID classe', type: 'number', required: true }, { name: 'dateEvaluation', label: 'Date', type: 'date', required: true }, { name: 'type', label: 'Type', required: true }]} columns={[{ label: 'Date', key: 'dateEvaluation' }, { label: 'Type', key: 'type' }, { label: 'Matière', key: 'nomMatiere' }, { label: 'Classe', key: 'nomClasse' }]} />)} />
		<Route path="/absences" element={roleRoute(['ADMIN', 'ENSEIGNANT', 'PARENT', 'ELEVE'], <AbsencesPage />)} />
		<Route path="/absences/classe" element={roleRoute(['ADMIN', 'ENSEIGNANT'], <AbsencesClassePage />)} />
		<Route path="/absences/enseignant" element={roleRoute(['ADMIN', 'ENSEIGNANT'], <AbsencesEnseignantPage />)} />
		<Route path="/absences/statistiques" element={roleRoute(['ADMIN', 'ENSEIGNANT'], <StatistiquesAbsencesPage />)} />
		<Route path="/emploi-du-temps" element={roleRoute(['ADMIN', 'ENSEIGNANT', 'PARENT', 'ELEVE'], <EmploiDuTempsPage />)} />
		<Route path="/communication" element={roleRoute(['ADMIN', 'ENSEIGNANT', 'PARENT', 'ELEVE'], <AnnoncesPage />)} />
		<Route path="/communication/annonces/:id" element={<AnnonceDetailPage />} />
		<Route path="/communication/notifications" element={roleRoute(['ADMIN', 'ENSEIGNANT', 'PARENT', 'ELEVE'], <NotificationsPage />)} />
		<Route path="/comptabilite" element={roleRoute(['ADMIN', 'PARENT'], <ResourcePage eyebrow="Gestion financière" title="Paiements" description="Consultez les règlements correspondant à votre rôle." loader={getPaiements} createAction={createPaiement} updateAction={updatePaiement} deleteAction={deletePaiement} rowActions={[{ label: 'Annuler', danger: true, onClick: async (item, reload, setFeedback) => { await cancelPaiement(item.idPaiement || item.id); setFeedback('Paiement annulé.'); await reload(); } }]} formFields={[{ name: 'idEcheance', label: 'ID échéance', type: 'number', required: true }, { name: 'montant', label: 'Montant', type: 'number', required: true }, { name: 'datePaiement', label: 'Date', type: 'date' }, { name: 'modePaiement', label: 'Mode de paiement', required: true }]} columns={[{ label: 'Date', key: 'datePaiement' }, { label: 'Montant', render: (item) => item.montant ? `${item.montant} €` : '—' }, { label: 'Mode', key: 'modePaiement' }, { label: 'Statut', key: 'statut' }]} />)} />
		<Route path="/comptabilite/paiements/:id" element={<PaiementDetailPage />} />
		<Route path="/comptabilite/recus" element={roleRoute(['ADMIN', 'PARENT'], <RecusPage />)} />
		<Route path="/admin" element={<RoleRoute allowedRoles={['ADMIN']}><AdminDashboardPage /></RoleRoute>} />
		<Route path="/admin/annees-scolaires" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Années scolaires" description="Gérez les périodes de référence de l’établissement." loader={getAnneesScolaires} createAction={createAnneeScolaire} updateAction={updateAnneeScolaire} deleteAction={deleteAnneeScolaire} rowActions={[{ label: 'Activer', onClick: async (item, reload, setFeedback) => { await activerAnneeScolaire(item.id || item.idAnnee); setFeedback('Année scolaire activée.'); await reload(); } }]} formFields={[{ name: 'libelle', label: 'Libellé', required: true }, { name: 'dateDebut', label: 'Date de début', type: 'date', required: true }, { name: 'dateFin', label: 'Date de fin', type: 'date', required: true }, { name: 'statut', label: 'Statut', required: true }]} columns={[{ label: 'Libellé', key: 'libelle' }, { label: 'Début', key: 'dateDebut' }, { label: 'Fin', key: 'dateFin' }, { label: 'État', key: 'active' }]} /></RoleRoute>} />
		<Route path="/admin/classes" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Classes" description="Gérez les classes et leurs niveaux." loader={getClasses} createAction={createClasse} updateAction={updateClasse} deleteAction={deleteClasse} formFields={[{ name: 'nomClasse', label: 'Nom de la classe', required: true }, { name: 'niveau', label: 'Niveau', required: true }]} columns={[{ label: 'Classe', key: 'nomClasse' }, { label: 'Niveau', key: 'niveau' }]} /></RoleRoute>} />
		<Route path="/admin/enseignants" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Enseignants" description="Gérez les enseignants et leurs informations de contact." loader={getEnseignants} createAction={createEnseignant} updateAction={updateEnseignant} deleteAction={deleteEnseignant} formFields={[{ name: 'prenom', label: 'Prénom', required: true }, { name: 'nom', label: 'Nom', required: true }, { name: 'numeroTelephone', label: 'Téléphone', required: true }, { name: 'email', label: 'Email', type: 'email' }, { name: 'motDePasse', label: 'Mot de passe', type: 'password', required: true }, { name: 'specialite', label: 'Spécialité', required: true }]} columns={[{ label: 'Nom', render: (item) => `${item.prenom || ''} ${item.nom || ''}` }, { label: 'Email', key: 'email' }, { label: 'Téléphone', key: 'numeroTelephone' }, { label: 'Spécialité', key: 'specialite' }]} /></RoleRoute>} />
		<Route path="/admin/utilisateurs/:id" element={<RoleRoute allowedRoles={['ADMIN']}><UtilisateurDetailPage /></RoleRoute>} />
		<Route path="/admin/evaluations/:id" element={<RoleRoute allowedRoles={['ADMIN']}><EvaluationDetailPage /></RoleRoute>} />
		<Route path="/admin/formations" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Formations" description="Gérez les formations proposées par l’établissement." loader={getFormations} createAction={createFormation} updateAction={updateFormation} deleteAction={deleteFormation} formFields={[{ name: 'nomFormation', label: 'Nom', required: true }, { name: 'description', label: 'Description' }, { name: 'duree', label: 'Durée', type: 'number', required: true }]} columns={[{ label: 'Nom', key: 'nom' }, { label: 'Description', key: 'description' }, { label: 'Statut', key: 'statut' }]} /></RoleRoute>} />
		<Route path="/admin/inscriptions" element={<RoleRoute allowedRoles={['ADMIN']}><InscriptionsPage /></RoleRoute>} />
		<Route path="/admin/parents" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Parents" description="Retrouvez les responsables et leurs élèves associés." loader={getParents} createAction={createParent} updateAction={updateParent} deleteAction={deleteParent} formFields={[{ name: 'prenom', label: 'Prénom', required: true }, { name: 'nom', label: 'Nom', required: true }, { name: 'numeroTelephone', label: 'Téléphone', required: true }, { name: 'email', label: 'Email', type: 'email' }, { name: 'motDePasse', label: 'Mot de passe', type: 'password', required: true }]} columns={[{ label: 'Nom', render: (item) => `${item.prenom || ''} ${item.nom || ''}` }, { label: 'Email', key: 'email' }, { label: 'Téléphone', key: 'numeroTelephone' }]} /></RoleRoute>} />
		<Route path="/admin/matieres" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Matières" description="Gérez les matières utilisées dans les cours et évaluations." loader={getMatieres} createAction={createMatiere} updateAction={updateMatiere} deleteAction={deleteMatiere} formFields={[{ name: 'nomMatiere', label: 'Nom', required: true }, { name: 'coefficient', label: 'Coefficient', type: 'number', required: true }, { name: 'code', label: 'Code', required: true }, { name: 'nom', label: 'Nom court' }]} columns={[{ label: 'Nom', key: 'nomMatiere' }, { label: 'Coefficient', key: 'coefficient' }, { label: 'Code', key: 'code' }]} /></RoleRoute>} />
		<Route path="/admin/assignations" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Assignations" description="Consultez les affectations pédagogiques par classe." loader={getAssignations} columns={[{ label: 'Classe', key: 'nomClasse' }, { label: 'Matière', key: 'nomMatiere' }, { label: 'Enseignant', key: 'nomEnseignant' }]} /></RoleRoute>} />
		<Route path="/admin/notes" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Notes" description="Saisissez et modifiez les résultats des élèves." loader={getNotes} createAction={createNote} updateAction={updateNote} deleteAction={deleteNote} formFields={[{ name: 'idEvaluation', label: 'ID évaluation', type: 'number', required: true }, { name: 'idEleve', label: 'ID élève', type: 'number', required: true }, { name: 'valeur', label: 'Note sur 20', type: 'number', required: true }]} columns={[{ label: 'Élève', key: 'nomEleve' }, { label: 'Évaluation', key: 'nomEvaluation' }, { label: 'Note', key: 'valeur' }, { label: 'Appréciation', key: 'appreciation' }]} /></RoleRoute>} />
		<Route path="/admin/utilisateurs" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Utilisateurs" description="Administrez les comptes et les accès à la plateforme." loader={getUtilisateurs} createAction={createUtilisateur} updateAction={updateUtilisateur} deleteAction={deleteUtilisateur} rowActions={[{ label: 'Activer', onClick: async (item, reload, setFeedback) => { await activerUtilisateur(item.id); setFeedback('Utilisateur activé.'); await reload(); } }, { label: 'Désactiver', danger: true, onClick: async (item, reload, setFeedback) => { await desactiverUtilisateur(item.id); setFeedback('Utilisateur désactivé.'); await reload(); } }]} formFields={[{ name: 'prenom', label: 'Prénom', required: true }, { name: 'nom', label: 'Nom', required: true }, { name: 'numeroTelephone', label: 'Téléphone', required: true }, { name: 'email', label: 'Email', type: 'email' }, { name: 'motDePasse', label: 'Mot de passe', type: 'password', required: true }, { name: 'statut', label: 'Statut', required: true }, { name: 'typeRole', label: 'Rôle', required: true }]} columns={[{ label: 'Nom', render: (item) => `${item.prenom || ''} ${item.nom || ''}` }, { label: 'Identifiant', key: 'identifiant' }, { label: 'Rôle', key: 'role' }, { label: 'Statut', key: 'statut' }]} /></RoleRoute>} />
		<Route path="/admin/permissions" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Permissions" description="Gérez les droits disponibles dans le système." loader={getPermissions} createAction={createPermission} updateAction={updatePermission} deleteAction={deletePermission} formFields={[{ name: 'nomPermission', label: 'Nom', required: true }, { name: 'description', label: 'Description' }]} columns={[{ label: 'Nom', key: 'nom' }, { label: 'Code', key: 'code' }, { label: 'Description', key: 'description' }]} /></RoleRoute>} />
		<Route path="/admin/journaux" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Journaux système" description="Suivez les événements et actions enregistrés par l’application." loader={getLogs} columns={[{ label: 'Date', key: 'date' }, { label: 'Action', key: 'action' }, { label: 'Utilisateur', key: 'utilisateur' }, { label: 'Détail', key: 'details' }]} /></RoleRoute>} />
		<Route path="/admin/notifications" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Notifications" description="Consultez les notifications générées par la plateforme." loader={getNotifications} columns={[{ label: 'Titre', key: 'titre' }, { label: 'Message', key: 'message' }, { label: 'Date', key: 'dateCreation' }, { label: 'Lu', key: 'lu' }]} /></RoleRoute>} />
		<Route path="/admin/echeances" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Échéances" description="Créez et suivez les échéances financières." loader={getEcheances} createAction={createEcheance} updateAction={updateEcheance} deleteAction={deleteEcheance} formFields={[{ name: 'idInscription', label: 'ID inscription', type: 'number', required: true }, { name: 'libelle', label: 'Libellé', required: true }, { name: 'montant', label: 'Montant', type: 'number', required: true }, { name: 'dateLimite', label: 'Date limite', type: 'date', required: true }]} columns={[{ label: 'Libellé', key: 'libelle' }, { label: 'Montant', key: 'montant' }, { label: 'Date limite', key: 'dateEcheance' }, { label: 'Statut', key: 'statut' }]} /></RoleRoute>} />
		<Route path="/admin/recus" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Reçus" description="Consultez les reçus générés pour les paiements." loader={getRecus} columns={[{ label: 'Paiement', key: 'idPaiement' }, { label: 'Numéro', key: 'numero' }, { label: 'Date', key: 'dateEmission' }]} /></RoleRoute>} />
		<Route path="/admin/tokens" element={<RoleRoute allowedRoles={['ADMIN']}><ResourcePage title="Tokens de réinitialisation" description="Surveillez les demandes de réinitialisation des comptes." loader={getPasswordTokens} columns={[{ label: 'Utilisateur', key: 'idUtilisateur' }, { label: 'Expiration', key: 'dateExpiration' }, { label: 'Utilisé', key: 'utilise' }]} /></RoleRoute>} />
		<Route path="*" element={<Navigate to="/" replace />} />
	</>;
}
