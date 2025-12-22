export interface VehicleDTO {
  id?: number; // opcional para create
  model: string;
  licensePlate: string;
  capacity: number;
  type: string;
  year: number;
  color: string;
  active?: boolean;
  companyLinkId?: number;
}
export interface VehicleResponseDTO {
  id: number;
  model: string;
  licensePlate: string;
  capacity: number;
  type: string;
  year: number;
  color: string;
  active: boolean;
  companyLinkId?: number;
}
