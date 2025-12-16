export interface CompanyLinkResponseDTO {
  id: number | null;
  userId: number;
  userName: string;
  companyId: number;
  role: string;
  companyName: string;
  status: 'PENDING' | 'APPROVED' | 'REJECTED' | null;
}
