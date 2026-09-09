import { apiClient } from './client';
export const getAssignations = () => apiClient.get('/api/assignations');
export const getAssignationsClasse = (id) => apiClient.get(`/api/assignations/classe/${id}`);
export const createAssignation = (payload) => apiClient.post('/api/assignations', payload);
export const updateAssignation = (id, payload) => apiClient.put(`/api/assignations/${id}`, payload);
export const deleteAssignation = (id) => apiClient.delete(`/api/assignations/${id}`);
