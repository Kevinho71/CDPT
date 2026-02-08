package app.documento.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "documento_profesional")
public class DocumentoProfesionalEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo; // Ej: "Título de Maestría en Clínica"
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "archivo_url", nullable = false, length = 500)
    private String archivoUrl; // URL de Cloudinary o local
    
    @Column(name = "tipo_archivo", length = 50)
    private String tipoArchivo; // PDF, JPG, PNG
    
    @Column(name = "estado")
    private Integer estado = 1;
    
    @Column(name = "fecha_subida")
    private LocalDateTime fechaSubida = LocalDateTime.now();
    
    // Constructores
    public DocumentoProfesionalEntity() {}
    
    public DocumentoProfesionalEntity(Integer id, String titulo, String descripcion,
                                     String archivoUrl, String tipoArchivo, Integer estado,
                                     LocalDateTime fechaSubida) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.archivoUrl = archivoUrl;
        this.tipoArchivo = tipoArchivo;
        this.estado = estado;
        this.fechaSubida = fechaSubida;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getArchivoUrl() {
        return archivoUrl;
    }
    
    public void setArchivoUrl(String archivoUrl) {
        this.archivoUrl = archivoUrl;
    }
    
    public String getTipoArchivo() {
        return tipoArchivo;
    }
    
    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }
    
    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }
}
