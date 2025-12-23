import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { UserSelectCompanyComponent } from '../user-select-company/user-select-company.component';
import { FinalRegistrationClientComponent } from '../final-registration-client/final-registration-client.component';
import { CompanyLinkService } from '../../../core/services/companyLink.service';
import { NavbarComponent } from '../../navbar/navbar.component';
import { ListJourneysComponent } from '../../journey/list-journeys/list-journeys.component';
export type ClientFlowStatus = 'NO_COMPANY' | 'PENDING' | 'APPROVED';

@Component({
  selector: 'app-home-driver',
  standalone: true,
  imports: [
    CommonModule,
    UserSelectCompanyComponent,
    FinalRegistrationClientComponent,
    NavbarComponent,
    ListJourneysComponent
  ],
  templateUrl: './home-driver.component.html',
  styleUrls: ['./home-driver.component.scss'],
})
export class HomeDriverComponent implements OnInit{
  status: ClientFlowStatus = 'NO_COMPANY';
  step = 0;
  userName: string | null = null;
  finallyDriverComplete = false

  constructor(
    private authService: AuthService,
    private companyLinkService: CompanyLinkService
  ) {
    this.userName = this.authService.getUserName();
  }

  ngOnInit(): void {
    this.resolveClientStatus();
  }

  resolveClientStatus(): void {
    this.companyLinkService.getMyLinks().subscribe({
      next: (links) => {
        console.warn(links);
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
