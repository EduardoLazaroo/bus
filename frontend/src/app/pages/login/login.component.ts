import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  email = '';
  password = '';
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.email, this.password).subscribe({
      next: (user) => {
        if (user.role === 'ADMIN' || user.role === 'USER' || user.role === 'DRIVER') {
          this.router.navigate(['/home']);
        } else {
          this.errorMessage = 'Role inválida!';
        }
      },
      error: () => {
        this.errorMessage = 'Credenciais inválidas!';
      }
    });
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
}
