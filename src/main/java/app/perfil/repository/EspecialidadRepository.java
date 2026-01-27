package app.perfil.repository;

import app.perfil.entity.EspecialidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity, Integer> {
    
    @Query(value = "SELECT e.* FROM especialidades e WHERE (e.estado = :estado OR :estado = -1) " +
                   "AND UPPER(e.nombre) LIKE CONCAT('%', UPPER(:search), '%') " +
                   "ORDER BY e.nombre ASC", nativeQuery = true)
    List<EspecialidadEntity> findAll(@Param("estado") int estado, @Param("search") String search);
    
    @Modifying
    @Query(value = "UPDATE especialidades SET estado = CASE WHEN estado = 1 THEN 0 ELSE 1 END WHERE id = :id", nativeQuery = true)
    void updateStatus(@Param("id") Integer id);
    
    boolean existsByNombreIgnoreCase(String nombre);
}
