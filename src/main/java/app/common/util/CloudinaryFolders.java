package app.common.util;

/**
 * Constants for Cloudinary folder organization
 */
public class CloudinaryFolders {
    
    // Company (Empresa) folders
    public static final String EMPRESA_LOGO = "EMPRESA_LOGO";
    public static final String EMPRESA_BANNER = "EMPRESA_BANNER";
    public static final String EMPRESA_GALERIA = "EMPRESA_GALERIA";
    
    // Member (Socio) folders
    public static final String SOCIO_PERFIL = "SOCIO_PERFIL";
    public static final String SOCIO_BANNER = "SOCIO_BANNER";
    public static final String SOCIO_LOGO = "SOCIO_LOGO";
    public static final String SOCIO_QR = "SOCIO_QR";
    
    // Image type constants
    public static final String TIPO_PERFIL = "PERFIL";
    public static final String TIPO_BANNER = "BANNER";
    public static final String TIPO_GALERIA = "GALERIA";
    
    // Limits
    public static final int MAX_GALERIA_IMAGES = 3;
    
    private CloudinaryFolders() {
        // Private constructor to prevent instantiation
    }
}
