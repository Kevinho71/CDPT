package app.clinica.dto;

/**
 * DTO simple para representar un participante en una cita.
 */
public class ParticipanteSimpleDTO {
    
    private Integer id;
    private Integer pacienteId;
    private String nombreCompleto;
    private String tipoParticipacion; // TITULAR, PAREJA, HIJO, etc.
    
    // Constructores
    public ParticipanteSimpleDTO() {}
    
    public ParticipanteSimpleDTO(Integer id, Integer pacienteId, String nombreCompleto, 
                                String tipoParticipacion) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.nombreCompleto = nombreCompleto;
        this.tipoParticipacion = tipoParticipacion;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getPacienteId() {
        return pacienteId;
    }
    
    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String getTipoParticipacion() {
        return tipoParticipacion;
    }
    
    public void setTipoParticipacion(String tipoParticipacion) {
        this.tipoParticipacion = tipoParticipacion;
    }
}
