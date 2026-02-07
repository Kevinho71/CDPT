package app.clinica.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class PacienteUpdateDTO {
    
    @Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
    private String nombres;
    
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    private String apellidos;
    
    @Size(max = 20, message = "El CI no puede exceder 20 caracteres")
    private String ci;
    
    private LocalDate fechaNacimiento;
    
    @Size(max = 20, message = "El género no puede exceder 20 caracteres")
    private String genero;
    
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
    
    private Integer estado; // 1: Activo, 0: Inactivo/Archivado
    
    // Constructor vacío
    public PacienteUpdateDTO() {}
    
    // Getters y Setters
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
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
