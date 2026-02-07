package app.documento.dto;

/**
 * DTO para actualizar información de un documento del socio
 * Permite editar título, descripción, visibilidad y orden
 */
public class SocioDocumentoEditDTO {
    
    private String titulo;
    private String descripcion;
    private Boolean esVisible;
    private Integer orden;
    
    // Constructores
    public SocioDocumentoEditDTO() {}
    
    public SocioDocumentoEditDTO(String titulo, String descripcion, Boolean esVisible, Integer orden) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.esVisible = esVisible;
        this.orden = orden;
    }
    
    // Getters y Setters
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
    
    public Boolean getEsVisible() {
        return esVisible;
    }
    
    public void setEsVisible(Boolean esVisible) {
        this.esVisible = esVisible;
    }
    
    public Integer getOrden() {
        return orden;
    }
    
    public void setOrden(Integer orden) {
        this.orden = orden;
    }
}
