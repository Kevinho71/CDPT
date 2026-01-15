package app.perfil.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "socio_idiomas")
public class SocioIdiomaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_perfil_socio", nullable = false)
    private PerfilSocioEntity perfilSocio;
    
    @ManyToOne
    @JoinColumn(name = "fk_idioma", nullable = false)
    private IdiomaEntity idioma;
    
    @Column(name = "nivel", nullable = false, length = 50)
    private String nivel; // basico, intermedio, avanzado, nativo
    
    @Column(name = "orden")
    private Integer orden = 0;
    
    @Column(name = "estado")
    private Integer estado = 1;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    // Constructores
    public SocioIdiomaEntity() {}
    
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
    
    public IdiomaEntity getIdioma() {
        return idioma;
    }
    
    public void setIdioma(IdiomaEntity idioma) {
        this.idioma = idioma;
    }
    
    public String getNivel() {
        return nivel;
    }
    
    public void setNivel(String nivel) {
        this.nivel = nivel;
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
