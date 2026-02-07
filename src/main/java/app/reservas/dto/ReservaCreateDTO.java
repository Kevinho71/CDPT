package app.reservas.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para crear una reserva.
 * El sistema calcula automáticamente el monto basándose en el precio_hora del ambiente.
 */
public class ReservaCreateDTO {
    
    @NotNull(message = "El ID del ambiente es obligatorio")
    private Integer ambienteId;
    
    @NotNull(message = "El ID del socio es obligatorio")
    private Integer socioId;
    
    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDate fechaReserva;
    
    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;
    
    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;
    
    // Constructores
    public ReservaCreateDTO() {}
    
    public ReservaCreateDTO(Integer ambienteId, Integer socioId, LocalDate fechaReserva,
                           LocalTime horaInicio, LocalTime horaFin) {
        this.ambienteId = ambienteId;
        this.socioId = socioId;
        this.fechaReserva = fechaReserva;
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
    
    public Integer getSocioId() {
        return socioId;
    }
    
    public void setSocioId(Integer socioId) {
        this.socioId = socioId;
    }
    
    public LocalDate getFechaReserva() {
        return fechaReserva;
    }
    
    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
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
