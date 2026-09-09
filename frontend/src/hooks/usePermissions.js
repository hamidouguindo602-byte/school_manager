import { useAuth } from './useAuth';

export function usePermissions() { const { user } = useAuth(); const permissions = user?.permissions || user?.permissionNames || []; return { permissions, hasPermission: (permission) => user?.role === 'ADMIN' || permissions.includes(permission) }; }
