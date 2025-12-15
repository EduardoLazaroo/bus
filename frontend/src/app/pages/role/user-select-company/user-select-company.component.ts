import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompanyLinkService } from '../../../core/services/companyLink.service';
import { CompanyLinkResponseDTO } from '../../../core/models/company-link.model';


@Component({
  selector: 'app-user-select-company',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-select-company.component.html',
  styleUrls: ['./user-select-company.component.scss'],
})
export class UserSelectCompanyComponent implements OnInit {
  @Output() requestSent = new EventEmitter<void>();

  availableCompanies: CompanyLinkResponseDTO[] = [];
  selectedCompanyId: number | null = null;
  step = 'SELECT';

  constructor(private companyLinkService: CompanyLinkService) {}

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

  goToConfirm(): void {
    if (!this.selectedCompanyId) return;
    this.step = 'CONFIRM';
  }

  backToSelect(): void {
    this.step = 'SELECT';
  }

  requestAccess(): void {
    if (!this.selectedCompanyId) return;

    this.companyLinkService
      .requestAccess(this.selectedCompanyId)
      .subscribe(() => {
        this.requestSent.emit();
      });
  }
}
