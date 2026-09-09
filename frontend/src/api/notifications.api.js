import { apiClient } from './client';
export const getNotifications = () => apiClient.get('/api/notifications');
export const createNotification = (payload) => apiClient.post('/api/notifications', payload);
export const markNotificationRead = (id) => apiClient.patch(`/api/notifications/${id}/lire`, {});
export const deleteNotification = (id) => apiClient.delete(`/api/notifications/${id}`);
