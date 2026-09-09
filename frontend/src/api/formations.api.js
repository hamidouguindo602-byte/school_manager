import { apiClient } from './client';
export const getFormations = () => apiClient.get('/api/formations');
export const getFormation = (id) => apiClient.get(`/api/formations/${id}`);
export const createFormation = (payload) => apiClient.post('/api/formations', payload);
export const updateFormation = (id, payload) => apiClient.put(`/api/formations/${id}`, payload);
export const deleteFormation = (id) => apiClient.delete(`/api/formations/${id}`);
export const getFormationClasses = (id) => apiClient.get(`/api/formations/${id}/classes`);
