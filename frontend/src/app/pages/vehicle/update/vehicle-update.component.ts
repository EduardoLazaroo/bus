import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { Navbar } from '../../navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { VehicleDTO } from '../../../core/models/vehicle.model';
import { VehicleService } from '../../../core/services/vehicle.services';

@Component({
  selector: 'app-vehicle-update',
  standalone: true,
  imports: [CommonModule, Navbar, FormsModule],
  templateUrl: './vehicle-update.component.html',
  styleUrls: ['./vehicle-update.component.scss'],
})
export class VehicleUpdateComponent {
  vehicle: VehicleDTO = {
    model: '',
    licensePlate: '',
    capacity: 0,
    type: '',
    year: new Date().getFullYear(),
    color: '',
  };

  constructor(
    private vehicleService: VehicleService,
    private location: Location,
    private router: Router
  ) {}

  backPage(): void {
    this.location.back();
  }

  createVehicle(): void {
    this.vehicleService.createVehicle(this.vehicle).subscribe(() => {
      this.router.navigate(['/vehicle']);
    });
  }
}
