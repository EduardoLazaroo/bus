import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  CompanyLinkService,
  CompanyLinkRole,
} from '../../../core/services/companyLink.service';
import { CompanyLinkResponseDTO } from '../../../core/models/company-link.model';
import { AuthService } from '../../../core/services/auth.service';
import { DriverProfileService } from '../../../core/services/driverProfile.service';
import { DriverProfileCreateDTO } from '../../../core/models/driver-profile.model';

@Component({
  selector: 'app-user-select-company',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './user-select-company.component.html',
  styleUrls: ['./user-select-company.component.scss'],
})
export class UserSelectCompanyComponent implements OnInit {
  @Output() requestSent = new EventEmitter<void>();

  userRole: CompanyLinkRole = 'CLIENT';
  availableCompanies: CompanyLinkResponseDTO[] = [];
  selectedCompanyId: number | null = null;

  step: 'SELECT' | 'DRIVER_PROFILE' | 'CONFIRM' = 'SELECT';

  // DRIVER PROFILE FORM
  driverProfile: DriverProfileCreateDTO = {
    cpf: '',
    rg: '',
    cnhNumber: '',
    cnhCategory: '',
    cnhExpirationDate: '',
    cnhImage: ''
  };

  constructor(
    private companyLinkService: CompanyLinkService,
    private driverProfileService: DriverProfileService,
    private authService: AuthService
  ) {
    const role = this.authService.getUserRole();

    if (role === 'DRIVER' || role === 'CLIENT') {
      this.userRole = role;
    } else {
      this.userRole = 'CLIENT';
    }
  }

  ngOnInit(): void {
    this.loadAvailableCompanies();
  }

  loadAvailableCompanies(): void {
    this.companyLinkService.getAvailableCompanies().subscribe((res) => {
      this.availableCompanies = res;
    });
  }

  onCompanyChange(event: Event): void {
    const value = (event.target as HTMLSelectElement).value;
    this.selectedCompanyId = value ? Number(value) : null;
  }

  goNext(): void {
    if (!this.selectedCompanyId) return;

    if (this.userRole === 'DRIVER') {
      this.step = 'DRIVER_PROFILE';
    } else {
      this.step = 'CONFIRM';
    }
  }

  back(): void {
    if (this.step === 'DRIVER_PROFILE') {
      this.step = 'SELECT';
    } else {
      this.step = 'SELECT';
    }
  }

  saveDriverProfileAndContinue(): void {
    this.driverProfileService
      .createOrUpdate(this.driverProfile)
      .subscribe(() => {
        this.step = 'CONFIRM';
      });
  }

  requestAccessToOwner(): void {
    if (!this.selectedCompanyId || !this.userRole) return;

    this.companyLinkService
      .requestAccess(this.selectedCompanyId, this.userRole)
      .subscribe(() => {
        this.requestSent.emit();
      });
  }
}
