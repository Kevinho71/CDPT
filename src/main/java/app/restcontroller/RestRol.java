/*    */ package BOOT-INF.classes.app.restcontroller;
/*    */ 
/*    */ import app.entity.GenericEntity;
/*    */ import app.entity.RolEntity;
/*    */ import app.restcontroller.RestControllerGenericImpl;
/*    */ import app.service.RolServiceImpl;
/*    */ import app.util.Constantes;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.data.domain.PageRequest;
/*    */ import org.springframework.data.domain.Pageable;
/*    */ import org.springframework.data.repository.query.Param;
/*    */ import org.springframework.http.HttpStatus;
/*    */ import org.springframework.http.ResponseEntity;
/*    */ import org.springframework.web.bind.annotation.GetMapping;
/*    */ import org.springframework.web.bind.annotation.PostMapping;
/*    */ import org.springframework.web.bind.annotation.RequestBody;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestParam;
/*    */ import org.springframework.web.bind.annotation.RestController;
/*    */ 
/*    */ @RestController
/*    */ @RequestMapping({"/RestRoles"})
/*    */ public class RestRol
/*    */   extends RestControllerGenericImpl<RolEntity, RolServiceImpl>
/*    */ {
/*    */   @GetMapping({"/lista"})
/*    */   public ResponseEntity<?> search(@RequestParam String search, @RequestParam int status) {
/* 32 */     System.out.println("lista");
/*    */     
/*    */     try {
/* 35 */       return ResponseEntity.status(HttpStatus.OK).body(((RolServiceImpl)this.servicio).findAll(search, status));
/* 36 */     } catch (Exception e) {
/* 37 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
/*    */     } 
/*    */   }
/*    */   
/*    */   @GetMapping({"/listar"})
/*    */   public ResponseEntity<?> getAll(HttpServletRequest request, @Param("draw") int draw, @Param("length") int length, @Param("start") int start, @Param("estado") int estado) throws IOException {
/* 43 */     String total = "";
/* 44 */     Map<String, Object> Data = new HashMap<>();
/*    */ 
/*    */     
/*    */     try {
/* 48 */       String search = request.getParameter("search[value]");
/* 49 */       int tot = Constantes.NUM_MAX_DATATABLE.intValue();
/* 50 */       System.out.println("tot:" + tot + "estado:" + estado + "search:" + search + "length:" + length + "start:" + start);
/* 51 */       PageRequest pageRequest = PageRequest.of(start, length);
/* 52 */       List<?> lista = ((RolServiceImpl)this.servicio).findAll(estado, search, (Pageable)pageRequest).getContent();
/* 53 */       System.out.println("lista:" + lista.toString());
/*    */       
/*    */       try {
/* 56 */         total = String.valueOf(lista.size());
/*    */       }
/* 58 */       catch (Exception e) {
/* 59 */         total = "0";
/*    */       } 
/* 61 */       Data.put("draw", Integer.valueOf(draw));
/* 62 */       Data.put("recordsTotal", total);
/* 63 */       Data.put("data", lista);
/* 64 */       if (!search.equals("")) {
/* 65 */         Data.put("recordsFiltered", Integer.valueOf(lista.size()));
/*    */       } else {
/* 67 */         Data.put("recordsFiltered", total);
/*    */       } 
/* 69 */       return ResponseEntity.status(HttpStatus.OK).body(Data);
/* 70 */     } catch (Exception e) {
/* 71 */       e.printStackTrace();
/* 72 */       System.out.println(e.getMessage());
/*    */       
/* 74 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Data);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @PostMapping({"updateStatus"})
/*    */   public ResponseEntity<?> updateStatus(@RequestBody RolEntity entity) {
/*    */     try {
/* 82 */       System.out.println("Entidad:" + entity.toString());
/* 83 */       ((RolServiceImpl)this.servicio).updateStatus(entity.getEstado(), entity.getId().longValue());
/* 84 */       RolEntity entity2 = (RolEntity)((RolServiceImpl)this.servicio).findById(entity.getId());
/* 85 */       return ResponseEntity.status(HttpStatus.OK).body(entity2);
/* 86 */     } catch (Exception e) {
/* 87 */       System.out.println(e.getMessage());
/* 88 */       e.printStackTrace();
/* 89 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
/*    */     } 
/*    */   }
/*    */   
/*    */   @PostMapping({""})
/*    */   public ResponseEntity<?> save(@RequestBody RolEntity entidad) {
/*    */     try {
/* 96 */       System.out.println("Entidad" + entidad.toString());
/* 97 */       return ResponseEntity.status(HttpStatus.OK).body(((RolServiceImpl)this.servicio).save(entidad));
/* 98 */     } catch (Exception e) {
/* 99 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\RestRol.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */