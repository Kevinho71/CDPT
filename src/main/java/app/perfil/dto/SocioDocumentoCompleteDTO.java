package app.perfil.dto;

import java.time.LocalDateTime;

/**
 * DTO completo para respuesta de documentos del socio
 * Incluye toda la información del documento y la relación
 */
public class SocioDocumentoCompleteDTO {
    
    private Integer id; // ID de la relación socio_documentos
    private Integer documentoId; // ID del documento profesional
    private String titulo;
    private String descripcion;
    private String archivoUrl;
    private String tipoArchivo;
    private Integer orden;
    private Boolean esVisible;
    private LocalDateTime fechaSubida;
    private LocalDateTime fechaAsociacion;
    
    // Constructores
    public SocioDocumentoCompleteDTO() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getDocumentoId() {
        return documentoId;
    }
    
    public void setDocumentoId(Integer documentoId) {
        this.documentoId = documentoId;
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
    
    public Integer getOrden() {
        return orden;
    }
    
    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    
    public Boolean getEsVisible() {
        return esVisible;
    }
    
    public void setEsVisible(Boolean esVisible) {
        this.esVisible = esVisible;
    }
    
    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }
    
    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }
    
    public LocalDateTime getFechaAsociacion() {
        return fechaAsociacion;
    }
    
    public void setFechaAsociacion(LocalDateTime fechaAsociacion) {
        this.fechaAsociacion = fechaAsociacion;
    }
}
