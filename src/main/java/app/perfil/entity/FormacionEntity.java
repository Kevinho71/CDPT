package app.perfil.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "formacion")
public class FormacionEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_perfil_socio", nullable = false)
    private PerfilSocioEntity perfilSocio;
    
    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo; // 'universitaria', 'postgrado', 'diplomado', 'curso'
    
    @Column(name = "titulo", nullable = false, length = 300)
    private String titulo;
    
    @Column(name = "institucion", length = 300)
    private String institucion;
    
    @Column(name = "pais", length = 100)
    private String pais;
    
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
    @Column(name = "en_curso")
    private Boolean enCurso = false;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "orden")
    private Integer orden = 0;
    
    @Column(name = "estado")
    private Integer estado = 1;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    // Constructores
    public FormacionEntity() {}
    
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
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getInstitucion() {
        return institucion;
    }
    
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }
    
    public String getPais() {
        return pais;
    }
    
    public void setPais(String pais) {
        this.pais = pais;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public Boolean getEnCurso() {
        return enCurso;
    }
    
    public void setEnCurso(Boolean enCurso) {
        this.enCurso = enCurso;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
