package app.repository;

import app.entity.PaisEntity;
import app.repository.GenericRepositoryNormal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends GenericRepositoryNormal<PaisEntity, Integer> {
  @Query(value = "select COALESCE(max(id),0)+1 as id from pais", nativeQuery = true)
  int getIdPrimaryKey();
  
  @Query(value = "SELECT COALESCE(max(codigo),0)+1 as id from pais", nativeQuery = true)
  Integer getCodigo();
  
  @Query(value = "select t.* from pais t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.nombre,'')) like concat('%',upper(:search),'%')) ORDER BY t.id ASC LIMIT :length OFFSET :start ", countQuery = "SELECT count(t.*) FROM pais t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.nombre,'')) like concat('%',upper(:search),'%')) LIMIT :length OFFSET :start ", nativeQuery = true)
  List<PaisEntity> findAll(@Param("estado") int paramInt1, @Param("search") String paramString, @Param("length") int paramInt2, @Param("start") int paramInt3);
  
  @Modifying
  @Query(value = "UPDATE pais SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2", nativeQuery = true)
  void updateStatus(Integer paramInteger1, Integer paramInteger2);
  
  @Query(value = "select count(t.*) from pais t where (upper(concat(t.id,t.nombre,'')) like concat('%',upper(:search),'%')) and (t.estado=:estado or :estado=-1) ", nativeQuery = true)
  Integer getTotAll(@Param("search") String paramString, @Param("estado") Integer paramInteger);
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\repository\PaisRepository.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */