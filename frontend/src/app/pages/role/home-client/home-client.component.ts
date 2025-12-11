import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService,  } from '../../../core/services/auth.service';
import { UserSelectCompanyComponent } from '../user-select-company/user-select-company.component';
import { Navbar } from '../../navbar/navbar.component';
import { FinalRegistrationClientComponent } from '../final-registration-client/final-registration-client.component';
type ClientStatus = 'NO_COMPANY' | 'PENDING' | 'APPROVED' | 'REJECTED';

@Component({
  selector: 'app-home-client',
  standalone: true,
  imports: [
    CommonModule,
    UserSelectCompanyComponent,
    FinalRegistrationClientComponent,
    Navbar,
  ],
  templateUrl: './home-client.component.html',
  styleUrls: ['./home-client.component.scss'],
})
export class HomeClientComponent  {
  @Output() userActivated = new EventEmitter<void>();

  userRole: string | null = null;
  status: ClientStatus = 'NO_COMPANY';
  step: number = 0;

  constructor(private authService: AuthService, private router: Router) {
    this.userRole = this.authService.getUserRole();
  }

  onUserActivated() {
    this.step = 2;
  }
}
