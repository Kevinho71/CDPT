package app.documento.entity;

import app.socio.entity.SocioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "documentos")
public class DocumentoEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_socio", nullable = false)
    private SocioEntity socio;
    
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    
    @Column(name = "tipo", length = 50)
    private String tipo; // 'requisito', 'certificado', 'factura', 'otro'
    
    @Column(name = "archivo_url", nullable = false, length = 500)
    private String archivoUrl;
    
    @Column(name = "mime_type", length = 100)
    private String mimeType; // 'application/pdf', 'image/jpeg'
    
    @Column(name = "estado")
    private Integer estado = 1; // 1: Activo, 2: En revisión
    
    @Column(name = "fecha_subida")
    private LocalDateTime fechaSubida = LocalDateTime.now();
    
    // Constructores
    public DocumentoEntity() {}
    
    public DocumentoEntity(Integer id, SocioEntity socio, String nombre, String tipo, 
                          String archivoUrl, String mimeType, Integer estado, LocalDateTime fechaSubida) {
        this.id = id;
        this.socio = socio;
        this.nombre = nombre;
        this.tipo = tipo;
        this.archivoUrl = archivoUrl;
        this.mimeType = mimeType;
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
    
    public SocioEntity getSocio() {
        return socio;
    }
    
    public void setSocio(SocioEntity socio) {
        this.socio = socio;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getArchivoUrl() {
        return archivoUrl;
    }
    
    public void setArchivoUrl(String archivoUrl) {
        this.archivoUrl = archivoUrl;
    }
    
    public String getMimeType() {
        return mimeType;
    }
    
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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
