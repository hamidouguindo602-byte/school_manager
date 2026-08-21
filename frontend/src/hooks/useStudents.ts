import { useCallback, useEffect, useState } from 'react'
import { studentsApi } from '../api/students'
import type { Student } from '../types/student'

/** Chargement de la liste des etudiants (etat + erreur + rechargement). */
export function useStudents() {
  const [students, setStudents] = useState<Student[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const reload = useCallback(async () => {
    setLoading(true)
    setError(null)
    try {
      setStudents(await studentsApi.list())
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erreur inconnue')
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    void reload()
  }, [reload])

  return { students, loading, error, reload }
}
