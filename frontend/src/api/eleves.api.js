import { apiClient } from './client';

export const getEleves = () => apiClient.get('/api/eleves');
export const getEleve = (id) => apiClient.get(`/api/eleves/${id}`);
export const createEleve = (payload) => apiClient.post('/api/eleves', payload);
export const updateEleve = (id, payload) => apiClient.put(`/api/eleves/${id}`, payload);
export const deleteEleve = (id) => apiClient.delete(`/api/eleves/${id}`);
export const getEleveAbsences = (id) => apiClient.get(`/api/eleves/${id}/absences`);
