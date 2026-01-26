package app.posts.dto;

/**
 * DTO para respuestas de operaciones que solo retornan URL (upload de imágenes)
 */
public class ImageUploadResponse {
    private String url;
    private String message;

    public ImageUploadResponse() {}

    public ImageUploadResponse(String url, String message) {
        this.url = url;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
