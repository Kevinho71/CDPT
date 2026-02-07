package app.clinica.entity;

import app.perfil.entity.PerfilSocioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entidad que representa las solicitudes de cita recibidas desde la landing page.
 * Es la "bandeja de entrada" del psicólogo con leads de pacientes potenciales.
 */
@Entity
@Table(name = "solicitud_cita")
public class SolicitudCitaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_perfil_socio", nullable = false)
    private PerfilSocioEntity perfilSocio;
    
    // Datos del interesado (visitante)
    @Column(name = "nombre_paciente", nullable = false, length = 150)
    private String nombrePaciente;
    
    @Column(name = "celular", nullable = false, length = 50)
    private String celular; // CRÍTICO: Para botón de WhatsApp
    
    @Column(name = "email", length = 150)
    private String email;
    
    // Detalles de la intención
    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;
    
    @Column(name = "modalidad", length = 20)
    private String modalidad; // VIRTUAL, PRESENCIAL
    
    /**
     * Gestión del Lead:
     * - PENDIENTE: El psicólogo no lo ha visto
     * - CONTACTADO: El psicólogo ya abrió el WhatsApp
     * - CONVERTIDO: Se volvió una cita real
     * - DESCARTADO: No contestó o no interesó
     */
    @Column(name = "estado", length = 30)
    private String estado = "PENDIENTE";
    
    @Column(name = "nota_interna", columnDefinition = "TEXT")
    private String notaInterna; // "Le escribí y quedamos en hablar el lunes"
    
    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud = LocalDateTime.now();
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Constructores
    public SolicitudCitaEntity() {}
    
    public SolicitudCitaEntity(PerfilSocioEntity perfilSocio, String nombrePaciente, 
                              String celular, String email, String motivoConsulta, 
                              String modalidad) {
        this.perfilSocio = perfilSocio;
        this.nombrePaciente = nombrePaciente;
        this.celular = celular;
        this.email = email;
        this.motivoConsulta = motivoConsulta;
        this.modalidad = modalidad;
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
