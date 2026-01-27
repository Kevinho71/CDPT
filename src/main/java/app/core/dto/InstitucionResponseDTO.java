package app.core.dto;

public class InstitucionResponseDTO {
    
    private Integer id;
    private String nit;
    private String compania;
    private String institucion;
    private String representante;
    private String correo;
    private String direccion;
    private String telefono;
    private String fax;
    private String host;
    private String port;
    private Integer estado;
    
    // Datos desnormalizados de provincia
    private Integer provinciaId;
    private String provinciaNombre;
    
    // Constructores
    public InstitucionResponseDTO() {}
    
    public InstitucionResponseDTO(Integer id, String nit, String compania, String institucion, 
                                 String representante, String correo, String direccion, String telefono, 
                                 String fax, String host, String port, Integer estado, 
                                 Integer provinciaId, String provinciaNombre) {
        this.id = id;
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
        this.provinciaNombre = provinciaNombre;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
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
    
    public String getProvinciaNombre() {
        return provinciaNombre;
    }
    
    public void setProvinciaNombre(String provinciaNombre) {
        this.provinciaNombre = provinciaNombre;
    }
    
    @Override
    public String toString() {
        return "InstitucionResponseDTO{" +
                "id=" + id +
                ", nit='" + nit + '\'' +
                ", compania='" + compania + '\'' +
                ", institucion='" + institucion + '\'' +
                ", provinciaNombre='" + provinciaNombre + '\'' +
                '}';
    }
}
