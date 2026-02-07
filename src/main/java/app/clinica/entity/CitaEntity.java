package app.clinica.entity;

import app.perfil.entity.PerfilSocioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "cita")
public class CitaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_perfil_socio", nullable = false)
    private PerfilSocioEntity perfilSocio;
    
    @ManyToOne
    @JoinColumn(name = "fk_paciente", nullable = false)
    private PacienteEntity paciente;
    
    @Column(name = "fecha_cita", nullable = false)
    private LocalDate fechaCita;
    
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;
    
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;
    
    @Column(name = "modalidad", length = 20)
    private String modalidad; // PRESENCIAL, VIRTUAL, DOMICILIO
    
    @Column(name = "tipo_sesion", length = 50)
    private String tipoSesion; // PRIMERA_CONSULTA, TERAPIA, EVALUACION
    
    @Column(name = "estado_cita", length = 20)
    private String estadoCita = "PROGRAMADA"; // PROGRAMADA, REALIZADA, CANCELADA, NO_ASISTIO
    
    @Column(name = "motivo_breve", length = 255)
    private String motivoBreve;
    
    @Column(name = "notas_internas", columnDefinition = "TEXT")
    private String notasInternas;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    // Constructores
    public CitaEntity() {}
    
    public CitaEntity(Integer id, PerfilSocioEntity perfilSocio, PacienteEntity paciente,
                     LocalDate fechaCita, LocalTime horaInicio, LocalTime horaFin,
                     String modalidad, String tipoSesion, String estadoCita,
                     String motivoBreve, String notasInternas, LocalDateTime fechaCreacion) {
        this.id = id;
        this.perfilSocio = perfilSocio;
        this.paciente = paciente;
        this.fechaCita = fechaCita;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.modalidad = modalidad;
        this.tipoSesion = tipoSesion;
        this.estadoCita = estadoCita;
        this.motivoBreve = motivoBreve;
        this.notasInternas = notasInternas;
        this.fechaCreacion = fechaCreacion;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public PerfilSocioEntity getPerfilSocio() {
        return perfilSocio;
    }
    
    public void setPerfilSocio(PerfilSocioEntity perfilSocio) {
        this.perfilSocio = perfilSocio;
    }
    
    public PacienteEntity getPaciente() {
        return paciente;
    }
    
    public void setPaciente(PacienteEntity paciente) {
        this.paciente = paciente;
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
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
