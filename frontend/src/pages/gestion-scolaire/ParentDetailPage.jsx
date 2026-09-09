import { useParams } from 'react-router-dom';
import EntityDetailPage from '../../components/common/EntityDetailPage';
import { getParent } from '../../api/parents.api';
export default function ParentDetailPage() { const { id } = useParams(); return <EntityDetailPage title="Dossier parent" eyebrow="Gestion scolaire" loader={() => getParent(id)} sections={[{ title: 'Identité', fields: [{ label: 'Prénom', key: 'prenom' }, { label: 'Nom', key: 'nom' }] }, { title: 'Contact', fields: [{ label: 'Téléphone', key: 'numeroTelephone' }, { label: 'Email', key: 'email' }] }]} />; }
