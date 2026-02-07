package app.clinica.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "historia_clinica")
public class HistoriaClinicaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_paciente", nullable = false)
    private PacienteEntity paciente;
    
    @ManyToOne
    @JoinColumn(name = "fk_cita")
    private CitaEntity cita;
    
    @Column(name = "fecha_consulta", nullable = false)
    private LocalDate fechaConsulta;
    
    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "diagnostico", columnDefinition = "TEXT")
    private String diagnostico;
    
    @Column(name = "tratamiento_plan", columnDefinition = "TEXT")
    private String tratamientoPlan;
    
    @Column(name = "evolucion", columnDefinition = "TEXT")
    private String evolucion;
    
    @Column(name = "archivos_adjuntos", length = 1000)
    private String archivosAdjuntos; // URLs separadas por coma
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();
    
    // Constructores
    public HistoriaClinicaEntity() {}
    
    public HistoriaClinicaEntity(Integer id, PacienteEntity paciente, CitaEntity cita,
                                LocalDate fechaConsulta, String motivoConsulta, String observaciones,
                                String diagnostico, String tratamientoPlan, String evolucion,
                                String archivosAdjuntos, LocalDateTime fechaCreacion,
                                LocalDateTime fechaActualizacion) {
        this.id = id;
        this.paciente = paciente;
        this.cita = cita;
        this.fechaConsulta = fechaConsulta;
        this.motivoConsulta = motivoConsulta;
        this.observaciones = observaciones;
        this.diagnostico = diagnostico;
        this.tratamientoPlan = tratamientoPlan;
        this.evolucion = evolucion;
        this.archivosAdjuntos = archivosAdjuntos;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public PacienteEntity getPaciente() {
        return paciente;
    }
    
    public void setPaciente(PacienteEntity paciente) {
        this.paciente = paciente;
    }
    
    public CitaEntity getCita() {
        return cita;
    }
    
    public void setCita(CitaEntity cita) {
        this.cita = cita;
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
