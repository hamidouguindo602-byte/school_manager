import { useCallback, useEffect, useState } from 'react';

export function useEntityList(loader) {
	const [items, setItems] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState('');

	const reload = useCallback(async () => {
		setLoading(true);
		setError('');
		try { setItems((await loader()) || []); }
		catch (requestError) { setError(requestError.message || 'Impossible de charger les données.'); }
		finally { setLoading(false); }
	}, [loader]);

	useEffect(() => { reload(); }, [reload]);
	return { items, setItems, loading, error, reload };
}
