package app.clinica.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * DTO para crear citas con modelo sistémico (múltiples participantes).
 * Ejemplo de uso: Terapia de pareja, familiar o grupal.
 */
public class CitaConParticipantesCreateDTO {
    
    @NotNull(message = "El ID del perfil del socio es obligatorio")
    private Integer fkPerfilSocio;
    
    @NotNull(message = "El ID del paciente titular es obligatorio")
    private Integer idTitular; // Quien reserva/paga
    
    @NotEmpty(message = "Debe haber al menos un participante en la cita")
    private List<ParticipanteDTO> participantes;
    
    @NotNull(message = "La fecha de la cita es obligatoria")
    private LocalDate fechaCita;
    
    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;
    
    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;
    
    @Size(max = 20, message = "La modalidad no puede exceder 20 caracteres")
    private String modalidad; // PRESENCIAL, VIRTUAL, DOMICILIO
    
    @Size(max = 50, message = "El tipo de sesión no puede exceder 50 caracteres")
    private String tipoSesion; // INDIVIDUAL, PAREJA, FAMILIA, GRUPO
    
    @Size(max = 255, message = "El motivo breve no puede exceder 255 caracteres")
    private String motivoBreve;
    
    private String notasInternas;
    
    private BigDecimal montoAcordado;
    
    // Inner class para representar cada participante
    public static class ParticipanteDTO {
        @NotNull(message = "El ID del paciente es obligatorio")
        private Integer idPaciente;
        
        @Size(max = 50, message = "El tipo de participación no puede exceder 50 caracteres")
        private String tipoParticipacion; // TITULAR, PAREJA, HIJO, PADRE, MADRE, OBSERVADOR
        
        public ParticipanteDTO() {}
        
        public ParticipanteDTO(Integer idPaciente, String tipoParticipacion) {
            this.idPaciente = idPaciente;
            this.tipoParticipacion = tipoParticipacion;
        }
        
        public Integer getIdPaciente() {
            return idPaciente;
        }
        
        public void setIdPaciente(Integer idPaciente) {
            this.idPaciente = idPaciente;
        }
        
        public String getTipoParticipacion() {
            return tipoParticipacion;
        }
        
        public void setTipoParticipacion(String tipoParticipacion) {
            this.tipoParticipacion = tipoParticipacion;
        }
    }
    
    // Constructores
    public CitaConParticipantesCreateDTO() {}
    
    // Getters y Setters
    public Integer getFkPerfilSocio() {
        return fkPerfilSocio;
    }
    
    public void setFkPerfilSocio(Integer fkPerfilSocio) {
        this.fkPerfilSocio = fkPerfilSocio;
    }
    
    public Integer getIdTitular() {
        return idTitular;
    }
    
    public void setIdTitular(Integer idTitular) {
        this.idTitular = idTitular;
    }
    
    public List<ParticipanteDTO> getParticipantes() {
        return participantes;
    }
    
    public void setParticipantes(List<ParticipanteDTO> participantes) {
        this.participantes = participantes;
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
    
    public BigDecimal getMontoAcordado() {
        return montoAcordado;
    }
    
    public void setMontoAcordado(BigDecimal montoAcordado) {
        this.montoAcordado = montoAcordado;
    }
}
