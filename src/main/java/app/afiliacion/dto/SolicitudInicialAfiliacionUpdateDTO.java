package app.afiliacion.dto;

import jakarta.validation.constraints.Size;

public class SolicitudInicialAfiliacionUpdateDTO {
    
    @Size(max = 20, message = "El estado no puede exceder 20 caracteres")
    private String estado; // PENDIENTE, CONTACTADO, LINK_ENVIADO, FINALIZADO
    
    private String notasAdmin;
    
    // Constructor vacío
    public SolicitudInicialAfiliacionUpdateDTO() {}
    
    // Constructor con parámetros
    public SolicitudInicialAfiliacionUpdateDTO(String estado, String notasAdmin) {
        this.estado = estado;
        this.notasAdmin = notasAdmin;
    }
    
    // Getters y Setters
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getNotasAdmin() {
        return notasAdmin;
    }
    
    public void setNotasAdmin(String notasAdmin) {
        this.notasAdmin = notasAdmin;
    }
}
