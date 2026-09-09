export const fullName = (item = {}) => `${item.prenom || ''} ${item.nom || ''}`.trim();
