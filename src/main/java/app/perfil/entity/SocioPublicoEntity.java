package app.perfil.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "socio_publico")
public class SocioPublicoEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_perfil_socio", nullable = false)
    private PerfilSocioEntity perfilSocio;
    
    @ManyToOne
    @JoinColumn(name = "fk_publico_objetivo", nullable = false)
    private PublicoObjetivoEntity publicoObjetivo;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    // Constructores
    public SocioPublicoEntity() {}
    
    public SocioPublicoEntity(Integer id, PerfilSocioEntity perfilSocio, 
                             PublicoObjetivoEntity publicoObjetivo, LocalDateTime fechaCreacion) {
        this.id = id;
        this.perfilSocio = perfilSocio;
        this.publicoObjetivo = publicoObjetivo;
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
    
    public PublicoObjetivoEntity getPublicoObjetivo() {
        return publicoObjetivo;
    }
    
    public void setPublicoObjetivo(PublicoObjetivoEntity publicoObjetivo) {
        this.publicoObjetivo = publicoObjetivo;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
