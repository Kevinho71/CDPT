package app.posts.service;

import app.posts.dto.*;
import app.posts.entity.PostEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar Posts (Noticias y Eventos)
 */
public interface PostService {

    /**
     * Lista resumen de posts publicados (para público)
     * @param tipo Filtro opcional por tipo ('NOTICIA' o 'EVENTO')
     * @return Lista de PostSummaryDTO
     */
    List<PostSummaryDTO> findAllPublished(String tipo);

    /**
     * Lista resumen de todos los posts (para admin)
     * @param tipo Filtro opcional por tipo
     * @return Lista de PostSummaryDTO
     */
    List<PostSummaryDTO> findAll(String tipo);

    /**
     * Obtiene detalle completo de un post por slug (para público)
     * Solo si está publicado
     */
    PostDetailDTO findPublishedBySlug(String slug);

    /**
     * Obtiene detalle completo de un post por slug (para admin)
     * Incluye borradores
     */
    PostDetailDTO findBySlug(String slug);

    /**
     * Obtiene detalle completo de un post por ID (para admin)
     */
    PostDetailDTO findById(Integer id);

    /**
     * Crea un nuevo post
     * @param dto Datos del post a crear
     * @param usuarioId ID del usuario que crea el post
     * @return Post creado
     */
    PostDetailDTO createPost(PostCreateDTO dto, Integer usuarioId);

    /**
     * Actualiza un post existente
     * @param id ID del post a actualizar
     * @param dto Datos a actualizar (campos opcionales)
     * @return Post actualizado
     */
    PostDetailDTO updatePost(Integer id, PostUpdateDTO dto);

    /**
     * Elimina un post (borrado lógico)
     */
    void deletePost(Integer id);

    /**
     * Cambia el estado de publicación de un post
     * @param id ID del post
     * @param publicado true para publicar, false para despublicar
     */
    PostDetailDTO togglePublicado(Integer id, Boolean publicado);

    /**
     * Sube imagen de portada a Cloudinary
     * @param file Archivo de imagen
     * @return URL de Cloudinary
     */
    String uploadPortada(MultipartFile file);

    /**
     * Sube imagen de sección a Cloudinary
     * @param file Archivo de imagen
     * @return URL de Cloudinary
     */
    String uploadSeccionImagen(MultipartFile file);

    /**
     * Elimina una imagen de Cloudinary
     * @param publicId Public ID de Cloudinary
     */
    void deleteImage(String publicId);

    /**
     * Genera un slug único a partir del título
     * @param titulo Título del post
     * @return Slug único
     */
    String generateUniqueSlug(String titulo);

    /**
     * Genera un slug único para actualización (excluyendo el post actual)
     */
    String generateUniqueSlugForUpdate(String titulo, Integer postId);

    /**
     * Obtiene los últimos N posts publicados
     */
    List<PostSummaryDTO> findLatestPublished(int limit);

    /**
     * Obtiene próximo ID para nuevo post
     */
    Integer getNextId();
}
