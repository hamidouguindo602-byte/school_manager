import { apiClient } from './client';
export const getPermissions = () => apiClient.get('/api/permissions');
export const createPermission = (payload) => apiClient.post('/api/permissions', payload);
export const updatePermission = (id, payload) => apiClient.put(`/api/permissions/${id}`, payload);
export const deletePermission = (id) => apiClient.delete(`/api/permissions/${id}`);
