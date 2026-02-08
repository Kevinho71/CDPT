package app.perfil.dto;

import java.time.LocalDateTime;

public class SocioDocumentoResponseDTO {
    
    private Integer id;
    private Integer fkPerfilSocio;
    private Integer fkDocumento;
    private String documentoTitulo; // Título del documento asociado
    private String documentoUrl; // URL del documento
    private String documentoTipo; // Tipo de archivo del documento
    private Integer orden;
    private Boolean esVisible;
    private LocalDateTime fechaAsociacion;
    
    // Constructor vacío
    public SocioDocumentoResponseDTO() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getFkPerfilSocio() {
        return fkPerfilSocio;
    }
    
    public void setFkPerfilSocio(Integer fkPerfilSocio) {
        this.fkPerfilSocio = fkPerfilSocio;
    }
    
    public Integer getFkDocumento() {
        return fkDocumento;
    }
    
    public void setFkDocumento(Integer fkDocumento) {
        this.fkDocumento = fkDocumento;
    }
    
    public String getDocumentoTitulo() {
        return documentoTitulo;
    }
    
    public void setDocumentoTitulo(String documentoTitulo) {
        this.documentoTitulo = documentoTitulo;
    }
    
    public String getDocumentoUrl() {
        return documentoUrl;
    }
    
    public void setDocumentoUrl(String documentoUrl) {
        this.documentoUrl = documentoUrl;
    }
    
    public String getDocumentoTipo() {
        return documentoTipo;
    }
    
    public void setDocumentoTipo(String documentoTipo) {
        this.documentoTipo = documentoTipo;
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
    
    public LocalDateTime getFechaAsociacion() {
        return fechaAsociacion;
    }
    
    public void setFechaAsociacion(LocalDateTime fechaAsociacion) {
        this.fechaAsociacion = fechaAsociacion;
    }
}
