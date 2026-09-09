export const normalizePayment = (item = {}) => ({ ...item, montant: Number(item.montant || 0) });
