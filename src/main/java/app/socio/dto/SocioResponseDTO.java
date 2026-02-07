package app.socio.dto;

import java.time.LocalDate;

/**
 * DTO simplificado para respuestas de API
 * Contiene solo la información esencial del socio
 */
public class SocioResponseDTO {
    private Integer id;
    private Integer codigo;
    private String nrodocumento;
    private String matricula;
    private String nombresocio;
    private LocalDate fechaemision;
    private LocalDate fechaexpiracion;
    private Integer estado;
    
    // Datos de persona (desnormalizados para respuesta rápida)
    private Integer personaId;
    private String ci;
    private String nombrecompleto;
    private String email;
    private Integer celular;
    
    // Datos relacionados (solo IDs y nombres)
    private Integer profesionId;
    private String profesionNombre;
    private Integer institucionId;
    private String institucionNombre;
    
    // URLs de recursos
    private String logoUrl;

    public SocioResponseDTO() {}

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

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombresocio() {
        return nombresocio;
    }

    public void setNombresocio(String nombresocio) {
        this.nombresocio = nombresocio;
    }

    public LocalDate getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(LocalDate fechaemision) {
        this.fechaemision = fechaemision;
    }

    public LocalDate getFechaexpiracion() {
        return fechaexpiracion;
    }

    public void setFechaexpiracion(LocalDate fechaexpiracion) {
        this.fechaexpiracion = fechaexpiracion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCelular() {
        return celular;
    }

    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    public Integer getProfesionId() {
        return profesionId;
    }

    public void setProfesionId(Integer profesionId) {
        this.profesionId = profesionId;
    }

    public String getProfesionNombre() {
        return profesionNombre;
    }

    public void setProfesionNombre(String profesionNombre) {
        this.profesionNombre = profesionNombre;
    }

    public Integer getInstitucionId() {
        return institucionId;
    }

    public void setInstitucionId(Integer institucionId) {
        this.institucionId = institucionId;
    }

    public String getInstitucionNombre() {
        return institucionNombre;
    }

    public void setInstitucionNombre(String institucionNombre) {
        this.institucionNombre = institucionNombre;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
