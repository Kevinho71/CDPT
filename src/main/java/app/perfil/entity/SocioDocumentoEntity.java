package app.perfil.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "socio_documentos")
public class SocioDocumentoEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_perfil_socio", nullable = false)
    private PerfilSocioEntity perfilSocio;
    
    @ManyToOne
    @JoinColumn(name = "fk_documento", nullable = false)
    private DocumentoProfesionalEntity documento;
    
    @Column(name = "orden")
    private Integer orden = 0; // Para ordenar documentos
    
    @Column(name = "es_visible")
    private Boolean esVisible = true; // Permite ocultarlo sin borrar la relación
    
    @Column(name = "fecha_asociacion")
    private LocalDateTime fechaAsociacion = LocalDateTime.now();
    
    // Constructores
    public SocioDocumentoEntity() {}
    
    public SocioDocumentoEntity(Integer id, PerfilSocioEntity perfilSocio, 
                               DocumentoProfesionalEntity documento, Integer orden,
                               Boolean esVisible, LocalDateTime fechaAsociacion) {
        this.id = id;
        this.perfilSocio = perfilSocio;
        this.documento = documento;
        this.orden = orden;
        this.esVisible = esVisible;
        this.fechaAsociacion = fechaAsociacion;
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
    
    public DocumentoProfesionalEntity getDocumento() {
        return documento;
    }
    
    public void setDocumento(DocumentoProfesionalEntity documento) {
        this.documento = documento;
    }
    
    public Integer getOrden() {
        return orden;
    }
    
    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    
    public Boolean getEsVisible() {
        return esVisible;
    }
    
    public void setEsVisible(Boolean esVisible) {
        this.esVisible = esVisible;
    }
    
    public LocalDateTime getFechaAsociacion() {
        return fechaAsociacion;
    }
    
    public void setFechaAsociacion(LocalDateTime fechaAsociacion) {
        this.fechaAsociacion = fechaAsociacion;
    }
}
