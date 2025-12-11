import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { UserSelectCompanyComponent } from '../user-select-company/user-select-company.component';

@Component({
  selector: 'app-home-client',
  standalone: true,
  imports: [CommonModule, UserSelectCompanyComponent],
  templateUrl: './home-client.component.html',
  styleUrls: ['./home-client.component.scss']
})
export class HomeClientComponent {
  userRole: string | null = null;

  constructor(private authService: AuthService, private router: Router) {
    this.userRole = this.authService.getUserRole();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
