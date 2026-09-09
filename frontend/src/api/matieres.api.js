import { apiClient } from './client';
export const getMatieres = () => apiClient.get('/api/matieres');
export const createMatiere = (payload) => apiClient.post('/api/matieres', payload);
export const updateMatiere = (id, payload) => apiClient.put(`/api/matieres/${id}`, payload);
export const deleteMatiere = (id) => apiClient.delete(`/api/matieres/${id}`);
