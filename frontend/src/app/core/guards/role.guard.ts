import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const RoleGuard: CanActivateFn = (route) => {
  const auth = inject(AuthService);  // injeta o serviço de autenticação
  const router = inject(Router);     // injeta o roteador

  const expectedRoles = route.data['roles'] as string[]; // roles permitidas da rota
  const userRole = auth.getUserRole();                  // role atual do usuário

  // Map para redirecionamento automático
  const roleRedirectMap: Record<string, string> = {
    CLIENT: '/home-client',
    OWNER: '/home-owner',
    ADMIN: '/home-admin',
    DRIVER: '/home-driver'
  };

  if (!userRole) {
    router.navigate(['/login'], { replaceUrl: true });  // redireciona se não tiver permissão
    return false;
  }

  // SE O USUÁRIO TEM ROLE MAS NÃO É A ESPERADA → envia para a home correta da role dele
  if (!expectedRoles.includes(userRole)) {
    const redirect = roleRedirectMap[userRole] || '/login';
    router.navigate([redirect], { replaceUrl: true });
    return false;
  }

  return true; // libera acesso
};
