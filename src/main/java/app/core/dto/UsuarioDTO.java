package app.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class UsuarioDTO {
    
    @NotBlank(message = "El username es requerido")
    private String username;
    
    @NotBlank(message = "La contraseña es requerida")
    private String password;
    
    @NotNull(message = "El estado es requerido")
    private Integer estado;
    
    @NotNull(message = "La persona es requerida")
    private Integer personaId;
    
    @NotEmpty(message = "Debe asignar al menos un rol")
    private List<Long> roleIds;
    
    // Constructores
    public UsuarioDTO() {}
    
    public UsuarioDTO(String username, String password, Integer estado, Integer personaId, List<Long> roleIds) {
        this.username = username;
        this.password = password;
        this.estado = estado;
        this.personaId = personaId;
        this.roleIds = roleIds;
    }
    
    // Getters y Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
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
    
    public List<Long> getRoleIds() {
        return roleIds;
    }
    
    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
    
    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "username='" + username + '\'' +
                ", estado=" + estado +
                ", personaId=" + personaId +
                ", roleIds=" + roleIds +
                '}';
    }
}
