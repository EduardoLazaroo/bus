import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService, UserDTO } from '../../../core/services/auth.service';
import { CompanyDTO } from '../../../core/models/company.model';
import { CompanyService } from '../../../core/services/company.service';

@Component({
  selector: 'app-final-registration-owner',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './final-registration-owner.component.html',
  styleUrls: ['./final-registration-owner.component.scss'],
})
export class FinalRegistrationOwnerComponent implements OnInit {
  userRole: string | null = null;
  step: number = 1;

  user: UserDTO = {
    name: '',
    email: '',
    password: '',
    role: 'CLIENT',
  };

  company: CompanyDTO = {
    id: 0,
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
    private router: Router,
    private companyService: CompanyService
  ) {
    this.userRole = this.authService.getUserRole();
  }

  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe({
      next: (user) => {
        this.user = user;
      },
      error: (err) => console.error('Erro ao carregar usuário', err),
    });

    this.companyService.getCompaniesByOwner().subscribe({
      next: (companies) => {
        if (companies.length > 0) {
          this.company = companies[0]; // automaticamente preenche os inputs
        } else {
          console.error('Nenhuma empresa encontrada para este usuário.');
        }
      },
      error: (err) => console.error('Erro ao buscar empresa', err),
    });
  }

  updateUser() {
    this.authService.updateUser(this.user).subscribe({
      next: (res) => {
        console.log('Usuário atualizado com sucesso', res);
        this.step = 2;
      },
      error: (err) => {
        console.error('Erro ao atualizar usuário', err);
        alert('Erro ao atualizar usuário');
      },
    });
  }

  updateCompany() {
    if (!this.company.id) {
      console.error('ID da empresa não encontrado.');
      return;
    }

    this.companyService.updateCompany(this.company.id, this.company).subscribe({
      next: (res) => {
        console.log('Empresa atualizada com sucesso', res);
        this.router.navigate(['/home-owner']);
      },
      error: (err) => {
        console.error('Erro ao atualizar empresa', err);
      },
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
