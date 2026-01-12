package BOOT-INF.classes.app.service;

import app.entity.SocioEntity;
import app.service.GenericServiceNormal;
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
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\SocioService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */