import { apiClient } from './client';
export const getAnneesScolaires = () => apiClient.get('/api/annees-scolaires');
export const createAnneeScolaire = (payload) => apiClient.post('/api/annees-scolaires', payload);
export const updateAnneeScolaire = (id, payload) => apiClient.put(`/api/annees-scolaires/${id}`, payload);
export const deleteAnneeScolaire = (id) => apiClient.delete(`/api/annees-scolaires/${id}`);
export const activerAnneeScolaire = (id) => apiClient.patch(`/api/annees-scolaires/${id}/activer`, {});
