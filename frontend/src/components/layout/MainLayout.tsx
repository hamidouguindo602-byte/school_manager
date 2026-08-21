import { Outlet } from 'react-router-dom'
import { Header } from './Header'
import { Sidebar } from './Sidebar'

/** Coquille de l'application : sidebar + header + zone de contenu routee. */
export function MainLayout() {
  return (
    <div className="layout">
      <Sidebar />
      <div className="layout__main">
        <Header />
        <main className="layout__content">
          <Outlet />
        </main>
      </div>
    </div>
  )
}
