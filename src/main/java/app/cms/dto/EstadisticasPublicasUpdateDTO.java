package app.cms.dto;

import jakarta.validation.constraints.Size;

public class EstadisticasPublicasUpdateDTO {
    
    @Size(max = 100, message = "El título no puede exceder 100 caracteres")
    private String titulo;
    
    @Size(max = 500, message = "El valor no puede exceder 500 caracteres")
    private String valor;
    
    @Size(max = 100, message = "El icono no puede exceder 100 caracteres")
    private String icono;
    
    private String descripcion;
    
    private Integer orden;
    
    private Boolean visible;
    
    private Integer estado;
    
    // Constructor vacío
    public EstadisticasPublicasUpdateDTO() {}
    
    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getValor() {
        return valor;
    }
    
    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public String getIcono() {
        return icono;
    }
    
    public void setIcono(String icono) {
        this.icono = icono;
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
    
    public Boolean getVisible() {
        return visible;
    }
    
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
