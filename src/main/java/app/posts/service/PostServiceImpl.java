package app.posts.service;

import app.common.exception.ResourceNotFoundException;
import app.common.exception.InvalidDataException;
import app.common.exception.ImageUploadException;
import app.common.exception.UnauthorizedException;
import app.common.util.ArchivoService;
import app.core.entity.UsuarioEntity;
import app.core.repository.UsuarioRepository;
import app.posts.dto.*;
import app.posts.entity.PostEntity;
import app.posts.entity.PostSeccionEntity;
import app.posts.repository.PostRepository;
import app.posts.repository.PostSeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostSeccionRepository seccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ArchivoService archivoService;

    private static final String CLOUDINARY_FOLDER_PORTADA = "POST_PORTADA";
    private static final String CLOUDINARY_FOLDER_SECCION = "POST_SECCION";

    @Override
    @Transactional(readOnly = true)
    public List<PostSummaryDTO> findAllPublished(String tipo) {
        return postRepository.findAllPublishedSummaries(tipo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostSummaryDTO> findAll(String tipo) {
        return postRepository.findAllSummaries(tipo);
    }

    @Override
    @Transactional(readOnly = true)
    public PostDetailDTO findPublishedBySlug(String slug) {
        PostEntity post = postRepository.findPublishedBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "slug", slug));
        return toDetailDTO(post);
    }

    @Override
    @Transactional(readOnly = true)
    public PostDetailDTO findBySlug(String slug) {
        PostEntity post = postRepository.findBySlugWithSections(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "slug", slug));
        return toDetailDTO(post);
    }

    @Override
    @Transactional(readOnly = true)
    public PostDetailDTO findById(Integer id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        
        // Cargar secciones explícitamente si es lazy
        post.getSecciones().size(); // Force lazy loading
        
        return toDetailDTO(post);
    }

    @Override
    @Transactional
    public PostDetailDTO createPost(PostCreateDTO dto, Integer usuarioId) {
        // Validar usuario
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        // Crear entity
        PostEntity post = new PostEntity();
        post.setId(getNextId());
        post.setEstado(1);
        post.setUsuario(usuario);
        
        // Generar slug único
        post.setSlug(generateUniqueSlug(dto.getTitulo()));
        
        // Mapear campos básicos
        post.setTitulo(dto.getTitulo());
        post.setIntro(dto.getIntro());
        post.setAutor(dto.getAutor());
        post.setTipo(dto.getTipo());
        post.setPublicado(dto.getPublicado() != null ? dto.getPublicado() : false);
        post.setCreatedAt(LocalDateTime.now());
        
        // Campos de evento (pueden ser null)
        post.setFechaEvento(dto.getFechaEvento());
        post.setLugarEvento(dto.getLugarEvento());
        post.setDireccionEvento(dto.getDireccionEvento());

        // NO asignar portadaUrl todavía (se subirá después de guardar)

        // Guardar post primero SIN URLS de imágenes
        post = postRepository.save(post);

        // Mapear y agregar secciones SIN URLS de imágenes
        if (dto.getSecciones() != null && !dto.getSecciones().isEmpty()) {
            for (PostSeccionCreateDTO seccionDTO : dto.getSecciones()) {
                PostSeccionEntity seccion = createSeccionEntity(seccionDTO, post);
                post.addSeccion(seccion);
            }
        }

        // Guardar con las secciones (sin URLs aún)
        post = postRepository.save(post);

        // AHORA que el post está guardado, subir las imágenes Base64 a Cloudinary
        boolean needsUpdate = false;

        // 1. Subir imagen de portada si viene en Base64
        if (dto.getPortadaBase64() != null && !dto.getPortadaBase64().isEmpty()) {
            try {
                String portadaUrl = uploadBase64Image(dto.getPortadaBase64(), CLOUDINARY_FOLDER_PORTADA, "portada_" + post.getId() + "_" + System.currentTimeMillis());
                post.setPortadaUrl(portadaUrl);
                needsUpdate = true;
            } catch (Exception e) {
                System.err.println("Error al subir portada Base64: " + e.getMessage());
            }
        } else if (dto.getPortadaUrl() != null && !dto.getPortadaUrl().isEmpty()) {
            // Si ya viene una URL (caso de compatibilidad), usarla directamente
            post.setPortadaUrl(dto.getPortadaUrl());
            needsUpdate = true;
        }

        // 2. Subir imágenes de secciones si vienen en Base64
        for (int i = 0; i < post.getSecciones().size(); i++) {
            PostSeccionEntity seccion = post.getSecciones().get(i);
            PostSeccionCreateDTO seccionDTO = dto.getSecciones().get(i);
            
            if (seccionDTO.getImagenBase64() != null && !seccionDTO.getImagenBase64().isEmpty()) {
                try {
                    String imagenUrl = uploadBase64Image(seccionDTO.getImagenBase64(), CLOUDINARY_FOLDER_SECCION, "seccion_" + post.getId() + "_" + i + "_" + System.currentTimeMillis());
                    seccion.setImagenUrl(imagenUrl);
                    needsUpdate = true;
                } catch (Exception e) {
                    System.err.println("Error al subir imagen de sección Base64: " + e.getMessage());
                }
            } else if (seccionDTO.getImagenUrl() != null && !seccionDTO.getImagenUrl().isEmpty()) {
                // Si ya viene una URL, usarla directamente
                seccion.setImagenUrl(seccionDTO.getImagenUrl());
                needsUpdate = true;
            }
        }

        // 3. Guardar URLs actualizadas
        if (needsUpdate) {
            post = postRepository.save(post);
        }

        return toDetailDTO(post);
    }

    @Override
    @Transactional
    public PostDetailDTO updatePost(Integer id, PostUpdateDTO dto) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        // Actualizar solo los campos proporcionados (no nulos)
        if (dto.getTitulo() != null) {
            post.setTitulo(dto.getTitulo());
            // Regenerar slug si cambió el título
            post.setSlug(generateUniqueSlugForUpdate(dto.getTitulo(), id));
        }
        
        if (dto.getIntro() != null) {
            post.setIntro(dto.getIntro());
        }
        
        if (dto.getAutor() != null) {
            post.setAutor(dto.getAutor());
        }
        
        // Manejar actualización de portada de manera inteligente
        if (dto.getPortadaBase64() != null && !dto.getPortadaBase64().isEmpty()) {
            // Nueva imagen en Base64 → Eliminar la anterior y subir la nueva
            String oldPortadaUrl = post.getPortadaUrl();
            
            try {
                String newPortadaUrl = uploadBase64Image(dto.getPortadaBase64(), CLOUDINARY_FOLDER_PORTADA, "portada_" + post.getId() + "_" + System.currentTimeMillis());
                post.setPortadaUrl(newPortadaUrl);
                
                // Eliminar la imagen antigua SOLO si es diferente
                if (oldPortadaUrl != null && !oldPortadaUrl.isEmpty() && !oldPortadaUrl.equals(newPortadaUrl)) {
                    try {
                        deleteImage(oldPortadaUrl);
                    } catch (Exception e) {
                        System.err.println("Error al eliminar portada anterior: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.err.println("Error al subir nueva portada: " + e.getMessage());
                throw new ImageUploadException("Error al subir nueva portada", e);
            }
        } else if (dto.getPortadaUrl() != null) {
            // Se proporcionó una URL directamente (sin Base64)
            String oldPortadaUrl = post.getPortadaUrl();
            
            // Solo eliminar la vieja si es DIFERENTE a la nueva
            if (oldPortadaUrl != null && !oldPortadaUrl.isEmpty() && !oldPortadaUrl.equals(dto.getPortadaUrl())) {
                try {
                    deleteImage(oldPortadaUrl);
                } catch (Exception e) {
                    System.err.println("Error al eliminar portada anterior: " + e.getMessage());
                }
            }
            
            post.setPortadaUrl(dto.getPortadaUrl());
        }
        
        if (dto.getTipo() != null) {
            post.setTipo(dto.getTipo());
        }
        
        if (dto.getPublicado() != null) {
            post.setPublicado(dto.getPublicado());
        }
        
        if (dto.getFechaEvento() != null) {
            post.setFechaEvento(dto.getFechaEvento());
        }
        
        if (dto.getLugarEvento() != null) {
            post.setLugarEvento(dto.getLugarEvento());
        }
        
        if (dto.getDireccionEvento() != null) {
            post.setDireccionEvento(dto.getDireccionEvento());
        }

        // Si se proporcionan secciones, comparar y actualizar inteligentemente
        if (dto.getSecciones() != null) {
            // Recopilar URLs de imágenes antiguas
            List<String> oldImageUrls = post.getSecciones().stream()
                    .map(PostSeccionEntity::getImagenUrl)
                    .filter(url -> url != null && !url.isEmpty())
                    .collect(Collectors.toList());
            
            // Limpiar secciones existentes (orphanRemoval hace el delete en BD)
            post.getSecciones().clear();
            
            // Agregar nuevas secciones
            List<String> newImageUrls = new ArrayList<>();
            for (int i = 0; i < dto.getSecciones().size(); i++) {
                PostSeccionCreateDTO seccionDTO = dto.getSecciones().get(i);
                PostSeccionEntity seccion = createSeccionEntity(seccionDTO, post);
                
                // Si la sección tiene imagen en Base64, subirla
                if (seccionDTO.getImagenBase64() != null && !seccionDTO.getImagenBase64().isEmpty()) {
                    try {
                        String imagenUrl = uploadBase64Image(seccionDTO.getImagenBase64(), CLOUDINARY_FOLDER_SECCION, "seccion_" + post.getId() + "_" + i + "_" + System.currentTimeMillis());
                        seccion.setImagenUrl(imagenUrl);
                        newImageUrls.add(imagenUrl);
                    } catch (Exception e) {
                        System.err.println("Error al subir imagen de sección: " + e.getMessage());
                    }
                } else if (seccionDTO.getImagenUrl() != null && !seccionDTO.getImagenUrl().isEmpty()) {
                    // Si ya tiene URL, conservarla
                    newImageUrls.add(seccionDTO.getImagenUrl());
                }
                
                post.addSeccion(seccion);
            }
            
            // Eliminar de Cloudinary SOLO las imágenes que ya NO están en las nuevas secciones
            for (String oldUrl : oldImageUrls) {
                if (!newImageUrls.contains(oldUrl)) {
                    try {
                        deleteImage(oldUrl);
                        System.out.println("Imagen obsoleta eliminada: " + oldUrl);
                    } catch (Exception e) {
                        System.err.println("Error al eliminar imagen obsoleta: " + e.getMessage());
                    }
                }
            }
        }

        post = postRepository.save(post);
        return toDetailDTO(post);
    }

    @Override
    @Transactional
    public void deletePost(Integer id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        // Eliminar portada de Cloudinary
        if (post.getPortadaUrl() != null && !post.getPortadaUrl().isEmpty()) {
            try {
                deleteImage(post.getPortadaUrl());
            } catch (Exception e) {
                System.err.println("Error al eliminar portada: " + e.getMessage());
            }
        }

        // Eliminar imágenes de secciones de Cloudinary
        for (PostSeccionEntity seccion : post.getSecciones()) {
            if (seccion.getImagenUrl() != null && !seccion.getImagenUrl().isEmpty()) {
                try {
                    deleteImage(seccion.getImagenUrl());
                } catch (Exception e) {
                    System.err.println("Error al eliminar imagen de sección: " + e.getMessage());
                }
            }
        }

        // Borrado lógico
        post.setEstado(0);
        postRepository.save(post);
    }

    @Override
    @Transactional
    public PostDetailDTO togglePublicado(Integer id, Boolean publicado) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        
        post.setPublicado(publicado);
        post = postRepository.save(post);
        
        return toDetailDTO(post);
    }

    @Override
    public String uploadPortada(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ImageUploadException("El archivo está vacío");
        }
        
        try {
            String publicId = "portada_" + System.currentTimeMillis();
            return archivoService.subirImagen(CLOUDINARY_FOLDER_PORTADA, file, publicId);
        } catch (Exception e) {
            System.err.println("Error al subir portada: " + e.getMessage());
            throw new ImageUploadException("Error al subir portada: " + e.getMessage(), e);
        }
    }

    @Override
    public String uploadSeccionImagen(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ImageUploadException("El archivo está vacío");
        }
        
        try {
            String publicId = "seccion_" + System.currentTimeMillis();
            return archivoService.subirImagen(CLOUDINARY_FOLDER_SECCION, file, publicId);
        } catch (Exception e) {
            System.err.println("Error al subir imagen de sección: " + e.getMessage());
            throw new ImageUploadException("Error al subir imagen de sección: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteImage(String publicId) {
        if (publicId != null && !publicId.isEmpty()) {
            try {
                archivoService.eliminarImagen(publicId);
            } catch (Exception e) {
                System.err.println("Error al eliminar imagen de Cloudinary: " + e.getMessage());
                throw new ImageUploadException("Error al eliminar imagen: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public String generateUniqueSlug(String titulo) {
        String baseSlug = slugify(titulo);
        String slug = baseSlug;
        int counter = 1;
        
        while (postRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }
        
        return slug;
    }

    @Override
    public String generateUniqueSlugForUpdate(String titulo, Integer postId) {
        String baseSlug = slugify(titulo);
        String slug = baseSlug;
        int counter = 1;
        
        while (postRepository.existsBySlugAndIdNot(slug, postId)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }
        
        return slug;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostSummaryDTO> findLatestPublished(int limit) {
        List<PostSummaryDTO> all = postRepository.findLatestPublished();
        return all.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getNextId() {
        try {
            return postRepository.getNextId();
        } catch (Exception e) {
            System.err.println("Error al obtener próximo ID: " + e.getMessage());
            return 1;
        }
    }

    // ==================== MÉTODOS PRIVADOS DE UTILIDAD ====================

    /**
     * Convierte una imagen Base64 a MultipartFile y la sube a Cloudinary
     * @param base64String String Base64 de la imagen (puede incluir data:image/...;base64, o no)
     * @param folder Carpeta de Cloudinary (POST_PORTADA o POST_SECCION)
     * @param publicId Public ID para la imagen
     * @return URL de Cloudinary de la imagen subida
     */
    private String uploadBase64Image(String base64String, String folder, String publicId) throws Exception {
        try {
            // Limpiar el string Base64 (remover el prefijo data:image/...;base64, si existe)
            String base64Data = base64String;
            if (base64String.contains(",")) {
                base64Data = base64String.split(",")[1];
            }
            
            // Decodificar Base64 a bytes
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);
            
            // Detectar el tipo de contenido de la imagen
            String contentType = "image/png"; // Por defecto PNG
            if (base64String.contains("data:image/")) {
                String header = base64String.substring(0, base64String.indexOf(";"));
                contentType = header.replace("data:", "");
            }
            
            // Crear un MultipartFile custom desde los bytes
            String filename = publicId + getImageExtension(contentType);
            MultipartFile multipartFile = new Base64MultipartFile(
                    imageBytes,
                    filename,
                    contentType
            );
            
            // Subir a Cloudinary usando el servicio existente
            return archivoService.subirImagen(folder, multipartFile, publicId);
            
        } catch (Exception e) {
            System.err.println("Error al procesar imagen Base64: " + e.getMessage());
            throw new ImageUploadException("Error al procesar imagen Base64: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene la extensión de archivo según el tipo de contenido
     */
    private String getImageExtension(String contentType) {
        if (contentType.contains("jpeg") || contentType.contains("jpg")) {
            return ".jpg";
        } else if (contentType.contains("png")) {
            return ".png";
        } else if (contentType.contains("gif")) {
            return ".gif";
        } else if (contentType.contains("webp")) {
            return ".webp";
        }
        return ".png"; // Por defecto
    }

    /**
     * Convierte un título a formato slug (URL-friendly)
     */
    private String slugify(String text) {
        if (text == null || text.isEmpty()) {
            return "post-" + System.currentTimeMillis();
        }
        
        return text.trim()
                .toLowerCase()
                .replaceAll("[áàäâ]", "a")
                .replaceAll("[éèëê]", "e")
                .replaceAll("[íìïî]", "i")
                .replaceAll("[óòöô]", "o")
                .replaceAll("[úùüû]", "u")
                .replaceAll("[ñ]", "n")
                .replaceAll("[^a-z0-9\\s-]", "") // Remover caracteres especiales
                .replaceAll("\\s+", "-") // Reemplazar espacios por guiones
                .replaceAll("-+", "-") // Remover guiones múltiples
                .replaceAll("^-|-$", ""); // Remover guiones al inicio/fin
    }

    /**
     * Crea una entidad PostSeccionEntity desde un DTO
     */
    private PostSeccionEntity createSeccionEntity(PostSeccionCreateDTO dto, PostEntity post) {
        PostSeccionEntity seccion = new PostSeccionEntity();
        
        try {
            seccion.setId(seccionRepository.getNextId());
        } catch (Exception e) {
            seccion.setId(1);
        }
        
        seccion.setEstado(1);
        seccion.setPost(post);
        seccion.setOrden(dto.getOrden());
        seccion.setTipoSeccion(dto.getTipoSeccion());
        seccion.setSubtitulo(dto.getSubtitulo());
        seccion.setContenido(dto.getContenido());
        seccion.setImagenUrl(dto.getImagenUrl());
        seccion.setVideoUrl(dto.getVideoUrl());
        
        return seccion;
    }

    /**
     * Convierte PostEntity a PostDetailDTO
     */
    private PostDetailDTO toDetailDTO(PostEntity post) {
        PostDetailDTO dto = new PostDetailDTO();
        dto.setId(post.getId());
        dto.setTitulo(post.getTitulo());
        dto.setSlug(post.getSlug());
        dto.setIntro(post.getIntro());
        dto.setPortadaUrl(post.getPortadaUrl());
        dto.setAutor(post.getAutor());
        dto.setTipo(post.getTipo());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setPublicado(post.getPublicado());
        dto.setFechaEvento(post.getFechaEvento());
        dto.setLugarEvento(post.getLugarEvento());
        dto.setDireccionEvento(post.getDireccionEvento());
        
        // Usuario autor
        if (post.getUsuario() != null && post.getUsuario().getPersona() != null) {
            dto.setUsuarioNombre(post.getUsuario().getPersona().getNombrecompleto());
        }
        
        // Convertir secciones a DTOs
        List<PostSeccionDTO> seccionDTOs = post.getSecciones().stream()
                .filter(s -> s.getEstado() == 1)
                .map(this::toSeccionDTO)
                .collect(Collectors.toList());
        dto.setSecciones(seccionDTOs);
        
        return dto;
    }

    /**
     * Convierte PostSeccionEntity a PostSeccionDTO
     */
    private PostSeccionDTO toSeccionDTO(PostSeccionEntity seccion) {
        return new PostSeccionDTO(
                seccion.getId(),
                seccion.getOrden(),
                seccion.getTipoSeccion(),
                seccion.getSubtitulo(),
                seccion.getContenido(),
                seccion.getImagenUrl(),
                seccion.getVideoUrl()
        );
    }

    /**
     * Implementación simple de MultipartFile para imágenes Base64
     */
    private static class Base64MultipartFile implements MultipartFile {
        private final byte[] content;
        private final String name;
        private final String contentType;

        public Base64MultipartFile(byte[] content, String name, String contentType) {
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
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(content);
        }

        @Override
        public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(dest)) {
                fos.write(content);
            }
        }
    }
}
