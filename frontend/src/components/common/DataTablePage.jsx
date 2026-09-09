import { useMemo, useState } from 'react';
import { useEntityList } from '../../hooks/useEntityList';

export default function DataTablePage({ eyebrow, title, description, loader, columns, emptyLabel }) {
  const { items, loading, error } = useEntityList(loader);
  const [query, setQuery] = useState('');
  const filtered = useMemo(() => items.filter((item) => JSON.stringify(item).toLowerCase().includes(query.toLowerCase())), [items, query]);
  return <section className="page-shell"><div className="page-header"><div><p className="eyebrow">{eyebrow}</p><h1>{title}</h1><p className="page-lead">{description}</p></div></div><div className="card-panel entity-panel"><div className="table-toolbar"><strong>{items.length} élément{items.length > 1 ? 's' : ''}</strong><input className="search-input" placeholder="Rechercher..." value={query} onChange={(event) => setQuery(event.target.value)} /></div>{loading ? <p className="state-message">Chargement...</p> : error ? <p className="state-message error-text">{error}</p> : <div className="table-wrap"><table><thead><tr>{columns.map((column) => <th key={column.label}>{column.label}</th>)}</tr></thead><tbody>{filtered.map((item, index) => <tr key={item.id || item.idAnnonce || item.idPaiement || index}>{columns.map((column) => <td key={column.label}>{column.render ? column.render(item) : item[column.key] || '—'}</td>)}</tr>)}{!filtered.length && <tr><td colSpan={columns.length} className="state-message">{emptyLabel}</td></tr>}</tbody></table></div>}</div></section>;
}