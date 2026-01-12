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
/*    */ 
/*    */ @Entity
/*    */ @Table(name = "pais")
/*    */ public class PaisEntity
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
/*    */   public PaisEntity() {}
/*    */   
/*    */   public PaisEntity(Integer id, String nombre, Integer estado) {
/* 39 */     this.id = id;
/* 40 */     this.nombre = nombre;
/* 41 */     this.estado = estado;
/*    */   }
/*    */   
/*    */   public Integer getId() {
/* 45 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(Integer id) {
/* 49 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getNombre() {
/* 53 */     return this.nombre;
/*    */   }
/*    */   
/*    */   public void setNombre(String nombre) {
/* 57 */     this.nombre = nombre;
/*    */   }
/*    */   
/*    */   public Integer getEstado() {
/* 61 */     return this.estado;
/*    */   }
/*    */   
/*    */   public void setEstado(Integer estado) {
/* 65 */     this.estado = estado;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return "PaisEntity [id=" + this.id + ", nombre=" + this.nombre + ", estado=" + this.estado + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\PaisEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */