import { useParams } from 'react-router-dom';
import EntityDetailPage from '../../components/common/EntityDetailPage';
import { getFormation } from '../../api/formations.api';
export default function FormationDetailPage() { const { id } = useParams(); return <EntityDetailPage title="Détail de la formation" eyebrow="Gestion scolaire" loader={() => getFormation(id)} sections={[{ title: 'Formation', fields: [{ label: 'Nom', key: 'nomFormation' }, { label: 'Description', key: 'description' }, { label: 'Durée', key: 'duree' }, { label: 'Statut', key: 'statut' }] }]} />; }
