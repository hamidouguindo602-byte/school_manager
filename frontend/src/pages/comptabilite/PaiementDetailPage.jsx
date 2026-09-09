import { useParams } from 'react-router-dom';
import EntityDetailPage from '../../components/common/EntityDetailPage';
import { getPaiement } from '../../api/paiements.api';
export default function PaiementDetailPage() { const { id } = useParams(); return <EntityDetailPage title="Détail du paiement" eyebrow="Comptabilité" loader={() => getPaiement(id)} sections={[{ title: 'Règlement', fields: [{ label: 'Échéance', key: 'idEcheance' }, { label: 'Montant', key: 'montant' }, { label: 'Date', key: 'datePaiement' }, { label: 'Mode', key: 'modePaiement' }, { label: 'Statut', key: 'statut' }] }]} />; }
