import { useStudents } from '../hooks/useStudents'

export function StudentsPage() {
  const { students, loading, error, reload } = useStudents()

  return (
    <section>
      <div className="page-header">
        <h2>Etudiants</h2>
        <button type="button" onClick={() => void reload()}>
          Rafraichir
        </button>
      </div>

      {loading && <p className="muted">Chargement...</p>}
      {error && <p className="error">{error}</p>}

      {!loading && !error && (
        <table className="table">
          <thead>
            <tr>
              <th>Id</th>
              <th>Prenom</th>
              <th>Nom</th>
              <th>Email</th>
              <th>Naissance</th>
            </tr>
          </thead>
          <tbody>
            {students.length === 0 ? (
              <tr>
                <td colSpan={5} className="muted">
                  Aucun etudiant.
                </td>
              </tr>
            ) : (
              students.map((student) => (
                <tr key={student.id}>
                  <td>{student.id}</td>
                  <td>{student.firstName}</td>
                  <td>{student.lastName}</td>
                  <td>{student.email}</td>
                  <td>{student.birthDate ?? '-'}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      )}
    </section>
  )
}
