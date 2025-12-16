import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CompanyLinkResponseDTO } from '../models/company-link.model';

@Injectable({ providedIn: 'root' })
export class CompanyLinkService {
  private apiUrl = 'http://localhost:8080/api/company-links';

  constructor(private http: HttpClient) {}

  // CLIENT lista empresas aprovadas disponíveis para solicitar vínculo
  getAvailableCompanies(): Observable<CompanyLinkResponseDTO[]> {
    return this.http.get<CompanyLinkResponseDTO[]>(`${this.apiUrl}/available`);
  }

  // CLIENT solicita vínculo com uma empresa
  requestAccess(companyId: number): Observable<CompanyLinkResponseDTO> {
    return this.http.post<CompanyLinkResponseDTO>(
      `${this.apiUrl}/request/${companyId}`,
      null
    );
  }

  // CLIENT vê seus vínculos (PENDING / APPROVED)
  getMyLinks(): Observable<CompanyLinkResponseDTO[]> {
    return this.http.get<CompanyLinkResponseDTO[]>(`${this.apiUrl}/mine`);
  }

  // OWNER vê solicitações pendentes
  getPendingRequests(): Observable<CompanyLinkResponseDTO[]> {
    return this.http.get<CompanyLinkResponseDTO[]>(`${this.apiUrl}/pending`);
  }

  // OWNER aprova solicitação
  approveRequest(linkId: number): Observable<CompanyLinkResponseDTO> {
    return this.http.put<CompanyLinkResponseDTO>(
      `${this.apiUrl}/${linkId}/approve`,
      null
    );
  }

  getUsersLinkedToCompany(
    companyId: number
  ): Observable<CompanyLinkResponseDTO[]> {
    return this.http.get<CompanyLinkResponseDTO[]>(
      `${this.apiUrl}/company/${companyId}/users`
    );
  }
}
