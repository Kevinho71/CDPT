package app.reservas.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para verificar disponibilidad de un ambiente.
 */
public class DisponibilidadRequestDTO {
    
    private Integer ambienteId;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    
    // Constructores
    public DisponibilidadRequestDTO() {}
    
    public DisponibilidadRequestDTO(Integer ambienteId, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        this.ambienteId = ambienteId;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
    
    // Getters y Setters
    public Integer getAmbienteId() {
        return ambienteId;
    }
    
    public void setAmbienteId(Integer ambienteId) {
        this.ambienteId = ambienteId;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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
}
