package app.clinica.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entidad que representa los participantes de una cita (sesión).
 * Permite el modelo sistémico: Una cita puede tener múltiples pacientes (pareja, familia, grupo).
 */
@Entity
@Table(name = "cita_participantes",
       uniqueConstraints = @UniqueConstraint(columnNames = {"fk_cita", "fk_paciente"}))
public class CitaParticipanteEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "fk_cita", nullable = false)
    private CitaEntity cita;
    
    @ManyToOne
    @JoinColumn(name = "fk_paciente", nullable = false)
    private PacienteEntity paciente;
    
    /**
     * Rol del participante en la sesión:
     * - TITULAR: Quien reservó/paga
     * - PAREJA: Pareja en terapia de pareja
     * - HIJO: Miembro familiar (hijo/a)
     * - PADRE/MADRE: Progenitores en terapia familiar
     * - OBSERVADOR: Asiste pero no participa activamente
     * - PACIENTE: Rol genérico
     */
    @Column(name = "tipo_participacion", length = 50)
    private String tipoParticipacion = "PACIENTE";
    
    @Column(name = "fecha_agregado")
    private LocalDateTime fechaAgregado = LocalDateTime.now();
    
    // Constructores
    public CitaParticipanteEntity() {}
    
    public CitaParticipanteEntity(CitaEntity cita, PacienteEntity paciente, String tipoParticipacion) {
        this.cita = cita;
        this.paciente = paciente;
        this.tipoParticipacion = tipoParticipacion;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public CitaEntity getCita() {
        return cita;
    }
    
    public void setCita(CitaEntity cita) {
        this.cita = cita;
    }
    
    public PacienteEntity getPaciente() {
        return paciente;
    }
    
    public void setPaciente(PacienteEntity paciente) {
        this.paciente = paciente;
    }
    
    public String getTipoParticipacion() {
        return tipoParticipacion;
    }
    
    public void setTipoParticipacion(String tipoParticipacion) {
        this.tipoParticipacion = tipoParticipacion;
    }
    
    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }
    
    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }
}
