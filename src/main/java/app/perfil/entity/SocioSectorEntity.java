package app.perfil.entity;

import app.socio.entity.SocioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "socio_sectores")
public class SocioSectorEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_socio", nullable = false)
    private SocioEntity socio;
    
    @ManyToOne
    @JoinColumn(name = "fk_sector", nullable = false)
    private SectorEntity sector;
    
    @Column(name = "fecha_asignacion")
    private LocalDateTime fechaAsignacion = LocalDateTime.now();
    
    // Constructores
    public SocioSectorEntity() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public SocioEntity getSocio() {
        return socio;
    }
    
    public void setSocio(SocioEntity socio) {
        this.socio = socio;
    }
    
    public SectorEntity getSector() {
        return sector;
    }
    
    public void setSector(SectorEntity sector) {
        this.sector = sector;
    }
    
    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }
    
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
