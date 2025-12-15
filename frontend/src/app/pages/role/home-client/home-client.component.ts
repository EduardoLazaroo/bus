import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserSelectCompanyComponent } from '../user-select-company/user-select-company.component';
import { Navbar } from '../../navbar/navbar.component';
import { FinalRegistrationClientComponent } from '../final-registration-client/final-registration-client.component';
import { CompanyLinkService } from '../../../core/services/companyLink.service';
export type ClientFlowStatus = 'NO_COMPANY' | 'PENDING' | 'APPROVED';

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
export class HomeClientComponent implements OnInit {
  status: ClientFlowStatus = 'NO_COMPANY';
  step = 0;

  constructor(private companyLinkService: CompanyLinkService) {}

  ngOnInit(): void {
    this.resolveClientStatus();
  }

  private resolveClientStatus(): void {
    this.companyLinkService.getMyLinks().subscribe({
      next: (links) => {
        if (links.some((l) => l.status === 'APPROVED')) {
          this.status = 'APPROVED';
          return;
        }

        if (links.some((l) => l.status === 'PENDING')) {
          this.status = 'PENDING';
          return;
        }

        this.status = 'NO_COMPANY';
      },
    });
  }

  onUserActivated(): void {
    this.step = 2;
  }
}
