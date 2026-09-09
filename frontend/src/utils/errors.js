export const getErrorMessage = (error) => error?.response?.data?.message || error?.message || 'Une erreur est survenue.';
