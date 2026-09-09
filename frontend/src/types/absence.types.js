export const normalizeAbsence = (item = {}) => ({ ...item, justifiee: Boolean(item.justifiee) });
