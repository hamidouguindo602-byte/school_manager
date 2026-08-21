/** Format d'erreur renvoye par le backend (ApiError cote Spring). */
export interface ApiError {
  timestamp: string
  status: number
  error: string
  message: string
  path: string
  fieldErrors?: Record<string, string>
}
