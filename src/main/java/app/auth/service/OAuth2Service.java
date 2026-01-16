package app.auth.service;

import app.auth.dto.OAuth2LoginResponseDTO;
import app.auth.entity.UsuarioSocialEntity;
import app.auth.repository.UsuarioSocialRepository;
import app.auth.util.JwtUtil;
import app.core.entity.PersonaEntity;
import app.core.entity.UsuarioEntity;
import app.core.repository.PersonaRepository;
import app.core.repository.UsuarioRepository;
import app.socio.entity.SocioEntity;
import app.socio.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OAuth2Service {
    
    @Autowired
    private UsuarioSocialRepository usuarioSocialRepository;
    
    @Autowired
    private PersonaRepository personaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private SocioRepository socioRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * Procesa la autenticación OAuth2 siguiendo el flujo completo:
     * 1. Búsqueda Directa (Usuario Recurrente)
     * 2. Auto-Linking (Usuario Nuevo en Social, pero Antiguo en el Sistema)
     * 3. Rechazo (Usuario Desconocido)
     */
    @Transactional
    public OAuth2LoginResponseDTO processOAuth2Login(OAuth2User oauth2User, String provider) {
        // Extraer datos del usuario OAuth2
        String providerId = oauth2User.getAttribute("sub"); // Google ID
        String email = oauth2User.getAttribute("email");
        String nombreCompleto = oauth2User.getAttribute("name");
        String fotoPerfil = oauth2User.getAttribute("picture");
        
        if (providerId == null || email == null) {
            throw new RuntimeException("Datos de OAuth2 incompletos");
        }
        
        // PASO A: Búsqueda Directa - Usuario Recurrente
        Optional<UsuarioSocialEntity> usuarioSocialOpt = 
            usuarioSocialRepository.findByProviderAndProviderId(provider.toUpperCase(), providerId);
        
        if (usuarioSocialOpt.isPresent()) {
            // Usuario conocido - actualizar última autenticación
            UsuarioSocialEntity usuarioSocial = usuarioSocialOpt.get();
            usuarioSocial.setFechaUltimaAutenticacion(LocalDateTime.now());
            usuarioSocialRepository.save(usuarioSocial);
            
            // Generar JWT
            PersonaEntity persona = usuarioSocial.getPersona();
            String token = generateJwtForPersona(persona);
            
            return new OAuth2LoginResponseDTO(
                token,
                persona.getId().longValue(),
                email,
                nombreCompleto,
                fotoPerfil,
                false, // No es nuevo usuario
                provider
            );
        }
        
        // PASO B: Auto-Linking - Buscar en persona por email
        Optional<PersonaEntity> personaOpt = personaRepository.findByEmail(email);
        
        if (personaOpt.isPresent()) {
            // Email existe en la base de datos - vincular cuenta
            PersonaEntity persona = personaOpt.get();
            
            // Verificar que la persona esté activa
            if (persona.getEstado() != 1) {
                throw new RuntimeException("La cuenta asociada al email " + email + " no está activa");
            }
            
            // Crear registro en usuario_social
            UsuarioSocialEntity nuevoUsuarioSocial = new UsuarioSocialEntity();
            nuevoUsuarioSocial.setProvider(provider.toUpperCase());
            nuevoUsuarioSocial.setProviderId(providerId);
            nuevoUsuarioSocial.setEmail(email);
            nuevoUsuarioSocial.setNombreCompleto(nombreCompleto);
            nuevoUsuarioSocial.setFotoPerfilUrl(fotoPerfil);
            nuevoUsuarioSocial.setPersona(persona);
            nuevoUsuarioSocial.setEmailVerificado(true);
            nuevoUsuarioSocial.setFechaVinculacion(LocalDateTime.now());
            nuevoUsuarioSocial.setFechaUltimaAutenticacion(LocalDateTime.now());
            nuevoUsuarioSocial.setEstado(1);
            
            usuarioSocialRepository.save(nuevoUsuarioSocial);
            
            // Generar JWT
            String token = generateJwtForPersona(persona);
            
            return new OAuth2LoginResponseDTO(
                token,
                persona.getId().longValue(),
                email,
                nombreCompleto,
                fotoPerfil,
                true, // Es nuevo en OAuth2 pero usuario existente
                provider
            );
        }
        
        // PASO C: Rechazo - Usuario no registrado
        throw new RuntimeException(
            "El correo " + email + " no está registrado como socio activo. " +
            "Por favor, contacte con la administración para registrarse."
        );
    }
    
    /**
     * Genera JWT con la información de la persona y sus roles
     */
    private String generateJwtForPersona(PersonaEntity persona) {
        // Buscar usuario tradicional asociado a esta persona para obtener roles
        UsuarioEntity usuario = usuarioRepository.findByPersonaId(persona.getId());
        
        String roles = "ROLE_SOCIO"; // Rol por defecto para OAuth2
        
        // Si existe usuario tradicional, obtener sus roles reales
        if (usuario != null && usuario.getRoles() != null && !usuario.getRoles().isEmpty()) {
            roles = usuario.getRoles().stream()
                .map(rol -> rol.getNombre())
                .reduce((a, b) -> a + "," + b)
                .orElse("ROLE_SOCIO");
        }
        
        // Buscar socio asociado a esta persona para obtener socioId
        SocioEntity socio = socioRepository.findByPersonaId(persona.getId());
        Integer socioId = socio != null ? socio.getId() : null;
        
        return jwtUtil.generateToken(
            persona.getId().longValue(),
            persona.getEmail(),
            roles,
            socioId
        );
    }
    
    /**
     * Desvincula una cuenta OAuth2
     */
    @Transactional
    public void unlinkOAuth2Account(Long personaId, String provider) {
        Optional<UsuarioSocialEntity> usuarioSocialOpt = 
            usuarioSocialRepository.findByEmailAndProvider(
                personaRepository.findById(personaId.intValue())
                    .orElseThrow(() -> new RuntimeException("Persona no encontrada"))
                    .getEmail(),
                provider.toUpperCase()
            );
        
        if (usuarioSocialOpt.isPresent()) {
            UsuarioSocialEntity usuarioSocial = usuarioSocialOpt.get();
            usuarioSocial.setEstado(0); // Desactivar en lugar de eliminar
            usuarioSocialRepository.save(usuarioSocial);
        }
    }
}
