/*    */ package BOOT-INF.classes.app.restcontroller;
/*    */ 
/*    */ import app.entity.GenericEntity;
/*    */ import app.restcontroller.RestControllerGeneric;
/*    */ import app.service.GenericServiceImpl;
/*    */ import java.io.Serializable;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.data.domain.Pageable;
/*    */ import org.springframework.http.HttpStatus;
/*    */ import org.springframework.http.ResponseEntity;
/*    */ import org.springframework.web.bind.annotation.DeleteMapping;
/*    */ import org.springframework.web.bind.annotation.GetMapping;
/*    */ import org.springframework.web.bind.annotation.PathVariable;
/*    */ import org.springframework.web.bind.annotation.PostMapping;
/*    */ import org.springframework.web.bind.annotation.PutMapping;
/*    */ import org.springframework.web.bind.annotation.RequestBody;
/*    */ 
/*    */ public abstract class RestControllerGenericImpl<E extends GenericEntity, S extends GenericServiceImpl<E, Long>>
/*    */   implements RestControllerGeneric<E, Long>
/*    */ {
/*    */   @GetMapping({""})
/*    */   public ResponseEntity<?> getAll() {
/* 23 */     System.out.println("GET LLAMADA");
/*    */     try {
/* 25 */       return ResponseEntity.status(HttpStatus.OK).body(this.servicio.findAll());
/* 26 */     } catch (Exception e) {
/* 27 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*    */     } 
/*    */   } @Autowired
/*    */   protected S servicio; @GetMapping({"/paged"})
/*    */   public ResponseEntity<?> getAll(Pageable pageable) {
/*    */     try {
/* 33 */       System.out.println("page:" + pageable.toString());
/* 34 */       return ResponseEntity.status(HttpStatus.OK).body(this.servicio.findAll(pageable));
/* 35 */     } catch (Exception e) {
/* 36 */       e.printStackTrace();
/* 37 */       System.out.println(e.getMessage());
/* 38 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*    */     } 
/*    */   }
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
/*    */   @GetMapping({"/{id}"})
/*    */   public ResponseEntity<?> getOne(@PathVariable Long id) {
/*    */     try {
/* 54 */       return ResponseEntity.status(HttpStatus.OK).body(this.servicio.findById(id));
/* 55 */     } catch (Exception e) {
/* 56 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*    */     } 
/*    */   }
/*    */   @PostMapping({""})
/*    */   public ResponseEntity<?> save(@RequestBody E entidad) {
/* 61 */     System.out.println("EntidadSave:" + entidad.toString());
/*    */ 
/*    */     
/*    */     try {
/* 65 */       System.out.println("Entidad" + entidad.toString());
/* 66 */       return ResponseEntity.status(HttpStatus.OK).body(this.servicio.save((GenericEntity)entidad));
/* 67 */     } catch (Exception e) {
/* 68 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*    */     } 
/*    */   }
/*    */   @PutMapping({"/{id}"})
/*    */   public ResponseEntity<?> update(@PathVariable Long id, @RequestBody E entidad) {
/*    */     try {
/* 74 */       return ResponseEntity.status(HttpStatus.OK).body(this.servicio.update(id, (GenericEntity)entidad));
/* 75 */     } catch (Exception e) {
/* 76 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*    */     } 
/*    */   }
/*    */   
/*    */   @DeleteMapping({"/{id}"})
/*    */   public ResponseEntity<?> delete(@PathVariable Long id) {
/*    */     try {
/* 83 */       return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Boolean.valueOf(this.servicio.delete(id)));
/* 84 */     } catch (Exception e) {
/* 85 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\RestControllerGenericImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */