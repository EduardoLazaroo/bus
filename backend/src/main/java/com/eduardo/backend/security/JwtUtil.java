package com.eduardo.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // 🔐 Chave secreta usada para assinar e validar os tokens JWT
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "umaChaveSuperSecretaDePeloMenos32Caracteres!".getBytes()
    );

    // ⏱️ Tempo de expiração do token (1 hora)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    // 🎟️ Gera um token JWT contendo o e-mail e o papel (role) do usuário
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)                        // Define o e-mail como "subject" do token
                .claim("role", role)                      // Adiciona o papel (role) como claim
                .setIssuedAt(new Date())                  // Data de emissão
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiração
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Assina o token com algoritmo HS256
                .compact();
    }

    // 🔍 Extrai as "claims" (dados internos) de um token JWT
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Verifica se o token ainda é válido (não expirado e bem formado)
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // 📧 Extrai o e-mail do usuário (subject) do token
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // 🧩 Extrai o papel (role) do usuário do token
    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }
}
