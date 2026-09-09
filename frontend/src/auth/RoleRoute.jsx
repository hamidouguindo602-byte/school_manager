import { Navigate } from 'react-router-dom';
import { useAuth } from './AuthProvider';

export function RoleRoute({ allowedRoles, children }) {
  const { user } = useAuth();

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  const userRole = user.role;
  const hasAccess = allowedRoles.includes(userRole);

  if (!hasAccess) {
    return <Navigate to="/" replace />;
  }

  return children;
}
