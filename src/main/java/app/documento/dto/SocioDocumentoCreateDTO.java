package app.documento.dto;

import jakarta.validation.constraints.NotNull;

public class SocioDocumentoCreateDTO {
    
    @NotNull(message = "El ID del perfil del socio es obligatorio")
    private Integer fkPerfilSocio;
    
    @NotNull(message = "El ID del documento es obligatorio")
    private Integer fkDocumento;
    
    private Integer orden = 0;
    
    private Boolean esVisible = true;
    
    // Constructor vacío
    public SocioDocumentoCreateDTO() {}
    
    // Getters y Setters
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
