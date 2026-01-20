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
     * 
     * @param socioId The socio ID
     * @param oldPublicId The current profile photo public_id (can be null)
     * @param newPhoto The new photo file
     * @return The new Cloudinary public_id
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

        // Upload new photo
        String publicId = "socio_" + socioId + "_perfil_" + System.currentTimeMillis();
        String newPublicId = archivoService.subirImagen(CloudinaryFolders.SOCIO_PERFIL, newPhoto, publicId);
        
        System.out.println("Nueva foto de perfil subida: " + newPublicId);
        return newPublicId;
    }

    /**
     * Updates a socio's banner photo
     * Deletes the old banner before uploading the new one
     * 
     * @param socioId The socio ID
     * @param oldPublicId The current banner public_id (can be null)
     * @param newBanner The new banner file
     * @return The new Cloudinary public_id
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

        // Upload new banner
        String publicId = "socio_" + socioId + "_banner_" + System.currentTimeMillis();
        String newPublicId = archivoService.subirImagen(CloudinaryFolders.SOCIO_BANNER, newBanner, publicId);
        
        System.out.println("Nuevo banner subido: " + newPublicId);
        return newPublicId;
    }

    /**
     * Updates a socio's credential photo (logo for ID card)
     * Deletes the old photo before uploading the new one
     * 
     * @param socioId The socio ID
     * @param oldPublicId The current credential photo public_id (can be null)
     * @param newLogo The new logo file
     * @return The new Cloudinary public_id
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
        String newPublicId = archivoService.subirImagen(CloudinaryFolders.SOCIO_LOGO, newLogo, publicId);
        
        System.out.println("Nueva foto de credencial subida: " + newPublicId);
        return newPublicId;
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

    /**
     * Updates a socio's QR code image
     * Deletes the old QR before uploading the new one
     * 
     * @param socioId The socio ID
     * @param oldPublicId The current QR public_id (can be null)
     * @param newQR The new QR image file
     * @return The new Cloudinary public_id
     * @throws IOException if upload fails
     */
    public String actualizarQR(Integer socioId, String oldPublicId, MultipartFile newQR) throws IOException {
        // Delete old QR if exists
        if (oldPublicId != null && !oldPublicId.isEmpty()) {
            try {
                archivoService.eliminarImagen(oldPublicId);
                System.out.println("QR anterior eliminado: " + oldPublicId);
            } catch (Exception e) {
                System.err.println("Error al eliminar QR anterior: " + e.getMessage());
            }
        }

        // Upload new QR
        String publicId = "socio_" + socioId + "_qr_" + System.currentTimeMillis();
        String newPublicId = archivoService.subirImagen(CloudinaryFolders.SOCIO_QR, newQR, publicId);
        
        System.out.println("Nuevo QR subido: " + newPublicId);
        return newPublicId;
    }

    /**
     * Deletes a socio's QR code from Cloudinary
     * 
     * @param publicId The QR public_id
     * @throws IOException if deletion fails
     */
    public void eliminarQR(String publicId) throws IOException {
        if (publicId != null && !publicId.isEmpty()) {
            archivoService.eliminarImagen(publicId);
            System.out.println("QR eliminado: " + publicId);
        }
    }
}
