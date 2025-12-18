import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CompanyLinkResponseDTO } from '../models/company-link.model';
import { CompanyLinkRequirementsDTO } from '../models/company-link-requirements.model';
export type CompanyLinkRole = 'CLIENT' | 'DRIVER';

@Injectable({ providedIn: 'root' })
export class CompanyLinkService {
  private apiUrl = 'http://localhost:8080/api/company-links';

  constructor(private http: HttpClient) {}

  // CLIENT lista empresas aprovadas disponíveis para solicitar vínculo
  getAvailableCompanies(): Observable<CompanyLinkResponseDTO[]> {
    return this.http.get<CompanyLinkResponseDTO[]>(`${this.apiUrl}/available`);
  }

  // USER solicita vínculo com role (CLIENT ou DRIVER)
  requestAccess(
    companyId: number,
    requestedRole: CompanyLinkRole
  ): Observable<CompanyLinkResponseDTO> {
    return this.http.post<CompanyLinkResponseDTO>(
      `${this.apiUrl}/request`,
      {
        companyId,
        requestedRole,
      }
    );
  }

  // CLIENT vê seus vínculos
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

  // OWNER vê usuários vinculados
  getUsersLinkedToCompany(
    companyId: number
  ): Observable<CompanyLinkResponseDTO[]> {
    return this.http.get<CompanyLinkResponseDTO[]>(
      `${this.apiUrl}/company/${companyId}/users`
    );
  }

  // Requisitos para criação de jornada
  getRequirements(): Observable<CompanyLinkRequirementsDTO> {
    return this.http.get<CompanyLinkRequirementsDTO>(`${this.apiUrl}/requirements`);
  }
}
