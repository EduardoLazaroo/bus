import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { RoleGuard } from './core/guards/role.guard';


// IMPORTS DOS COMPONENTES
import { HomeClientComponent } from './pages/role/home-client/home-client.component';
import { HomeOwnerComponent } from './pages/role/home-owner/home-owner.component';
import { HomeAdminComponent } from './pages/role/home-admin/home-admin.component';
import { HomeDriverComponent } from './pages/role/home-driver/home-driver.component';

// import { ProfileUserComponent } from './pages/profile-user/profile-user.component';
// import { ProfileOwnerComponent } from './pages/profile-owner/profile-owner.component';
// import { ProfileAdminComponent } from './pages/profile-admin/profile-admin.component';
// import { ProfileDriverComponent } from './pages/profile-driver/profile-driver.component';


export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // HOME POR ROLE
  {
    path: 'home-client',
    component: HomeClientComponent,
    canActivate: [RoleGuard],
    data: { roles: ['CLIENT'] }
  },
  {
    path: 'home-owner',
    component: HomeOwnerComponent,
    canActivate: [RoleGuard],
    data: { roles: ['OWNER'] }
  },
  {
    path: 'home-admin',
    component: HomeAdminComponent,
    canActivate: [RoleGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'home-driver',
    component: HomeDriverComponent,
    canActivate: [RoleGuard],
    data: { roles: ['DRIVER'] }
  },

  // PERFIL POR ROLE
  // {
  //   path: 'profile-client',
  //   component: ProfileclientComponent,
  //   canActivate: [RoleGuard],
  //   data: { roles: ['CLIENT'] }
  // },
  // {
  //   path: 'profile-owner',
  //   component: ProfileOwnerComponent,
  //   canActivate: [RoleGuard],
  //   data: { roles: ['OWNER'] }
  // },
  // {
  //   path: 'profile-admin',
  //   component: ProfileAdminComponent,
  //   canActivate: [RoleGuard],
  //   data: { roles: ['ADMIN'] }
  // },
  // {
  //   path: 'profile-driver',
  //   component: ProfileDriverComponent,
  //   canActivate: [RoleGuard],
  //   data: { roles: ['DRIVER'] }
  // },

  { path: '**', redirectTo: '' },
];
