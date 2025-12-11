import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserDTO } from '../../../core/models/auth.model';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-final-registration-client',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './final-registration-client.component.html',
  styleUrls: ['./final-registration-client.component.scss'],
})
export class FinalRegistrationClientComponent implements OnInit {
  @Output() userActivated = new EventEmitter<void>();

  user: UserDTO = {
    name: '',
    email: '',
    password: '',
    role: 'CLIENT',
  };

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe({
      next: (user) => {
        this.user = user;
      },
      error: (err) => console.error('Erro ao carregar usuário', err),
    });
  }

  updateUser() {
    this.authService.updateUser(this.user).subscribe({
      next: (res) => {
        console.log('Usuário atualizado com sucesso', res);
        this.userActivated.emit();
      },
      error: (err) => {
        console.error('Erro ao atualizar usuário', err);
        alert('Erro ao atualizar usuário');
      },
    });
  }
}
