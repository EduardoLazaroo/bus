import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class Navbar implements OnInit {
  userName: string | null = null;

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit() {
    this.userName = this.authService.getUserName();
  }

  goToProfile() {
    this.router.navigate(['/profile']);
  }
}
