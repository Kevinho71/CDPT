package app.perfil.entity;

import app.socio.entity.SocioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "socio_especialidades")
public class SocioEspecialidadEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_socio", nullable = false)
    private SocioEntity socio;
    
    @ManyToOne
    @JoinColumn(name = "fk_especialidad", nullable = false)
    private EspecialidadEntity especialidad;
    
    @Column(name = "fecha_asignacion")
    private LocalDateTime fechaAsignacion = LocalDateTime.now();
    
    // Constructores
    public SocioEspecialidadEntity() {}
    
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
    
    public EspecialidadEntity getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(EspecialidadEntity especialidad) {
        this.especialidad = especialidad;
    }
    
    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }
    
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
