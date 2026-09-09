import { getAuthSession, clearAuthSession } from '../auth/authStorage';
export const authStore = { get: getAuthSession, clear: clearAuthSession };
