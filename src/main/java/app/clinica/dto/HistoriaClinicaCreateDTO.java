package app.clinica.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class HistoriaClinicaCreateDTO {
    
    @NotNull(message = "El ID del paciente es obligatorio")
    private Integer fkPaciente;
    
    private Integer fkCita; // Opcional: puede ser una consulta sin cita previa
    
    @NotNull(message = "La fecha de consulta es obligatoria")
    private LocalDate fechaConsulta;
    
    private String motivoConsulta;
    
    private String observaciones;
    
    private String diagnostico;
    
    private String tratamientoPlan;
    
    private String evolucion;
    
    private String archivosAdjuntos; // URLs separadas por coma
    
    // Constructor vacío
    public HistoriaClinicaCreateDTO() {}
    
    // Getters y Setters
    public Integer getFkPaciente() {
        return fkPaciente;
    }
    
    public void setFkPaciente(Integer fkPaciente) {
        this.fkPaciente = fkPaciente;
    }
    
    public Integer getFkCita() {
        return fkCita;
    }
    
    public void setFkCita(Integer fkCita) {
        this.fkCita = fkCita;
    }
    
    public LocalDate getFechaConsulta() {
        return fechaConsulta;
    }
    
    public void setFechaConsulta(LocalDate fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }
    
    public String getMotivoConsulta() {
        return motivoConsulta;
    }
    
    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public String getDiagnostico() {
        return diagnostico;
    }
    
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    
    public String getTratamientoPlan() {
        return tratamientoPlan;
    }
    
    public void setTratamientoPlan(String tratamientoPlan) {
        this.tratamientoPlan = tratamientoPlan;
    }
    
    public String getEvolucion() {
        return evolucion;
    }
    
    public void setEvolucion(String evolucion) {
        this.evolucion = evolucion;
    }
    
    public String getArchivosAdjuntos() {
        return archivosAdjuntos;
    }
    
    public void setArchivosAdjuntos(String archivosAdjuntos) {
        this.archivosAdjuntos = archivosAdjuntos;
    }
}
