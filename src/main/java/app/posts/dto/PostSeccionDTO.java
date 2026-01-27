package app.posts.dto;

/**
 * DTO para representar una sección individual del post
 */
public class PostSeccionDTO {
    private Integer id;
    private Integer orden;
    private String tipoSeccion; // 'ESTANDAR', 'VIDEO', 'CITA'
    private String subtitulo;
    private String contenido; // HTML String
    private String imagenUrl;
    private String videoUrl;

    // Constructor vacío
    public PostSeccionDTO() {}

    // Constructor completo
    public PostSeccionDTO(Integer id, Integer orden, String tipoSeccion, 
                         String subtitulo, String contenido, String imagenUrl, String videoUrl) {
        this.id = id;
        this.orden = orden;
        this.tipoSeccion = tipoSeccion;
        this.subtitulo = subtitulo;
        this.contenido = contenido;
        this.imagenUrl = imagenUrl;
        this.videoUrl = videoUrl;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
