package app.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InstitucionDTO {
    
    @NotBlank(message = "El NIT es requerido")
    private String nit;
    
    @NotBlank(message = "La compañía es requerida")
    private String compania;
    
    @NotBlank(message = "La institución es requerida")
    private String institucion;
    
    private String representante;
    
    @Email(message = "El correo debe ser válido")
    private String correo;
    
    private String direccion;
    private String telefono;
    private String fax;
    private String host;
    private String port;
    
    @NotNull(message = "El estado es requerido")
    private Integer estado;
    
    @NotNull(message = "La provincia es requerida")
    private Integer provinciaId;
    
    // Constructores
    public InstitucionDTO() {}
    
    public InstitucionDTO(String nit, String compania, String institucion, String representante, 
                         String correo, String direccion, String telefono, String fax, 
                         String host, String port, Integer estado, Integer provinciaId) {
        this.nit = nit;
        this.compania = compania;
        this.institucion = institucion;
        this.representante = representante;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fax = fax;
        this.host = host;
        this.port = port;
        this.estado = estado;
        this.provinciaId = provinciaId;
    }
    
    // Getters y Setters
    public String getNit() {
        return nit;
    }
    
    public void setNit(String nit) {
        this.nit = nit;
    }
    
    public String getCompania() {
        return compania;
    }
    
    public void setCompania(String compania) {
        this.compania = compania;
    }
    
    public String getInstitucion() {
        return institucion;
    }
    
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }
    
    public String getRepresentante() {
        return representante;
    }
    
    public void setRepresentante(String representante) {
        this.representante = representante;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getFax() {
        return fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public String getHost() {
        return host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public String getPort() {
        return port;
    }
    
    public void setPort(String port) {
        this.port = port;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    public Integer getProvinciaId() {
        return provinciaId;
    }
    
    public void setProvinciaId(Integer provinciaId) {
        this.provinciaId = provinciaId;
    }
    
    @Override
    public String toString() {
        return "InstitucionDTO{" +
                "nit='" + nit + '\'' +
                ", compania='" + compania + '\'' +
                ", institucion='" + institucion + '\'' +
                ", provinciaId=" + provinciaId +
                ", estado=" + estado +
                '}';
    }
}
