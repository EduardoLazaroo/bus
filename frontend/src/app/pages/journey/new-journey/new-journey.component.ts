import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { JourneyService } from '../../../core/services/journey.service';
import {
  JourneyCreateDTO,
  JourneyOptionsDTO,
} from '../../../core/models/journey.model';
import { NavbarComponent } from '../../navbar/navbar.component';

@Component({
  selector: 'app-new-journey',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './new-journey.component.html',
  styleUrls: ['./new-journey.component.scss'],
})
export class NewJourneyComponent implements OnInit {
  dto: JourneyCreateDTO = { name: '', description: '' };
  options: JourneyOptionsDTO | null = null;
  loading = false;
  currentStep = 1; // 1 = dados gerais, 2 = passageiros

  constructor(private journeyService: JourneyService, public router: Router) {}

  ngOnInit(): void {
    this.journeyService
      .getOptions()
      .subscribe({ next: (o) => (this.options = o) });
    if (!this.dto.stops) {
      this.dto.stops = [];
    }
  }

  submit() {
    this.loading = true;
    this.journeyService.create(this.dto).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/home-owner']);
      },
      error: () => (this.loading = false),
    });
  }

  next() {
    if (this.currentStep === 1 && !this.dto.name) return;
    this.currentStep = Math.min(2, this.currentStep + 1);
  }

  prev() {
    this.currentStep = Math.max(1, this.currentStep - 1);
  }

  addStop() {
    if (!this.dto.stops) this.dto.stops = [];
    const nextOrder = this.dto.stops.length + 1;
    this.dto.stops.push({
      seqOrder: nextOrder,
      name: '',
      address: '',
      time: '',
    });
  }

  removeStop(i: number) {
    if (!this.dto.stops) return;
    this.dto.stops.splice(i, 1);
    // atualizar seqOrder
    this.dto.stops.forEach((s, idx) => (s.seqOrder = idx + 1));
  }
}
