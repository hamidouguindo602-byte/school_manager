import { usePermissions } from '../../hooks/usePermissions';
export default function PermissionGate({ permission, children, fallback = null }) { const { hasPermission } = usePermissions(); return hasPermission(permission) ? children : fallback; }
