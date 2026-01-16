package app.auth.dto;

public class LoginResponse {
    
    private String token;
    private String email;
    private Integer personaId;
    private String username;
    
    public LoginResponse() {}
    
    public LoginResponse(String token, String email, Integer personaId, String username) {
        this.token = token;
        this.email = email;
        this.personaId = personaId;
        this.username = username;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Integer getPersonaId() {
        return personaId;
    }
    
    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}
