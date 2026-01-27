package app.posts.repository;

import app.posts.dto.PostSummaryDTO;
import app.posts.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    /**
     * Obtiene el próximo ID para un nuevo post
     */
    @Query("SELECT COALESCE(MAX(p.id), 0) + 1 FROM PostEntity p")
    Integer getNextId();

    /**
     * OPTIMIZADO: Lista resumen de posts publicados para landing page
     * Usa constructor expression para crear DTOs directamente
     * NO carga las secciones (lazy)
     */
    @Query("SELECT new app.posts.dto.PostSummaryDTO(" +
           "p.id, p.titulo, p.slug, p.intro, p.portadaUrl, p.autor, p.tipo, " +
           "p.createdAt, p.fechaEvento, p.publicado) " +
           "FROM PostEntity p " +
           "WHERE p.publicado = true " +
           "AND p.estado = 1 " +
           "AND (:tipo IS NULL OR p.tipo = :tipo) " +
           "ORDER BY p.createdAt DESC")
    List<PostSummaryDTO> findAllPublishedSummaries(@Param("tipo") String tipo);

    /**
     * OPTIMIZADO: Lista resumen de posts (todos) para el panel admin
     * Incluye borradores y publicados
     */
    @Query("SELECT new app.posts.dto.PostSummaryDTO(" +
           "p.id, p.titulo, p.slug, p.intro, p.portadaUrl, p.autor, p.tipo, " +
           "p.createdAt, p.fechaEvento, p.publicado) " +
           "FROM PostEntity p " +
           "WHERE p.estado = 1 " +
           "AND (:tipo IS NULL OR p.tipo = :tipo) " +
           "ORDER BY p.createdAt DESC")
    List<PostSummaryDTO> findAllSummaries(@Param("tipo") String tipo);

    /**
     * Busca un post por slug (para URLs amigables)
     * Trae el post completo con secciones (fetch join)
     */
    @Query("SELECT p FROM PostEntity p " +
           "LEFT JOIN FETCH p.secciones " +
           "WHERE p.slug = :slug " +
           "AND p.estado = 1")
    Optional<PostEntity> findBySlugWithSections(@Param("slug") String slug);

    /**
     * Busca un post por slug solo si está publicado (para público)
     */
    @Query("SELECT p FROM PostEntity p " +
           "LEFT JOIN FETCH p.secciones " +
           "WHERE p.slug = :slug " +
           "AND p.publicado = true " +
           "AND p.estado = 1")
    Optional<PostEntity> findPublishedBySlug(@Param("slug") String slug);

    /**
     * Verifica si existe un slug (para validación de unicidad)
     */
    boolean existsBySlug(String slug);

    /**
     * Verifica si existe un slug diferente al del post actual (para updates)
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
           "FROM PostEntity p " +
           "WHERE p.slug = :slug AND p.id != :id")
    boolean existsBySlugAndIdNot(@Param("slug") String slug, @Param("id") Integer id);

    /**
     * Obtiene los N últimos posts publicados (para widgets)
     */
    @Query("SELECT new app.posts.dto.PostSummaryDTO(" +
           "p.id, p.titulo, p.slug, p.intro, p.portadaUrl, p.autor, p.tipo, " +
           "p.createdAt, p.fechaEvento, p.publicado) " +
           "FROM PostEntity p " +
           "WHERE p.publicado = true " +
           "AND p.estado = 1 " +
           "ORDER BY p.createdAt DESC")
    List<PostSummaryDTO> findLatestPublished();

    /**
     * Busca posts por tipo y publicado (para filtros)
     */
    List<PostEntity> findByTipoAndPublicadoAndEstadoOrderByCreatedAtDesc(String tipo, Boolean publicado, Integer estado);

    /**
     * Cuenta total de posts publicados
     */
    @Query("SELECT COUNT(p) FROM PostEntity p WHERE p.publicado = true AND p.estado = 1")
    Long countPublished();

    /**
     * Cuenta total de posts por tipo
     */
    @Query("SELECT COUNT(p) FROM PostEntity p WHERE p.tipo = :tipo AND p.estado = 1")
    Long countByTipo(@Param("tipo") String tipo);
}
