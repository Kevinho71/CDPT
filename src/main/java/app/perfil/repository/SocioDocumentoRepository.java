package app.documento.repository;

import app.documento.entity.SocioDocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocioDocumentoRepository extends JpaRepository<SocioDocumentoEntity, Integer> {
    
    List<SocioDocumentoEntity> findByFkPerfilSocio_Id(Integer perfilSocioId);
    
    List<SocioDocumentoEntity> findByFkDocumento_Id(Integer documentoId);
    
    List<SocioDocumentoEntity> findByFkPerfilSocio_IdAndEsVisibleTrue(Integer perfilSocioId);
    
    // Ordenados por campo 'orden'
    List<SocioDocumentoEntity> findByPerfilSocio_IdOrderByOrdenAsc(Integer perfilSocioId);
    
    List<SocioDocumentoEntity> findByPerfilSocio_IdAndEsVisibleTrueOrderByOrdenAsc(Integer perfilSocioId);
}
