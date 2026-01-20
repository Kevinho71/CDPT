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
     * @return The Cloudinary public_id (folder/publicId format)
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
            
            // Return the public_id for storage in database
            String resultPublicId = (String) uploadResult.get("public_id");
            System.out.println("Imagen subida exitosamente a Cloudinary: " + resultPublicId);
            
            return resultPublicId;
            
        } catch (Exception e) {
            System.err.println("Error al subir imagen a Cloudinary: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Error al subir imagen a Cloudinary: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes an image from Cloudinary using its public ID
     * @param publicId The full public ID including folder (e.g., "EMPRESA_LOGO/empresa_123")
     */
    @Override
    public void eliminarImagen(String publicId) throws IOException {
        if (publicId == null || publicId.isEmpty()) {
            System.out.println("Public ID es nulo o vacío, no se puede eliminar");
            return;
        }

        try {
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
