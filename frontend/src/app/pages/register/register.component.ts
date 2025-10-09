import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService, UserDTO } from '../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  user: UserDTO = { name: '', email: '', password: '', role: 'USER' };
  successMessage = '';
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    this.authService.register(this.user).subscribe({
      next: (user) => {
        this.successMessage = `Usuário ${user.name} criado com sucesso!`;
        this.errorMessage = '';
        // redireciona conforme role
        if (user.role === 'ADMIN') {
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/user']);
        }
      },
      error: (err) => {
        this.errorMessage = 'Erro ao criar usuário.';
        this.successMessage = '';
        console.error(err);
      }
    });
  }
}
