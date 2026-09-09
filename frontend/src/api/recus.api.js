import { apiClient } from './client';
export const getRecus = () => apiClient.get('/api/recus');
export const getRecu = (id) => apiClient.get(`/api/recus/${id}`);
export const downloadRecu = (id) => apiClient.download(`/api/recus/${id}/pdf`);
