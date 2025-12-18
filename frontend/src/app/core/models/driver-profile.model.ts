export interface DriverProfileCreateDTO {
  cpf: string;
  rg: string;
  cnhNumber: string;
  cnhCategory: string;
  cnhExpirationDate: string; // yyyy-MM-dd
  cnhImage?: string;
}

export interface DriverProfileResponseDTO {
  id: number;
  userId: number;
  userName: string;
  cpf: string;
  rg: string;
  cnhNumber: string;
  cnhCategory: string;
  cnhExpirationDate: string;
  cnhImage?: string;
  active: boolean;
}
