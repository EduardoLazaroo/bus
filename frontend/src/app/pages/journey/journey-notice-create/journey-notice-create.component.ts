import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { JourneyExtrasService } from '../../../core/services/journey-extras.service';

@Component({
  selector: 'app-journey-notice-create',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './journey-notice-create.component.html',
  styleUrls: ['./journey-notice-create.component.scss'],
})
export class JourneyNoticeCreateComponent {
  journeyId!: number;
  message = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private extras: JourneyExtrasService
  ) {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) this.journeyId = Number(id);
  }

  create() {
    if (!this.message || !this.message.trim()) return;
    this.extras.createNotice(this.journeyId, this.message).subscribe({
      next: () => this.router.navigate(['/journeys', this.journeyId]),
    });
  }

  cancel() {
    this.router.navigate(['/journeys', this.journeyId]);
  }
}
