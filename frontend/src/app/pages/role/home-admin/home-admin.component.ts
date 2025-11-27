import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { CompanyService } from '../../../core/services/company.service';
import { CompanyDTO } from '../../../core/models/company.model';

@Component({
  selector: 'app-home-admin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.scss']
})
export class HomeAdminComponent implements OnInit {

  pendingCompanies: CompanyDTO[] = [];

  constructor(
    private authService: AuthService,
    private companyService: CompanyService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadPendingCompanies();
  }

  // Buscar empresas pendentes
  loadPendingCompanies() {
    this.companyService.getPendingCompanies().subscribe({
      next: (data) => this.pendingCompanies = data,
      error: () => this.pendingCompanies = []
    });
  }

  // Aprovar ou rejeitar empresa
  approve(id: number, approved: boolean) {
    this.companyService.approveCompany(id, approved).subscribe({
      next: () => this.loadPendingCompanies()
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
