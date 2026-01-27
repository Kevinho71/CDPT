package app.perfil.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificaciones")
public class CertificacionEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_perfil_socio", nullable = false)
    private PerfilSocioEntity perfilSocio;
    
    @Column(name = "nombre", nullable = false, length = 300)
    private String nombre;
    
    @Column(name = "institucion_emisora", length = 300)
    private String institucionEmisora;
    
    @Column(name = "url_verificacion", length = 500)
    private String urlVerificacion;
    
    @Column(name = "orden")
    private Integer orden = 0;
    
    @Column(name = "estado")
    private Integer estado = 1;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    // Constructores
    public CertificacionEntity() {}
    
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
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getInstitucionEmisora() {
        return institucionEmisora;
    }
    
    public void setInstitucionEmisora(String institucionEmisora) {
        this.institucionEmisora = institucionEmisora;
    }
    
    public String getUrlVerificacion() {
        return urlVerificacion;
    }
    
    public void setUrlVerificacion(String urlVerificacion) {
        this.urlVerificacion = urlVerificacion;
    }
    
    public Integer getOrden() {
        return orden;
    }
    
    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
