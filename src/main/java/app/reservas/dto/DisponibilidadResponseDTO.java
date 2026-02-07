package app.reservas.dto;

/**
 * DTO de respuesta para consultas de disponibilidad.
 */
public class DisponibilidadResponseDTO {
    
    private boolean disponible;
    private String mensaje;
    
    // Constructores
    public DisponibilidadResponseDTO() {}
    
    public DisponibilidadResponseDTO(boolean disponible, String mensaje) {
        this.disponible = disponible;
        this.mensaje = mensaje;
    }
    
    // Getters y Setters
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
