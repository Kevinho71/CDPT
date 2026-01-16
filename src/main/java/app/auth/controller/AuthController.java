package app.auth.controller;

import app.auth.dto.OAuth2LoginResponseDTO;
import app.auth.dto.LoginRequest;
import app.auth.dto.LoginResponse;
import app.auth.util.JwtUtil;
import app.core.entity.UsuarioEntity;
import app.core.entity.PersonaEntity;
import app.core.repository.UsuarioRepository;
import app.socio.entity.SocioEntity;
import app.socio.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private SocioRepository socioRepository;
    
    /**
     * Endpoint para login tradicional con username/password
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar usuario
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );
            
            // Obtener usuario desde la base de datos
            UsuarioEntity usuario = usuarioRepository.findByUsername(loginRequest.getUsername());
            
            if (usuario == null) {
                return ResponseEntity.status(401).body(Map.of("error", "Usuario no encontrado"));
            }
            
            // Obtener persona asociada
            PersonaEntity persona = usuario.getPersona();
            String email = persona != null ? persona.getEmail() : "";
            Integer personaId = persona != null ? persona.getId() : null;
            
            // Obtener roles del usuario
            String roles = usuario.getRoles() != null && !usuario.getRoles().isEmpty() 
                ? usuario.getRoles().stream()
                    .map(rol -> rol.getNombre())
                    .reduce((a, b) -> a + "," + b)
                    .orElse("USER")
                : "USER";
            
            // Buscar socio asociado a esta persona para obtener socioId
            SocioEntity socio = personaId != null ? socioRepository.findByPersonaId(personaId) : null;
            Integer socioId = socio != null ? socio.getId() : null;
            
            // Generar token JWT con los parámetros correctos: (personaId, email, roles, socioId)
            String token = jwtUtil.generateToken(
                personaId != null ? personaId.longValue() : 0L,
                email,
                roles,
                socioId
            );
            
            // Crear respuesta
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setEmail(email);
            response.setPersonaId(personaId);
            response.setUsername(usuario.getUsername());
            
            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error en el servidor: " + e.getMessage()));
        }
    }
    
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
