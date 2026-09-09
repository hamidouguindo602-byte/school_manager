import { apiClient } from './client';
export const getInscriptions = () => apiClient.get('/api/inscriptions');
export const createInscription = (payload) => apiClient.post('/api/inscriptions', payload);
export const updateInscription = (id, payload) => apiClient.put(`/api/inscriptions/${id}`, payload);
export const deleteInscription = (id) => apiClient.delete(`/api/inscriptions/${id}`);
export const validerInscription = (id) => apiClient.put(`/api/inscriptions/${id}/valider`, {});
export const annulerInscription = (id) => apiClient.put(`/api/inscriptions/${id}/annuler`, {});
