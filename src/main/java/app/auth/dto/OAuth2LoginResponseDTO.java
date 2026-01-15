package app.auth.dto;

public class OAuth2LoginResponseDTO {
    
    private String token;
    private String type = "Bearer";
    private Long personaId;
    private String email;
    private String nombreCompleto;
    private String fotoPerfil;
    private boolean isNewUser;
    private String provider;
    
    public OAuth2LoginResponseDTO() {}
    
    public OAuth2LoginResponseDTO(String token, Long personaId, String email, 
                                   String nombreCompleto, String fotoPerfil, 
                                   boolean isNewUser, String provider) {
        this.token = token;
        this.personaId = personaId;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.fotoPerfil = fotoPerfil;
        this.isNewUser = isNewUser;
        this.provider = provider;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Long getPersonaId() {
        return personaId;
    }
    
    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
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
    
    public String getFotoPerfil() {
        return fotoPerfil;
    }
    
    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
    
    public boolean isNewUser() {
        return isNewUser;
    }
    
    public void setNewUser(boolean newUser) {
        isNewUser = newUser;
    }
    
    public String getProvider() {
        return provider;
    }
    
    public void setProvider(String provider) {
        this.provider = provider;
    }
}
