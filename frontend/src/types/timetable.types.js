export const normalizeSlot = (item = {}) => ({ ...item, heureDebut: item.heureDebut || '', heureFin: item.heureFin || '' });
