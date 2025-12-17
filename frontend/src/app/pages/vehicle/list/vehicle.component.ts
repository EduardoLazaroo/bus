import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { Navbar } from '../../navbar/navbar.component';
import { Router } from '@angular/router';
import { VehicleDTO } from '../../../core/models/vehicle.model';
import { VehicleService } from '../../../core/services/vehicle.services';

@Component({
  selector: 'app-vehicle',
  standalone: true,
  imports: [CommonModule, Navbar],
  templateUrl: './vehicle.component.html',
  styleUrls: ['./vehicle.component.scss'],
})
export class VehicleComponent implements OnInit {
  vehicles: VehicleDTO[] = [];

  constructor(
    private vehicleService: VehicleService,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadVehicles();
  }

  loadVehicles(): void {
    this.vehicleService
      .getMyVehicles()
      .subscribe((res) => (this.vehicles = res));
  }

  backPage(): void {
    this.location.back();
  }

  goToUpdate(): void {
    this.router.navigate(['/vehicle-update']);
  }
}
