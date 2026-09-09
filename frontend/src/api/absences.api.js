import { apiClient } from './client';

export const getAbsencesEleve = (id) => apiClient.get(`/api/absences/eleve/${id}`);
export const getAbsencesClasse = (classeId, debut, fin) => apiClient.get(`/api/absences/classe/${classeId}${debut && fin ? `/periode?debut=${debut}&fin=${fin}` : ''}`);
export const getAbsenceStatsClasse = (classeId) => apiClient.get(`/api/absences/stats/classe/${classeId}`);
export const declarerAbsence = (payload) => apiClient.post('/api/absences', payload);
export const justifierAbsence = (id, payload) => apiClient.put(`/api/absences/${id}/justifier`, payload);
export const justifierAbsencesEnLot = (payload) => apiClient.put('/api/absences/batch-justifier', payload);
export const uploaderDocumentAbsence = (id, file) => { const data = new FormData(); data.append('document', file); return apiClient.upload(`/api/absences/${id}/document`, data, 'PUT'); };
export const getAbsencesEnseignant = (id) => apiClient.get(`/api/absences/enseignant/${id}`);
export const getAbsencesClasse = (id) => apiClient.get(`/api/absences/classe/${id}`);
export const getAbsencesClassePeriode = (id, debut, fin) => apiClient.get(`/api/absences/classe/${id}/periode?debut=${debut}&fin=${fin}`);
export const getAbsenceStats = (id) => apiClient.get(`/api/absences/stats/classe/${id}`);
