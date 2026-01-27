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

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        post.setPortadaUrl(dto.getPortadaUrl());
        post.setTipo(dto.getTipo());
        post.setPublicado(dto.getPublicado() != null ? dto.getPublicado() : false);
        post.setCreatedAt(LocalDateTime.now());
        
        // Campos de evento (pueden ser null)
        post.setFechaEvento(dto.getFechaEvento());
        post.setLugarEvento(dto.getLugarEvento());
        post.setDireccionEvento(dto.getDireccionEvento());

        // Guardar post primero
        post = postRepository.save(post);

        // Mapear y agregar secciones
        if (dto.getSecciones() != null && !dto.getSecciones().isEmpty()) {
            for (PostSeccionCreateDTO seccionDTO : dto.getSecciones()) {
                PostSeccionEntity seccion = createSeccionEntity(seccionDTO, post);
                post.addSeccion(seccion);
            }
            // Guardar con las secciones
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
        
        if (dto.getPortadaUrl() != null) {
            // Si hay una nueva portada, eliminar la anterior de Cloudinary
            if (post.getPortadaUrl() != null && !post.getPortadaUrl().isEmpty()) {
                try {
                    deleteImage(post.getPortadaUrl());
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

        // Si se proporcionan secciones, reemplazar todas
        if (dto.getSecciones() != null) {
            // Eliminar imágenes de secciones anteriores de Cloudinary
            for (PostSeccionEntity oldSeccion : post.getSecciones()) {
                if (oldSeccion.getImagenUrl() != null && !oldSeccion.getImagenUrl().isEmpty()) {
                    try {
                        deleteImage(oldSeccion.getImagenUrl());
                    } catch (Exception e) {
                        System.err.println("Error al eliminar imagen de sección: " + e.getMessage());
                    }
                }
            }
            
            // Limpiar secciones existentes (orphanRemoval hace el delete en BD)
            post.getSecciones().clear();
            
            // Agregar nuevas secciones
            for (PostSeccionCreateDTO seccionDTO : dto.getSecciones()) {
                PostSeccionEntity seccion = createSeccionEntity(seccionDTO, post);
                post.addSeccion(seccion);
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
}
