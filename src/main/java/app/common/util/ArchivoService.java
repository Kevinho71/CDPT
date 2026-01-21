package app.common.util;

import java.io.IOException;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface ArchivoService {
  /**
   * Uploads an image to Cloudinary in a specific folder
   * @param folder The Cloudinary folder name (e.g., "EMPRESA_LOGO", "SOCIO_PERFIL")
   * @param archivo The multipart file to upload
   * @param publicId The public ID to use for the image (without file extension)
   * @return The full Cloudinary HTTPS URL of the uploaded image (e.g., "https://res.cloudinary.com/.../image.jpg")
   * @throws IOException if upload fails
   */
  String subirImagen(String folder, MultipartFile archivo, String publicId) throws IOException;
  
  /**
   * Deletes an image from Cloudinary using its public ID or URL
   * @param publicId The full public ID including folder (e.g., "EMPRESA_LOGO/empresa_123") or full Cloudinary URL
   * @throws IOException if deletion fails
   */
  void eliminarImagen(String publicId) throws IOException;
  
  /**
   * Gets information about an uploaded asset
   * @param publicId The public ID of the asset
   * @return Map containing asset details
   * @throws IOException if retrieval fails
   */
  Map<String, Object> obtenerDetallesImagen(String publicId) throws IOException;
  
  /**
   * Lists all resources in a specific folder
   * @param folder The folder name
   * @param maxResults Maximum number of results to return
   * @return Map containing the list of resources
   * @throws IOException if retrieval fails
   */
  Map<String, Object> listarImagenesCarpeta(String folder, int maxResults) throws IOException;
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\ArchivoService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */