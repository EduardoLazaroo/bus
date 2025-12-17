import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { VehicleDTO } from '../models/vehicle.model';

@Injectable({ providedIn: 'root' })
export class VehicleService {
  private apiUrl = 'http://localhost:8080/api/vehicles';

  constructor(private http: HttpClient) {}

  // OWNER cria veículo
  createVehicle(data: VehicleDTO): Observable<VehicleDTO> {
    return this.http.post<VehicleDTO>(`${this.apiUrl}`, data);
  }

  // OWNER busca os veículos dele (ativos e desativados)
  getMyVehicles(): Observable<VehicleDTO[]> {
    return this.http.get<VehicleDTO[]>(`${this.apiUrl}/mine`);
  }

  // OWNER atualiza veículo existente
  updateVehicle(id: number, data: VehicleDTO): Observable<VehicleDTO> {
    return this.http.put<VehicleDTO>(`${this.apiUrl}/${id}`, data);
  }

  // OWNER desativa veículo (soft delete)
  deactivateVehicle(vehicleId: number): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${vehicleId}/deactivate`, {});
  }
}
