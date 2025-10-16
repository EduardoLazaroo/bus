import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const RoleGuard: CanActivateFn = (route) => {
  const auth = inject(AuthService);  // injeta o serviço de autenticação
  const router = inject(Router);     // injeta o roteador

  const expectedRoles = route.data['roles'] as string[]; // roles permitidas da rota
  const userRole = auth.getUserRole();                  // role atual do usuário

  if (!userRole || !expectedRoles.includes(userRole)) {
    router.navigate(['/login'], { replaceUrl: true });  // redireciona se não tiver permissão
    return false;
  }

  return true; // libera acesso
};
