package app.afiliacion.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitud_afiliacion")
public class SolicitudAfiliacionEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
    @JoinColumn(name = "fk_solicitud_inicial_afiliacion", unique = true)
    private SolicitudInicialAfiliacionEntity solicitudInicial;
    
    // DATOS PERSONALES
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;
    
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;
    
    @Column(name = "ci", nullable = false, length = 20)
    private String ci;
    
    @Column(name = "ci_expedido", length = 10)
    private String ciExpedido;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(name = "genero", length = 20)
    private String genero;
    
    @Column(name = "direccion_domicilio", columnDefinition = "TEXT")
    private String direccionDomicilio;
    
    // DOCUMENTOS ADMINISTRATIVOS
    @Column(name = "url_foto_carnet_anverso", length = 500)
    private String urlFotoCarnetAnverso;
    
    @Column(name = "url_foto_carnet_reverso", length = 500)
    private String urlFotoCarnetReverso;
    
    @Column(name = "url_foto_titulo_provisicion", length = 500)
    private String urlFotoTituloProvisicion;
    
    @Column(name = "url_cv", length = 500)
    private String urlCv;
    
    // ESTADOS DEL PROCESO
    @Column(name = "estado_afiliacion", length = 30)
    private String estadoAfiliacion = "EN_REVISION"; // EN_REVISION, OBSERVADO, APROBADO, RECHAZADO
    
    @Column(name = "fecha_revision")
    private LocalDateTime fechaRevision;
    
    // Constructores
    public SolicitudAfiliacionEntity() {}
    
    public SolicitudAfiliacionEntity(Integer id, SolicitudInicialAfiliacionEntity solicitudInicial,
                                    String nombres, String apellidos, String ci, String ciExpedido,
                                    LocalDate fechaNacimiento, String genero, String direccionDomicilio,
                                    String urlFotoCarnetAnverso, String urlFotoCarnetReverso,
                                    String urlFotoTituloProvisicion, String urlCv,
                                    String estadoAfiliacion, LocalDateTime fechaRevision) {
        this.id = id;
        this.solicitudInicial = solicitudInicial;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.ci = ci;
        this.ciExpedido = ciExpedido;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.direccionDomicilio = direccionDomicilio;
        this.urlFotoCarnetAnverso = urlFotoCarnetAnverso;
        this.urlFotoCarnetReverso = urlFotoCarnetReverso;
        this.urlFotoTituloProvisicion = urlFotoTituloProvisicion;
        this.urlCv = urlCv;
        this.estadoAfiliacion = estadoAfiliacion;
        this.fechaRevision = fechaRevision;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public SolicitudInicialAfiliacionEntity getSolicitudInicial() {
        return solicitudInicial;
    }
    
    public void setSolicitudInicial(SolicitudInicialAfiliacionEntity solicitudInicial) {
        this.solicitudInicial = solicitudInicial;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getCi() {
        return ci;
    }
    
    public void setCi(String ci) {
        this.ci = ci;
    }
    
    public String getCiExpedido() {
        return ciExpedido;
    }
    
    public void setCiExpedido(String ciExpedido) {
        this.ciExpedido = ciExpedido;
    }
    
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getDireccionDomicilio() {
        return direccionDomicilio;
    }
    
    public void setDireccionDomicilio(String direccionDomicilio) {
        this.direccionDomicilio = direccionDomicilio;
    }
    
    public String getUrlFotoCarnetAnverso() {
        return urlFotoCarnetAnverso;
    }
    
    public void setUrlFotoCarnetAnverso(String urlFotoCarnetAnverso) {
        this.urlFotoCarnetAnverso = urlFotoCarnetAnverso;
    }
    
    public String getUrlFotoCarnetReverso() {
        return urlFotoCarnetReverso;
    }
    
    public void setUrlFotoCarnetReverso(String urlFotoCarnetReverso) {
        this.urlFotoCarnetReverso = urlFotoCarnetReverso;
    }
    
    public String getUrlFotoTituloProvisicion() {
        return urlFotoTituloProvisicion;
    }
    
    public void setUrlFotoTituloProvisicion(String urlFotoTituloProvisicion) {
        this.urlFotoTituloProvisicion = urlFotoTituloProvisicion;
    }
    
    public String getUrlCv() {
        return urlCv;
    }
    
    public void setUrlCv(String urlCv) {
        this.urlCv = urlCv;
    }
    
    public String getEstadoAfiliacion() {
        return estadoAfiliacion;
    }
    
    public void setEstadoAfiliacion(String estadoAfiliacion) {
        this.estadoAfiliacion = estadoAfiliacion;
    }
    
    public LocalDateTime getFechaRevision() {
        return fechaRevision;
    }
    
    public void setFechaRevision(LocalDateTime fechaRevision) {
        this.fechaRevision = fechaRevision;
    }
}
