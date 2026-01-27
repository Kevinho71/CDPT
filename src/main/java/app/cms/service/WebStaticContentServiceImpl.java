package app.cms.service;

import app.cms.dto.ContentCreateDTO;
import app.cms.dto.ContentDTO;
import app.cms.entity.WebStaticContentEntity;
import app.cms.repository.WebStaticContentRepository;
import app.common.exception.ImageUploadException;
import app.common.exception.InvalidDataException;
import app.common.exception.ResourceNotFoundException;
import app.common.util.ArchivoService;
import app.core.entity.UsuarioEntity;
import app.core.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WebStaticContentServiceImpl implements WebStaticContentService {

    @Autowired
    private WebStaticContentRepository contentRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ArchivoService archivoService;

    private static final String CLOUDINARY_FOLDER = "WEB_CONTENT";

    @Override
    @Transactional
    public ContentDTO createContent(ContentCreateDTO createDTO, String usuarioEmail) {
        // Validar que la clave no exista
        if (contentRepository.existsByClave(createDTO.getClave())) {
            throw new InvalidDataException("Ya existe un contenido con la clave: " + createDTO.getClave());
        }

        // Validar que el valor no esté vacío en creación
        if (createDTO.getValor() == null || createDTO.getValor().trim().isEmpty()) {
            throw new InvalidDataException("El valor no puede estar vacío al crear un contenido");
        }

        UsuarioEntity usuario = usuarioRepository.findByUsername(usuarioEmail);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado: " + usuarioEmail);
        }

        WebStaticContentEntity entity = new WebStaticContentEntity();
        entity.setClave(createDTO.getClave());
        entity.setSeccion(createDTO.getSeccion());
        entity.setTipoInput(createDTO.getTipoInput() != null ? createDTO.getTipoInput() : "TEXT");
        entity.setUsuarioModificador(usuario);
        entity.setFechaModificacion(LocalDateTime.now());

        // Si es tipo IMAGE y viene Base64, subir a Cloudinary
        String valorFinal = createDTO.getValor();
        if ("IMAGE".equalsIgnoreCase(createDTO.getTipoInput()) && isBase64(valorFinal)) {
            try {
                String cloudinaryUrl = uploadBase64Image(valorFinal);
                valorFinal = cloudinaryUrl;
            } catch (Exception e) {
                throw new ImageUploadException("Error al subir la imagen a Cloudinary: " + e.getMessage());
            }
        }

        entity.setValor(valorFinal);
        WebStaticContentEntity saved = contentRepository.save(entity);

        return toDTO(saved);
    }

    @Override
    @Transactional
    public List<ContentDTO> batchUpdateContent(Map<String, String> updates, String usuarioEmail) {
        if (updates == null || updates.isEmpty()) {
            throw new InvalidDataException("No se enviaron datos para actualizar");
        }

        UsuarioEntity usuario = usuarioRepository.findByUsername(usuarioEmail);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado: " + usuarioEmail);
        }

        List<ContentDTO> updatedContents = new ArrayList<>();

        for (Map.Entry<String, String> entry : updates.entrySet()) {
            String clave = entry.getKey();
            String nuevoValor = entry.getValue();

            // Validar que la clave exista
            WebStaticContentEntity entity = contentRepository.findByClave(clave)
                    .orElseThrow(() -> new ResourceNotFoundException("No existe contenido con la clave: " + clave));

            // Si el valor no cambió, saltar
            if (nuevoValor != null && nuevoValor.equals(entity.getValor())) {
                continue;
            }

            String valorAnterior = entity.getValor();
            String valorFinal = nuevoValor;

            // Si es tipo IMAGE, manejar lógica de imágenes
            if ("IMAGE".equalsIgnoreCase(entity.getTipoInput())) {
                if (isBase64(nuevoValor)) {
                    // Es una nueva imagen en Base64 → Subir a Cloudinary
                    try {
                        String cloudinaryUrl = uploadBase64Image(nuevoValor);
                        valorFinal = cloudinaryUrl;

                        // Eliminar la imagen anterior de Cloudinary (si existe y es una URL de Cloudinary)
                        if (valorAnterior != null && isCloudinaryUrl(valorAnterior)) {
                            deleteCloudinaryImage(valorAnterior);
                        }
                    } catch (Exception e) {
                        throw new ImageUploadException("Error al subir imagen para clave " + clave + ": " + e.getMessage());
                    }
                } else if (isCloudinaryUrl(nuevoValor)) {
                    // Es una URL de Cloudinary existente → No hacer nada, mantener el valor
                    valorFinal = nuevoValor;
                } else if (nuevoValor == null || nuevoValor.trim().isEmpty()) {
                    // Valor vacío → Eliminar imagen anterior si existe
                    if (valorAnterior != null && isCloudinaryUrl(valorAnterior)) {
                        deleteCloudinaryImage(valorAnterior);
                    }
                    valorFinal = null;
                }
            }

            // Actualizar entity
            entity.setValor(valorFinal);
            entity.setUsuarioModificador(usuario);
            entity.setFechaModificacion(LocalDateTime.now());

            WebStaticContentEntity saved = contentRepository.save(entity);
            updatedContents.add(toDTO(saved));
        }

        return updatedContents;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentDTO> getAllContentList() {
        return contentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> getAllContentMap() {
        List<WebStaticContentEntity> contents = contentRepository.findAll();
        Map<String, String> contentMap = new HashMap<>();

        for (WebStaticContentEntity entity : contents) {
            contentMap.put(entity.getClave(), entity.getValor());
        }

        return contentMap;
    }

    @Override
    @Transactional(readOnly = true)
    public ContentDTO getContentByClave(String clave) {
        WebStaticContentEntity entity = contentRepository.findByClave(clave)
                .orElseThrow(() -> new ResourceNotFoundException("No existe contenido con la clave: " + clave));
        return toDTO(entity);
    }

    // ============================================================================
    // MÉTODOS AUXILIARES
    // ============================================================================

    /**
     * Convierte Entity a DTO
     */
    private ContentDTO toDTO(WebStaticContentEntity entity) {
        ContentDTO dto = new ContentDTO();
        dto.setClave(entity.getClave());
        dto.setValor(entity.getValor());
        dto.setSeccion(entity.getSeccion());
        dto.setTipoInput(entity.getTipoInput());
        dto.setFechaModificacion(entity.getFechaModificacion());

        if (entity.getUsuarioModificador() != null) {
            dto.setUsuarioModificador(entity.getUsuarioModificador().getUsername());
        }

        return dto;
    }

    /**
     * Verifica si un string es Base64
     */
    private boolean isBase64(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        // Detectar formato: data:image/...;base64,XXXXX
        return value.startsWith("data:image/") && value.contains(";base64,");
    }

    /**
     * Verifica si una URL es de Cloudinary
     */
    private boolean isCloudinaryUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        return url.contains("cloudinary.com") || url.contains("res.cloudinary.com");
    }

    /**
     * Sube una imagen Base64 a Cloudinary
     */
    private String uploadBase64Image(String base64Data) throws IOException {
        // Extraer el Base64 puro (sin el prefijo data:image/...;base64,)
        String[] parts = base64Data.split(",");
        if (parts.length != 2) {
            throw new InvalidDataException("Formato de Base64 inválido");
        }

        String base64Image = parts[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Convertir a MultipartFile usando clase interna
        MultipartFile multipartFile = new Base64MultipartFile(imageBytes, "image.jpg");

        // Subir a Cloudinary usando ArchivoService
        // Generar un publicId único basado en timestamp
        String publicId = "content_" + System.currentTimeMillis();
        return archivoService.subirImagen(CLOUDINARY_FOLDER, multipartFile, publicId);
    }

    /**
     * Elimina una imagen de Cloudinary
     */
    private void deleteCloudinaryImage(String imageUrl) {
        try {
            archivoService.eliminarImagen(imageUrl);
        } catch (Exception e) {
            // Log del error pero no detener la operación
            System.err.println("Error al eliminar imagen de Cloudinary: " + imageUrl + " - " + e.getMessage());
        }
    }

    // ============================================================================
    // CLASE INTERNA: Base64MultipartFile
    // ============================================================================

    /**
     * Implementación de MultipartFile para convertir bytes Base64
     */
    private static class Base64MultipartFile implements MultipartFile {
        private final byte[] content;
        private final String name;

        public Base64MultipartFile(byte[] content, String name) {
            this.content = content;
            this.name = name;
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
            return "image/jpeg";
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
        public byte[] getBytes() {
            return content;
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(content);
        }

        @Override
        public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
            throw new UnsupportedOperationException("transferTo not supported");
        }
    }
}
