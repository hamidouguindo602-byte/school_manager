import { apiClient } from './client';

export const getGrilleClasse = (id) => apiClient.get(`/api/emploi-du-temps/grille/classe/${id}`);
export const getEmploisClasse = (id) => apiClient.get(`/api/emploi-du-temps/classe/${id}`);
export const createCreneau = (payload) => apiClient.post('/api/emploi-du-temps', payload);
export const updateCreneau = (id, payload) => apiClient.put(`/api/emploi-du-temps/${id}`, payload);
export const deleteCreneau = (id) => apiClient.delete(`/api/emploi-du-temps/${id}`);
