package com.eduardo.backend.config;

import com.eduardo.backend.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    // Define as configurações de segurança do Spring Security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ❌ Desativa CSRF (necessário em APIs REST)
                .csrf(csrf -> csrf.disable())

                // 🌐 Ativa e aplica configuração de CORS
                .cors(Customizer.withDefaults())

                // 🔓 Permite todas as requisições (modo dev)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // 🧩 Adiciona o filtro JWT antes da autenticação padrão
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 🌍 Configuração de CORS permitindo o frontend (Angular, React, etc.)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200")); // Origem permitida
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos HTTP
        config.setAllowedHeaders(List.of("*")); // Permite todos os headers
        config.setAllowCredentials(true); // Permite envio de cookies/autenticação

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica em todas as rotas
        return source;
    }
}
