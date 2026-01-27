package app.catalogo.repository;

import app.catalogo.entity.CatalogoEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogoRepository extends JpaRepository<CatalogoEntity, Integer> {
  @Query(value = "select COALESCE(max(id),0)+1 as id from catalogo", nativeQuery = true)
  int getIdPrimaryKey();
  
  @Query(value = "SELECT COALESCE(max(codigo),0)+1 as id from catalogo", nativeQuery = true)
  Integer getCodigo();
  
  List<CatalogoEntity> findAllByOrderByIdAsc();
  @Modifying
  @Query(value = "UPDATE catalogo SET estado= CASE ?1 WHEN 1 THEN 0 ELSE 1 END WHERE id=?2", nativeQuery = true)
  void updateStatus(Integer paramInteger1, Integer paramInteger2);
  
  @Query(value = "select count(t.*) from catalogo t where (upper(concat(t.id,t.nit,'')) like concat('%',upper(:search),'%')) and (t.estado=:estado or :estado=-1) ", nativeQuery = true)
  Integer getTotAll(@Param("search") String paramString, @Param("estado") Integer paramInteger);
  
  List<CatalogoEntity> findByEstadoOrderByIdAsc(int estado);
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\repository\CatalogoRepository.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */