import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';


@Component({
  selector: 'app-use-select-company',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-select-company.component.html',
  styleUrls: ['./user-select-company.component.scss']
})
export class UserSelectCompanyComponent {}
