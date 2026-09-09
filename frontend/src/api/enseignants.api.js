import { apiClient } from './client';
export const getEnseignants = () => apiClient.get('/api/enseignants');
export const getEnseignant = (id) => apiClient.get(`/api/enseignants/${id}`);
export const createEnseignant = (payload) => apiClient.post('/api/enseignants', payload);
export const updateEnseignant = (id, payload) => apiClient.put(`/api/enseignants/${id}`, payload);
export const deleteEnseignant = (id) => apiClient.delete(`/api/enseignants/${id}`);
export const getEnseignantEmplois = (id) => apiClient.get(`/api/enseignants/${id}/emplois-du-temps`);
export const getEnseignantEvaluations = (id) => apiClient.get(`/api/enseignants/${id}/evaluations`);
