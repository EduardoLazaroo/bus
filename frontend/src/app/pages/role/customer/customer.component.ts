import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompanyLinkService } from '../../../core/services/companyLink.service';
import { CompanyLinkResponseDTO } from '../../../core/models/company-link.model';

@Component({
  selector: 'app-customer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss'],
})
export class CustomerComponent implements OnInit {
  customersPending: CompanyLinkResponseDTO[] = [];
  constructor(private companyLinkService: CompanyLinkService) {}

  ngOnInit(): void {
    this.loadCustomersPending();
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
