export interface Student {
  id: number
  firstName: string
  lastName: string
  email: string
  birthDate?: string
}

export type StudentPayload = Omit<Student, 'id'>
