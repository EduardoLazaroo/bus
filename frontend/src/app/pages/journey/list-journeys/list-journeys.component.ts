import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { JourneyService } from '../../../core/services/journey.service';
import { AuthService } from '../../../core/services/auth.service';
import { JourneyResponseDTO } from '../../../core/models/journey.model';
import { NavbarComponent } from '../../navbar/navbar.component';

@Component({
  selector: 'app-list-journeys',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './list-journeys.component.html',
  styleUrls: ['./list-journeys.component.scss'],
})
export class ListJourneysComponent implements OnInit {
  journeyResponseDTO: JourneyResponseDTO[] = [];
  role: string | null = null;

  constructor(
    private journeyService: JourneyService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.role = this.authService.getUserRole();

    if (this.role === 'CLIENT' || this.role === 'DRIVER') {
      this.journeyService.listMy().subscribe({
        next: (res) => (this.journeyResponseDTO = res),
      });
    } else {
      this.journeyService.list().subscribe({
        next: (res) => (this.journeyResponseDTO = res),
      });
    }
  }

  goToCustomer(): void {
    this.router.navigate(['/customer']);
  }

  goToVehicle(): void {
    this.router.navigate(['/vehicle']);
  }

  goToCreateJourney(): void {
    this.router.navigate(['/journeys/new']);
  }

  openJourneyDetails(journey: JourneyResponseDTO): void {
    this.router.navigate(['/journeys', journey.id]);
  }
}
