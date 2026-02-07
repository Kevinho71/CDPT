package app.clinica.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * DTO de respuesta de Cita con soporte para modelo sistémico.
 * Incluye lista de participantes para terapias multi-paciente (pareja, familiar, grupal).
 */
public class CitaResponseDTO {
    
    private Integer id;
    private Integer fkPerfilSocio;
    private String perfilSocioNombre; // Nombre del psicólogo
    private Integer fkPaciente; // ID del paciente titular (responsable administrativo)
    private String pacienteNombre; // Nombre completo del paciente titular
    private LocalDate fechaCita;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String modalidad;
    private String tipoSesion;
    private String estadoCita;
    private String motivoBreve;
    private String notasInternas;
    private BigDecimal montoAcordado;
    private LocalDateTime fechaCreacion;
    
    // NUEVO: Para modelo sistémico (terapia de pareja, familiar, grupal)
    private List<ParticipanteSimpleDTO> participantes;
    
    // Constructor vacío
    public CitaResponseDTO() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getFkPerfilSocio() {
        return fkPerfilSocio;
    }
    
    public void setFkPerfilSocio(Integer fkPerfilSocio) {
        this.fkPerfilSocio = fkPerfilSocio;
    }
    
    public String getPerfilSocioNombre() {
        return perfilSocioNombre;
    }
    
    public void setPerfilSocioNombre(String perfilSocioNombre) {
        this.perfilSocioNombre = perfilSocioNombre;
    }
    
    public Integer getFkPaciente() {
        return fkPaciente;
    }
    
    public void setFkPaciente(Integer fkPaciente) {
        this.fkPaciente = fkPaciente;
    }
    
    public String getPacienteNombre() {
        return pacienteNombre;
    }
    
    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }
    
    public LocalDate getFechaCita() {
        return fechaCita;
    }
    
    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }
    
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    public LocalTime getHoraFin() {
        return horaFin;
    }
    
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
    
    public String getModalidad() {
        return modalidad;
    }
    
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
    
    public String getTipoSesion() {
        return tipoSesion;
    }
    
    public void setTipoSesion(String tipoSesion) {
        this.tipoSesion = tipoSesion;
    }
    
    public String getEstadoCita() {
        return estadoCita;
    }
    
    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }
    
    public String getMotivoBreve() {
        return motivoBreve;
    }
    
    public void setMotivoBreve(String motivoBreve) {
        this.motivoBreve = motivoBreve;
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
    
    public BigDecimal getMontoAcordado() {
        return montoAcordado;
    }
    
    public void setMontoAcordado(BigDecimal montoAcordado) {
        this.montoAcordado = montoAcordado;
    }
    
    public List<ParticipanteSimpleDTO> getParticipantes() {
        return participantes;
    }
    
    public void setParticipantes(List<ParticipanteSimpleDTO> participantes) {
        this.participantes = participantes;
    }
}
