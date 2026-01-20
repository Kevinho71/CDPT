package app.examples;

import app.common.util.CloudinaryFolders;
import app.common.util.QRCodeGeneratorService;
import app.common.util.SocioImagenService;
import app.socio.entity.SocioEntity;
import app.socio.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * SERVICIO DE EJEMPLO PARA GENERACIÓN Y ALMACENAMIENTO DE QR EN CLOUDINARY
 * 
 * Este servicio demuestra cómo:
 * 1. Generar un código QR para un socio
 * 2. Subirlo automáticamente a Cloudinary (carpeta SOCIO_QR)
 * 3. Actualizar el registro del socio con el public_id de Cloudinary
 * 4. Eliminar el QR anterior automáticamente
 * 
 * NOTA: Este es un archivo de ejemplo. Adapte el código a su servicio de socios.
 */
@Service
public class SocioQRCloudinaryExample {

    @Autowired
    private QRCodeGeneratorService qrCodeGeneratorService;
    
    @Autowired
    private SocioImagenService socioImagenService;
    
    @Autowired
    private SocioRepository socioRepository;

    /**
     * Genera un nuevo QR para el socio y lo sube a Cloudinary
     * Elimina automáticamente el QR anterior si existe
     * 
     * @param socioId El ID del socio
     * @param qrContent El contenido del QR (URL, texto, JSON, etc.)
     * @return El public_id de Cloudinary del QR generado
     * @throws Exception si ocurre un error
     */
    public String generarYSubirQR(Integer socioId, String qrContent) throws Exception {
        try {
            SocioEntity socio = socioRepository.findById(socioId)
                .orElseThrow(() -> new Exception("Socio no encontrado con ID: " + socioId));

            // 1. Generar el código QR como imagen
            byte[] qrImageBytes = qrCodeGeneratorService.generateQRCodeImage(qrContent, 300, 300);
            
            // 2. Convertir a MultipartFile para subirlo
            MultipartFile qrMultipartFile = new MultipartFileFromBytes(
                qrImageBytes, 
                "qr_socio_" + socioId + ".png",
                "image/png"
            );

            // 3. Subir a Cloudinary (elimina el anterior automáticamente)
            String newPublicId = socioImagenService.actualizarQR(
                socioId,
                socio.getQr(),  // Public ID del QR anterior (puede ser null)
                qrMultipartFile
            );

            // 4. Actualizar el socio con el nuevo public_id
            socio.setQr(newPublicId);
            
            // También puedes actualizar el linkqr con la URL de Cloudinary
            String cloudinaryUrl = obtenerURLCloudinary(newPublicId);
            socio.setLinkqr(cloudinaryUrl);
            
            socioRepository.save(socio);

            System.out.println("QR generado y subido a Cloudinary: " + newPublicId);
            return newPublicId;
            
        } catch (Exception e) {
            System.err.println("Error al generar y subir QR: " + e.getMessage());
            throw new Exception("Error al generar QR para socio: " + e.getMessage());
        }
    }

    /**
     * Genera un QR con información completa del socio en formato JSON
     * y lo sube a Cloudinary
     * 
     * @param socioId El ID del socio
     * @return El public_id de Cloudinary
     * @throws Exception si ocurre un error
     */
    public String generarQRConInformacionCompleta(Integer socioId) throws Exception {
        try {
            SocioEntity socio = socioRepository.findById(socioId)
                .orElseThrow(() -> new Exception("Socio no encontrado"));

            // Crear contenido del QR con información del socio
            String qrContent = String.format(
                "{\"id\":%d,\"codigo\":%d,\"matricula\":\"%s\",\"nombre\":\"%s\",\"documento\":\"%s\"}",
                socio.getId(),
                socio.getCodigo(),
                socio.getMatricula() != null ? socio.getMatricula() : "",
                socio.getNombresocio() != null ? socio.getNombresocio() : "",
                socio.getNrodocumento() != null ? socio.getNrodocumento() : ""
            );

            return generarYSubirQR(socioId, qrContent);
            
        } catch (Exception e) {
            throw new Exception("Error al generar QR con información completa: " + e.getMessage());
        }
    }

    /**
     * Genera un QR con una URL de verificación del socio
     * 
     * @param socioId El ID del socio
     * @param baseUrl La URL base de la aplicación (ej: "https://cadet.com")
     * @return El public_id de Cloudinary
     * @throws Exception si ocurre un error
     */
    public String generarQRConURLVerificacion(Integer socioId, String baseUrl) throws Exception {
        try {
            String verificacionUrl = baseUrl + "/verificar-socio/" + socioId;
            return generarYSubirQR(socioId, verificacionUrl);
            
        } catch (Exception e) {
            throw new Exception("Error al generar QR con URL: " + e.getMessage());
        }
    }

    /**
     * Regenera el QR de un socio (útil cuando cambian sus datos)
     * 
     * @param socioId El ID del socio
     * @return El nuevo public_id de Cloudinary
     * @throws Exception si ocurre un error
     */
    public String regenerarQR(Integer socioId) throws Exception {
        try {
            return generarQRConInformacionCompleta(socioId);
            
        } catch (Exception e) {
            throw new Exception("Error al regenerar QR: " + e.getMessage());
        }
    }

    /**
     * Elimina el QR de un socio de Cloudinary
     * 
     * @param socioId El ID del socio
     * @throws Exception si ocurre un error
     */
    public void eliminarQR(Integer socioId) throws Exception {
        try {
            SocioEntity socio = socioRepository.findById(socioId)
                .orElseThrow(() -> new Exception("Socio no encontrado"));

            if (socio.getQr() != null && !socio.getQr().isEmpty()) {
                socioImagenService.eliminarQR(socio.getQr());
                socio.setQr(null);
                socio.setLinkqr(null);
                socioRepository.save(socio);
                
                System.out.println("QR eliminado para socio: " + socioId);
            }
            
        } catch (Exception e) {
            throw new Exception("Error al eliminar QR: " + e.getMessage());
        }
    }

    /**
     * Obtiene la URL pública de Cloudinary para un public_id
     * 
     * @param publicId El public_id de Cloudinary (ej: "SOCIO_QR/socio_123_qr_1234567890")
     * @return La URL completa de la imagen en Cloudinary
     */
    private String obtenerURLCloudinary(String publicId) {
        // Formato: https://res.cloudinary.com/{cloud_name}/image/upload/{public_id}
        // Nota: Necesitarás inyectar la configuración de Cloudinary para obtener cloud_name
        // o construir la URL manualmente
        
        // Ejemplo básico (ajustar según tu configuración):
        String cloudName = "tu-cloud-name"; // Obtener de configuración
        return String.format("https://res.cloudinary.com/%s/image/upload/%s.png", 
                           cloudName, publicId);
    }

    // ============================================================================
    // CLASE HELPER PARA CONVERTIR BYTES A MULTIPARTFILE
    // ============================================================================

    /**
     * Clase helper para convertir un array de bytes en MultipartFile
     * Necesaria porque el QRCodeGeneratorService devuelve bytes
     */
    private static class MultipartFileFromBytes implements MultipartFile {
        private final byte[] content;
        private final String name;
        private final String contentType;

        public MultipartFileFromBytes(byte[] content, String name, String contentType) {
            this.content = content;
            this.name = name;
            this.contentType = contentType;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOriginalFilename() {
            return name;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return content == null || content.length == 0;
        }

        @Override
        public long getSize() {
            return content.length;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return content;
        }

        @Override
        public java.io.InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(content);
        }

        @Override
        public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
            java.nio.file.Files.write(dest.toPath(), content);
        }
    }
}
