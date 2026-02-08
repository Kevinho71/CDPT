package app.common;

/**
 * Respuesta genérica estandarizada para la API REST.
 * Envuelve el resultado con metadatos de éxito y mensaje.
 *
 * @param <T> Tipo del dato de respuesta
 */
public class ApiResponse<T> {
    
    private boolean success;
    private String message;
    private T data;
    
    // Constructores
    public ApiResponse() {}
    
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    
    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
}
