import ResourcePage from '../../components/common/ResourcePage';
import { createClasse, deleteClasse, getClasses, updateClasse } from '../../api/classes.api';
export default function ClassesPage() { return <ResourcePage title="Classes" description="Gérez les classes." loader={getClasses} createAction={createClasse} updateAction={updateClasse} deleteAction={deleteClasse} formFields={[{ name: 'nomClasse', label: 'Nom', required: true }, { name: 'niveau', label: 'Niveau', required: true }]} columns={[{ label: 'Classe', key: 'nomClasse' }, { label: 'Niveau', key: 'niveau' }]} />; }
