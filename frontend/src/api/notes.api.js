import { apiClient } from './client';
export const getNotes = () => apiClient.get('/api/notes');
export const getNotesEleve = (id) => apiClient.get(`/api/eleves/${id}/notes`);
export const getNotesEvaluation = (id) => apiClient.get(`/api/evaluations/${id}/notes`);
export const createNote = (payload) => apiClient.post('/api/notes', payload);
export const updateNote = (id, payload) => apiClient.put(`/api/notes/${id}`, payload);
export const deleteNote = (id) => apiClient.delete(`/api/notes/${id}`);
