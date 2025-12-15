export interface CompanyLinkResponseDTO {
  id: number | null;
  userId: number;
  userName: string;
  companyId: number;
  companyName: string;
  status: 'PENDING' | 'APPROVED' | 'REJECTED' | null;
}
