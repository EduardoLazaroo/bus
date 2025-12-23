
export enum UserRole {
  ADMIN = 'ADMIN',
  CLIENT = 'CLIENT',
  DRIVER = 'DRIVER',
  OWNER = 'OWNER',
}
export interface UserDTO {
  id?: number;
  name?: string;
  email?: string;
  password?: string;
  phone?: string;
  profileImage?: string;
  cep?: string;
  logradouro?: string;
  bairro?: string;
  complemento?: string;
  numero?: string;

  role?: UserRole;
  token?: string; // JWT retornado no login
}
