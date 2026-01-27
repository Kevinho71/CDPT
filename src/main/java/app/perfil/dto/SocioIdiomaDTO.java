package app.perfil.dto;

public class SocioIdiomaDTO {
    private Integer idiomaId;
    private String nivel; // Básico, Intermedio, Avanzado, Nativo
    
    public SocioIdiomaDTO() {}
    
    public Integer getIdiomaId() {
        return idiomaId;
    }
    
    public void setIdiomaId(Integer idiomaId) {
        this.idiomaId = idiomaId;
    }
    
    public String getNivel() {
        return nivel;
    }
    
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
