import { useParams } from 'react-router-dom';
import EntityDetailPage from '../../components/common/EntityDetailPage';
import { getEvaluation } from '../../api/evaluations.api';
export default function EvaluationDetailPage() { const { id } = useParams(); return <EntityDetailPage title="Détail de l’évaluation" eyebrow="Évaluations" loader={() => getEvaluation(id)} sections={[{ title: 'Évaluation', fields: [{ label: 'Type', key: 'type' }, { label: 'Date', key: 'dateEvaluation' }, { label: 'Matière', key: 'nomMatiere' }, { label: 'Classe', key: 'nomClasse' }, { label: 'Enseignant', key: 'idEnseignant' }] }]} />; }
