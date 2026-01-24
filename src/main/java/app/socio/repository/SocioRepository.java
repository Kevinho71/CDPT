package app.socio.repository;

import app.socio.entity.SocioEntity;
import app.common.util.GenericRepositoryNormal;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends GenericRepositoryNormal<SocioEntity, Integer> {
  @Query(value = "select COALESCE(max(id),0)+1 as id from socio", nativeQuery = true)
  int getIdPrimaryKey();
  
  @Query(value = "SELECT COALESCE(max(codigo),0)+1 as id from socio", nativeQuery = true)
  Integer getCodigo();
  
  @Query(value = "select t.* from socio t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.matricula,'')) like concat('%',upper(:search),'%')) ORDER BY t.id ASC LIMIT :length OFFSET :start ", countQuery = "SELECT count(t.*) FROM socio t where (t.estado=:estado or :estado=-1) and  (upper(concat(t.id,t.matricula,'')) like concat('%',upper(:search),'%')) LIMIT :length OFFSET :start ", nativeQuery = true)
  List<SocioEntity> findAll(@Param("estado") int paramInt1, @Param("search") String paramString, @Param("length") int paramInt2, @Param("start") int paramInt3);
  
  @Modifying
  @Query(value = "UPDATE socio SET estado= ?1 WHERE id=?2", nativeQuery = true)
  void updateStatus(Integer paramInteger1, Integer paramInteger2);
  
  @Query(value = "select count(t.*) from socio t where (upper(concat(t.id,t.matricula,'')) like concat('%',upper(:search),'%')) and (t.estado=:estado or :estado=-1) ", nativeQuery = true)
  Integer getTotAll(@Param("search") String paramString, @Param("estado") Integer paramInteger);
  
  SocioEntity findByNrodocumento(String paramString);
  
  @Query(value = "SELECT * FROM socio WHERE fk_persona = ?1 AND estado = 1", nativeQuery = true)
  SocioEntity findByPersonaId(Integer personaId);
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\repository\SocioRepository.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */