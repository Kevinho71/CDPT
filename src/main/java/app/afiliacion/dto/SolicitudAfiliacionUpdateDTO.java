package app.afiliacion.dto;

import jakarta.validation.constraints.Size;

public class SolicitudAfiliacionUpdateDTO {
    
    @Size(max = 30, message = "El estado de afiliación no puede exceder 30 caracteres")
    private String estadoAfiliacion; // EN_REVISION, OBSERVADO, APROBADO, RECHAZADO
    
    // Constructor vacío
    public SolicitudAfiliacionUpdateDTO() {}
    
    // Constructor con parámetros
    public SolicitudAfiliacionUpdateDTO(String estadoAfiliacion) {
        this.estadoAfiliacion = estadoAfiliacion;
    }
    
    // Getters y Setters
    public String getEstadoAfiliacion() {
        return estadoAfiliacion;
    }
    
    public void setEstadoAfiliacion(String estadoAfiliacion) {
        this.estadoAfiliacion = estadoAfiliacion;
    }
}
