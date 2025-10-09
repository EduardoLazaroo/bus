import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';

export interface UserDTO {
  id?: number;
  name: string;
  email: string;
  password?: string;
  role?: 'USER' | 'ADMIN';
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/users';
  private currentUser: UserDTO | null = null;

  constructor(private http: HttpClient) {}

  register(user: UserDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>(this.apiUrl, user).pipe(
      tap(user => this.currentUser = user)
    );
  }

  login(email: string, password: string): Observable<UserDTO> {
    // Para teste, supondo que você tenha endpoint POST /api/login
    return this.http.post<UserDTO>(`${this.apiUrl}/login`, { email, password }).pipe(
      tap(user => this.currentUser = user)
    );
  }

  logout() {
    this.currentUser = null;
  }

  getUserRole(): 'USER' | 'ADMIN' | null {
    return this.currentUser?.role ?? null;
  }

  isLoggedIn(): boolean {
    return !!this.currentUser;
  }
}
