import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CompanyDTO } from '../models/company.model';

@Injectable({ providedIn: 'root' })
export class CompanyService {
  private apiUrl = 'http://localhost:8080/api/companies';

  constructor(private http: HttpClient) {}

  // OWNER cria empresa → retorna CompanyResponseDTO
  createCompany(data: CompanyDTO): Observable<CompanyDTO> {
    return this.http.post<CompanyDTO>(`${this.apiUrl}`, data);
  }

  // OWNER ATUALIZA EMPRESA JÁ EXISTENTE
  updateCompany(id: number, data: CompanyDTO): Observable<CompanyDTO> {
    return this.http.put<CompanyDTO>(`${this.apiUrl}/${id}`, data);
  }

  // ADMIN busca pendentes
  getPendingCompanies(): Observable<CompanyDTO[]> {
    return this.http.get<CompanyDTO[]>(`${this.apiUrl}/pending`);
  }

  // ADMIN aprova/reprova
  approveCompany(companyId: number, approved: boolean): Observable<CompanyDTO> {
    return this.http.put<CompanyDTO>(
      `${this.apiUrl}/${companyId}/approve`,
      { approved } // CompanyApproveDTO
    );
  }

  // OWNER busca empresas dele
  getCompaniesByOwner(): Observable<CompanyDTO[]> {
    return this.http.get<CompanyDTO[]>(`${this.apiUrl}/mine`);
  }

  // qualquer um pega empresa por id
  getCompany(id: number): Observable<CompanyDTO> {
    return this.http.get<CompanyDTO>(`${this.apiUrl}/${id}`);
  }
}
