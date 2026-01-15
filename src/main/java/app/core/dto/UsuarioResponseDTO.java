package app.core.dto;

import java.util.List;

public class UsuarioResponseDTO {
    
    private Integer id;
    private String username;
    private Integer estado;
    
    // Datos desnormalizados de persona
    private Integer personaId;
    private String personaCi;
    private String personaNombre;
    private String personaEmail;
    
    // Roles del usuario
    private List<RolResponseDTO> roles;
    
    // Constructores
    public UsuarioResponseDTO() {}
    
    public UsuarioResponseDTO(Integer id, String username, Integer estado, 
                             Integer personaId, String personaCi, String personaNombre, String personaEmail,
                             List<RolResponseDTO> roles) {
        this.id = id;
        this.username = username;
        this.estado = estado;
        this.personaId = personaId;
        this.personaCi = personaCi;
        this.personaNombre = personaNombre;
        this.personaEmail = personaEmail;
        this.roles = roles;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    public Integer getPersonaId() {
        return personaId;
    }
    
    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }
    
    public String getPersonaCi() {
        return personaCi;
    }
    
    public void setPersonaCi(String personaCi) {
        this.personaCi = personaCi;
    }
    
    public String getPersonaNombre() {
        return personaNombre;
    }
    
    public void setPersonaNombre(String personaNombre) {
        this.personaNombre = personaNombre;
    }
    
    public String getPersonaEmail() {
        return personaEmail;
    }
    
    public void setPersonaEmail(String personaEmail) {
        this.personaEmail = personaEmail;
    }
    
    public List<RolResponseDTO> getRoles() {
        return roles;
    }
    
    public void setRoles(List<RolResponseDTO> roles) {
        this.roles = roles;
    }
    
    @Override
    public String toString() {
        return "UsuarioResponseDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", personaNombre='" + personaNombre + '\'' +
                ", roles=" + roles +
                '}';
    }
}
