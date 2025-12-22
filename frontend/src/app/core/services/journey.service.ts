import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  JourneyCreateDTO,
  JourneyOptionsDTO,
  JourneyResponseDTO,
} from '../models/journey.model';

@Injectable({ providedIn: 'root' })
export class JourneyService {
  private apiUrl = 'http://localhost:8080/api/journeys';

  constructor(private http: HttpClient) {}

  // Busca opções necessárias para criação de viagens
  getOptions(): Observable<JourneyOptionsDTO> {
    return this.http.get<JourneyOptionsDTO>(`${this.apiUrl}/options`);
  }

  // Cria uma nova viagem
  create(dto: JourneyCreateDTO): Observable<JourneyResponseDTO> {
    return this.http.post<JourneyResponseDTO>(`${this.apiUrl}`, dto);
  }

  // Lista todas as viagens
  list(): Observable<JourneyResponseDTO[]> {
    return this.http.get<JourneyResponseDTO[]>(`${this.apiUrl}`);
  }

  // Busca uma viagem pelo id
  get(id: number) {
    return this.http.get<JourneyResponseDTO>(`${this.apiUrl}/${id}`);
  }

  // Atualiza os dados de uma viagem
  update(id: number, dto: JourneyCreateDTO) {
    return this.http.put<JourneyResponseDTO>(`${this.apiUrl}/${id}`, dto);
  }

  // Desativa uma viagem
  deactivate(id: number) {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
