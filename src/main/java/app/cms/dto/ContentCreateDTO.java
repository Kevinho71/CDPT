package app.cms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para crear un nuevo contenido estático
 */
public class ContentCreateDTO {

    @NotBlank(message = "La clave no puede estar vacía")
    @Size(max = 100, message = "La clave no puede exceder 100 caracteres")
    private String clave;

    @NotBlank(message = "El valor no puede estar vacío")
    private String valor;

    @Size(max = 50, message = "La sección no puede exceder 50 caracteres")
    private String seccion;

    @Size(max = 20, message = "El tipo de input no puede exceder 20 caracteres")
    private String tipoInput; // 'TEXT', 'TEXTAREA', 'IMAGE', 'LINK'

    // CONSTRUCTORES
    public ContentCreateDTO() {}

    public ContentCreateDTO(String clave, String valor, String seccion, String tipoInput) {
        this.clave = clave;
        this.valor = valor;
        this.seccion = seccion;
        this.tipoInput = tipoInput;
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
}
