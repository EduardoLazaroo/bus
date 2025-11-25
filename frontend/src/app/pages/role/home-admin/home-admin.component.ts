import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-home-admin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.scss']
})
export class HomeAdminComponent {
  userRole: string | null = null;

  constructor(private authService: AuthService, private router: Router) {
    this.userRole = this.authService.getUserRole();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
