package app.clinica.dto;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para solicitudes de cita.
 */
public class SolicitudCitaResponseDTO {
    
    private Integer id;
    private Integer perfilSocioId;
    private String nombrePsicologo;
    private String nombrePaciente;
    private String celular;
    private String email;
    private String motivoConsulta;
    private String modalidad;
    private String estado;
    private String notaInterna;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaActualizacion;
    
    // Constructores
    public SolicitudCitaResponseDTO() {}
    
    public SolicitudCitaResponseDTO(Integer id, Integer perfilSocioId, String nombrePsicologo,
                                   String nombrePaciente, String celular, String email,
                                   String motivoConsulta, String modalidad, String estado,
                                   String notaInterna, LocalDateTime fechaSolicitud,
                                   LocalDateTime fechaActualizacion) {
        this.id = id;
        this.perfilSocioId = perfilSocioId;
        this.nombrePsicologo = nombrePsicologo;
        this.nombrePaciente = nombrePaciente;
        this.celular = celular;
        this.email = email;
        this.motivoConsulta = motivoConsulta;
        this.modalidad = modalidad;
        this.estado = estado;
        this.notaInterna = notaInterna;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaActualizacion = fechaActualizacion;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getPerfilSocioId() {
        return perfilSocioId;
    }
    
    public void setPerfilSocioId(Integer perfilSocioId) {
        this.perfilSocioId = perfilSocioId;
    }
    
    public String getNombrePsicologo() {
        return nombrePsicologo;
    }
    
    public void setNombrePsicologo(String nombrePsicologo) {
        this.nombrePsicologo = nombrePsicologo;
    }
    
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
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getNotaInterna() {
        return notaInterna;
    }
    
    public void setNotaInterna(String notaInterna) {
        this.notaInterna = notaInterna;
    }
    
    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }
    
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
