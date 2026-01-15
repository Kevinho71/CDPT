package app.core.service;

import app.core.entity.UsuarioEntity;
import app.common.util.GenericServiceNormal;
import java.util.List;

public interface UsuarioService extends GenericServiceNormal<UsuarioEntity, Integer> {
  UsuarioEntity findByEmail(String paramString) throws Exception;
  
  int getIdPrimaryKey() throws Exception;
  
  List<UsuarioEntity> findAll(int paramInt1, String paramString, int paramInt2, int paramInt3) throws Exception;
  
  void updateStatus(int paramInt1, int paramInt2) throws Exception;
  
  UsuarioEntity getUserByLogin(String paramString) throws Exception;
  
  Integer getTotAll(String paramString, int paramInt) throws Exception;
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\UsuarioService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */