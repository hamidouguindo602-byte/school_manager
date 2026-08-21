import axios from 'axios'
import type { ApiError } from '../types/api'

export const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL ?? '/api',
  headers: { 'Content-Type': 'application/json' },
})

/** Intercepteur unique : on remonte un message lisible aux composants. */
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    const apiError = error.response?.data as ApiError | undefined
    return Promise.reject(new Error(apiError?.message ?? error.message ?? 'Erreur reseau'))
  },
)
