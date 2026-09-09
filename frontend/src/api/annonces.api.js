import { apiClient } from './client';

export const getAnnonces = () => apiClient.get('/api/annonces');
export const getAnnonce = (id) => apiClient.get(`/api/annonces/${id}`);
export const deleteAnnonce = (id) => apiClient.delete(`/api/annonces/${id}`);
export const createAnnonce = ({ titre, contenu, fichier }) => { const data = new FormData(); data.append('titre', titre); data.append('contenu', contenu || ''); if (fichier) data.append('fichier', fichier); return apiClient.upload('/api/annonces', data); };
export const updateAnnonce = (id, { titre, contenu, fichier }) => { const data = new FormData(); data.append('titre', titre); data.append('contenu', contenu || ''); if (fichier) data.append('fichier', fichier); return apiClient.upload(`/api/annonces/${id}`, data, 'PUT'); };
export const downloadAnnonce = (id) => apiClient.download(`/api/annonces/${id}/fichier`);
