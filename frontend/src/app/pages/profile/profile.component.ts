import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { UserDTO } from '../../core/models/auth.model';
@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  userDto: UserDTO | null = null;

  constructor(
    private authService: AuthService,
    private router: Router,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe((user) => {
      this.userDto = user;
      console.warn('User data loaded:', user);
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  backPage() {
    // todo
    this.location.back();
  }
}
