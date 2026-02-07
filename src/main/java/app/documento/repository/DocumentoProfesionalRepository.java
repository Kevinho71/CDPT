package app.documento.repository;

import app.documento.entity.DocumentoProfesionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoProfesionalRepository extends JpaRepository<DocumentoProfesionalEntity, Integer> {
    
    List<DocumentoProfesionalEntity> findByTipoArchivo(String tipoArchivo);
    
    List<DocumentoProfesionalEntity> findByEstado(Integer estado);
}
