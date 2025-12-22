import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { CompanyLinkService } from '../../../core/services/companyLink.service';
import { CompanyLinkResponseDTO } from '../../../core/models/company-link.model';
import { NavbarComponent } from '../../navbar/navbar.component';

@Component({
  selector: 'app-customer',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss'],
})
export class CustomerComponent implements OnInit {
  customersPending: CompanyLinkResponseDTO[] = [];
  customersLinked: CompanyLinkResponseDTO[] = [];
  companyId!: number;

  constructor(
    private companyLinkService: CompanyLinkService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.initData();
  }

  backPage(): void {
    this.location.back();
  }

  private initData(): void {
    this.companyLinkService.getPendingRequests().subscribe({
      next: (pending) => {
        this.customersPending = pending;

        if (pending.length > 0) {
          this.companyId = pending[0].companyId;
          this.loadUsersLinked();
        } else {
          this.loadLinkedFromApproved();
        }
      },
    });
  }

  private loadLinkedFromApproved(): void {
    this.companyLinkService.getMyLinks().subscribe({
      next: (links) => {
        if (links.length > 0) {
          this.companyId = links[0].companyId;
          this.loadUsersLinked();
        }
      },
    });
  }

  loadUsersLinked(): void {
    if (!this.companyId) return;

    this.companyLinkService.getUsersLinkedToCompany(this.companyId).subscribe({
      next: (res) => {
        this.customersLinked = res;
      },
    });
  }

  approve(linkId: number): void {
    this.companyLinkService.approveRequest(linkId).subscribe({
      next: () => {
        this.customersPending = this.customersPending.filter(
          (c) => c.id !== linkId
        );
        this.loadUsersLinked();
      },
    });
  }
}
