import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { JourneyService } from '../../../core/services/journey.service';
import { JourneyExtrasService } from '../../../core/services/journey-extras.service';
import { JourneyResponseDTO } from '../../../core/models/journey.model';
import { JourneyNoticeDTO } from '../../../core/models/journey-notice.model';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../../navbar/navbar.component';
import { JourneyPollDTO } from '../../../core/models/journey-poll.model';

@Component({
  selector: 'app-journey-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, NavbarComponent],
  templateUrl: './journey-detail.component.html',
  styleUrls: ['./journey-detail.component.scss'],
})
export class JourneyDetailComponent implements OnInit {
  journeyId!: number;
  journey: JourneyResponseDTO | null = null;
  notices: JourneyNoticeDTO[] = [];
  polls: JourneyPollDTO[] = [];

  constructor(
    private route: ActivatedRoute,
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

  backPage() {
    // todo
    this.location.back();
  }

  load() {
    this.journeyService
      .get(this.journeyId)
      .subscribe({ next: (j) => (this.journey = j) });
    this.extras
      .getNotices(this.journeyId)
      .subscribe({ next: (n) => (this.notices = n) });
    this.extras
      .getPolls(this.journeyId)
      .subscribe({ next: (p) => (this.polls = p) });
  }

  back() {
    this.location.back();
  }
}
