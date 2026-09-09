import { apiClient } from './client';
export const getLogs = () => apiClient.get('/api/logs');
export const deleteLog = (id) => apiClient.delete(`/api/logs/${id}`);
