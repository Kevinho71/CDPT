package app.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class PacienteCreateDTO {
    
    @NotNull(message = "El ID del socio es obligatorio")
    private Integer fkSocio;
    
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
    private String nombres;
    
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    private String apellidos;
    
    @Size(max = 20, message = "El CI no puede exceder 20 caracteres")
    private String ci;
    
    private LocalDate fechaNacimiento;
    
    @Size(max = 20, message = "El género no puede exceder 20 caracteres")
    private String genero; // M, F, Otro
    
    @Size(max = 50, message = "El teléfono no puede exceder 50 caracteres")
    private String telefono;
    
    @Size(max = 150, message = "El email no puede exceder 150 caracteres")
    private String email;
    
    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    private String direccion;
    
    @Size(max = 100, message = "La ocupación no puede exceder 100 caracteres")
    private String ocupacion;
    
    @Size(max = 50, message = "El estado civil no puede exceder 50 caracteres")
    private String estadoCivil;
    
    @Size(max = 150, message = "El nombre del contacto de emergencia no puede exceder 150 caracteres")
    private String nombreContactoEmergencia;
    
    @Size(max = 50, message = "El teléfono del contacto de emergencia no puede exceder 50 caracteres")
    private String telefonoContactoEmergencia;
    
    // Constructor vacío
    public PacienteCreateDTO() {}
    
    // Getters y Setters
    public Integer getFkSocio() {
        return fkSocio;
    }
    
    public void setFkSocio(Integer fkSocio) {
        this.fkSocio = fkSocio;
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
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getOcupacion() {
        return ocupacion;
    }
    
    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }
    
    public String getEstadoCivil() {
        return estadoCivil;
    }
    
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }
    
    public String getNombreContactoEmergencia() {
        return nombreContactoEmergencia;
    }
    
    public void setNombreContactoEmergencia(String nombreContactoEmergencia) {
        this.nombreContactoEmergencia = nombreContactoEmergencia;
    }
    
    public String getTelefonoContactoEmergencia() {
        return telefonoContactoEmergencia;
    }
    
    public void setTelefonoContactoEmergencia(String telefonoContactoEmergencia) {
        this.telefonoContactoEmergencia = telefonoContactoEmergencia;
    }
}
