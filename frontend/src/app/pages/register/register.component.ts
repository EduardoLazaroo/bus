import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  name = '';
  email = '';
  password = '';
  role = 'CLIENT'; // valor padrão

  roles = ['CLIENT', 'ADMIN', 'USER']; // opções que aparecem no select

  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient, private router: Router) {}

  // onRegister() {
  //   const userData = {
  //     name: this.name,
  //     email: this.email,
  //     password: this.password,
  //     role: this.role
  //   };

  //   this.http.post(this.apiUrl, userData).subscribe({
  //     next: () => {
  //       alert('Usuário cadastrado com sucesso!');
  //       this.router.navigate(['/login']);
  //     },
  //     error: (err) => {
  //       console.error(err);
  //       alert('Erro ao cadastrar usuário');
  //     }
  //   });
  // }
}
