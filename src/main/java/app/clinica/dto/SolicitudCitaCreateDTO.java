package app.clinica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para recibir solicitudes de cita desde la landing page pública.
 */
public class SolicitudCitaCreateDTO {
    
    @NotBlank(message = "El nombre del paciente es obligatorio")
    @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
    private String nombrePaciente;
    
    @NotBlank(message = "El celular es obligatorio")
    @Size(max = 50, message = "El celular no puede exceder 50 caracteres")
    private String celular;
    
    @Email(message = "El email debe ser válido")
    @Size(max = 150, message = "El email no puede exceder 150 caracteres")
    private String email;
    
    private String motivoConsulta;
    
    @Size(max = 20, message = "La modalidad no puede exceder 20 caracteres")
    private String modalidad; // VIRTUAL, PRESENCIAL
    
    // Constructores
    public SolicitudCitaCreateDTO() {}
    
    public SolicitudCitaCreateDTO(String nombrePaciente, String celular, String email, 
                                  String motivoConsulta, String modalidad) {
        this.nombrePaciente = nombrePaciente;
        this.celular = celular;
        this.email = email;
        this.motivoConsulta = motivoConsulta;
        this.modalidad = modalidad;
    }
    
    // Getters y Setters
    public String getNombrePaciente() {
        return nombrePaciente;
    }
    
    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }
    
    public String getCelular() {
        return celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMotivoConsulta() {
        return motivoConsulta;
    }
    
    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }
    
    public String getModalidad() {
        return modalidad;
    }
    
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
}
