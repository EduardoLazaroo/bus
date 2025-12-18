import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  DriverProfileCreateDTO,
  DriverProfileResponseDTO,
} from '../models/driver-profile.model';

@Injectable({
  providedIn: 'root',
})
export class DriverProfileService {
  private readonly apiUrl = 'http://localhost:8080/api/driver-profile';

  constructor(private http: HttpClient) {}

  createOrUpdate(
    payload: DriverProfileCreateDTO
  ): Observable<DriverProfileResponseDTO> {
    return this.http.post<DriverProfileResponseDTO>(this.apiUrl, payload);
  }

  getMyProfile(): Observable<DriverProfileResponseDTO> {
    return this.http.get<DriverProfileResponseDTO>(`${this.apiUrl}/mine`);
  }
}
