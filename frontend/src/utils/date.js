export const formatDate = (value) => value ? new Intl.DateTimeFormat('fr-FR', { dateStyle: 'medium' }).format(new Date(value)) : '—';
export const toInputDate = (value) => value ? new Date(value).toISOString().slice(0, 10) : '';
