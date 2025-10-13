// package com.eduardo.backend.security;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;

// @Component
// public class JwtFilter extends OncePerRequestFilter {

//     private final JwtUtil jwtUtil;

//     public JwtFilter(JwtUtil jwtUtil) {
//         this.jwtUtil = jwtUtil;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {

//         String path = request.getRequestURI();

//         // Ignora rotas públicas
//         if (path.startsWith("/api/users")) {
//             filterChain.doFilter(request, response);
//             return;
//         }

//         // Pega header Authorization
//         String authHeader = request.getHeader("Authorization");
//         if (authHeader != null && authHeader.startsWith("Bearer ")) {
//             String token = authHeader.substring(7);
//             if (jwtUtil.isTokenValid(token)) {
//                 request.setAttribute("email", jwtUtil.extractEmail(token));
//                 request.setAttribute("role", jwtUtil.extractRole(token));
//             } else {
//                 response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
//                 return;
//             }
//         } else {
//             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token ausente");
//             return;
//         }

//         filterChain.doFilter(request, response);
//     }
// }
