/*    */ package BOOT-INF.classes.app.restcontroller;
/*    */ 
/*    */ import app.entity.UsuarioEntity;
/*    */ import app.restcontroller.RestControllerGenericNormalImpl;
/*    */ import app.service.UsuarioServiceImpl;
/*    */ import app.util.Constantes;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.data.repository.query.Param;
/*    */ import org.springframework.http.HttpStatus;
/*    */ import org.springframework.http.ResponseEntity;
/*    */ import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/*    */ import org.springframework.web.bind.annotation.GetMapping;
/*    */ import org.springframework.web.bind.annotation.PostMapping;
/*    */ import org.springframework.web.bind.annotation.RequestBody;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RestController;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @RestController
/*    */ @RequestMapping({"/RestUsuarios"})
/*    */ public class RestUsuario
/*    */   extends RestControllerGenericNormalImpl<UsuarioEntity, UsuarioServiceImpl>
/*    */ {
/*    */   @Autowired
/*    */   private BCryptPasswordEncoder passwordEncoder;
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
/* 51 */       List<?> lista = ((UsuarioServiceImpl)this.servicio).findAll(estado, search, length, start);
/* 52 */       System.out.println("listar_usuarios:" + lista.toString());
/*    */       
/*    */       try {
/* 55 */         total = String.valueOf(((UsuarioServiceImpl)this.servicio).getTotAll(search, estado));
/*    */       }
/* 57 */       catch (Exception e) {
/* 58 */         total = "0";
/*    */       } 
/* 60 */       Data.put("draw", Integer.valueOf(draw));
/* 61 */       Data.put("recordsTotal", total);
/* 62 */       Data.put("data", lista);
/* 63 */       if (!search.equals("")) {
/* 64 */         Data.put("recordsFiltered", Integer.valueOf(lista.size()));
/*    */       } else {
/* 66 */         Data.put("recordsFiltered", total);
/*    */       } 
/* 68 */       return ResponseEntity.status(HttpStatus.OK).body(Data);
/* 69 */     } catch (Exception e) {
/* 70 */       e.printStackTrace();
/* 71 */       System.out.println(e.getMessage());
/*    */       
/* 73 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Data);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @PostMapping({"updateStatus"})
/*    */   public ResponseEntity<?> updateStatus(@RequestBody UsuarioEntity entity) {
/*    */     try {
/* 81 */       System.out.println("Entidad:" + entity.toString());
/* 82 */       ((UsuarioServiceImpl)this.servicio).updateStatus(entity.getEstado().intValue(), entity.getId().intValue());
/* 83 */       UsuarioEntity entity2 = (UsuarioEntity)((UsuarioServiceImpl)this.servicio).findById(entity.getId());
/* 84 */       return ResponseEntity.status(HttpStatus.OK).body(entity2);
/* 85 */     } catch (Exception e) {
/* 86 */       System.out.println(e.getMessage());
/* 87 */       e.printStackTrace();
/* 88 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\RestUsuario.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */