import { useEffect, useState } from 'react';
import { getClasses } from '../../api/classes.api';
import { getGrilleClasse } from '../../api/emploisDuTemps.api';

const weekdays = ['LUNDI', 'MARDI', 'MERCREDI', 'JEUDI', 'VENDREDI'];

export default function EmploiDuTempsPage() {
  const [classes, setClasses] = useState([]);
  const [classId, setClassId] = useState('');
  const [grid, setGrid] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  useEffect(() => { getClasses().then(setClasses).catch((requestError) => setError(requestError.message)); }, []);
  const selectClass = async (event) => { const id = event.target.value; setClassId(id); if (!id) return setGrid({}); setLoading(true); setError(''); try { setGrid(await getGrilleClasse(id) || {}); } catch (requestError) { setError(requestError.message); } finally { setLoading(false); } };
  return <section className="page-shell"><div className="page-header"><div><p className="eyebrow">Organisation pédagogique</p><h1>Emploi du temps</h1><p className="page-lead">Consultez la grille hebdomadaire d’une classe.</p></div></div><div className="card-panel absence-filters"><label className="field"><span>Choisir une classe</span><select value={classId} onChange={selectClass}><option value="">Sélectionner une classe...</option>{classes.map((schoolClass) => <option key={schoolClass.id} value={schoolClass.id}>{schoolClass.nom || schoolClass.name || `Classe ${schoolClass.id}`}</option>)}</select></label></div>{error && <div className="error-box">{error}</div>}{classId && <div className="schedule-grid">{weekdays.map((day) => <section className="schedule-day" key={day}><h2>{day}</h2>{loading ? <p className="state-message">...</p> : (grid[day] || []).map((slot) => <article className="schedule-slot" key={slot.id}><strong>{slot.nomMatiere || 'Matière'}</strong><span>{slot.heureDebut} - {slot.heureFin}</span><small>{slot.nomEnseignant || ''} {slot.prenomEnseignant || ''}</small></article>)}{!loading && !(grid[day] || []).length && <p className="day-empty">Aucun cours</p>}</section>)}</div>}</section>;
}