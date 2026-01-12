package BOOT-INF.classes.app.restcontroller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface RestControllerGenericNormal<E, ID extends java.io.Serializable> {
  ResponseEntity<? extends Object> getAll();
  
  ResponseEntity<?> getAll(Pageable paramPageable);
  
  ResponseEntity<?> getOne(@PathVariable ID paramID);
  
  ResponseEntity<?> save(@RequestBody E paramE);
  
  ResponseEntity<?> update(@PathVariable ID paramID, @RequestBody E paramE);
  
  ResponseEntity<?> delete(@PathVariable ID paramID);
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\RestControllerGenericNormal.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */