import { useParams } from 'react-router-dom';
import EntityDetailPage from '../../components/common/EntityDetailPage';
import { getEnseignant } from '../../api/enseignants.api';
export default function EnseignantDetailPage() { const { id } = useParams(); return <EntityDetailPage title="Dossier enseignant" eyebrow="Gestion scolaire" loader={() => getEnseignant(id)} sections={[{ title: 'Identité', fields: [{ label: 'Prénom', key: 'prenom' }, { label: 'Nom', key: 'nom' }, { label: 'Spécialité', key: 'specialite' }, { label: 'Statut', key: 'statut' }] }, { title: 'Contact', fields: [{ label: 'Téléphone', key: 'numeroTelephone' }, { label: 'Email', key: 'email' }] }]} />; }
