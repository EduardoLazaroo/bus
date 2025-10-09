import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { HomeUserComponent } from './pages/home-user/home-user.component';
import { HomeAdminComponent } from './pages/home-admin/home-admin.component';
import { RoleGuard } from './core/guards/role.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'admin',
    component: HomeAdminComponent,
    canActivate: [RoleGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'user',
    component: HomeUserComponent,
    canActivate: [RoleGuard],
    data: { role: 'USER' },
  },
  { path: '**', redirectTo: '' },
];
