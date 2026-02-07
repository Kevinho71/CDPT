package app.clinica.dto;

import java.time.LocalDate;

public class HistoriaClinicaUpdateDTO {
    
    private LocalDate fechaConsulta;
    
    private String motivoConsulta;
    
    private String observaciones;
    
    private String diagnostico;
    
    private String tratamientoPlan;
    
    private String evolucion;
    
    private String archivosAdjuntos;
    
    // Constructor vacío
    public HistoriaClinicaUpdateDTO() {}
    
    // Getters y Setters
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
