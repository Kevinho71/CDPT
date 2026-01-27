package app.perfil.repository;

import app.perfil.entity.ServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioEntity, Integer> {
    
    @Query(value = "SELECT s.* FROM servicios s WHERE (s.estado = :estado OR :estado = -1) " +
                   "AND (UPPER(s.nombre) LIKE CONCAT('%', UPPER(:search), '%') OR " +
                   "UPPER(s.categoria) LIKE CONCAT('%', UPPER(:search), '%')) " +
                   "ORDER BY s.nombre ASC", nativeQuery = true)
    List<ServicioEntity> findAll(@Param("estado") int estado, @Param("search") String search);
    
    @Modifying
    @Query(value = "UPDATE servicios SET estado = CASE WHEN estado = 1 THEN 0 ELSE 1 END WHERE id = :id", nativeQuery = true)
    void updateStatus(@Param("id") Integer id);
    
    boolean existsByNombreIgnoreCase(String nombre);
}
