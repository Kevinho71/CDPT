package app.socio.repository;

import app.socio.entity.SocioEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository independiente para Socio
 * No extiende de ningún repository genérico
 */
@Repository
public interface SocioRepository extends JpaRepository<SocioEntity, Integer> {

  List<SocioEntity> findAllByOrderByIdAsc();

  @Query(value = "select COALESCE(max(id),0)+1 as id from socio", nativeQuery = true)
  int getIdPrimaryKey();
  
  @Query(value = "SELECT COALESCE(max(codigo),0)+1 as id from socio", nativeQuery = true)
  Integer getCodigo();
    
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