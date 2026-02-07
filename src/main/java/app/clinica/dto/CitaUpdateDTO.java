package app.clinica.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

public class CitaUpdateDTO {
    
    private LocalDate fechaCita;
    
    private LocalTime horaInicio;
    
    private LocalTime horaFin;
    
    @Size(max = 20, message = "La modalidad no puede exceder 20 caracteres")
    private String modalidad;
    
    @Size(max = 50, message = "El tipo de sesión no puede exceder 50 caracteres")
    private String tipoSesion;
    
    @Size(max = 20, message = "El estado de la cita no puede exceder 20 caracteres")
    private String estadoCita; // PROGRAMADA, REALIZADA, CANCELADA, NO_ASISTIO
    
    @Size(max = 255, message = "El motivo breve no puede exceder 255 caracteres")
    private String motivoBreve;
    
    private String notasInternas;
    
    // Constructor vacío
    public CitaUpdateDTO() {}
    
    // Getters y Setters
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
    
    public String getEstadoCita() {
        return estadoCita;
    }
    
    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
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
