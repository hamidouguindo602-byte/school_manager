const API_BASE = '/api/authentification';
import { getAuthSession } from '../auth/authStorage';

export async function logoutRequest() {
  const { token } = getAuthSession();
  await fetch(`${API_BASE}/deconnexion`, { method: 'POST', headers: token ? { Authorization: `Bearer ${token}` } : {} }).catch(() => undefined);
}

export async function loginRequest(identifiant, motDePasse) {
  const response = await fetch(`${API_BASE}/connexion`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ identifiant, motDePasse }),
  });

  const data = await response.json().catch(() => ({}));

  if (!response.ok) {
    const message =
      data?.message ||
      data?.error ||
      'Échec de la connexion. Vérifie tes identifiants.';
    throw new Error(message);
  }

  return data;
}

export async function changePassword(ancienMotDePasse, nouveauMotDePasse) {
  const response = await fetch(`${API_BASE}/modifier-mot-de-passe`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ ancienMotDePasse, nouveauMotDePasse }) });
  const data = await response.text(); if (!response.ok) throw new Error(data || 'Modification impossible.'); return data;
}

export async function requestPasswordReset(email) {
  const response = await fetch(`${API_BASE}/mot-de-passe-oublie`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ email }) });
  const data = await response.text(); if (!response.ok) throw new Error(data || 'Demande impossible.'); return data;
}

export async function resetPassword(token, nouveauMotDePasse) {
  const response = await fetch(`${API_BASE}/reinitialiser-mot-de-passe`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ token, nouveauMotDePasse }) });
  const data = await response.text(); if (!response.ok) throw new Error(data || 'Réinitialisation impossible.'); return data;
}
