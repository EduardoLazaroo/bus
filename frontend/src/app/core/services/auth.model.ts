// src/app/core/services/auth.model.ts

export type UserRole = 'USER' | 'ADMIN' | 'DRIVER';

export interface UserDTO {
  id?: number;
  name: string;
  email: string;
  password?: string; // usado apenas para registro/login
  role?: UserRole;
  token?: string;    // JWT retornado no login
}
