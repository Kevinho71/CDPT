package app.auth.security;

import app.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro JWT que intercepta todas las peticiones HTTP
 * Valida el token JWT y autentica al usuario en Spring Security
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                     HttpServletResponse response, 
                                     FilterChain filterChain) throws ServletException, IOException {
        
        // Saltar filtro JWT para rutas públicas (optimización)
        String requestPath = request.getRequestURI();
        if (requestPath.startsWith("/api/public/") || 
            requestPath.startsWith("/public/") ||
            requestPath.startsWith("/api/auth/") ||
            requestPath.startsWith("/oauth2/") ||
            requestPath.startsWith("/swagger-ui") ||
            requestPath.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return; // ← Salir sin procesar el token
        }
        
        try {
            // 1. Extraer el token del header Authorization
            String authorizationHeader = request.getHeader("Authorization");
            
            String email = null;
            String jwt = null;

            // 2. Validar formato: "Bearer <token>"
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                email = jwtUtil.extractEmail(jwt);
            }

            // 3. Si hay token y NO hay autenticación previa
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // 4. Cargar usuario desde la base de datos
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // 5. Validar el token
                if (jwtUtil.validateToken(jwt, email)) {
                    
                    // 6. Crear objeto de autenticación
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(
                            userDetails, 
                            null, 
                            userDetails.getAuthorities()
                        );
                    
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // 7. Establecer autenticación en el contexto de Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Si hay error al validar el token, simplemente continuar sin autenticar
            // Spring Security manejará la autorización
            logger.error("Error al procesar JWT: " + e.getMessage());
        }

        // 8. Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
