package app.afiliacion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SolicitudInicialAfiliacionCreateDTO {
    
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 255, message = "El nombre completo no puede exceder 255 caracteres")
    private String nombreCompleto;
    
    @NotBlank(message = "El celular es obligatorio")
    @Size(max = 20, message = "El celular no puede exceder 20 caracteres")
    private String celular;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    @Size(max = 255, message = "El correo no puede exceder 255 caracteres")
    private String correo;
    
    // Constructor vacío
    public SolicitudInicialAfiliacionCreateDTO() {}
    
    // Constructor con parámetros
    public SolicitudInicialAfiliacionCreateDTO(String nombreCompleto, String celular, String correo) {
        this.nombreCompleto = nombreCompleto;
        this.celular = celular;
        this.correo = correo;
    }
    
    // Getters y Setters
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String getCelular() {
        return celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
