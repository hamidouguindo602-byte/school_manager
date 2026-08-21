import { NavLink } from 'react-router-dom'

const links = [
  { to: '/', label: 'Tableau de bord', end: true },
  { to: '/students', label: 'Etudiants' },
  // Ajouter ici les prochains modules (enseignants, cours, notes...).
]

export function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="sidebar__brand">School Management</div>
      <nav className="sidebar__nav">
        {links.map((link) => (
          <NavLink
            key={link.to}
            to={link.to}
            end={link.end}
            className={({ isActive }) => (isActive ? 'sidebar__link sidebar__link--active' : 'sidebar__link')}
          >
            {link.label}
          </NavLink>
        ))}
      </nav>
    </aside>
  )
}
