export const normalizeGrade = (item = {}) => ({ ...item, valeur: Number(item.valeur || 0) });
