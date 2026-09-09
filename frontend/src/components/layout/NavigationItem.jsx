import { NavLink } from 'react-router-dom';
export default function NavigationItem({ to, label, end }) { return <NavLink end={end} className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`} to={to}><span className="sidebar-link-dot" aria-hidden="true" />{label}</NavLink>; }
