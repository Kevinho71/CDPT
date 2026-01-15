package app.perfil.entity;

import app.socio.entity.SocioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "socio_servicios")
public class SocioServicioEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_socio", nullable = false)
    private SocioEntity socio;
    
    @ManyToOne
    @JoinColumn(name = "fk_servicio", nullable = false)
    private ServicioEntity servicio;
    
    @Column(name = "fecha_asignacion")
    private LocalDateTime fechaAsignacion = LocalDateTime.now();
    
    // Constructores
    public SocioServicioEntity() {}
    
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
    
    public ServicioEntity getServicio() {
        return servicio;
    }
    
    public void setServicio(ServicioEntity servicio) {
        this.servicio = servicio;
    }
    
    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }
    
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
