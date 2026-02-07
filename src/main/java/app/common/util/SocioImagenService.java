package app.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Helper service for managing Socio (Member) images with Cloudinary
 * Handles profile photos, banners, and credential photos with automatic deletion of old images
 */
@Service
public class SocioImagenService {

    @Autowired
    private ArchivoService archivoService;

    /**
     * Updates a socio's profile photo
     * Deletes the old photo before uploading the new one
     * If newPhoto is null or empty, only deletes the old photo and returns null
     * 
     * @param socioId The socio ID
     * @param oldPublicId The current profile photo URL or public_id (can be null)
     * @param newPhoto The new photo file (can be null/empty to delete)
     * @return The full Cloudinary HTTPS URL of the uploaded image, or null if deleted
     * @throws IOException if upload fails
     */
    public String actualizarFotoPerfil(Integer socioId, String oldPublicId, MultipartFile newPhoto) throws IOException {
        // Delete old photo if exists
        if (oldPublicId != null && !oldPublicId.isEmpty()) {
            try {
                archivoService.eliminarImagen(oldPublicId);
                System.out.println("Foto de perfil anterior eliminada: " + oldPublicId);
            } catch (Exception e) {
                System.err.println("Error al eliminar foto de perfil anterior: " + e.getMessage());
            }
        }

        // Si newPhoto es null o está vacío, solo eliminar (retornar null)
        if (newPhoto == null || newPhoto.isEmpty()) {
            System.out.println("Foto de perfil eliminada (no se subió reemplazo)");
            return null;
        }

        // Upload new photo
        String publicId = "socio_" + socioId + "_perfil_" + System.currentTimeMillis();
        String newImageUrl = archivoService.subirImagen(CloudinaryFolders.SOCIO_PERFIL, newPhoto, publicId);
        
        System.out.println("Nueva foto de perfil subida. URL: " + newImageUrl);
        return newImageUrl;
    }

    /**
     * Updates a socio's banner photo
     * Deletes the old banner before uploading the new one
     * If newBanner is null or empty, only deletes the old banner and returns null
     * 
     * @param socioId The socio ID
     * @param oldPublicId The current banner URL or public_id (can be null)
     * @param newBanner The new banner file (can be null/empty to delete)
     * @return The full Cloudinary HTTPS URL of the uploaded image, or null if deleted
     * @throws IOException if upload fails
     */
    public String actualizarBanner(Integer socioId, String oldPublicId, MultipartFile newBanner) throws IOException {
        // Delete old banner if exists
        if (oldPublicId != null && !oldPublicId.isEmpty()) {
            try {
                archivoService.eliminarImagen(oldPublicId);
                System.out.println("Banner anterior eliminado: " + oldPublicId);
            } catch (Exception e) {
                System.err.println("Error al eliminar banner anterior: " + e.getMessage());
            }
        }

        // Si newBanner es null o está vacío, solo eliminar (retornar null)
        if (newBanner == null || newBanner.isEmpty()) {
            System.out.println("Banner eliminado (no se subió reemplazo)");
            return null;
        }

        // Upload new banner
        String publicId = "socio_" + socioId + "_banner_" + System.currentTimeMillis();
        String newImageUrl = archivoService.subirImagen(CloudinaryFolders.SOCIO_BANNER, newBanner, publicId);
        
        System.out.println("Nuevo banner subido. URL: " + newImageUrl);
        return newImageUrl;
    }

    /**
     * Updates a socio's credential photo (logo for ID card)
     * Deletes the old photo before uploading the new one
     * 
     * @param socioId The socio ID
     * @param oldPublicId The current credential photo URL or public_id (can be null)
     * @param newLogo The new logo file
     * @return The full Cloudinary HTTPS URL of the uploaded image
     * @throws IOException if upload fails
     */
    public String actualizarFotoCredencial(Integer socioId, String oldPublicId, MultipartFile newLogo) throws IOException {
        // Delete old credential photo if exists
        if (oldPublicId != null && !oldPublicId.isEmpty()) {
            try {
                archivoService.eliminarImagen(oldPublicId);
                System.out.println("Foto de credencial anterior eliminada: " + oldPublicId);
            } catch (Exception e) {
                System.err.println("Error al eliminar foto de credencial anterior: " + e.getMessage());
            }
        }

        // Upload new credential photo
        String publicId = "socio_" + socioId + "_credencial_" + System.currentTimeMillis();
        String newImageUrl = archivoService.subirImagen(CloudinaryFolders.SOCIO_LOGO, newLogo, publicId);
        
        System.out.println("Nueva foto de credencial subida. URL: " + newImageUrl);
        return newImageUrl;
    }

    /**
     * Deletes a socio's profile photo from Cloudinary
     * 
     * @param publicId The profile photo public_id
     * @throws IOException if deletion fails
     */
    public void eliminarFotoPerfil(String publicId) throws IOException {
        if (publicId != null && !publicId.isEmpty()) {
            archivoService.eliminarImagen(publicId);
            System.out.println("Foto de perfil eliminada: " + publicId);
        }
    }

    /**
     * Deletes a socio's banner from Cloudinary
     * 
     * @param publicId The banner public_id
     * @throws IOException if deletion fails
     */
    public void eliminarBanner(String publicId) throws IOException {
        if (publicId != null && !publicId.isEmpty()) {
            archivoService.eliminarImagen(publicId);
            System.out.println("Banner eliminado: " + publicId);
        }
    }

    /**
     * Deletes a socio's credential photo from Cloudinary
     * 
     * @param publicId The credential photo public_id
     * @throws IOException if deletion fails
     */
    public void eliminarFotoCredencial(String publicId) throws IOException {
        if (publicId != null && !publicId.isEmpty()) {
            archivoService.eliminarImagen(publicId);
            System.out.println("Foto de credencial eliminada: " + publicId);
        }
    }
}
