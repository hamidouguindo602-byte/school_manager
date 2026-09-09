import { apiClient } from './client';

export const getClasses = () => apiClient.get('/api/classes');
export const createClasse = (payload) => apiClient.post('/api/classes', payload);
export const updateClasse = (id, payload) => apiClient.put(`/api/classes/${id}`, payload);
export const deleteClasse = (id) => apiClient.delete(`/api/classes/${id}`);
