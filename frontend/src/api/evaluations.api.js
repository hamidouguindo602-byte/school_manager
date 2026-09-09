import { apiClient } from './client';

export const getEvaluations = () => apiClient.get('/api/evaluations');
export const getEvaluation = (id) => apiClient.get(`/api/evaluations/${id}`);
export const createEvaluation = (payload) => apiClient.post('/api/evaluations', payload);
export const updateEvaluation = (id, payload) => apiClient.put(`/api/evaluations/${id}`, payload);
export const deleteEvaluation = (id) => apiClient.delete(`/api/evaluations/${id}`);
