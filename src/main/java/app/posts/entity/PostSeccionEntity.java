package app.posts.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "post_secciones")
public class PostSeccionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private Integer estado;

    // RELACIÓN CON EL POST PADRE
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_post", nullable = false)
    private PostEntity post;

    // ORDENAMIENTO
    @Column(nullable = false)
    private Integer orden;

    // TIPO DE CONTENIDO
    @Column(name = "tipo_seccion", length = 20)
    private String tipoSeccion = "ESTANDAR"; // 'ESTANDAR', 'VIDEO', 'CITA'

    // CAMPOS DE CONTENIDO
    @Column(length = 255)
    private String subtitulo;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;

    @Column(name = "video_url", length = 500)
    private String videoUrl;

    // CONSTRUCTORES
    public PostSeccionEntity() {}

    public PostSeccionEntity(Integer id,  Integer estado, PostEntity post, 
                             Integer orden, String tipoSeccion, String subtitulo, String contenido, 
                             String imagenUrl, String videoUrl) {
        this.id = id;
        this.estado = estado;
        this.post = post;
        this.orden = orden;
        this.tipoSeccion = tipoSeccion;
        this.subtitulo = subtitulo;
        this.contenido = contenido;
        this.imagenUrl = imagenUrl;
        this.videoUrl = videoUrl;
    }

    // GETTERS Y SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getTipoSeccion() {
        return tipoSeccion;
    }

    public void setTipoSeccion(String tipoSeccion) {
        this.tipoSeccion = tipoSeccion;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "PostSeccionEntity{" +
                "id=" + id +
                ", estado=" + estado +
                ", orden=" + orden +
                ", tipoSeccion='" + tipoSeccion + '\'' +
                ", subtitulo='" + subtitulo + '\'' +
                '}';
    }
}
