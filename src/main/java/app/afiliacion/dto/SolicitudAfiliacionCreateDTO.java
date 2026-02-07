package app.afiliacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

public class SolicitudAfiliacionCreateDTO {
    
    private Integer fkSolicitudInicialAfiliacion; // Opcional: puede venir de un lead o ser directo
    
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
    private String nombres;
    
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    private String apellidos;
    
    @NotBlank(message = "El CI es obligatorio")
    @Size(max = 20, message = "El CI no puede exceder 20 caracteres")
    private String ci;
    
    @Size(max = 10, message = "El lugar de expedición no puede excede 10 caracteres")
    private String ciExpedido;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;
    
    @Size(max = 20, message = "El género no puede exceder 20 caracteres")
    private String genero;
    
    private String direccionDomicilio;
    
    // Archivos (MultipartFile para manejar uploads)
    private MultipartFile fotoCarnetAnverso;
    private MultipartFile fotoCarnetReverso;
    private MultipartFile fotoTituloProvisicion;
    private MultipartFile cv;
    
    // O si vienen como URLs directas (si ya fueron subidos previamente)
    private String urlFotoCarnetAnverso;
    private String urlFotoCarnetReverso;
    private String urlFotoTituloProvisicion;
    private String urlCv;
    
    // Constructor vacío
    public SolicitudAfiliacionCreateDTO() {}
    
    // Getters y Setters
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
    
    public MultipartFile getFotoCarnetAnverso() {
        return fotoCarnetAnverso;
    }
    
    public void setFotoCarnetAnverso(MultipartFile fotoCarnetAnverso) {
        this.fotoCarnetAnverso = fotoCarnetAnverso;
    }
    
    public MultipartFile getFotoCarnetReverso() {
        return fotoCarnetReverso;
    }
    
    public void setFotoCarnetReverso(MultipartFile fotoCarnetReverso) {
        this.fotoCarnetReverso = fotoCarnetReverso;
    }
    
    public MultipartFile getFotoTituloProvisicion() {
        return fotoTituloProvisicion;
    }
    
    public void setFotoTituloProvisicion(MultipartFile fotoTituloProvisicion) {
        this.fotoTituloProvisicion = fotoTituloProvisicion;
    }
    
    public MultipartFile getCv() {
        return cv;
    }
    
    public void setCv(MultipartFile cv) {
        this.cv = cv;
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
}
