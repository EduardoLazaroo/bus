export type UserRole = 'CLIENT' | 'ADMIN' | 'DRIVER' | 'OWNER';

export interface UserDTO {
  id?: number;
  name: string;
  email: string;
  password?: string; // usado apenas para registro/login
  role?: UserRole;
  token?: string;    // JWT retornado no login
}
