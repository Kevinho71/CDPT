package app.afiliacion.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SolicitudAfiliacionResponseDTO {
    
    private Integer id;
    private Integer fkSolicitudInicialAfiliacion;
    private String nombres;
    private String apellidos;
    private String ci;
    private String ciExpedido;
    private LocalDate fechaNacimiento;
    private String genero;
    private String direccionDomicilio;
    private String urlFotoCarnetAnverso;
    private String urlFotoCarnetReverso;
    private String urlFotoTituloProvisicion;
    private String urlCv;
    private String estadoAfiliacion;
    private LocalDateTime fechaRevision;
    
    // Constructor vacío
    public SolicitudAfiliacionResponseDTO() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getFkSolicitudInicialAfiliacion() {
        return fkSolicitudInicialAfiliacion;
    }
    
    public void setFkSolicitudInicialAfiliacion(Integer fkSolicitudInicialAfiliacion) {
        this.fkSolicitudInicialAfiliacion = fkSolicitudInicialAfiliacion;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getCi() {
        return ci;
    }
    
    public void setCi(String ci) {
        this.ci = ci;
    }
    
    public String getCiExpedido() {
        return ciExpedido;
    }
    
    public void setCiExpedido(String ciExpedido) {
        this.ciExpedido = ciExpedido;
    }
    
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getDireccionDomicilio() {
        return direccionDomicilio;
    }
    
    public void setDireccionDomicilio(String direccionDomicilio) {
        this.direccionDomicilio = direccionDomicilio;
    }
    
    public String getUrlFotoCarnetAnverso() {
        return urlFotoCarnetAnverso;
    }
    
    public void setUrlFotoCarnetAnverso(String urlFotoCarnetAnverso) {
        this.urlFotoCarnetAnverso = urlFotoCarnetAnverso;
    }
    
    public String getUrlFotoCarnetReverso() {
        return urlFotoCarnetReverso;
    }
    
    public void setUrlFotoCarnetReverso(String urlFotoCarnetReverso) {
        this.urlFotoCarnetReverso = urlFotoCarnetReverso;
    }
    
    public String getUrlFotoTituloProvisicion() {
        return urlFotoTituloProvisicion;
    }
    
    public void setUrlFotoTituloProvisicion(String urlFotoTituloProvisicion) {
        this.urlFotoTituloProvisicion = urlFotoTituloProvisicion;
    }
    
    public String getUrlCv() {
        return urlCv;
    }
    
    public void setUrlCv(String urlCv) {
        this.urlCv = urlCv;
    }
    
    public String getEstadoAfiliacion() {
        return estadoAfiliacion;
    }
    
    public void setEstadoAfiliacion(String estadoAfiliacion) {
        this.estadoAfiliacion = estadoAfiliacion;
    }
    
    public LocalDateTime getFechaRevision() {
        return fechaRevision;
    }
    
    public void setFechaRevision(LocalDateTime fechaRevision) {
        this.fechaRevision = fechaRevision;
    }
}
