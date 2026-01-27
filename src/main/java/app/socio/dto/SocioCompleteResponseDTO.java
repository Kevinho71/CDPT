package app.socio.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO unificado para mostrar toda la información del socio:
 * - Datos del socio
 * - Usuario asociado (si tiene)
 * - Empresas/catálogos asociados
 */
public class SocioCompleteResponseDTO {
    
    // Datos del socio
    private Integer id;
    private Integer codigo;
    private String nrodocumento;
    private String matricula;
    private String nombresocio;
    private LocalDate fechaemision;
    private LocalDate fechaexpiracion;
    private Integer estado;
    private String imagen;
    
    // Datos de la persona asociada
    private Integer personaId;
    private String personaCi;
    private String personaNombre;
    private String personaEmail;
    private Integer personaCelular;
    
    // Datos de la profesión
    private String profesion;
    
    // Datos de la institución
    private String institucion;
    
    // Usuario asociado (puede ser null)
    private UsuarioInfo usuario;
    
    // Empresas/Catálogos asociados
    private List<EmpresaInfo> empresas;
    
    // Inner class para información del usuario
    public static class UsuarioInfo {
        private Integer id;
        private String username;
        private Integer estado;
        private List<String> roles;
        
        public UsuarioInfo() {}
        
        public UsuarioInfo(Integer id, String username, Integer estado, List<String> roles) {
            this.id = id;
            this.username = username;
            this.estado = estado;
            this.roles = roles;
        }
        
        // Getters y Setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public Integer getEstado() { return estado; }
        public void setEstado(Integer estado) { this.estado = estado; }
        
        public List<String> getRoles() { return roles; }
        public void setRoles(List<String> roles) { this.roles = roles; }
    }
    
    // Inner class para información de empresas/catálogos
    public static class EmpresaInfo {
        private Integer id;
        private Integer codigo;
        private String nit;
        private String nombre;
        private String descripcion;
        private String direccion;
        private String tipo;
        private String nombrelogo;
        private Integer estado;
        
        public EmpresaInfo() {}
        
        public EmpresaInfo(Integer id, Integer codigo, String nit, String nombre, 
                          String descripcion, String direccion, String tipo, 
                          String nombrelogo, Integer estado) {
            this.id = id;
            this.codigo = codigo;
            this.nit = nit;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.direccion = direccion;
            this.tipo = tipo;
            this.nombrelogo = nombrelogo;
            this.estado = estado;
        }
        
        // Getters y Setters
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public Integer getCodigo() { return codigo; }
        public void setCodigo(Integer codigo) { this.codigo = codigo; }
        
        public String getNit() { return nit; }
        public void setNit(String nit) { this.nit = nit; }
        
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        
        public String getDireccion() { return direccion; }
        public void setDireccion(String direccion) { this.direccion = direccion; }
        
        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }
        
        public String getNombrelogo() { return nombrelogo; }
        public void setNombrelogo(String nombrelogo) { this.nombrelogo = nombrelogo; }
        
        public Integer getEstado() { return estado; }
        public void setEstado(Integer estado) { this.estado = estado; }
    }
    
    // Constructor vacío
    public SocioCompleteResponseDTO() {}
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Integer getCodigo() { return codigo; }
    public void setCodigo(Integer codigo) { this.codigo = codigo; }
    
    public String getNrodocumento() { return nrodocumento; }
    public void setNrodocumento(String nrodocumento) { this.nrodocumento = nrodocumento; }
    
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    
    public String getNombresocio() { return nombresocio; }
    public void setNombresocio(String nombresocio) { this.nombresocio = nombresocio; }
    
    public LocalDate getFechaemision() { return fechaemision; }
    public void setFechaemision(LocalDate fechaemision) { this.fechaemision = fechaemision; }
    
    public LocalDate getFechaexpiracion() { return fechaexpiracion; }
    public void setFechaexpiracion(LocalDate fechaexpiracion) { this.fechaexpiracion = fechaexpiracion; }
    
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }
    
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    
    public Integer getPersonaId() { return personaId; }
    public void setPersonaId(Integer personaId) { this.personaId = personaId; }
    
    public String getPersonaCi() { return personaCi; }
    public void setPersonaCi(String personaCi) { this.personaCi = personaCi; }
    
    public String getPersonaNombre() { return personaNombre; }
    public void setPersonaNombre(String personaNombre) { this.personaNombre = personaNombre; }
    
    public String getPersonaEmail() { return personaEmail; }
    public void setPersonaEmail(String personaEmail) { this.personaEmail = personaEmail; }
    
    public Integer getPersonaCelular() { return personaCelular; }
    public void setPersonaCelular(Integer personaCelular) { this.personaCelular = personaCelular; }
    
    public String getProfesion() { return profesion; }
    public void setProfesion(String profesion) { this.profesion = profesion; }
    
    public String getInstitucion() { return institucion; }
    public void setInstitucion(String institucion) { this.institucion = institucion; }
    
    public UsuarioInfo getUsuario() { return usuario; }
    public void setUsuario(UsuarioInfo usuario) { this.usuario = usuario; }
    
    public List<EmpresaInfo> getEmpresas() { return empresas; }
    public void setEmpresas(List<EmpresaInfo> empresas) { this.empresas = empresas; }
}
