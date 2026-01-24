package app.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO para actualizar usuarios existentes.
 * Todos los campos son opcionales excepto aquellos que no pueden ser nulos en BD.
 * Si un campo viene null, NO se actualiza (se mantiene el valor actual).
 */
public class UsuarioUpdateDTO {
    
    @NotBlank(message = "El username es requerido")
    private String username;
    
    // Password es OPCIONAL en updates - si viene null, no se modifica
    private String password;
    
    @NotNull(message = "El estado es requerido")
    private Integer estado;
    
    @NotNull(message = "La persona es requerida")
    private Integer personaId;
    
    // Roles es OPCIONAL - si viene null, no se modifican
    private List<Long> roleIds;
    
    // Constructores
    public UsuarioUpdateDTO() {}
    
    public UsuarioUpdateDTO(String username, String password, Integer estado, Integer personaId, List<Long> roleIds) {
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
        return "UsuarioUpdateDTO{" +
                "username='" + username + '\'' +
                ", estado=" + estado +
                ", personaId=" + personaId +
                ", roleIds=" + roleIds +
                ", passwordProvided=" + (password != null && !password.isEmpty()) +
                '}';
    }
}
