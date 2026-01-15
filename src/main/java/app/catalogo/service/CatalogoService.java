package app.catalogo.service;

import app.catalogo.dto.CatalogoDTO;
import app.catalogo.dto.CatalogoResponseDTO;
import app.catalogo.entity.CatalogoEntity;
import app.common.util.GenericServiceNormal;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CatalogoService extends GenericServiceNormal<CatalogoEntity, Integer> {
  int getIdPrimaryKey() throws Exception;
  
  Integer getCodigo() throws Exception;
  
  List<CatalogoEntity> findAll(int paramInt1, String paramString, int paramInt2, int paramInt3) throws Exception;
  
  void updateStatus(int paramInt1, int paramInt2) throws Exception;
  
  Integer getTotAll(String paramString, int paramInt) throws Exception;
  
  // Nuevos métodos con DTOs
  CatalogoResponseDTO create(CatalogoDTO dto, MultipartFile logo, List<MultipartFile> imagenes);
  
  CatalogoResponseDTO updateCatalogo(Integer id, CatalogoDTO dto, MultipartFile logo, List<MultipartFile> imagenes);
  
  CatalogoResponseDTO findByIdDTO(Integer id);
  
  List<CatalogoResponseDTO> findAllDTO();
  
  List<CatalogoResponseDTO> findAllDTO(int estado);
  
  CatalogoResponseDTO changeStatusCatalogo(Integer id);
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\CatalogoService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */