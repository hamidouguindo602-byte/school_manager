import ResourcePage from '../../components/common/ResourcePage';
import { getEcheancesEnRetard } from '../../api/echeances.api';
export default function EcheancesEnRetardPage() { return <ResourcePage title="Échéances en retard" description="Identifiez les échéances non réglées." loader={getEcheancesEnRetard} columns={[{ label: 'Libellé', key: 'libelle' }, { label: 'Montant', key: 'montant' }, { label: 'Date limite', key: 'dateLimite' }]} />; }
