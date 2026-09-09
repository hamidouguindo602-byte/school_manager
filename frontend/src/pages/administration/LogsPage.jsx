import ResourcePage from '../../components/common/ResourcePage';
import { getLogs } from '../../api/logs.api';
export default function LogsPage() { return <ResourcePage title="Journaux système" description="Suivez les actions enregistrées par l’application." loader={getLogs} columns={[{ label: 'Date', key: 'date' }, { label: 'Action', key: 'action' }, { label: 'Utilisateur', key: 'utilisateur' }, { label: 'Détail', key: 'details' }]} />; }
