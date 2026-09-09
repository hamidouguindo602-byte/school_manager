import ResourcePage from '../../components/common/ResourcePage';
import { getPasswordTokens } from '../../api/passwordTokens.api';
export default function PasswordResetTokensPage() { return <ResourcePage title="Tokens de réinitialisation" description="Surveillez les demandes de réinitialisation." loader={getPasswordTokens} columns={[{ label: 'Utilisateur', key: 'userId' }, { label: 'Expiration', key: 'dateExpiration' }, { label: 'Utilisé', key: 'utilise' }]} />; }
