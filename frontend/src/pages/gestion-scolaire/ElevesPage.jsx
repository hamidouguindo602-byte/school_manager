import { useMemo, useState } from 'react';
import { createEleve, deleteEleve, getEleves } from '../../api/eleves.api';
import { useEntityList } from '../../hooks/useEntityList';

const initialForm = { nom: '', prenom: '', numeroTelephone: '', email: '', motDePasse: '', dateNaissance: '', adresse: '' };

export default function ElevesPage() {
	const { items, loading, error, reload } = useEntityList(getEleves, []);
	const [query, setQuery] = useState('');
	const [form, setForm] = useState(initialForm);
	const [showForm, setShowForm] = useState(false);
	const [saving, setSaving] = useState(false);
	const [feedback, setFeedback] = useState('');
	const filtered = useMemo(() => items.filter((student) => `${student.prenom} ${student.nom} ${student.email || ''}`.toLowerCase().includes(query.toLowerCase())), [items, query]);

	const updateField = (event) => setForm({ ...form, [event.target.name]: event.target.value });
	const submit = async (event) => {
		event.preventDefault(); setSaving(true); setFeedback('');
		try { await createEleve(form); setForm(initialForm); setShowForm(false); setFeedback('Élève ajouté avec succès.'); await reload(); }
		catch (requestError) { setFeedback(requestError.message); }
		finally { setSaving(false); }
	};
	const remove = async (student) => {
		if (!window.confirm(`Supprimer ${student.prenom} ${student.nom} ?`)) return;
		try { await deleteEleve(student.id); await reload(); setFeedback('Élève supprimé.'); } catch (requestError) { setFeedback(requestError.message); }
	};

	return <section className="page-shell">
		<div className="page-header"><div><p className="eyebrow">Gestion scolaire</p><h1>Élèves</h1><p className="page-lead">Gérez les dossiers et les informations des élèves inscrits.</p></div><button className="primary-button" onClick={() => setShowForm(!showForm)}>{showForm ? 'Fermer' : '+ Ajouter un élève'}</button></div>
		{feedback && <div className="feedback-box">{feedback}</div>}
		{showForm && <form className="card-panel entity-form" onSubmit={submit}><h2>Nouvel élève</h2><div className="form-grid">{[['prenom','Prénom'],['nom','Nom'],['numeroTelephone','Téléphone'],['email','Email'],['dateNaissance','Date de naissance'],['motDePasse','Mot de passe'],['adresse','Adresse']].map(([name, label]) => <label className="field" key={name}>{label}<input required={['prenom','nom','numeroTelephone','dateNaissance','motDePasse'].includes(name)} name={name} type={name === 'dateNaissance' ? 'date' : name === 'motDePasse' ? 'password' : name === 'email' ? 'email' : 'text'} value={form[name]} onChange={updateField} /></label>)}</div><button className="primary-button" disabled={saving}>{saving ? 'Enregistrement...' : 'Enregistrer'}</button></form>}
		<div className="card-panel entity-panel"><div className="table-toolbar"><div><strong>{items.length}</strong> élève{items.length > 1 ? 's' : ''}</div><input className="search-input" placeholder="Rechercher un élève..." value={query} onChange={(event) => setQuery(event.target.value)} /></div>{loading ? <p className="state-message">Chargement des élèves...</p> : error ? <p className="state-message error-text">{error}</p> : <div className="table-wrap"><table><thead><tr><th>Élève</th><th>Contact</th><th>Statut</th><th>Actions</th></tr></thead><tbody>{filtered.map((student) => <tr key={student.id}><td><strong>{student.prenom} {student.nom}</strong><small>{student.dateNaissance || 'Date non renseignée'}</small></td><td>{student.email || student.numeroTelephone}</td><td><span className="status-pill">{student.statut || 'ACTIF'}</span></td><td><button className="table-action danger-action" onClick={() => remove(student)}>Supprimer</button></td></tr>)}{!filtered.length && <tr><td colSpan="4" className="state-message">Aucun élève trouvé.</td></tr>}</tbody></table></div>}</div>
	</section>;
}
