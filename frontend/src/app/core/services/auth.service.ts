import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { UserDTO, UserRole } from '../models/auth.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/users';
  private currentUser: UserDTO | null = null;

  constructor(private http: HttpClient) {
    const savedUser = localStorage.getItem('user');
    if (savedUser) this.currentUser = JSON.parse(savedUser);
  }

  register(user: UserDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>(`${this.apiUrl}/register`, user).pipe(
      tap((user) => {
        this.currentUser = user;
        localStorage.setItem('user', JSON.stringify(user));
        if (user.token) localStorage.setItem('token', user.token);
      })
    );
  }

  updateUser(data: Partial<UserDTO>): Observable<UserDTO> {
  return this.http.put<UserDTO>(`${this.apiUrl}/update`, data).pipe(
    tap((updatedUser) => {
      this.currentUser = {
        ...this.currentUser,
        ...updatedUser
      };

      localStorage.setItem('user', JSON.stringify(this.currentUser));
    })
  );
}

  login(email: string, password: string): Observable<UserDTO> {
    return this.http
      .post<UserDTO>(`${this.apiUrl}/login`, { email, password })
      .pipe(
        tap((user) => {
          this.currentUser = user;
          localStorage.setItem('user', JSON.stringify(user));
          if (user.token) localStorage.setItem('token', user.token);
        })
      );
  }

  getCurrentUser(): Observable<UserDTO> {
    return this.http.get<UserDTO>(`${this.apiUrl}/mine`);
  }

  getUserRole(): UserRole | null {
    return this.currentUser?.role ?? null;
  }

  getUserName(): string | null {
    return this.currentUser?.name ?? null;
  }

  logout() {
    this.currentUser = null;
    localStorage.removeItem('user');
    localStorage.removeItem('token');
  }
}
