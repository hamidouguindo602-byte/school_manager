import { apiClient } from './client';
export const getUtilisateurs = () => apiClient.get('/api/utilisateurs');
export const getUtilisateur = (id) => apiClient.get(`/api/utilisateurs/${id}`);
export const createUtilisateur = (payload) => apiClient.post('/api/utilisateurs', payload);
export const updateUtilisateur = (id, payload) => apiClient.put(`/api/utilisateurs/${id}`, payload);
export const deleteUtilisateur = (id) => apiClient.delete(`/api/utilisateurs/${id}`);
export const activerUtilisateur = (id) => apiClient.post(`/api/utilisateurs/${id}/activer`, {});
export const desactiverUtilisateur = (id) => apiClient.post(`/api/utilisateurs/${id}/desactiver`, {});
