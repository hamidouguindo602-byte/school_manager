import { apiClient } from './client';
export const getParents = () => apiClient.get('/api/parents');
export const getParent = (id) => apiClient.get(`/api/parents/${id}`);
export const createParent = (payload) => apiClient.post('/api/parents', payload);
export const updateParent = (id, payload) => apiClient.put(`/api/parents/${id}`, payload);
export const deleteParent = (id) => apiClient.delete(`/api/parents/${id}`);
export const getParentEleves = (id) => apiClient.get(`/api/parents/${id}/eleves`);
