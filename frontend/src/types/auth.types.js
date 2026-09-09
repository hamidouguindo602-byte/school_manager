export const normalizeUser = (user = {}) => ({ ...user, permissions: user.permissions || [] });
