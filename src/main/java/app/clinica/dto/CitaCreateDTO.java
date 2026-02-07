package app.clinica.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class CitaCreateDTO {
    
    @NotNull(message = "El ID del perfil del socio es obligatorio")
    private Integer fkPerfilSocio;
    
    @NotNull(message = "El ID del paciente es obligatorio")
    private Integer fkPaciente;
    
    @NotNull(message = "La fecha de la cita es obligatoria")
    private LocalDate fechaCita;
    
    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;
    
    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;
    
    @Size(max = 20, message = "La modalidad no puede exceder 20 caracteres")
    private String modalidad; // PRESENCIAL, VIRTUAL, DOMICILIO
    
    @Size(max = 50, message = "El tipo de sesión no puede exceder 50 caracteres")
    private String tipoSesion; // PRIMERA_CONSULTA, TERAPIA, EVALUACION
    
    @Size(max = 255, message = "El motivo breve no puede exceder 255 caracteres")
    private String motivoBreve;
    

    private BigDecimal montoAcordado;
    
    public BigDecimal getMontoAcordado() {
        return montoAcordado;
    }

    public void setMontoAcordado(BigDecimal montoAcordado) {
        this.montoAcordado = montoAcordado;
    }

    private String notasInternas;
    
    // Constructor vacío
    public CitaCreateDTO() {}
    
    // Getters y Setters
    public Integer getFkPerfilSocio() {
        return fkPerfilSocio;
    }
    
    public void setFkPerfilSocio(Integer fkPerfilSocio) {
        this.fkPerfilSocio = fkPerfilSocio;
    }
    
    public Integer getFkPaciente() {
        return fkPaciente;
    }
    
    public void setFkPaciente(Integer fkPaciente) {
        this.fkPaciente = fkPaciente;
    }
    
    public LocalDate getFechaCita() {
        return fechaCita;
    }
    
    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }
    
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    public LocalTime getHoraFin() {
        return horaFin;
    }
    
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
    
    public String getModalidad() {
        return modalidad;
    }
    
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
    
    public String getTipoSesion() {
        return tipoSesion;
    }
    
    public void setTipoSesion(String tipoSesion) {
        this.tipoSesion = tipoSesion;
    }
    
    public String getMotivoBreve() {
        return motivoBreve;
    }
    
    public void setMotivoBreve(String motivoBreve) {
        this.motivoBreve = motivoBreve;
    }
    
    public String getNotasInternas() {
        return notasInternas;
    }
    
    public void setNotasInternas(String notasInternas) {
        this.notasInternas = notasInternas;
    }
}
