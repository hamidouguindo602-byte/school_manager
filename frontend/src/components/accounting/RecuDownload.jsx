import { downloadBlob } from '../../utils/download';
export default function RecuDownload({ load, filename = 'recu.pdf' }) { return <button className="table-action" onClick={async () => downloadBlob(await load(), filename)}>Télécharger le reçu PDF</button>; }
