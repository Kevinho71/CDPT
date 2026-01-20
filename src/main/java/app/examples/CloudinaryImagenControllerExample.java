package app.examples;

import app.catalogo.service.ImagenCatalogoService;
import app.catalogo.entity.ImagenesCatalogoEntity;
import app.common.util.CloudinaryFolders;
import app.common.util.SocioImagenService;
import app.socio.entity.SocioEntity;
import app.socio.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * EJEMPLO DE CONTROLADOR PARA GESTIÓN DE IMÁGENES CON CLOUDINARY
 * 
 * Este archivo contiene ejemplos de cómo implementar endpoints para:
 * - Gestión de logos/fotos de perfil de empresas
 * - Gestión de banners de empresas
 * - Gestión de galerías de empresas (máximo 3 imágenes)
 * - Gestión de fotos de perfil de socios
 * - Gestión de banners de socios
 * - Gestión de fotos de credenciales de socios
 * 
 * NOTA: Este es un archivo de ejemplo. NO es un controlador real.
 * Copie y adapte estos ejemplos a sus controladores existentes.
 */
@RestController
@RequestMapping("/api/ejemplos")
public class CloudinaryImagenControllerExample {

    @Autowired
    private ImagenCatalogoService imagenCatalogoService;
    
    @Autowired
    private SocioImagenService socioImagenService;
    
    @Autowired
    private SocioRepository socioRepository;

    // ============================================================================
    // EJEMPLOS PARA EMPRESAS (CATÁLOGOS)
    // ============================================================================

    /**
     * Actualizar logo/foto de perfil de una empresa
     * Automáticamente elimina el logo anterior si existe
     */
    @PostMapping("/empresa/{id}/logo")
    public ResponseEntity<?> actualizarLogoEmpresa(
            @PathVariable Integer id,
            @RequestParam("logo") MultipartFile logo) {
        try {
            if (logo == null || logo.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo de logo es requerido");
            }

            // Guardar imagen automáticamente elimina la anterior
            ImagenesCatalogoEntity imagen = imagenCatalogoService.guardarImagen(
                id,
                CloudinaryFolders.TIPO_PERFIL,
                logo
            );

            return ResponseEntity.ok()
                .body("Logo actualizado exitosamente. Public ID: " + imagen.getImagen());
                
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al actualizar logo: " + e.getMessage());
        }
    }

    /**
     * Actualizar banner de una empresa
     * Automáticamente elimina el banner anterior si existe
     */
    @PostMapping("/empresa/{id}/banner")
    public ResponseEntity<?> actualizarBannerEmpresa(
            @PathVariable Integer id,
            @RequestParam("banner") MultipartFile banner) {
        try {
            if (banner == null || banner.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo de banner es requerido");
            }

            ImagenesCatalogoEntity imagen = imagenCatalogoService.guardarImagen(
                id,
                CloudinaryFolders.TIPO_BANNER,
                banner
            );

            return ResponseEntity.ok()
                .body("Banner actualizado exitosamente. Public ID: " + imagen.getImagen());
                
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al actualizar banner: " + e.getMessage());
        }
    }

    /**
     * Agregar imagen a la galería de una empresa
     * Valida que no se excedan las 3 imágenes permitidas
     */
    @PostMapping("/empresa/{id}/galeria")
    public ResponseEntity<?> agregarImagenGaleria(
            @PathVariable Integer id,
            @RequestParam("imagen") MultipartFile imagen) {
        try {
            if (imagen == null || imagen.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo de imagen es requerido");
            }

            // Verificar límite de galería
            if (!imagenCatalogoService.puedeAgregarImagenGaleria(id)) {
                return ResponseEntity.badRequest()
                    .body("No se pueden agregar más de " + CloudinaryFolders.MAX_GALERIA_IMAGES + 
                          " imágenes a la galería. Elimine una imagen existente primero.");
            }

            ImagenesCatalogoEntity nuevaImagen = imagenCatalogoService.guardarImagen(
                id,
                CloudinaryFolders.TIPO_GALERIA,
                imagen
            );

            return ResponseEntity.ok()
                .body("Imagen agregada a galería exitosamente. Public ID: " + nuevaImagen.getImagen());
                
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al agregar imagen a galería: " + e.getMessage());
        }
    }

    /**
     * Obtener todas las imágenes de galería de una empresa
     */
    @GetMapping("/empresa/{id}/galeria")
    public ResponseEntity<?> obtenerGaleriaEmpresa(@PathVariable Integer id) {
        try {
            List<ImagenesCatalogoEntity> imagenes = imagenCatalogoService.findByCatalogoAndTipo(
                id,
                CloudinaryFolders.TIPO_GALERIA
            );

            return ResponseEntity.ok(imagenes);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al obtener galería: " + e.getMessage());
        }
    }

    /**
     * Eliminar una imagen de galería específica
     * Automáticamente elimina la imagen de Cloudinary
     */
    @DeleteMapping("/empresa/galeria/{imagenId}")
    public ResponseEntity<?> eliminarImagenGaleria(@PathVariable Integer imagenId) {
        try {
            imagenCatalogoService.eliminarImagen(imagenId);
            
            return ResponseEntity.ok()
                .body("Imagen eliminada exitosamente");
                
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al eliminar imagen: " + e.getMessage());
        }
    }

    /**
     * Reemplazar una imagen específica de galería
     * 1. Elimina la imagen anterior
     * 2. Agrega la nueva imagen
     */
    @PutMapping("/empresa/galeria/{imagenId}")
    public ResponseEntity<?> reemplazarImagenGaleria(
            @PathVariable Integer imagenId,
            @RequestParam("nuevaImagen") MultipartFile nuevaImagen) {
        try {
            if (nuevaImagen == null || nuevaImagen.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo de imagen es requerido");
            }

            // Obtener la imagen actual para saber el catálogo
            ImagenesCatalogoEntity imagenActual = (ImagenesCatalogoEntity) 
                imagenCatalogoService.findById(imagenId)
                .orElseThrow(() -> new Exception("Imagen no encontrada"));

            // Eliminar imagen anterior
            imagenCatalogoService.eliminarImagen(imagenId);

            // Agregar nueva imagen (esto creará un nuevo registro)
            // Nota: El límite de 3 se mantiene porque eliminamos una antes de agregar
            ImagenesCatalogoEntity nuevaImagenEntity = imagenCatalogoService.guardarImagen(
                imagenActual.getCatalogo().getId(),
                CloudinaryFolders.TIPO_GALERIA,
                nuevaImagen
            );

            return ResponseEntity.ok()
                .body("Imagen reemplazada exitosamente. Nuevo Public ID: " + 
                      nuevaImagenEntity.getImagen());
                
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al reemplazar imagen: " + e.getMessage());
        }
    }

    // ============================================================================
    // EJEMPLOS PARA SOCIOS (MIEMBROS)
    // ============================================================================

    /**
     * Actualizar foto de perfil de un socio
     * Automáticamente elimina la foto anterior si existe
     */
    @PostMapping("/socio/{id}/perfil")
    public ResponseEntity<?> actualizarFotoPerfilSocio(
            @PathVariable Integer id,
            @RequestParam("foto") MultipartFile foto) {
        try {
            if (foto == null || foto.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo de foto es requerido");
            }

            SocioEntity socio = socioRepository.findById(id)
                .orElseThrow(() -> new Exception("Socio no encontrado"));

            // Actualizar foto de perfil (elimina la anterior automáticamente)
            String newPublicId = socioImagenService.actualizarFotoPerfil(
                id,
                socio.getImagen(),  // Public ID anterior (puede ser null)
                foto
            );

            // Actualizar entidad con nuevo public_id
            socio.setImagen(newPublicId);
            socioRepository.save(socio);

            return ResponseEntity.ok()
                .body("Foto de perfil actualizada exitosamente. Public ID: " + newPublicId);
                
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al actualizar foto de perfil: " + e.getMessage());
        }
    }

    /**
     * Actualizar banner de un socio
     * Automáticamente elimina el banner anterior si existe
     */
    @PostMapping("/socio/{id}/banner")
    public ResponseEntity<?> actualizarBannerSocio(
            @PathVariable Integer id,
            @RequestParam("banner") MultipartFile banner) {
        try {
            if (banner == null || banner.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo de banner es requerido");
            }

            SocioEntity socio = socioRepository.findById(id)
                .orElseThrow(() -> new Exception("Socio no encontrado"));

            // Nota: Necesitas agregar el campo bannerPublicId a SocioEntity
            String newPublicId = socioImagenService.actualizarBanner(
                id,
                null,  // Campo de banner en socio (agregar si no existe)
                banner
            );

            // Actualizar entidad
            // socio.setBannerPublicId(newPublicId);
            socioRepository.save(socio);

            return ResponseEntity.ok()
                .body("Banner actualizado exitosamente. Public ID: " + newPublicId);
                
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al actualizar banner: " + e.getMessage());
        }
    }

    /**
     * Actualizar foto de credencial de un socio
     * Esta es la foto que aparece en la credencial/carnet
     * Automáticamente elimina la foto anterior si existe
     */
    @PostMapping("/socio/{id}/credencial")
    public ResponseEntity<?> actualizarFotoCredencial(
            @PathVariable Integer id,
            @RequestParam("foto") MultipartFile foto) {
        try {
            if (foto == null || foto.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo de foto es requerido");
            }

            SocioEntity socio = socioRepository.findById(id)
                .orElseThrow(() -> new Exception("Socio no encontrado"));

            String newPublicId = socioImagenService.actualizarFotoCredencial(
                id,
                socio.getImagen(),  // O el campo específico para credencial
                foto
            );

            // Actualizar entidad
            socio.setImagen(newPublicId);
            socioRepository.save(socio);

            return ResponseEntity.ok()
                .body("Foto de credencial actualizada exitosamente. Public ID: " + newPublicId);
                
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al actualizar foto de credencial: " + e.getMessage());
        }
    }

    /**
     * Actualizar código QR de un socio
     * Automáticamente elimina el QR anterior si existe
     */
    @PostMapping("/socio/{id}/qr")
    public ResponseEntity<?> actualizarQRSocio(
            @PathVariable Integer id,
            @RequestParam("qr") MultipartFile qrImage) {
        try {
            if (qrImage == null || qrImage.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo de QR es requerido");
            }

            SocioEntity socio = socioRepository.findById(id)
                .orElseThrow(() -> new Exception("Socio no encontrado"));

            // Actualizar QR (elimina el anterior automáticamente)
            String newPublicId = socioImagenService.actualizarQR(
                id,
                socio.getQr(),  // Public ID anterior del QR
                qrImage
            );

            // Actualizar entidad
            socio.setQr(newPublicId);
            socioRepository.save(socio);

            return ResponseEntity.ok()
                .body("Código QR actualizado exitosamente. Public ID: " + newPublicId);
                
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al actualizar QR: " + e.getMessage());
        }
    }

    /**
     * Eliminar foto de perfil de un socio
     */
    @DeleteMapping("/socio/{id}/perfil")
    public ResponseEntity<?> eliminarFotoPerfilSocio(@PathVariable Integer id) {
        try {
            SocioEntity socio = socioRepository.findById(id)
                .orElseThrow(() -> new Exception("Socio no encontrado"));

            if (socio.getImagen() != null) {
                socioImagenService.eliminarFotoPerfil(socio.getImagen());
                socio.setImagen(null);
                socioRepository.save(socio);
            }

            return ResponseEntity.ok().body("Foto de perfil eliminada exitosamente");
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al eliminar foto de perfil: " + e.getMessage());
        }
    }

    /**
     * Eliminar código QR de un socio
     */
    @DeleteMapping("/socio/{id}/qr")
    public ResponseEntity<?> eliminarQRSocio(@PathVariable Integer id) {
        try {
            SocioEntity socio = socioRepository.findById(id)
                .orElseThrow(() -> new Exception("Socio no encontrado"));

            if (socio.getQr() != null) {
                socioImagenService.eliminarQR(socio.getQr());
                socio.setQr(null);
                socioRepository.save(socio);
            }

            return ResponseEntity.ok().body("Código QR eliminado exitosamente");
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al eliminar QR: " + e.getMessage());
        }
    }

    // ============================================================================
    // EJEMPLO DE ENDPOINT COMBINADO
    // ============================================================================

    /**
     * Actualizar múltiples imágenes de una empresa a la vez
     * Logo, banner y hasta 3 imágenes de galería
     */
    @PostMapping("/empresa/{id}/imagenes-completo")
    public ResponseEntity<?> actualizarImagenesCompleto(
            @PathVariable Integer id,
            @RequestParam(value = "logo", required = false) MultipartFile logo,
            @RequestParam(value = "banner", required = false) MultipartFile banner,
            @RequestParam(value = "galeria1", required = false) MultipartFile galeria1,
            @RequestParam(value = "galeria2", required = false) MultipartFile galeria2,
            @RequestParam(value = "galeria3", required = false) MultipartFile galeria3) {
        
        try {
            StringBuilder resultado = new StringBuilder("Imágenes actualizadas:\n");

            // Actualizar logo
            if (logo != null && !logo.isEmpty()) {
                ImagenesCatalogoEntity logoEntity = imagenCatalogoService.guardarImagen(
                    id, CloudinaryFolders.TIPO_PERFIL, logo);
                resultado.append("Logo: ").append(logoEntity.getImagen()).append("\n");
            }

            // Actualizar banner
            if (banner != null && !banner.isEmpty()) {
                ImagenesCatalogoEntity bannerEntity = imagenCatalogoService.guardarImagen(
                    id, CloudinaryFolders.TIPO_BANNER, banner);
                resultado.append("Banner: ").append(bannerEntity.getImagen()).append("\n");
            }

            // Actualizar galería (eliminar todas las existentes primero)
            List<ImagenesCatalogoEntity> galeriaActual = 
                imagenCatalogoService.findByCatalogoAndTipo(id, CloudinaryFolders.TIPO_GALERIA);
            
            for (ImagenesCatalogoEntity img : galeriaActual) {
                imagenCatalogoService.eliminarImagen(img.getId());
            }

            // Agregar nuevas imágenes de galería
            int galeriaCount = 0;
            if (galeria1 != null && !galeria1.isEmpty()) {
                ImagenesCatalogoEntity g1 = imagenCatalogoService.guardarImagen(
                    id, CloudinaryFolders.TIPO_GALERIA, galeria1);
                resultado.append("Galería 1: ").append(g1.getImagen()).append("\n");
                galeriaCount++;
            }
            if (galeria2 != null && !galeria2.isEmpty()) {
                ImagenesCatalogoEntity g2 = imagenCatalogoService.guardarImagen(
                    id, CloudinaryFolders.TIPO_GALERIA, galeria2);
                resultado.append("Galería 2: ").append(g2.getImagen()).append("\n");
                galeriaCount++;
            }
            if (galeria3 != null && !galeria3.isEmpty()) {
                ImagenesCatalogoEntity g3 = imagenCatalogoService.guardarImagen(
                    id, CloudinaryFolders.TIPO_GALERIA, galeria3);
                resultado.append("Galería 3: ").append(g3.getImagen()).append("\n");
                galeriaCount++;
            }

            resultado.append("\nTotal imágenes de galería: ").append(galeriaCount);

            return ResponseEntity.ok(resultado.toString());
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al actualizar imágenes: " + e.getMessage());
        }
    }
}
