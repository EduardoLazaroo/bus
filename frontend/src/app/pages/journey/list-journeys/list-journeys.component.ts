import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { JourneyService } from '../../../core/services/journey.service';
import { JourneyResponseDTO } from '../../../core/models/journey.model';
import { JourneyExtrasService } from '../../../core/services/journey-extras.service';
import { JourneyNoticeDTO } from '../../../core/models/journey-notice.model';

@Component({
  selector: 'app-list-journeys',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './list-journeys.component.html',
  styleUrls: ['./list-journeys.component.scss'],
})
export class ListJourneysComponent implements OnInit {
  list: JourneyResponseDTO[] = [];
  noticesMap: Record<number, JourneyNoticeDTO[]> = {};
  showingNotices: Record<number, boolean> = {};
  newNoticeText: Record<number, string> = {};

  constructor(
    private journeyService: JourneyService,
    private extras: JourneyExtrasService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.journeyService.list().subscribe({ next: (l) => (this.list = l) });
  }

  goNew() {
    this.router.navigate(['/journeys/new']);
  }

  openDetail(j: JourneyResponseDTO) {
    this.router.navigate(['/journeys', j.id]);
  }
}
