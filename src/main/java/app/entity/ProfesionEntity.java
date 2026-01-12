/*    */ package BOOT-INF.classes.app.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
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
/*    */ @Entity
/*    */ @Table(name = "profesion")
/*    */ public class ProfesionEntity
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @Id
/*    */   private Integer id;
/*    */   @Column(name = "nombre")
/*    */   private String nombre;
/*    */   @Column(name = "estado")
/*    */   private Integer estado;
/*    */   
/*    */   public ProfesionEntity() {}
/*    */   
/*    */   public ProfesionEntity(Integer id, String nombre, Integer estado) {
/* 38 */     this.id = id;
/* 39 */     this.nombre = nombre;
/* 40 */     this.estado = estado;
/*    */   }
/*    */   
/*    */   public Integer getId() {
/* 44 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(Integer id) {
/* 48 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getNombre() {
/* 52 */     return this.nombre;
/*    */   }
/*    */   
/*    */   public void setNombre(String nombre) {
/* 56 */     this.nombre = nombre;
/*    */   }
/*    */   
/*    */   public Integer getEstado() {
/* 60 */     return this.estado;
/*    */   }
/*    */   
/*    */   public void setEstado(Integer estado) {
/* 64 */     this.estado = estado;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 69 */     return "ProfesionEntity [id=" + this.id + ", nombre=" + this.nombre + ", estado=" + this.estado + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\ProfesionEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */