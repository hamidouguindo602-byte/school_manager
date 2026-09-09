import { useEffect, useState } from 'react';
import { downloadRecu, getRecus } from '../../api/recus.api';

export default function RecusPage() {
  const [items, setItems] = useState([]); const [error, setError] = useState('');
  useEffect(() => { getRecus().then(setItems).catch((requestError) => setError(requestError.message)); }, []);
  const download = async (item) => { try { const blob = await downloadRecu(item.id || item.idRecu); const url = URL.createObjectURL(blob); const link = document.createElement('a'); link.href = url; link.download = `recu-${item.id || item.idRecu}.pdf`; link.click(); URL.revokeObjectURL(url); } catch (requestError) { setError(requestError.message); } };
  return <section className="page-shell"><div className="page-header"><div><p className="eyebrow">Gestion financière</p><h1>Reçus PDF</h1><p className="page-lead">Générez et téléchargez les reçus liés aux paiements.</p></div></div>{error && <div className="error-box">{error}</div>}<div className="card-panel entity-panel"><div className="table-wrap"><table><thead><tr><th>Numéro</th><th>Paiement</th><th>Date</th><th>Action</th></tr></thead><tbody>{items.map((item) => <tr key={item.id || item.idRecu}><td>{item.numero || item.reference || '—'}</td><td>{item.idPaiement || '—'}</td><td>{item.dateEmission || item.dateCreation || '—'}</td><td><button className="table-action" onClick={() => download(item)}>Télécharger PDF</button></td></tr>)}{!items.length && <tr><td colSpan="4" className="state-message">Aucun reçu disponible.</td></tr>}</tbody></table></div></div></section>;
}