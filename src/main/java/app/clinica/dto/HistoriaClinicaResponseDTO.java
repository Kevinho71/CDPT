package app.clinica.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HistoriaClinicaResponseDTO {
    
    private Integer id;
    private Integer fkPaciente;
    private String pacienteNombre; // Nombre completo del paciente
    private Integer fkCita;
    private LocalDate fechaConsulta;
    private String motivoConsulta;
    private String observaciones;
    private String diagnostico;
    private String tratamientoPlan;
    private String evolucion;
    private String archivosAdjuntos;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    
    // Constructor vacío
    public HistoriaClinicaResponseDTO() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getFkPaciente() {
        return fkPaciente;
    }
    
    public void setFkPaciente(Integer fkPaciente) {
        this.fkPaciente = fkPaciente;
    }
    
    public String getPacienteNombre() {
        return pacienteNombre;
    }
    
    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
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
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
