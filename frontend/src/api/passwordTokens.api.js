import { apiClient } from './client';
export const getPasswordTokens = () => apiClient.get('/api/password-reset-tokens');
export const createPasswordToken = (payload) => apiClient.post('/api/password-reset-tokens', payload);
export const updatePasswordToken = (id, payload) => apiClient.put(`/api/password-reset-tokens/${id}`, payload);
export const deletePasswordToken = (id) => apiClient.delete(`/api/password-reset-tokens/${id}`);
