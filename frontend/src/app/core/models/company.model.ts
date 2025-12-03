export interface CompanyDTO {
  id?: number;
  companyName: string;
  cnpj: string;
  country: string;
  state: string;
  city: string;
  district: string;
  street: string;
  phone: string;
  zipCode: string;
  number: string;
  complement?: string;
  email?: string;
  paymentType?: string;
  paymentInfo?: string;
  recipientName?: string;
  mobilePhone?: string;
  unitType?: string;

  status?: string;
  ownerId?: number;
  ownerName?: string;
  approved?: boolean;
}
