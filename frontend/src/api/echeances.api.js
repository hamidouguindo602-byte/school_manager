import { apiClient } from './client';
export const getEcheances = () => apiClient.get('/api/echeances');
export const getEcheancesEnRetard = () => apiClient.get('/api/echeances/retard');
export const createEcheance = (payload) => apiClient.post('/api/echeances', payload);
export const updateEcheance = (id, payload) => apiClient.put(`/api/echeances/${id}`, payload);
export const deleteEcheance = (id) => apiClient.delete(`/api/echeances/${id}`);
