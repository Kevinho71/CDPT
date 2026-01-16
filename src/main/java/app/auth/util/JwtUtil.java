package app.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret:cadet_secret_key_super_secure_2026_change_in_production_environment}")
    private String secret;
    
    @Value("${jwt.expiration:86400000}") // 24 horas por defecto
    private Long expiration;
    
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    /**
     * Genera JWT con información del usuario
     */
    public String generateToken(Long personaId, String email, String roles, Integer socioId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("personaId", personaId);
        claims.put("email", email);
        claims.put("roles", roles);
        if (socioId != null) {
            claims.put("socioId", socioId);
        }
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * Extrae el email (subject) del token
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    /**
     * Extrae personaId del token
     */
    public Long extractPersonaId(String token) {
        return extractClaim(token, claims -> claims.get("personaId", Long.class));
    }
    
    /**
     * Extrae socioId del token
     */
    public Integer extractSocioId(String token) {
        return extractClaim(token, claims -> claims.get("socioId", Integer.class));
    }
    
    /**
     * Extrae fecha de expiración
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    /**
     * Extrae un claim específico
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * Verifica si el token ha expirado
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    /**
     * Valida el token
     */
    public Boolean validateToken(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }
}
