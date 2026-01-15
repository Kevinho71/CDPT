package app.catalogo.dto;

import java.util.List;

public class CatalogoResponseDTO {
    
    private Integer id;
    private Integer codigo;
    private String nit;
    private String nombre;
    private String descripcion;
    private String direccion;
    private List<String> descuentos; // Array JSON de descuentos
    private String tipo;
    private String longitud;
    private String latitud;
    private Integer estado;
    private String logoUrl; // URL del logo
    private List<String> imagenesUrls; // URLs de imágenes adicionales
    
    // Constructores
    public CatalogoResponseDTO() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
    public String getNit() {
        return nit;
    }
    
    public void setNit(String nit) {
        this.nit = nit;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public List<String> getDescuentos() {
        return descuentos;
    }
    
    public void setDescuentos(List<String> descuentos) {
        this.descuentos = descuentos;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getLongitud() {
        return longitud;
    }
    
    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
    
    public String getLatitud() {
        return latitud;
    }
    
    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    public String getLogoUrl() {
        return logoUrl;
    }
    
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
    
    public List<String> getImagenesUrls() {
        return imagenesUrls;
    }
    
    public void setImagenesUrls(List<String> imagenesUrls) {
        this.imagenesUrls = imagenesUrls;
    }
    
    @Override
    public String toString() {
        return "CatalogoResponseDTO{" +
                "id=" + id +
                ", codigo=" + codigo +
                ", nit='" + nit + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descuentos=" + descuentos +
                ", estado=" + estado +
                '}';
    }
}
