package app.common.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Service implementation for managing images using Cloudinary
 * Handles upload, deletion, and retrieval of images with automatic folder organization
 */
@Service
public class ArchivoServiceImpl implements ArchivoService {

    @Autowired
    private Cloudinary cloudinary;

    /**
     * Uploads an image to Cloudinary in a specific folder
     * @param folder The Cloudinary folder name (EMPRESA_LOGO, EMPRESA_BANNER, EMPRESA_GALERIA, SOCIO_PERFIL, SOCIO_BANNER, SOCIO_LOGO)
     * @param archivo The multipart file to upload
     * @param publicId The public ID to use (without file extension)
     * @return The full Cloudinary HTTPS URL (https://res.cloudinary.com/.../image.jpg)
     */
    @Override
    public String subirImagen(String folder, MultipartFile archivo, String publicId) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            throw new IOException("El archivo está vacío.");
        }

        try {
            // Build the full public_id with folder
            String fullPublicId = folder + "/" + publicId;
            
            // Upload parameters
            Map<String, Object> uploadParams = ObjectUtils.asMap(
                "public_id", fullPublicId,
                "folder", folder,
                "resource_type", "image",
                "overwrite", true,  // Overwrite if exists
                "invalidate", true  // Invalidate CDN cache
            );

            // Upload to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(archivo.getBytes(), uploadParams);
            
            // Return the secure URL for storage in database
            String secureUrl = (String) uploadResult.get("secure_url");
            String resultPublicId = (String) uploadResult.get("public_id");
            System.out.println("Imagen subida exitosamente a Cloudinary: " + resultPublicId);
            System.out.println("URL de Cloudinary: " + secureUrl);
            
            return secureUrl;
            
        } catch (Exception e) {
            System.err.println("Error al subir imagen a Cloudinary: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Error al subir imagen a Cloudinary: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes an image from Cloudinary using its public ID or URL
     * @param publicIdOrUrl The full public ID including folder (e.g., "EMPRESA_LOGO/empresa_123") or full Cloudinary URL
     */
    @Override
    public void eliminarImagen(String publicIdOrUrl) throws IOException {
        if (publicIdOrUrl == null || publicIdOrUrl.isEmpty()) {
            System.out.println("Public ID/URL es nulo o vacío, no se puede eliminar");
            return;
        }

        try {
            // Extract public_id from URL if it's a full Cloudinary URL
            String publicId = publicIdOrUrl;
            if (publicIdOrUrl.startsWith("http")) {
                // URL format: https://res.cloudinary.com/{cloud_name}/image/upload/{transformations}/{version}/{public_id}.{format}
                // We need to extract the public_id
                publicId = extractPublicIdFromUrl(publicIdOrUrl);
                System.out.println("Extracted public_id from URL: " + publicId);
            }
            
            System.out.println("Eliminando imagen de Cloudinary: " + publicId);
            
            Map params = ObjectUtils.asMap(
                "resource_type", "image",
                "invalidate", true
            );
            
            Map result = cloudinary.uploader().destroy(publicId, params);
            
            String resultStatus = (String) result.get("result");
            System.out.println("Resultado de eliminación: " + resultStatus);
            
            if (!"ok".equals(resultStatus) && !"not found".equals(resultStatus)) {
                System.err.println("Advertencia: No se pudo eliminar la imagen: " + publicId);
            }
            
        } catch (Exception e) {
            System.err.println("Error al eliminar imagen de Cloudinary: " + e.getMessage());
            e.printStackTrace();
            // No lanzamos excepción para no interrumpir el flujo si la imagen ya no existe
        }
    }
    
    /**
     * Extracts the public_id from a Cloudinary URL
     * Example: https://res.cloudinary.com/demo/image/upload/v1234/SOCIO_PERFIL/socio_1.jpg
     * Returns: SOCIO_PERFIL/socio_1
     */
    private String extractPublicIdFromUrl(String url) {
        try {
            // Find the position of "/upload/" in the URL
            int uploadIndex = url.indexOf("/upload/");
            if (uploadIndex == -1) {
                return url; // Not a valid Cloudinary URL, return as-is
            }
            
            // Get everything after "/upload/"
            String afterUpload = url.substring(uploadIndex + 8); // +8 for "/upload/"
            
            // Skip version if present (starts with 'v' followed by digits)
            if (afterUpload.matches("^v\\d+/.*")) {
                afterUpload = afterUpload.substring(afterUpload.indexOf('/') + 1);
            }
            
            // Remove file extension
            int lastDotIndex = afterUpload.lastIndexOf('.');
            if (lastDotIndex > 0) {
                afterUpload = afterUpload.substring(0, lastDotIndex);
            }
            
            return afterUpload;
            
        } catch (Exception e) {
            System.err.println("Error extrayendo public_id de URL: " + e.getMessage());
            return url; // Return original if extraction fails
        }
    }

    /**
     * Gets information about an uploaded asset
     * @param publicId The public ID of the asset
     * @return Map containing asset details (url, secure_url, width, height, format, etc.)
     */
    @Override
    public Map<String, Object> obtenerDetallesImagen(String publicId) throws IOException {
        if (publicId == null || publicId.isEmpty()) {
            throw new IOException("Public ID es nulo o vacío");
        }

        try {
            Map params = ObjectUtils.emptyMap();
            Map result = cloudinary.api().resource(publicId, params);
            return result;
            
        } catch (Exception e) {
            System.err.println("Error al obtener detalles de imagen: " + e.getMessage());
            throw new IOException("Error al obtener detalles de imagen: " + e.getMessage(), e);
        }
    }

    /**
     * Lists all resources in a specific folder
     * @param folder The folder name (e.g., "EMPRESA_GALERIA")
     * @param maxResults Maximum number of results to return
     * @return Map containing the list of resources
     */
    @Override
    public Map<String, Object> listarImagenesCarpeta(String folder, int maxResults) throws IOException {
        if (folder == null || folder.isEmpty()) {
            throw new IOException("El nombre de la carpeta es nulo o vacío");
        }

        try {
            Map params = ObjectUtils.asMap(
                "type", "upload",
                "prefix", folder + "/",
                "max_results", maxResults > 0 ? maxResults : 50
            );
            
            Map result = cloudinary.api().resources(params);
            return result;
            
        } catch (Exception e) {
            System.err.println("Error al listar imágenes de carpeta: " + e.getMessage());
            throw new IOException("Error al listar imágenes: " + e.getMessage(), e);
        }
    }

    /**
     * Extracts the file extension from a MultipartFile
     */
    private String obtenerExtension(MultipartFile archivo) {
        String nombreOriginal = archivo.getOriginalFilename();
        if (nombreOriginal != null && nombreOriginal.contains(".")) {
            return nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        }
        return "";
    }

    /**
     * Generates a safe filename from a string
     */
    private String generarNombreSeguro(String nombre) {
        if (nombre == null) {
            return "imagen_" + System.currentTimeMillis();
        }
        return nombre.replaceAll("[^a-zA-Z0-9_-]", "_").toLowerCase();
    }
}
