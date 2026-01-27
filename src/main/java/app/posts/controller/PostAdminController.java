package app.posts.controller;

import app.common.exception.UnauthorizedException;
import app.core.entity.UsuarioEntity;
import app.posts.dto.*;
import app.posts.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador administrativo para gestión completa de posts
 * Requiere autenticación y rol ADMIN
 */
@RestController
@RequestMapping("/api/admin/posts")
public class PostAdminController {

    @Autowired
    private PostService postService;

    /**
     * GET /api/admin/posts
     * Lista TODOS los posts (publicados y borradores)
     */
    @GetMapping
    public ResponseEntity<PostListResponse> getAllPosts(
            @RequestParam(required = false) String tipo) {
        
        PostListResponse response = new PostListResponse(postService.findAll(tipo));
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/admin/posts/{id}
     * Obtiene detalle completo de un post por ID (incluye borradores)
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDetailDTO> getPostById(@PathVariable Integer id) {
        PostDetailDTO post = postService.findById(id);
        return ResponseEntity.ok(post);
    }

    /**
     * GET /api/admin/posts/slug/{slug}
     * Obtiene post por slug (incluye borradores)
     */
    @GetMapping("/slug/{slug}")
    public ResponseEntity<PostDetailDTO> getPostBySlug(@PathVariable String slug) {
        PostDetailDTO post = postService.findBySlug(slug);
        return ResponseEntity.ok(post);
    }

    /**
     * POST /api/admin/posts
     * Crea un nuevo post
     */
    @PostMapping
    public ResponseEntity<PostDetailDTO> createPost(@Valid @RequestBody PostCreateDTO dto) {
        Integer usuarioId = getCurrentUserId();
        PostDetailDTO post = postService.createPost(dto, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    /**
     * PUT /api/admin/posts/{id}
     * Actualiza un post existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostDetailDTO> updatePost(
            @PathVariable Integer id,
            @Valid @RequestBody PostUpdateDTO dto) {
        
        PostDetailDTO post = postService.updatePost(id, dto);
        return ResponseEntity.ok(post);
    }

    /**
     * DELETE /api/admin/posts/{id}
     * Elimina un post (borrado lógico)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * PATCH /api/admin/posts/{id}/publicar
     * Cambia el estado de publicación de un post
     */
    @PatchMapping("/{id}/publicar")
    public ResponseEntity<PostDetailDTO> togglePublicado(
            @PathVariable Integer id,
            @RequestBody(required = false) PublicarRequest request) {
        
        Boolean publicado = (request != null && request.getPublicado() != null) 
                ? request.getPublicado() 
                : true;
        
        PostDetailDTO post = postService.togglePublicado(id, publicado);
        return ResponseEntity.ok(post);
    }

    /**
     * POST /api/admin/posts/upload/portada
     * Sube una imagen de portada a Cloudinary
     */
    @PostMapping("/upload/portada")
    public ResponseEntity<ImageUploadResponse> uploadPortada(@RequestParam("file") MultipartFile file) {
        String url = postService.uploadPortada(file);
        ImageUploadResponse response = new ImageUploadResponse(url, "Portada subida exitosamente");
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/admin/posts/upload/seccion
     * Sube una imagen de sección a Cloudinary
     */
    @PostMapping("/upload/seccion")
    public ResponseEntity<ImageUploadResponse> uploadSeccionImagen(@RequestParam("file") MultipartFile file) {
        String url = postService.uploadSeccionImagen(file);
        ImageUploadResponse response = new ImageUploadResponse(url, "Imagen de sección subida exitosamente");
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/admin/posts/upload/{publicId}
     * Elimina una imagen de Cloudinary
     */
    @DeleteMapping("/upload/{publicId}")
    public ResponseEntity<Void> deleteImage(@PathVariable String publicId) {
        postService.deleteImage(publicId);
        return ResponseEntity.noContent().build();
    }

    // ==================== MÉTODOS PRIVADOS ====================

    /**
     * Obtiene el ID del usuario autenticado desde el contexto de seguridad
     */
    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Usuario no autenticado");
        }
        
        Object principal = authentication.getPrincipal();
        
        // Si el principal es un UsuarioEntity directamente
        if (principal instanceof UsuarioEntity) {
            return ((UsuarioEntity) principal).getId();
        }
        
        // Si el principal es un UserDetails con username
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            // Por ahora retornamos un ID por defecto
            // TODO: Implementar obtención de usuario por username
            return 1; // Temporal
        }
        
        throw new UnauthorizedException("No se pudo obtener el usuario autenticado");
    }

    // DTO interno para request de publicar
    public static class PublicarRequest {
        private Boolean publicado;

        public Boolean getPublicado() {
            return publicado;
        }

        public void setPublicado(Boolean publicado) {
            this.publicado = publicado;
        }
    }
}
