import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { JourneyService } from '../../../core/services/journey.service';
import {
  JourneyCreateDTO,
  JourneyOptionsDTO,
} from '../../../core/models/journey.model';

@Component({
  selector: 'app-new-journey',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './new-journey.component.html',
  styleUrls: ['./new-journey.component.scss'],
})
export class NewJourneyComponent implements OnInit {
  dto: JourneyCreateDTO = { name: '', description: '' };
  options: JourneyOptionsDTO | null = null;
  loading = false;

  constructor(private journeyService: JourneyService, public router: Router) {}

  ngOnInit(): void {
    this.journeyService
      .getOptions()
      .subscribe({ next: (o) => (this.options = o) });
  }

  submit() {
    this.loading = true;
    this.journeyService.create(this.dto).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/journeys']);
      },
      error: () => (this.loading = false),
    });
  }
}
