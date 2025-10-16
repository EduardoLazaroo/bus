package com.eduardo.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 🚦 Filtro executado a cada requisição HTTP para validar o token JWT
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization"); // 🔎 Captura o header Authorization

        // 🔐 Se existir um token no header e ele começar com "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove o prefixo "Bearer "
            if (jwtUtil.isTokenValid(token)) {
                // 🔓 Extrai informações do token (usuário e role)
                String email = jwtUtil.extractEmail(token);
                String role = jwtUtil.extractRole(token);

                // 💾 Armazena e-mail e role no request (para acesso em controladores)
                request.setAttribute("email", email);
                request.setAttribute("role", role);

                // 🧩 Cria objeto de autenticação com base nas roles do token
                List<SimpleGrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority("ROLE_" + role));
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                // 🧠 Define o usuário autenticado no contexto de segurança do Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // ⏭️ Continua o fluxo da requisição (mesmo se não houver token)
        filterChain.doFilter(request, response);
    }
}
