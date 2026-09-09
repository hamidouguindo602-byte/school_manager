import { useParams } from 'react-router-dom';
import EntityDetailPage from '../../components/common/EntityDetailPage';
import { getUtilisateur } from '../../api/utilisateurs.api';
export default function UtilisateurDetailPage() { const { id } = useParams(); return <EntityDetailPage title="Détail utilisateur" eyebrow="Administration" loader={() => getUtilisateur(id)} sections={[{ title: 'Compte', fields: [{ label: 'Prénom', key: 'prenom' }, { label: 'Nom', key: 'nom' }, { label: 'Email', key: 'email' }, { label: 'Téléphone', key: 'numeroTelephone' }] }, { title: 'Accès', fields: [{ label: 'Rôle', key: 'typeRole' }, { label: 'Statut', key: 'statut' }, { label: 'Permissions', render: (item) => (item.permissions || []).join(', ') || '—' }] }]} />; }
