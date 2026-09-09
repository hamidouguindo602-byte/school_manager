import { apiClient } from './client';

export const getPaiements = () => apiClient.get('/api/paiements');
export const getPaiement = (id) => apiClient.get(`/api/paiements/${id}`);
export const createPaiement = (payload) => apiClient.post('/api/paiements', payload);
export const updatePaiement = (id, payload) => apiClient.put(`/api/paiements/${id}`, payload);
export const deletePaiement = (id) => apiClient.delete(`/api/paiements/${id}`);
export const cancelPaiement = (id) => apiClient.patch(`/api/paiements/${id}/annuler`, {});
export const getPaiementRecu = (id) => apiClient.get(`/api/paiements/${id}/recu`);
