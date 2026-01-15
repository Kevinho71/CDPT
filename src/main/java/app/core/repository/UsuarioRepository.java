package app.core.repository;

import app.core.entity.UsuarioEntity;
import app.common.util.GenericRepositoryNormal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends GenericRepositoryNormal<UsuarioEntity, Integer> {
  @Query(value = "select COALESCE(max(id),0)+1 as id from usuario", nativeQuery = true)
  int getIdPrimaryKey();
  
  @Query(value = "select t.* from usuario t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.username,'')) like concat('%',upper(:search),'%')) ORDER BY t.id ASC LIMIT :length OFFSET :start ", countQuery = "SELECT count(t.*) FROM usuario t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.num,'')) like concat('%',upper(:search),'%')) LIMIT :length OFFSET :start ", nativeQuery = true)
  List<UsuarioEntity> findAll(@Param("estado") int paramInt1, @Param("search") String paramString, @Param("length") int paramInt2, @Param("start") int paramInt3);
  
  @Modifying
  @Query(value = "UPDATE usuario SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2", nativeQuery = true)
  void updateStatus(int paramInt1, int paramInt2);
  
  @Query(value = "SELECT * FROM usuario WHERE username=?1", nativeQuery = true)
  UsuarioEntity getUserByLogin(String paramString);
  
  @Query(value = "select count(t.*) from usuario t where (upper(concat(t.id,t.username,'')) like concat('%',upper(:search),'%')) and (t.estado=:estado or :estado=-1) ", nativeQuery = true)
  Integer getTotAll(@Param("search") String paramString, @Param("estado") int paramInt);
  
  @Query(value = "SELECT * FROM usuario WHERE username=?1 AND estado=1", nativeQuery = true)
  UsuarioEntity findByUsername(String paramString);
  
  // Métodos adicionales
  @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM usuario WHERE username = ?1", nativeQuery = true)
  boolean existsByUsername(String username);
  
  @Query(value = "SELECT * FROM usuario WHERE estado = ?1", nativeQuery = true)
  List<UsuarioEntity> findByEstado(int estado);
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\repository\UsuarioRepository.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */