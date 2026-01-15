package app.perfil.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas_contacto")
public class ConsultaContactoEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_perfil_socio", nullable = false)
    private PerfilSocioEntity perfilSocio;
    
    @Column(name = "nombre_completo", nullable = false, length = 255)
    private String nombreCompleto;
    
    @Column(name = "correo_electronico", nullable = false, length = 255)
    private String correoElectronico;
    
    @Column(name = "telefono", length = 50)
    private String telefono;
    
    @Column(name = "empresa", length = 255)
    private String empresa;
    
    @Column(name = "modalidad_preferida", length = 20)
    private String modalidadPreferida; // presencial, virtual, telefonica
    
    @Column(name = "horario_preferido", length = 100)
    private String horarioPreferido;
    
    @Column(name = "motivo_reunion", nullable = false, columnDefinition = "TEXT")
    private String motivoReunion;
    
    @Column(name = "detalles_adicionales", columnDefinition = "TEXT")
    private String detallesAdicionales;
    
    @Column(name = "estado", length = 20)
    private String estado = "pendiente"; // pendiente, contactado, completado, cancelado
    
    @Column(name = "fecha_contacto")
    private LocalDateTime fechaContacto;
    
    @Column(name = "notas_internas", columnDefinition = "TEXT")
    private String notasInternas;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    // Constructores
    public ConsultaContactoEntity() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public PerfilSocioEntity getPerfilSocio() {
        return perfilSocio;
    }
    
    public void setPerfilSocio(PerfilSocioEntity perfilSocio) {
        this.perfilSocio = perfilSocio;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String getCorreoElectronico() {
        return correoElectronico;
    }
    
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmpresa() {
        return empresa;
    }
    
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    
    public String getModalidadPreferida() {
        return modalidadPreferida;
    }
    
    public void setModalidadPreferida(String modalidadPreferida) {
        this.modalidadPreferida = modalidadPreferida;
    }
    
    public String getHorarioPreferido() {
        return horarioPreferido;
    }
    
    public void setHorarioPreferido(String horarioPreferido) {
        this.horarioPreferido = horarioPreferido;
    }
    
    public String getMotivoReunion() {
        return motivoReunion;
    }
    
    public void setMotivoReunion(String motivoReunion) {
        this.motivoReunion = motivoReunion;
    }
    
    public String getDetallesAdicionales() {
        return detallesAdicionales;
    }
    
    public void setDetallesAdicionales(String detallesAdicionales) {
        this.detallesAdicionales = detallesAdicionales;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public LocalDateTime getFechaContacto() {
        return fechaContacto;
    }
    
    public void setFechaContacto(LocalDateTime fechaContacto) {
        this.fechaContacto = fechaContacto;
    }
    
    public String getNotasInternas() {
        return notasInternas;
    }
    
    public void setNotasInternas(String notasInternas) {
        this.notasInternas = notasInternas;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
