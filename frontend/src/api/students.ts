import { apiClient } from './client'
import type { Student, StudentPayload } from '../types/student'

const RESOURCE = '/students'

export const studentsApi = {
  list: async (): Promise<Student[]> => {
    const { data } = await apiClient.get<Student[]>(RESOURCE)
    return data
  },

  getById: async (id: number): Promise<Student> => {
    const { data } = await apiClient.get<Student>(`${RESOURCE}/${id}`)
    return data
  },

  create: async (payload: StudentPayload): Promise<Student> => {
    const { data } = await apiClient.post<Student>(RESOURCE, payload)
    return data
  },

  update: async (id: number, payload: StudentPayload): Promise<Student> => {
    const { data } = await apiClient.put<Student>(`${RESOURCE}/${id}`, payload)
    return data
  },

  remove: async (id: number): Promise<void> => {
    await apiClient.delete(`${RESOURCE}/${id}`)
  },
}
