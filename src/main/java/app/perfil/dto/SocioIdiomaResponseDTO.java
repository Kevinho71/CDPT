package app.perfil.dto;

public class SocioIdiomaResponseDTO {
    private Integer id;
    private IdiomaResponseDTO idioma;
    private String nivel;
    
    public SocioIdiomaResponseDTO() {}
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public IdiomaResponseDTO getIdioma() {
        return idioma;
    }
    
    public void setIdioma(IdiomaResponseDTO idioma) {
        this.idioma = idioma;
    }
    
    public String getNivel() {
        return nivel;
    }
    
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
