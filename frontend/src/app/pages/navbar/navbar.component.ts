import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  userName: string | null = null;

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit() {
    this.userName = this.authService.getUserName();
  }

  goToProfile() {
    this.router.navigate(['/profile']);
  }
}
