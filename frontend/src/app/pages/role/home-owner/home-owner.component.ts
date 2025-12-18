import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { CompanyService } from '../../../core/services/company.service';
import { CompanyLinkService } from '../../../core/services/companyLink.service';
import { CompanyLinkRequirementsDTO } from '../../../core/models/company-link-requirements.model';
import { CompanyActivationComponent } from '../company-activation/company-activation.component';
import { CompanyDTO } from '../../../core/models/company.model';
import { Navbar } from '../../navbar/navbar.component';

type CompanyStatus = 'NO_COMPANY' | 'PENDING' | 'APPROVED' | 'REJECTED';

@Component({
  selector: 'app-home-owner',
  standalone: true,
  imports: [CommonModule, CompanyActivationComponent, Navbar],
  templateUrl: './home-owner.component.html',
  styleUrls: ['./home-owner.component.scss'],
})
export class HomeOwnerComponent implements OnInit {
  userRole = this.authService.getUserRole();
  company: CompanyDTO | null = null;
  status: CompanyStatus = 'NO_COMPANY'; // estado inicial padrão
  userName: string | null = null;
  isAdvancedConfig: boolean = false;
  hasClient: boolean = false;
  hasDriver: boolean = false;
  hasVehicle: boolean = false;
  canCreateJourney: boolean = false;

  constructor(
    private authService: AuthService,
    private companyService: CompanyService,
    private companyLinkService: CompanyLinkService,
    private router: Router
  ) {
    this.userRole = this.authService.getUserRole();
    this.userName = this.authService.getUserName();
  }

  ngOnInit(): void {
    this.loadCompanyStatus();
    this.loadRequirements();
  }

  goToAdvancedConf() {
    this.router.navigate(['/final-registration-owner']);
  }

  goToCustomer() {
    this.router.navigate(['/customer']);
  }

  goToVehicle() {
    this.router.navigate(['/vehicle']);
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

  loadRequirements() {
    this.companyLinkService.getRequirements().subscribe({
      next: (res: CompanyLinkRequirementsDTO) => {
        this.hasClient = res.hasClient;
        this.hasDriver = res.hasDriver;
        this.hasVehicle = res.hasVehicle;
        this.canCreateJourney = res.canCreateJourney;
      },
      error: () => {
        this.hasClient = false;
        this.hasDriver = false;
        this.hasVehicle = false;
        this.canCreateJourney = false;
      },
    });
  }

  companyCreated() {
    this.status = 'PENDING';
    this.loadCompanyStatus();
  }
}
