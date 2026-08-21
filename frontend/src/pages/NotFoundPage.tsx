import { Link } from 'react-router-dom'

export function NotFoundPage() {
  return (
    <section>
      <h2>404</h2>
      <p className="muted">Cette page n'existe pas.</p>
      <Link to="/">Retour au tableau de bord</Link>
    </section>
  )
}
