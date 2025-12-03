import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { CompanyService } from '../../../core/services/company.service';
import { CompanyDTO } from '../../../core/models/company.model';

@Component({
  selector: 'app-company-activation',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './company-activation.component.html',
  styleUrls: ['./company-activation.component.scss'],
})
export class CompanyActivationComponent {
  @Output() companyCreated = new EventEmitter<void>();
  step: number = 1;

  userRole: string | null = null;
  pendingCompanies: CompanyDTO[] = [];

  company: CompanyDTO = {
    companyName: '',
    cnpj: '',
    city: '',
    state: '',
    country: '',
    district: '',
    street: '',
    phone: '',
    zipCode: '',
    number: '',
    complement: '',
    email: '',
    paymentType: '',
    paymentInfo: '',
    recipientName: '',
    mobilePhone: '',
    unitType: '',
  };

  constructor(
    private authService: AuthService,
    private companyService: CompanyService
  ) {
    this.userRole = this.authService.getUserRole();
  }

  ngOnInit() {
    if (this.userRole === 'ADMIN') {
      this.loadPendingCompanies();
    }
  }

  /** OWNER cria empresa */
  registerCompany() {
    this.companyService.createCompany(this.company).subscribe({
      next: () => {
        this.company = {
          companyName: '',
          cnpj: '',
          city: '',
          state: '',
          country: '',
          district: '',
          street: '',
          phone: '',
          zipCode: '',
          number: '',
          complement: '',
          email: '',
          paymentType: '',
          paymentInfo: '',
          recipientName: '',
          mobilePhone: '',
          unitType: '',
        };
        this.companyCreated.emit();
      },
    });
  }

  /** ADMIN carrega pendentes */
  loadPendingCompanies() {
    this.companyService.getPendingCompanies().subscribe({
      next: (data) => (this.pendingCompanies = data),
      error: () => (this.pendingCompanies = []),
    });
  }

  /** ADMIN aprova ou reprova */
  approve(companyId: number, approved: boolean) {
    this.companyService.approveCompany(companyId, approved).subscribe({
      next: () => this.loadPendingCompanies(), // atualiza lista
    });
  }
}
