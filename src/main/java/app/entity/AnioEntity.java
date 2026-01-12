/*    */ package BOOT-INF.classes.app.entity;
/*    */ 
/*    */ import app.entity.GenericEntity;
/*    */ import app.entity.InstitucionEntity;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.JoinColumn;
/*    */ import javax.persistence.ManyToOne;
/*    */ import javax.persistence.Table;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Entity
/*    */ @Table(name = "anio")
/*    */ public class AnioEntity
/*    */   extends GenericEntity
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @Column(name = "nombre")
/*    */   private String nombre;
/*    */   @Column(name = "estado")
/*    */   private Integer estado;
/*    */   @ManyToOne(optional = false)
/*    */   @JoinColumn(name = "fk_institucion")
/*    */   private InstitucionEntity institucion;
/*    */   
/*    */   public AnioEntity() {}
/*    */   
/*    */   public AnioEntity(String nombre, Integer estado, InstitucionEntity institucion) {
/* 31 */     this.nombre = nombre;
/* 32 */     this.estado = estado;
/* 33 */     this.institucion = institucion;
/*    */   }
/*    */   
/*    */   public String getNombre() {
/* 37 */     return this.nombre;
/*    */   }
/*    */   
/*    */   public void setNombre(String nombre) {
/* 41 */     this.nombre = nombre;
/*    */   }
/*    */   
/*    */   public Integer getEstado() {
/* 45 */     return this.estado;
/*    */   }
/*    */   
/*    */   public void setEstado(Integer estado) {
/* 49 */     this.estado = estado;
/*    */   }
/*    */   
/*    */   public InstitucionEntity getInstitucion() {
/* 53 */     return this.institucion;
/*    */   }
/*    */   
/*    */   public void setInstitucion(InstitucionEntity institucion) {
/* 57 */     this.institucion = institucion;
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\AnioEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */