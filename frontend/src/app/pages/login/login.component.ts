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
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  email = '';
  password = '';
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.email, this.password).subscribe({
      next: (user) => {
        const routeMap: Record<string, string> = {
          ADMIN: '/home-admin',
          CLIENT: '/home-client',
          DRIVER: '/home-driver',
          OWNER: '/home-owner'
        };

        const redirect = routeMap[user?.role!];

        if (!redirect) {
          this.errorMessage = 'Role inválida!';
          return;
        }

        this.router.navigate([redirect]);
      },
      error: () => {
        this.errorMessage = 'Credenciais inválidas!';
      },
    });
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
}
