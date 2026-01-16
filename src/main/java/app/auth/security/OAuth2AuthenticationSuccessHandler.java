package app.auth.security;

import app.auth.dto.OAuth2LoginResponseDTO;
import app.auth.service.OAuth2Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    @Autowired
    private OAuth2Service oauth2Service;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        
        try {
            // Procesar el login OAuth2 con toda la lógica de negocio
            OAuth2LoginResponseDTO loginResponse = oauth2Service.processOAuth2Login(oauth2User, "GOOGLE");
            
            // Construir URL de redirección al frontend con el token
            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/oauth2/callback")
                    .queryParam("token", loginResponse.getToken())
                    .queryParam("personaId", loginResponse.getPersonaId())
                    .queryParam("email", loginResponse.getEmail())
                    .queryParam("isNewUser", loginResponse.isNewUser())
                    .build().toUriString();
            
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
            
        } catch (Exception e) {
            // Redirigir al frontend con el error
            String errorUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/oauth2/callback")
                    .queryParam("error", e.getMessage())
                    .build().toUriString();
            
            getRedirectStrategy().sendRedirect(request, response, errorUrl);
        }
    }
}
