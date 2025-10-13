import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const RoleGuard: CanActivateFn = (route) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  const expectedRoles = route.data['roles'] as string[];
  const userRole = auth.getUserRole();

  if (!userRole || !expectedRoles.includes(userRole)) {
    router.navigate(['/login']);
    return false;
  }

  return true;
};
