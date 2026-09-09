import { useCallback, useState } from 'react';

export function useApi(action) { const [loading, setLoading] = useState(false); const [error, setError] = useState(''); const execute = useCallback(async (...args) => { setLoading(true); setError(''); try { return await action(...args); } catch (requestError) { setError(requestError.message || 'Une erreur est survenue.'); throw requestError; } finally { setLoading(false); } }, [action]); return { execute, loading, error, setError }; }
