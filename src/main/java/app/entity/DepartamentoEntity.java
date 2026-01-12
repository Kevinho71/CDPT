/*    */ package BOOT-INF.classes.app.entity;
/*    */ 
/*    */ import app.entity.PaisEntity;
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
/*    */ @Entity
/*    */ @Table(name = "departamento")
/*    */ public class DepartamentoEntity
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @Id
/*    */   private Integer id;
/*    */   @Column(name = "abreviacion")
/*    */   private String abreviacion;
/*    */   @Column(name = "nombre")
/*    */   private String nombre;
/*    */   @Column(name = "estado")
/*    */   private Integer estado;
/*    */   @ManyToOne(optional = false)
/*    */   @JoinColumn(name = "fk_pais")
/*    */   private PaisEntity pais;
/*    */   
/*    */   public DepartamentoEntity() {}
/*    */   
/*    */   public DepartamentoEntity(Integer id, String abreviacion, String nombre, Integer estado, PaisEntity pais) {
/* 45 */     this.id = id;
/* 46 */     this.abreviacion = abreviacion;
/* 47 */     this.nombre = nombre;
/* 48 */     this.estado = estado;
/* 49 */     this.pais = pais;
/*    */   }
/*    */   
/*    */   public Integer getId() {
/* 53 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(Integer id) {
/* 57 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getAbreviacion() {
/* 61 */     return this.abreviacion;
/*    */   }
/*    */   
/*    */   public void setAbreviacion(String abreviacion) {
/* 65 */     this.abreviacion = abreviacion;
/*    */   }
/*    */   
/*    */   public String getNombre() {
/* 69 */     return this.nombre;
/*    */   }
/*    */   
/*    */   public void setNombre(String nombre) {
/* 73 */     this.nombre = nombre;
/*    */   }
/*    */   
/*    */   public Integer getEstado() {
/* 77 */     return this.estado;
/*    */   }
/*    */   
/*    */   public void setEstado(Integer estado) {
/* 81 */     this.estado = estado;
/*    */   }
/*    */   
/*    */   public PaisEntity getPais() {
/* 85 */     return this.pais;
/*    */   }
/*    */   
/*    */   public void setPais(PaisEntity pais) {
/* 89 */     this.pais = pais;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 94 */     return "DepartamentoEntity [id=" + this.id + ", abreviacion=" + this.abreviacion + ", nombre=" + this.nombre + ", estado=" + this.estado + ", pais=" + this.pais + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\DepartamentoEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */