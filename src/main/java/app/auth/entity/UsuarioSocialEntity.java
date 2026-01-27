package app.auth.entity;

import app.core.entity.PersonaEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_social")
public class UsuarioSocialEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "provider", nullable = false, length = 20)
    private String provider = "LOCAL"; // LOCAL, GOOGLE, FACEBOOK, etc.
    
    @Column(name = "provider_id", nullable = false, length = 255)
    private String providerId;
    
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    
    @Column(name = "nombre_completo", length = 255)
    private String nombreCompleto;
    
    @Column(name = "foto_perfil_url", length = 500)
    private String fotoPerfilUrl;
    
    @ManyToOne
    @JoinColumn(name = "fk_persona", nullable = false)
    private PersonaEntity persona;
    
    @Column(name = "access_token", columnDefinition = "TEXT")
    private String accessToken;
    
    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;
    
    @Column(name = "token_expires_at")
    private LocalDateTime tokenExpiresAt;
    
    @Column(name = "email_verificado")
    private Boolean emailVerificado = true;
    
    @Column(name = "fecha_vinculacion")
    private LocalDateTime fechaVinculacion = LocalDateTime.now();
    
    @Column(name = "fecha_ultima_autenticacion")
    private LocalDateTime fechaUltimaAutenticacion;
    
    @Column(name = "estado")
    private Integer estado = 1;
    
    // Constructores
    public UsuarioSocialEntity() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getProvider() {
        return provider;
    }
    
    public void setProvider(String provider) {
        this.provider = provider;
    }
    
    public String getProviderId() {
        return providerId;
    }
    
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }
    
    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
    
    public PersonaEntity getPersona() {
        return persona;
    }
    
    public void setPersona(PersonaEntity persona) {
        this.persona = persona;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getRefreshToken() {
        return refreshToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    public LocalDateTime getTokenExpiresAt() {
        return tokenExpiresAt;
    }
    
    public void setTokenExpiresAt(LocalDateTime tokenExpiresAt) {
        this.tokenExpiresAt = tokenExpiresAt;
    }
    
    public Boolean getEmailVerificado() {
        return emailVerificado;
    }
    
    public void setEmailVerificado(Boolean emailVerificado) {
        this.emailVerificado = emailVerificado;
    }
    
    public LocalDateTime getFechaVinculacion() {
        return fechaVinculacion;
    }
    
    public void setFechaVinculacion(LocalDateTime fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }
    
    public LocalDateTime getFechaUltimaAutenticacion() {
        return fechaUltimaAutenticacion;
    }
    
    public void setFechaUltimaAutenticacion(LocalDateTime fechaUltimaAutenticacion) {
        this.fechaUltimaAutenticacion = fechaUltimaAutenticacion;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
