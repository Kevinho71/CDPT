package app.reservas.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entidad que representa un espacio físico disponible para reservar.
 * Ejemplos: Consultorio 1, Auditorio, Churrasquera, Sala de Reuniones.
 */
@Entity
@Table(name = "ambiente")
public class AmbienteEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre; // "Consultorio 1", "Auditorio Principal"
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion; // "Consultorio equipado con diván, 20m²"
    
    /**
     * Precio por hora de uso del ambiente.
     * El sistema calcula automáticamente el costo total basándose en:
     * monto_total = precio_hora * (hora_fin - hora_inicio)
     */
    @Column(name = "precio_hora", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioHora;
    
    @Column(name = "estado")
    private Integer estado = 1; // 1: Activo, 0: Inactivo/En mantenimiento
    
    // Constructores
    public AmbienteEntity() {}
    
    public AmbienteEntity(Integer id, String nombre, String descripcion, 
                         BigDecimal precioHora, Integer estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioHora = precioHora;
        this.estado = estado;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public BigDecimal getPrecioHora() {
        return precioHora;
    }
    
    public void setPrecioHora(BigDecimal precioHora) {
        this.precioHora = precioHora;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
