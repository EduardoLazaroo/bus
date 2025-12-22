export interface JourneyCreateDTO {
  name: string;
  description?: string;
  driverCompanyLinkIds?: number[];
  clientCompanyLinkIds?: number[];
  vehicleIds?: number[];
  // optional lightweight stops as JSON string or array handled in frontend
  stops?: Array<{
    seqOrder?: number;
    name?: string;
    address?: string;
    time?: string;
  }>;
}

export interface JourneyResponseDTO {
  id: number;
  name: string;
  description?: string;
  status: string;
  companyLinkId?: number;
  driverCompanyLinkIds?: number[];
  clientCompanyLinkIds?: number[];
  vehicleIds?: number[];
}

export interface JourneyOptionsDTO {
  drivers: import('./company-link.model').CompanyLinkResponseDTO[];
  clients: import('./company-link.model').CompanyLinkResponseDTO[];
  vehicles: import('./vehicle.model').VehicleResponseDTO[];
}
