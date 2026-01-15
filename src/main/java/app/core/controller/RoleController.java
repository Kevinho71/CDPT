 package app.core.controller;
 
 import app.common.entity.GenericEntity;
 import app.core.entity.RolEntity;
 import app.common.util.RestControllerGenericImpl;
 import app.core.service.RolServiceImpl;
 import app.common.util.Constantes;
 import java.io.IOException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import jakarta.servlet.http.HttpServletRequest;
 import org.springframework.data.domain.PageRequest;
 import org.springframework.data.domain.Pageable;
 import org.springframework.data.repository.query.Param;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;
 
 @RestController
@RequestMapping({"/RestRoles"})
public class RolController
   extends RestControllerGenericImpl<RolEntity, RolServiceImpl>
 {
   @GetMapping({"/lista"})
   public ResponseEntity<?> search(@RequestParam String search, @RequestParam int status) {
     System.out.println("lista");
     
     try {
       return ResponseEntity.status(HttpStatus.OK).body(((RolServiceImpl)this.servicio).findAll(search, status));
     } catch (Exception e) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
     } 
   }
   
   @GetMapping({"/listar"})
   public ResponseEntity<?> getAll(HttpServletRequest request, @Param("draw") int draw, @Param("length") int length, @Param("start") int start, @Param("estado") int estado) throws IOException {
     String total = "";
     Map<String, Object> Data = new HashMap<>();


     try {
       String search = request.getParameter("search[value]");
       int tot = Constantes.NUM_MAX_DATATABLE.intValue();
       System.out.println("tot:" + tot + "estado:" + estado + "search:" + search + "length:" + length + "start:" + start);
       PageRequest pageRequest = PageRequest.of(start, length);
       List<?> lista = ((RolServiceImpl)this.servicio).findAll(estado, search, (Pageable)pageRequest).getContent();
       System.out.println("lista:" + lista.toString());
       
       try {
         total = String.valueOf(lista.size());
       }
       catch (Exception e) {
         total = "0";
       } 
       Data.put("draw", Integer.valueOf(draw));
       Data.put("recordsTotal", total);
       Data.put("data", lista);
       if (!search.equals("")) {
         Data.put("recordsFiltered", Integer.valueOf(lista.size()));
       } else {
         Data.put("recordsFiltered", total);
       } 
       return ResponseEntity.status(HttpStatus.OK).body(Data);
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Data);
     } 
   }


   @PostMapping({"updateStatus"})
   public ResponseEntity<?> updateStatus(@RequestBody RolEntity entity) {
     try {
       System.out.println("Entidad:" + entity.toString());
       ((RolServiceImpl)this.servicio).updateStatus(entity.getEstado(), entity.getId().longValue());
       RolEntity entity2 = (RolEntity)((RolServiceImpl)this.servicio).findById(entity.getId());
       return ResponseEntity.status(HttpStatus.OK).body(entity2);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
     } 
   }
   
   @PostMapping({""})
   public ResponseEntity<?> save(@RequestBody RolEntity entidad) {
     try {
       System.out.println("Entidad" + entidad.toString());
       return ResponseEntity.status(HttpStatus.OK).body(((RolServiceImpl)this.servicio).save(entidad));
     } catch (Exception e) {
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
     } 
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\RestRol.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */