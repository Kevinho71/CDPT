package app.socio.service;

import app.socio.dto.SocioCompleteResponseDTO;
import app.socio.dto.SocioResponseDTO;
import app.socio.entity.SocioEntity;
import app.common.util.GenericServiceNormal;
import java.util.List;

public interface SocioService extends GenericServiceNormal<SocioEntity, Integer> {
  int getIdPrimaryKey() throws Exception;
  
  Integer getCodigo() throws Exception;
  
  List<SocioEntity> findAll(int paramInt1, String paramString, int paramInt2, int paramInt3) throws Exception;
  
  void updateStatus(int paramInt1, int paramInt2) throws Exception;
  
  Integer getTotAll(String paramString, int paramInt) throws Exception;
  
  SocioEntity findByNrodocumento(String paramString) throws Exception;
  
  SocioEntity renovarQR(Integer paramInteger, SocioEntity paramSocioEntity) throws Exception;
  
  SocioEntity updatecatalogos(Integer paramInteger, SocioEntity paramSocioEntity) throws Exception;
  
  // Nuevos métodos con DTOs
  List<SocioResponseDTO> findAllDTO();
  
  SocioResponseDTO findByIdDTO(Integer id);
  
  SocioResponseDTO findByNrodocumentoDTO(String nrodocumento);
  
  SocioResponseDTO renovarQRDTO(Integer id);
  
  SocioResponseDTO updateCatalogosDTO(Integer id, List<Integer> catalogIds);
  
  // Método para obtener información completa de socios (incluye usuario y empresas)
  List<SocioCompleteResponseDTO> findAllComplete();
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\SocioService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */