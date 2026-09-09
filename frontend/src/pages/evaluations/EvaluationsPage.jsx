import DataTablePage from '../../components/common/DataTablePage';
import { getEvaluations } from '../../api/evaluations.api';

export default function EvaluationsPage() { return <DataTablePage eyebrow="Suivi pédagogique" title="Évaluations" description="Visualisez les évaluations planifiées par classe et matière." loader={getEvaluations} emptyLabel="Aucune évaluation enregistrée." columns={[{ label: 'Date', key: 'dateEvaluation' }, { label: 'Type', key: 'type' }, { label: 'Matière', key: 'nomMatiere' }, { label: 'Classe', key: 'nomClasse' }]} />; }