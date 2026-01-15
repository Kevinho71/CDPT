package app.catalogo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CatalogoDTO {
    
    @NotBlank(message = "El NIT es requerido")
    private String nit;
    
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    
    private String descripcion;
    
    private String direccion;
    
    private List<String> descuentos; // Array JSON de descuentos
    
    private String tipo;
    
    private String longitud;
    
    private String latitud;
    
    @NotNull(message = "El estado es requerido")
    private Integer estado;
    
    // Constructores
    public CatalogoDTO() {}
    
    public CatalogoDTO(String nit, String nombre, String descripcion, String direccion, 
                      List<String> descuentos, String tipo, String longitud, String latitud, Integer estado) {
        this.nit = nit;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.descuentos = descuentos;
        this.tipo = tipo;
        this.longitud = longitud;
        this.latitud = latitud;
        this.estado = estado;
    }
    
    // Getters y Setters
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
    
    @Override
    public String toString() {
        return "CatalogoDTO{" +
                "nit='" + nit + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descuentos=" + descuentos +
                ", estado=" + estado +
                '}';
    }
}
