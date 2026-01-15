package app.publicacion.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "publicacion_imagenes")
public class PublicacionImagenEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_publicacion", nullable = false)
    private PublicacionEntity publicacion;
    
    @Column(name = "imagen_url", nullable = false, length = 500)
    private String imagenUrl;
    
    @Column(name = "es_portada")
    private Boolean esPortada = false; // true para la imagen principal
    
    @Column(name = "orden")
    private Integer orden = 0; // Para ordenar la galería
    
    // Constructores
    public PublicacionImagenEntity() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public PublicacionEntity getPublicacion() {
        return publicacion;
    }
    
    public void setPublicacion(PublicacionEntity publicacion) {
        this.publicacion = publicacion;
    }
    
    public String getImagenUrl() {
        return imagenUrl;
    }
    
    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
    
    public Boolean getEsPortada() {
        return esPortada;
    }
    
    public void setEsPortada(Boolean esPortada) {
        this.esPortada = esPortada;
    }
    
    public Integer getOrden() {
        return orden;
    }
    
    public void setOrden(Integer orden) {
        this.orden = orden;
    }
}
