export interface CompanyDTO {
  id?: number;
  name: string;
  cnpj: string;
  address: string;
  active?: boolean;
  status?: string;
  ownerId?: number;
  ownerName?: string;
  approved?: boolean;
}
