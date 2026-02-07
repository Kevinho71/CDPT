package app.afiliacion.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitud_inicial_afiliacion")
public class SolicitudInicialAfiliacionEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre_completo", nullable = false, length = 255)
    private String nombreCompleto;
    
    @Column(name = "celular", nullable = false, length = 20)
    private String celular;
    
    @Column(name = "correo", nullable = false, length = 255)
    private String correo;
    
    @Column(name = "token_acceso", unique = true, length = 100)
    private String tokenAcceso;
    
    @Column(name = "token_expiracion")
    private LocalDateTime tokenExpiracion;
    
    @Column(name = "estado", length = 20)
    private String estado = "PENDIENTE"; // PENDIENTE, CONTACTADO, LINK_ENVIADO, FINALIZADO
    
    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud = LocalDateTime.now();
    
    @Column(name = "notas_admin", columnDefinition = "TEXT")
    private String notasAdmin;
    
    // Constructores
    public SolicitudInicialAfiliacionEntity() {}
    
    public SolicitudInicialAfiliacionEntity(Integer id, String nombreCompleto, String celular, 
                                           String correo, String tokenAcceso, LocalDateTime tokenExpiracion,
                                           String estado, LocalDateTime fechaSolicitud, String notasAdmin) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.celular = celular;
        this.correo = correo;
        this.tokenAcceso = tokenAcceso;
        this.tokenExpiracion = tokenExpiracion;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
        this.notasAdmin = notasAdmin;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String getCelular() {
        return celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getTokenAcceso() {
        return tokenAcceso;
    }
    
    public void setTokenAcceso(String tokenAcceso) {
        this.tokenAcceso = tokenAcceso;
    }
    
    public LocalDateTime getTokenExpiracion() {
        return tokenExpiracion;
    }
    
    public void setTokenExpiracion(LocalDateTime tokenExpiracion) {
        this.tokenExpiracion = tokenExpiracion;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }
    
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    
    public String getNotasAdmin() {
        return notasAdmin;
    }
    
    public void setNotasAdmin(String notasAdmin) {
        this.notasAdmin = notasAdmin;
    }
}
