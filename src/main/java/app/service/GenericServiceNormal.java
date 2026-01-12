package BOOT-INF.classes.app.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface GenericServiceNormal<E, ID extends java.io.Serializable> {
  List<E> findAll() throws Exception;
  
  Page<E> findAll(Pageable paramPageable) throws Exception;
  
  E findById(ID paramID) throws Exception;
  
  E save(E paramE) throws Exception;
  
  E update(ID paramID, E paramE) throws Exception;
  
  boolean delete(ID paramID) throws Exception;
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\GenericServiceNormal.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */