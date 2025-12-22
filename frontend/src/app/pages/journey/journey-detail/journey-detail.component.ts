import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { JourneyService } from '../../../core/services/journey.service';
import { JourneyExtrasService } from '../../../core/services/journey-extras.service';
import { JourneyResponseDTO } from '../../../core/models/journey.model';
import { JourneyNoticeDTO } from '../../../core/models/journey-notice.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-journey-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './journey-detail.component.html',
  styleUrls: ['./journey-detail.component.scss'],
})
export class JourneyDetailComponent implements OnInit {
  journeyId!: number;
  journey: JourneyResponseDTO | null = null;
  notices: JourneyNoticeDTO[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private journeyService: JourneyService,
    private extras: JourneyExtrasService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      if (id) {
        this.journeyId = Number(id);
        this.load();
      }
    });
  }

  load() {
    this.journeyService
      .get(this.journeyId)
      .subscribe({ next: (j) => (this.journey = j) });
    this.extras
      .getNotices(this.journeyId)
      .subscribe({ next: (n) => (this.notices = n) });
  }

  addNotice() {
    // criação movida para tela dedicada
    return;
  }

  back() {
    this.location.back();
  }
}
