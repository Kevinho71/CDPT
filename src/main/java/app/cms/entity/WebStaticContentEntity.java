package app.cms.entity;

import app.core.entity.UsuarioEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity para gestión de contenidos estáticos de la Landing Page
 * Arquitectura clave-valor para máxima flexibilidad
 */
@Entity
@Table(name = "web_static_content")
public class WebStaticContentEntity {

    @Id
    @Column(name = "clave", nullable = false, length = 100)
    private String clave; // PK: 'home_hero_titulo', 'footer_copy', etc.

    @Column(name = "valor", columnDefinition = "TEXT")
    private String valor; // Texto, URL de imagen, Link, etc.

    @Column(name = "seccion", length = 50)
    private String seccion; // 'HOME', 'NOSOTROS', 'CONTACTO', 'GLOBAL'

    @Column(name = "tipo_input", length = 20)
    private String tipoInput; // 'TEXT', 'TEXTAREA', 'IMAGE', 'LINK'

    // AUDITORÍA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario_modificador")
    private UsuarioEntity usuarioModificador;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    // CONSTRUCTORES
    public WebStaticContentEntity() {
        this.fechaModificacion = LocalDateTime.now();
    }

    public WebStaticContentEntity(String clave, String valor, String seccion, String tipoInput) {
        this.clave = clave;
        this.valor = valor;
        this.seccion = seccion;
        this.tipoInput = tipoInput;
        this.fechaModificacion = LocalDateTime.now();
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

    public UsuarioEntity getUsuarioModificador() {
        return usuarioModificador;
    }

    public void setUsuarioModificador(UsuarioEntity usuarioModificador) {
        this.usuarioModificador = usuarioModificador;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    // MÉTODOS AUXILIARES
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }
}
