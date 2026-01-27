package app.core.service;

import app.common.entity.GenericEntity;
import app.core.entity.RolEntity;
import app.common.util.GenericService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RolService extends GenericService<RolEntity, Long> {
  List<RolEntity> findAll(String paramString, int paramInt) throws Exception;
  
  Page<RolEntity> findAll(int paramInt, String paramString, Pageable paramPageable) throws Exception;
  
  void updateStatus(int paramInt, long paramLong) throws Exception;
  
  RolEntity save(RolEntity paramRolEntity) throws Exception;
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\RolService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */