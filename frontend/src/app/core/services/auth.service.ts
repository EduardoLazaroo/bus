import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  // 🔥 Método simples pra testar o CORS
  testCors() {
    const body = {
      email: 'eduardo@emailll.com',
      password: '123456'
    };

    this.http.post(`${this.apiUrl}/login`, body)
      .subscribe({
        next: (res) => console.log('✅ Requisição deu certo:', res),
        error: (err) => console.error('❌ Erro na requisição:', err)
      });
  }
}
