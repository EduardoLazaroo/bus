import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { JourneyExtrasService } from '../../../core/services/journey-extras.service';
import { NavbarComponent } from '../../navbar/navbar.component';

@Component({
  selector: 'app-journey-poll-create',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './journey-poll-create.component.html',
  styleUrls: ['./journey-poll-create.component.scss'],
})
export class JourneyPollCreateComponent {
  journeyId!: number;
  question = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private extras: JourneyExtrasService
  ) {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) this.journeyId = Number(id);
  }

  create() {
    if (!this.question || !this.question.trim()) return;
    this.extras.createPoll(this.journeyId, this.question).subscribe({
      next: () => this.router.navigate(['/journeys', this.journeyId]),
    });
  }

  cancel() {
    this.router.navigate(['/journeys', this.journeyId]);
  }
}
