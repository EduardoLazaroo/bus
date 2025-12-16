import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { CompanyLinkService } from '../../../core/services/companyLink.service';
import { CompanyLinkResponseDTO } from '../../../core/models/company-link.model';
import { Navbar } from '../../navbar/navbar.component';

@Component({
  selector: 'app-customer',
  standalone: true,
  imports: [CommonModule, Navbar],
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss'],
})
export class CustomerComponent implements OnInit {
  customersPending: CompanyLinkResponseDTO[] = [];
  constructor(
    private companyLinkService: CompanyLinkService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.loadCustomersPending();
  }

  backPage() {
    // todo
    this.location.back();
  }

  loadCustomersPending(): void {
    this.companyLinkService.getPendingRequests().subscribe((res) => {
      this.customersPending = res;
    });
  }

  approve(linkId: number): void {
    this.companyLinkService.approveRequest(linkId).subscribe({
      next: () => {
        // remove da lista após aprovação
        this.customersPending = this.customersPending.filter(
          (c) => c.id !== linkId
        );
      },
    });
  }
}
