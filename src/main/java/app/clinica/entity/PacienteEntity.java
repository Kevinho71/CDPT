package app.clinica.entity;

import app.socio.entity.SocioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "paciente")
public class PacienteEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_socio", nullable = false)
    private SocioEntity socio;
    
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;
    
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;
    
    @Column(name = "ci", length = 20)
    private String ci;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(name = "genero", length = 20)
    private String genero; // M, F, Otro
    
    @Column(name = "telefono", length = 50)
    private String telefono;
    
    @Column(name = "email", length = 150)
    private String email;
    
    @Column(name = "direccion", length = 255)
    private String direccion;
    
    @Column(name = "ocupacion", length = 100)
    private String ocupacion;
    
    @Column(name = "estado_civil", length = 50)
    private String estadoCivil;
    
    @Column(name = "nombre_contacto_emergencia", length = 150)
    private String nombreContactoEmergencia;
    
    @Column(name = "telefono_contacto_emergencia", length = 50)
    private String telefonoContactoEmergencia;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();
    
    @Column(name = "estado")
    private Integer estado = 1; // 1: Activo, 0: Inactivo/Archivado
    
    // Constructores
    public PacienteEntity() {}
    
    public PacienteEntity(Integer id, SocioEntity socio, String nombres, String apellidos, 
                         String ci, LocalDate fechaNacimiento, String genero, String telefono, 
                         String email, String direccion, String ocupacion, String estadoCivil,
                         String nombreContactoEmergencia, String telefonoContactoEmergencia,
                         LocalDateTime fechaRegistro, Integer estado) {
        this.id = id;
        this.socio = socio;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.ci = ci;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.ocupacion = ocupacion;
        this.estadoCivil = estadoCivil;
        this.nombreContactoEmergencia = nombreContactoEmergencia;
        this.telefonoContactoEmergencia = telefonoContactoEmergencia;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public SocioEntity getSocio() {
        return socio;
    }
    
    public void setSocio(SocioEntity socio) {
        this.socio = socio;
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
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
