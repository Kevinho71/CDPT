package app.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public org.springframework.security.authentication.AuthenticationManager authenticationManager(
            org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(customUserDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:5173",  // React Frontend
            "http://localhost:8080"   // Backend (para Swagger)
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .headers(headers -> headers.disable())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                // Recursos estáticos
                .requestMatchers(
                    "/registro**",
                    "/js/**",
                    "/css/**",
                    "/img/**",
                    "/logos/**",
                    "/socioslogos/**",
                    "/catalogosimg/**",
                    "/catalogoscrt/**"
                ).permitAll()
                
                // Legacy endpoints (mantener compatibilidad)
                .requestMatchers(
                    "/socios/estadosocio/**",
                    "/socios/empresas/**",
                    "/RestSocios/findByNrodocumento/**",
                    "/RestSocios/logo_socio/**",
                    "/RestCatalogos/buscar/**",
                    "/RestCatalogos/logo_empresa/**",
                    "/RestCatalogos/img_catalogos_empresa/**"
                ).permitAll()
                
                // Documentación API
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()
                
                // OAuth2 y autenticación
                .requestMatchers("/oauth2/**", "/api/auth/**").permitAll()
                
                // Posts públicos (sin autenticación)
                .requestMatchers("/api/public/posts/**").permitAll()
                
                // Posts admin (requiere ROLE_ADMIN)
                .requestMatchers("/api/admin/posts/**").hasRole("ADMIN")
                
                // Endpoints de socios y perfiles (requieren autenticación)
                .requestMatchers("/api/partners/**", "/api/perfiles/**").hasAnyRole("SOCIO", "ADMIN")
                
                // Catálogos de datos (públicos por ahora)
                .requestMatchers(
                    "/api/profesiones/**",
                    "/api/instituciones/**",
                    "/api/servicios/**",
                    "/api/sectores/**",
                    "/api/idiomas/**",
                    "/api/especialidades/**",
                    "/api/locations/**",
                    "/api/catalogos/**"
                ).permitAll()
                
                // Gestión de usuarios y roles (solo ADMIN)
                .requestMatchers("/api/roles/**", "/api/usuarios/**").hasRole("ADMIN")
                
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .successHandler(oauth2AuthenticationSuccessHandler)
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        
        return http.build();
    }
}