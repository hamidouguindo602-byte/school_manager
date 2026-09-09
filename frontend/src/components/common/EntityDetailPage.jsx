import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function EntityDetailPage({ title, eyebrow = 'Détail', loader, sections = [], actions }) {
  const navigate = useNavigate();
  const [entity, setEntity] = useState(null); const [loading, setLoading] = useState(true); const [error, setError] = useState('');
  useEffect(() => { let active = true; setLoading(true); loader().then((data) => active && setEntity(data)).catch((requestError) => active && setError(requestError.message)).finally(() => active && setLoading(false)); return () => { active = false; }; }, [loader]);
  return <section className="page-shell detail-page"><div className="detail-back"><button className="link-button" onClick={() => navigate(-1)}>← Retour</button></div><div className="page-header"><div><p className="eyebrow">{eyebrow}</p><h1>{title}</h1></div>{actions}</div>{loading && <div className="card-panel state-message">Chargement du détail...</div>}{error && <div className="error-box">{error}</div>}{entity && !loading && <div className="detail-sections">{sections.map((section) => <section className="card-panel detail-section" key={section.title}><h2>{section.title}</h2><dl>{section.fields.map((field) => <div className="detail-field" key={field.label}><dt>{field.label}</dt><dd>{field.render ? field.render(entity) : entity[field.key] ?? '—'}</dd></div>)}</dl></section>)}</div>}</section>;
}