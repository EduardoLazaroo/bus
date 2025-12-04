import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { CompanyService } from '../../../core/services/company.service';
import { CompanyActivationComponent } from '../company-activation/company-activation.component';
import { CompanyDTO } from '../../../core/models/company.model';
import { Observable } from 'rxjs';

type CompanyStatus = 'NO_COMPANY' | 'PENDING' | 'APPROVED' | 'REJECTED';

@Component({
  selector: 'app-home-owner',
  standalone: true,
  imports: [CommonModule, CompanyActivationComponent],
  templateUrl: './home-owner.component.html',
  styleUrls: ['./home-owner.component.scss'],
})
export class HomeOwnerComponent implements OnInit {
  userRole = this.authService.getUserRole();
  company: CompanyDTO | null = null;
  status: CompanyStatus = 'NO_COMPANY'; // estado inicial padrão
  userName: string | null = null;
  isAdvancedConfig: boolean = false;

  constructor(
    private authService: AuthService,
    private companyService: CompanyService,
    private router: Router
  ) {
    this.userRole = this.authService.getUserRole();
    this.userName = this.authService.getUserName();
  }

  ngOnInit(): void {
    this.loadCompanyStatus();
  }

  goToAdvancedConf(){
    this.router.navigate(['/final-registration-owner']);
  }

  loadCompanyStatus() {
    this.companyService.getCompaniesByOwner().subscribe({
      next: (companies) => {
        if (companies.length === 0) {
          this.status = 'NO_COMPANY';
          console.log('No companies found for owner.');
          return;
        }

        this.company = companies[0];
        console.log('Company status:', this.company.status);

        if (this.company.status === 'APPROVED') {
          this.status = 'APPROVED';
        } else if (this.company.status === 'PENDING') {
          this.status = 'PENDING';
        } else if (this.company.status === 'REJECTED') {
          this.status = 'REJECTED';
        } else {
          this.status = 'NO_COMPANY';
        }
      },
      error: () => (this.status = 'NO_COMPANY'),
    });
  }

  companyCreated() {
    this.status = 'PENDING';
    this.loadCompanyStatus();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
