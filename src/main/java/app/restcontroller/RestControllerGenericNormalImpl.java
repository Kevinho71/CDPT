/*    */ package BOOT-INF.classes.app.restcontroller;
/*    */ 
/*    */ import app.restcontroller.RestControllerGenericNormal;
/*    */ import app.service.GenericServiceImplNormal;
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
/*    */ public abstract class RestControllerGenericNormalImpl<E, S extends GenericServiceImplNormal<E, Integer>>
/*    */   implements RestControllerGenericNormal<E, Integer> {
/*    */   @Autowired
/*    */   protected S servicio;
/*    */   
/*    */   @GetMapping({""})
/*    */   public ResponseEntity<?> getAll() {
/*    */     try {
/* 25 */       System.out.println(this.servicio.findAll().toString());
/* 26 */       return ResponseEntity.status(HttpStatus.OK).body(this.servicio.findAll());
/* 27 */     } catch (Exception e) {
/* 28 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*    */     } 
/*    */   }
/*    */   @GetMapping({"/paged"})
/*    */   public ResponseEntity<?> getAll(Pageable pageable) {
/*    */     try {
/* 34 */       System.out.println("page:" + pageable.toString());
/* 35 */       return ResponseEntity.status(HttpStatus.OK).body(this.servicio.findAll(pageable));
/* 36 */     } catch (Exception e) {
/* 37 */       e.printStackTrace();
/* 38 */       System.out.println(e.getMessage());
/* 39 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
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
/*    */   public ResponseEntity<?> getOne(@PathVariable Integer id) {
/*    */     try {
/* 55 */       return ResponseEntity.status(HttpStatus.OK).body(this.servicio.findById(id));
/* 56 */     } catch (Exception e) {
/* 57 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*    */     } 
/*    */   }
/*    */   @PostMapping({""})
/*    */   public ResponseEntity<?> save(@RequestBody E entidad) {
/* 62 */     System.out.println("EntidadSave:" + entidad.toString());
/*    */ 
/*    */     
/*    */     try {
/* 66 */       System.out.println("Entidad" + entidad.toString());
/* 67 */       return ResponseEntity.status(HttpStatus.OK).body(this.servicio.save(entidad));
/* 68 */     } catch (Exception e) {
/* 69 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*    */     } 
/*    */   }
/*    */   @PutMapping({"/{id}"})
/*    */   public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody E entidad) {
/*    */     try {
/* 75 */       return ResponseEntity.status(HttpStatus.OK).body(this.servicio.update(id, entidad));
/* 76 */     } catch (Exception e) {
/* 77 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*    */     } 
/*    */   }
/*    */   
/*    */   @DeleteMapping({"/{id}"})
/*    */   public ResponseEntity<?> delete(@PathVariable Integer id) {
/*    */     try {
/* 84 */       return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Boolean.valueOf(this.servicio.delete(id)));
/* 85 */     } catch (Exception e) {
/* 86 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\RestControllerGenericNormalImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */