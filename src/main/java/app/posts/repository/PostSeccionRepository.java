package app.posts.repository;

import app.posts.entity.PostSeccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostSeccionRepository extends JpaRepository<PostSeccionEntity, Integer> {

    /**
     * Obtiene el próximo ID para una nueva sección
     */
    @Query("SELECT COALESCE(MAX(s.id), 0) + 1 FROM PostSeccionEntity s")
    Integer getNextId();

    /**
     * Busca todas las secciones de un post ordenadas
     */
    List<PostSeccionEntity> findByPostIdAndEstadoOrderByOrdenAsc(Integer postId, Integer estado);

    /**
     * Busca secciones por post (sin filtro de estado)
     */
    List<PostSeccionEntity> findByPostIdOrderByOrdenAsc(Integer postId);

    /**
     * Elimina todas las secciones de un post
     */
    void deleteByPostId(Integer postId);

    /**
     * Cuenta las secciones de un post
     */
    @Query("SELECT COUNT(s) FROM PostSeccionEntity s WHERE s.post.id = :postId AND s.estado = 1")
    Long countByPostId(@Param("postId") Integer postId);
}
