import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const RoleGuard: CanActivateFn = (route) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  const expectedRole = route.data['role'];
  const userRole = auth.getUserRole();

  if (!userRole || userRole !== expectedRole) {
    router.navigate(['/login']);
    return false;
  }
  return true;
};
