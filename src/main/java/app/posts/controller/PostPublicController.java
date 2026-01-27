package app.posts.controller;

import app.posts.dto.PostDetailDTO;
import app.posts.dto.PostListResponse;
import app.posts.dto.PostSummaryDTO;
import app.posts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador público para consulta de posts (noticias y eventos)
 * No requiere autenticación
 * Soporta ambas rutas: /api/public/posts y /public/posts
 */
@RestController
@RequestMapping("/api/public/posts")
public class PostPublicController {

    @Autowired
    private PostService postService;

    /**
     * GET /api/public/posts
     * Lista todos los posts publicados
     * 
     * Ejemplos:
     * - GET /api/public/posts -> Todos los posts
     * - GET /api/public/posts?tipo=NOTICIA -> Solo noticias
     * - GET /api/public/posts?tipo=EVENTO -> Solo eventos
     */
    @GetMapping
    public ResponseEntity<PostListResponse> getAllPublishedPosts(
            @RequestParam(required = false) String tipo) {
        
        PostListResponse response = new PostListResponse(postService.findAllPublished(tipo));
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/public/posts/{slug}
     * Obtiene el detalle completo de un post por su slug
     * 
     * Ejemplo:
     * - GET /api/public/posts/nueva-directiva-2026
     */
    @GetMapping("/{slug}")
    public ResponseEntity<PostDetailDTO> getPostBySlug(@PathVariable String slug) {
        PostDetailDTO post = postService.findPublishedBySlug(slug);
        return ResponseEntity.ok(post);
    }

    /**
     * GET /api/public/posts/latest/{limit}
     * Obtiene los últimos N posts publicados
     * 
     * Ejemplo:
     * - GET /api/public/posts/latest/5 -> Últimos 5 posts
     */
    @GetMapping("/latest/{limit}")
    public ResponseEntity<PostListResponse> getLatestPosts(@PathVariable int limit) {
        PostListResponse response = new PostListResponse(postService.findLatestPublished(limit));
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/public/posts/noticias
     * Atajo para obtener solo noticias
     */
    @GetMapping("/noticias")
    public ResponseEntity<PostListResponse> getNoticias() {
        return getAllPublishedPosts("NOTICIA");
    }

    /**
     * GET /api/public/posts/eventos
     * Atajo para obtener solo eventos
     */
    @GetMapping("/eventos")
    public ResponseEntity<PostListResponse> getEventos() {
        return getAllPublishedPosts("EVENTO");
    }
}
