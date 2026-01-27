package app.cms.dto;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de contenido estático
 */
public class ContentDTO {

    private String clave;
    private String valor;
    private String seccion;
    private String tipoInput;
    private String usuarioModificador; // Email del usuario que modificó
    private LocalDateTime fechaModificacion;

    // CONSTRUCTORES
    public ContentDTO() {}

    public ContentDTO(String clave, String valor, String seccion, String tipoInput, 
                      String usuarioModificador, LocalDateTime fechaModificacion) {
        this.clave = clave;
        this.valor = valor;
        this.seccion = seccion;
        this.tipoInput = tipoInput;
        this.usuarioModificador = usuarioModificador;
        this.fechaModificacion = fechaModificacion;
    }

    // GETTERS Y SETTERS
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getTipoInput() {
        return tipoInput;
    }

    public void setTipoInput(String tipoInput) {
        this.tipoInput = tipoInput;
    }

    public String getUsuarioModificador() {
        return usuarioModificador;
    }

    public void setUsuarioModificador(String usuarioModificador) {
        this.usuarioModificador = usuarioModificador;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
