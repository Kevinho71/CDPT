package BOOT-INF.classes.app.repository;

import app.entity.ProfesionEntity;
import app.repository.GenericRepositoryNormal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionRepository extends GenericRepositoryNormal<ProfesionEntity, Integer> {
  @Query(value = "SELECT max(id)+1  from profesion", nativeQuery = true)
  Integer getIdPrimaryKey();
  
  @Query(value = "SELECT  p.* FROM profesion p WHERE upper(concat(p.nombre)) LIKE  concat('%',upper(?1),'%') and (p.estado=?2 or ?2=-1) ORDER BY p.id DESC LIMIT 10", nativeQuery = true)
  List<ProfesionEntity> findAll(String paramString, int paramInt);
  
  @Query(value = "select t.* from profesion t where (t.estado=:estado or :estado=-1) and  (upper(t.nombre) like concat('%',upper(:search),'%')) ORDER BY t.id DESC ", nativeQuery = true)
  Page<ProfesionEntity> findAll(@Param("estado") int paramInt, @Param("search") String paramString, @Param("pageable") Pageable paramPageable);
  
  @Modifying
  @Query(value = "UPDATE profesion SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2", nativeQuery = true)
  void updateStatus(int paramInt, Integer paramInteger);
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\repository\ProfesionRepository.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */