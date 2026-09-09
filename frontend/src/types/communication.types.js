export const normalizeNotification = (item = {}) => ({ ...item, lu: Boolean(item.lu) });
