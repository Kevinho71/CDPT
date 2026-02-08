package app.perfil.dto;

public class SocioDocumentoUpdateDTO {
    
    private Integer orden;
    
    private Boolean esVisible;
    
    // Constructor vacío
    public SocioDocumentoUpdateDTO() {}
    
    // Getters y Setters
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
}
