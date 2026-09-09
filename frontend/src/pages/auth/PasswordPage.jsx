import { useState } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import { changePassword, requestPasswordReset, resetPassword } from '../../api/auth.api';

export default function PasswordPage({ mode = 'change' }) {
  const [params] = useSearchParams();
  const [values, setValues] = useState({ email: '', token: params.get('token') || '', ancienMotDePasse: '', nouveauMotDePasse: '' });
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const update = (event) => setValues({ ...values, [event.target.name]: event.target.value });
  const submit = async (event) => { event.preventDefault(); setLoading(true); setMessage(''); setError(''); try { if (mode === 'forgot') await requestPasswordReset(values.email); else if (mode === 'reset') await resetPassword(values.token, values.nouveauMotDePasse); else await changePassword(values.ancienMotDePasse, values.nouveauMotDePasse); setMessage('Opération effectuée avec succès.'); } catch (requestError) { setError(requestError.message); } finally { setLoading(false); } };
  const fields = mode === 'forgot' ? [['email', 'Adresse email', 'email']] : mode === 'reset' ? [['token', 'Token reçu par email', 'text'], ['nouveauMotDePasse', 'Nouveau mot de passe', 'password']] : [['ancienMotDePasse', 'Ancien mot de passe', 'password'], ['nouveauMotDePasse', 'Nouveau mot de passe', 'password']];
  const title = mode === 'forgot' ? 'Mot de passe oublié' : mode === 'reset' ? 'Réinitialiser le mot de passe' : 'Modifier mon mot de passe';
  return <main className="auth-page"><section className="auth-card"><p className="eyebrow">Sécurité du compte</p><h1>{title}</h1><p>Utilisez un mot de passe contenant une majuscule, une minuscule, un chiffre et un caractère spécial.</p><form className="auth-form" onSubmit={submit}>{fields.map(([name, label, type]) => <label className="field" key={name}>{label}<input required name={name} type={type} value={values[name]} onChange={update} /></label>)}{error && <div className="error-box">{error}</div>}{message && <div className="feedback-box">{message}</div>}<button className="primary-button" disabled={loading}>{loading ? 'Traitement...' : 'Valider'}</button></form><Link className="text-link" to="/login">Retour à la connexion</Link></section></main>;
}