package app.afiliacion.dto;

import java.time.LocalDateTime;

public class SolicitudInicialAfiliacionResponseDTO {
    
    private Integer id;
    private String nombreCompleto;
    private String celular;
    private String correo;
    private String tokenAcceso;
    private LocalDateTime tokenExpiracion;
    private String estado;
    private LocalDateTime fechaSolicitud;
    private String notasAdmin;
    
    // Constructor vacío
    public SolicitudInicialAfiliacionResponseDTO() {}
    
    // Constructor con parámetros
    public SolicitudInicialAfiliacionResponseDTO(Integer id, String nombreCompleto, String celular,
                                                String correo, String tokenAcceso, LocalDateTime tokenExpiracion,
                                                String estado, LocalDateTime fechaSolicitud, String notasAdmin) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.celular = celular;
        this.correo = correo;
        this.tokenAcceso = tokenAcceso;
        this.tokenExpiracion = tokenExpiracion;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
        this.notasAdmin = notasAdmin;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String getCelular() {
        return celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getTokenAcceso() {
        return tokenAcceso;
    }
    
    public void setTokenAcceso(String tokenAcceso) {
        this.tokenAcceso = tokenAcceso;
    }
    
    public LocalDateTime getTokenExpiracion() {
        return tokenExpiracion;
    }
    
    public void setTokenExpiracion(LocalDateTime tokenExpiracion) {
        this.tokenExpiracion = tokenExpiracion;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }
    
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    
    public String getNotasAdmin() {
        return notasAdmin;
    }
    
    public void setNotasAdmin(String notasAdmin) {
        this.notasAdmin = notasAdmin;
    }
}
