import { clearAuthSession, getAuthSession } from '../auth/authStorage';

const API_BASE = import.meta.env.VITE_API_URL || '';

async function request(path, options = {}) {
	const { token } = getAuthSession();
	const headers = new Headers(options.headers || {});
	if (!headers.has('Content-Type') && options.body && !(options.body instanceof FormData)) headers.set('Content-Type', 'application/json');
	if (token) headers.set('Authorization', `Bearer ${token}`);

	const response = await fetch(`${API_BASE}${path}`, { ...options, headers });
	const data = await response.json().catch(() => null);
	if (response.status === 401) clearAuthSession();
	if (!response.ok) throw new Error(data?.message || data?.error || `La requête a échoué (${response.status}).`);
	return data;
}

async function download(path) {
	const { token } = getAuthSession();
	const headers = token ? { Authorization: `Bearer ${token}` } : {};
	const response = await fetch(`${API_BASE}${path}`, { headers });
	if (!response.ok) throw new Error(`Téléchargement impossible (${response.status}).`);
	return response.blob();
}

export const apiClient = {
	get: (path) => request(path),
	post: (path, body) => request(path, { method: 'POST', body: JSON.stringify(body) }),
	put: (path, body) => request(path, { method: 'PUT', body: JSON.stringify(body) }),
	patch: (path, body) => request(path, { method: 'PATCH', body: JSON.stringify(body) }),
	delete: (path) => request(path, { method: 'DELETE' }),
	upload: (path, formData, method = 'POST') => request(path, { method, body: formData }),
	download,
};
