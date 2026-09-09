export default function LoadingState({ label = 'Chargement...' }) { return <p className="state-message" aria-live="polite">{label}</p>; }
