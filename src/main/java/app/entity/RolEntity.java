/*    */ package BOOT-INF.classes.app.entity;
/*    */ 
/*    */ import app.entity.GenericEntity;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Table;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ @Table(name = "rol")
/*    */ public class RolEntity
/*    */   extends GenericEntity
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String nombre;
/*    */   private int estado;
/*    */   
/*    */   public RolEntity() {}
/*    */   
/*    */   public RolEntity(String nombre, int estado) {
/* 35 */     this.nombre = nombre;
/* 36 */     this.estado = estado;
/*    */   }
/*    */ 
/*    */   
/*    */   public RolEntity(String nombre) {
/* 41 */     this.nombre = nombre;
/*    */   }
/*    */   public RolEntity(Long id) {
/* 44 */     super(id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getNombre() {
/* 51 */     return this.nombre;
/*    */   }
/*    */   
/*    */   public void setNombre(String nombre) {
/* 55 */     this.nombre = nombre;
/*    */   }
/*    */   
/*    */   public int getEstado() {
/* 59 */     return this.estado;
/*    */   }
/*    */   
/*    */   public void setEstado(int estado) {
/* 63 */     this.estado = estado;
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\RolEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */