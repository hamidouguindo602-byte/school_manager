import { useParams } from 'react-router-dom';
import EntityDetailPage from '../../components/common/EntityDetailPage';
import { getAnnonce } from '../../api/annonces.api';
export default function AnnonceDetailPage() { const { id } = useParams(); return <EntityDetailPage title="Détail de l’annonce" eyebrow="Communication" loader={() => getAnnonce(id)} sections={[{ title: 'Publication', fields: [{ label: 'Titre', key: 'titre' }, { label: 'Contenu', key: 'contenu' }, { label: 'Date de publication', key: 'datePublication' }, { label: 'Fichier', key: 'nomFichier' }] }]} />; }
