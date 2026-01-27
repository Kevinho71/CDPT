package app.core.repository;

import app.core.entity.PersonaEntity;
import app.common.util.GenericRepositoryNormal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends GenericRepositoryNormal<PersonaEntity, Integer> {
  @Query(value = "select COALESCE(max(id),0)+1 as id from persona", nativeQuery = true)
  int getIdPrimaryKey();
  
  @Query(value = "select t.* from persona t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.nombrecompleto,'')) like concat('%',upper(:search),'%')) ORDER BY t.id ASC LIMIT :length OFFSET :start ", countQuery = "SELECT count(t.*) FROM persona t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.combrecompleto,'')) like concat('%',upper(:search),'%')) LIMIT :length OFFSET :start ", nativeQuery = true)
  List<PersonaEntity> findAll(@Param("estado") int paramInt1, @Param("search") String paramString, @Param("length") int paramInt2, @Param("start") int paramInt3);
  
  @Modifying
  @Query(value = "UPDATE persona SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2", nativeQuery = true)
  void updateStatus(int paramInt1, int paramInt2);
  
  @Query(value = "select count(t.*) from persona t where (upper(concat(t.id,t.nombrecompleto,'')) like concat('%',upper(:search),'%')) and (t.estado=:estado or :estado=-1) ", nativeQuery = true)
  Integer getTotAll(@Param("search") String paramString, @Param("estado") int paramInt);
  
  /**
   * Busca persona por email (correo) - necesario para OAuth2
   */
  Optional<PersonaEntity> findByEmail(String email);
  
  /**
   * Verifica si existe una persona con el CI dado
   */
  boolean existsByCi(String ci);
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\repository\PersonaRepository.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */