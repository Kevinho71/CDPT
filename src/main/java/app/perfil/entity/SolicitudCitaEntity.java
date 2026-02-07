package app.perfil.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    
    @Column(name = "nombre_paciente", nullable = false, length = 150)
    private String nombrePaciente;
    
    @Column(name = "celular", nullable = false, length = 50)
    private String celular; // Fundamental para WhatsApp
    
    @Column(name = "email", length = 150)
    private String email;
    
    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;
    
    @Column(name = "modalidad", length = 20)
    private String modalidad; // VIRTUAL, PRESENCIAL
    
    @Column(name = "estado", length = 30)
    private String estado = "PENDIENTE"; // PENDIENTE, CONTACTADO, CONVERTIDO, ARCHIVADO
    
    @Column(name = "nota_interna", columnDefinition = "TEXT")
    private String notaInterna;
    
    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud = LocalDateTime.now();
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Constructores
    public SolicitudCitaEntity() {}
    
    public SolicitudCitaEntity(Integer id, PerfilSocioEntity perfilSocio, String nombrePaciente,
                              String celular, String email, String motivoConsulta, String modalidad,
                              String estado, String notaInterna, LocalDateTime fechaSolicitud,
                              LocalDateTime fechaActualizacion) {
        this.id = id;
        this.perfilSocio = perfilSocio;
        this.nombrePaciente = nombrePaciente;
        this.celular = celular;
        this.email = email;
        this.motivoConsulta = motivoConsulta;
        this.modalidad = modalidad;
        this.estado = estado;
        this.notaInterna = notaInterna;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaActualizacion = fechaActualizacion;
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
