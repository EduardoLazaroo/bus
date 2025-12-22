import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { RoleGuard } from './core/guards/role.guard';

import { HomeClientComponent } from './pages/role/home-client/home-client.component';
import { HomeOwnerComponent } from './pages/role/home-owner/home-owner.component';
import { HomeAdminComponent } from './pages/role/home-admin/home-admin.component';
import { HomeDriverComponent } from './pages/role/home-driver/home-driver.component';
import { FinalRegistrationOwnerComponent } from './pages/role/final-registration-owner/final-registration-owner.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { CustomerComponent } from './pages/role/customer/customer.component';
import { VehicleComponent } from './pages/vehicle/list/vehicle.component';
import { VehicleUpdateComponent } from './pages/vehicle/update/vehicle-update.component';
import { NewJourneyComponent } from './pages/journey/new-journey/new-journey.component';
import { ListJourneysComponent } from './pages/journey/list-journeys/list-journeys.component';
import { JourneyDetailComponent } from './pages/journey/journey-detail/journey-detail.component';
import { JourneyNoticeCreateComponent } from './pages/journey/journey-notice-create/journey-notice-create.component';
import { JourneyPollCreateComponent } from './pages/journey/journey-poll-create/journey-poll-create.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },

  // CLIENT
  {
    path: 'home-client',
    component: HomeClientComponent,
    canActivate: [RoleGuard],
    data: { roles: ['CLIENT'] },
  },

  // OWNER
  {
    path: 'home-owner',
    component: HomeOwnerComponent,
    canActivate: [RoleGuard],
    data: { roles: ['OWNER'] },
  },
  {
    path: 'final-registration-owner',
    component: FinalRegistrationOwnerComponent,
    canActivate: [RoleGuard],
    data: { roles: ['OWNER'] },
  },
  {
    path: 'customer',
    component: CustomerComponent,
    canActivate: [RoleGuard],
    data: { roles: ['OWNER'] },
  },
  {
    path: 'vehicle',
    component: VehicleComponent,
    canActivate: [RoleGuard],
    data: { roles: ['OWNER'] },
  },
  {
    path: 'vehicle-update',
    component: VehicleUpdateComponent,
    canActivate: [RoleGuard],
    data: { roles: ['OWNER'] },
  },

  // JOURNEYS
  {
    path: 'journeys',
    component: ListJourneysComponent,
    canActivate: [RoleGuard],
    data: { roles: ['OWNER'] },
  },
  {
    path: 'journeys/new',
    component: NewJourneyComponent,
    canActivate: [RoleGuard],
    data: { roles: ['OWNER'] },
  },
  {
    path: 'journeys/:id',
    component: JourneyDetailComponent,
    canActivate: [RoleGuard],
    data: { roles: ['OWNER'] },
  },
  {
    path: 'journeys/:id/notices/new',
    component: JourneyNoticeCreateComponent,
    canActivate: [RoleGuard],
    data: { roles: ['OWNER'] },
  },
  {
    path: 'journeys/:id/polls/new',
    component: JourneyPollCreateComponent,
    canActivate: [RoleGuard],
    data: { roles: ['OWNER'] },
  },

  // ADMIN
  {
    path: 'home-admin',
    component: HomeAdminComponent,
    canActivate: [RoleGuard],
    data: { roles: ['ADMIN'] },
  },

  // DRIVER
  {
    path: 'home-driver',
    component: HomeDriverComponent,
    canActivate: [RoleGuard],
    data: { roles: ['DRIVER'] },
  },

  { path: '**', redirectTo: '' },
];
