package BOOT-INF.classes.app.service;

import app.entity.PersonaEntity;
import app.service.GenericServiceNormal;
import java.util.List;

public interface PersonaService extends GenericServiceNormal<PersonaEntity, Integer> {
  int getIdPrimaryKey() throws Exception;
  
  List<PersonaEntity> findAll(int paramInt1, String paramString, int paramInt2, int paramInt3) throws Exception;
  
  void updateStatus(int paramInt1, int paramInt2) throws Exception;
  
  Integer getTotAll(String paramString, int paramInt) throws Exception;
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\PersonaService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */