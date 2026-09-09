import { useEffect, useState } from 'react';
import { createAnnonce, deleteAnnonce, downloadAnnonce, getAnnonces, updateAnnonce } from '../../api/annonces.api';

const empty = { titre: '', contenu: '', fichier: null };

export default function AnnoncesPage() {
  const [items, setItems] = useState([]);
  const [form, setForm] = useState(empty);
  const [editing, setEditing] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const load = async () => {
    setLoading(true);
    try { setItems(await getAnnonces() || []); } catch (requestError) { setError(requestError.message); } finally { setLoading(false); }
  };
  useEffect(() => { load(); }, []);
  const edit = (item) => { setEditing(item); setForm({ titre: item.titre || '', contenu: item.contenu || '', fichier: null }); setShowForm(true); };
  const submit = async (event) => {
    event.preventDefault(); setSaving(true); setMessage(''); setError('');
    try { if (editing) await updateAnnonce(editing.idAnnonce, form); else await createAnnonce(form); setMessage('Annonce enregistrée.'); setForm(empty); setEditing(null); setShowForm(false); await load(); }
    catch (requestError) { setError(requestError.message); } finally { setSaving(false); }
  };
  const remove = async (id) => { if (!window.confirm('Supprimer cette annonce ?')) return; try { await deleteAnnonce(id); await load(); } catch (requestError) { setError(requestError.message); } };
  const download = async (id, name) => { try { const blob = await downloadAnnonce(id); const url = URL.createObjectURL(blob); const link = document.createElement('a'); link.href = url; link.download = name || `annonce-${id}.pdf`; link.click(); URL.revokeObjectURL(url); } catch (requestError) { setError(requestError.message); } };

  return <section className="page-shell"><div className="page-header"><div><p className="eyebrow">Vie de l’établissement</p><h1>Communications</h1><p className="page-lead">Publiez des annonces et partagez les documents officiels.</p></div><button className="primary-button" onClick={() => { setEditing(null); setForm(empty); setShowForm(!showForm); }}>{showForm ? 'Fermer' : '+ Nouvelle annonce'}</button></div>{message && <div className="feedback-box">{message}</div>}{error && <div className="error-box">{error}</div>}{showForm && <form className="card-panel entity-form" onSubmit={submit}><h2>{editing ? 'Modifier l’annonce' : 'Nouvelle annonce'}</h2><div className="form-grid"><label className="field">Titre<input required value={form.titre} onChange={(event) => setForm({ ...form, titre: event.target.value })} /></label><label className="field">Fichier PDF<input type="file" accept="application/pdf" onChange={(event) => setForm({ ...form, fichier: event.target.files[0] || null })} /></label></div><label className="field">Contenu<textarea rows="5" value={form.contenu} onChange={(event) => setForm({ ...form, contenu: event.target.value })} /></label><button className="primary-button" disabled={saving}>{saving ? 'Publication...' : 'Publier'}</button></form>}<div className="card-panel entity-panel">{loading ? <p className="state-message">Chargement...</p> : <div className="table-wrap"><table><thead><tr><th>Titre</th><th>Publication</th><th>Document</th><th>Actions</th></tr></thead><tbody>{items.map((item) => <tr key={item.idAnnonce}><td><strong>{item.titre}</strong><small>{item.contenu?.slice(0, 90)}</small></td><td>{item.datePublication || '—'}</td><td>{item.nomFichier || 'Aucun fichier'}</td><td>{item.nomFichier && <button className="table-action" onClick={() => download(item.idAnnonce, item.nomFichier)}>PDF</button>}<button className="table-action" onClick={() => edit(item)}>Modifier</button><button className="table-action danger-action" onClick={() => remove(item.idAnnonce)}>Supprimer</button></td></tr>)}{!items.length && <tr><td colSpan="4" className="state-message">Aucune annonce publiée.</td></tr>}</tbody></table></div>}</div></section>;
}
