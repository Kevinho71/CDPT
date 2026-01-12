/*    */ package BOOT-INF.classes.app.entity;
/*    */ 
/*    */ import app.entity.DepartamentoEntity;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
/*    */ import javax.persistence.JoinColumn;
/*    */ import javax.persistence.ManyToOne;
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
/*    */ @Table(name = "provincia")
/*    */ public class ProvinciaEntity
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @Id
/*    */   private Integer id;
/*    */   @Column(name = "nombre")
/*    */   private String nombre;
/*    */   @Column(name = "estado")
/*    */   private Integer estado;
/*    */   @ManyToOne(optional = false)
/*    */   @JoinColumn(name = "fk_departamento")
/*    */   private DepartamentoEntity departamento;
/*    */   
/*    */   public ProvinciaEntity() {}
/*    */   
/*    */   public ProvinciaEntity(Integer id, String nombre, Integer estado, DepartamentoEntity departamento) {
/* 44 */     this.id = id;
/* 45 */     this.nombre = nombre;
/* 46 */     this.estado = estado;
/* 47 */     this.departamento = departamento;
/*    */   }
/*    */   
/*    */   public Integer getId() {
/* 51 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(Integer id) {
/* 55 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getNombre() {
/* 59 */     return this.nombre;
/*    */   }
/*    */   
/*    */   public void setNombre(String nombre) {
/* 63 */     this.nombre = nombre;
/*    */   }
/*    */   
/*    */   public Integer getEstado() {
/* 67 */     return this.estado;
/*    */   }
/*    */   
/*    */   public void setEstado(Integer estado) {
/* 71 */     this.estado = estado;
/*    */   }
/*    */   
/*    */   public DepartamentoEntity getDepartamento() {
/* 75 */     return this.departamento;
/*    */   }
/*    */   
/*    */   public void setDepartamento(DepartamentoEntity departamento) {
/* 79 */     this.departamento = departamento;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 84 */     return "ProvinciaEntity [id=" + this.id + ", nombre=" + this.nombre + ", estado=" + this.estado + ", departamento=" + this.departamento + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\ProvinciaEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */