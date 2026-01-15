package app.auth.controller;

import app.auth.dto.OAuth2LoginResponseDTO;
import app.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * Endpoint para validar un token JWT
     */
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Token inválido"));
            }
            
            String token = authHeader.substring(7);
            String email = jwtUtil.extractEmail(token);
            Long personaId = jwtUtil.extractPersonaId(token);
            
            if (jwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Token expirado"));
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("valid", true);
            response.put("email", email);
            response.put("personaId", personaId);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Token inválido: " + e.getMessage()));
        }
    }
    
    /**
     * Endpoint para obtener información del usuario desde el token
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Token inválido"));
            }
            
            String token = authHeader.substring(7);
            
            if (jwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Token expirado"));
            }
            
            String email = jwtUtil.extractEmail(token);
            Long personaId = jwtUtil.extractPersonaId(token);
            
            Map<String, Object> response = new HashMap<>();
            response.put("email", email);
            response.put("personaId", personaId);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Error al obtener usuario: " + e.getMessage()));
        }
    }
    
    /**
     * Endpoint de prueba para verificar que la API está funcionando
     */
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of(
            "status", "OK",
            "message", "OAuth2 API funcionando correctamente",
            "timestamp", System.currentTimeMillis()
        ));
    }
}
