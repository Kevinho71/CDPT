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
/*    */ @Entity
/*    */ @Table(name = "persona")
/*    */ public class PersonaEntity
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @Id
/*    */   private Integer id;
/*    */   @Column(name = "ci")
/*    */   private String ci;
/*    */   @Column(name = "nombrecompleto")
/*    */   private String nombrecompleto;
/*    */   @Column(name = "email")
/*    */   private String email;
/*    */   @Column(name = "celular")
/*    */   private Integer celular;
/*    */   @Column(name = "estado")
/*    */   private Integer estado;
/*    */   
/*    */   public PersonaEntity() {}
/*    */   
/*    */   public PersonaEntity(Integer id, String ci, String nombrecompleto, String email, Integer celular, Integer estado) {
/* 42 */     this.id = id;
/* 43 */     this.ci = ci;
/* 44 */     this.nombrecompleto = nombrecompleto;
/* 45 */     this.email = email;
/* 46 */     this.celular = celular;
/* 47 */     this.estado = estado;
/*    */   }
/*    */   public Integer getId() {
/* 50 */     return this.id;
/*    */   }
/*    */   public void setId(Integer id) {
/* 53 */     this.id = id;
/*    */   }
/*    */   public String getCi() {
/* 56 */     return this.ci;
/*    */   }
/*    */   public void setCi(String ci) {
/* 59 */     this.ci = ci;
/*    */   }
/*    */   public String getNombrecompleto() {
/* 62 */     return this.nombrecompleto;
/*    */   }
/*    */   public void setNombrecompleto(String nombrecompleto) {
/* 65 */     this.nombrecompleto = nombrecompleto;
/*    */   }
/*    */   public String getEmail() {
/* 68 */     return this.email;
/*    */   }
/*    */   public void setEmail(String email) {
/* 71 */     this.email = email;
/*    */   }
/*    */   public Integer getCelular() {
/* 74 */     return this.celular;
/*    */   }
/*    */   public void setCelular(Integer celular) {
/* 77 */     this.celular = celular;
/*    */   }
/*    */   public Integer getEstado() {
/* 80 */     return this.estado;
/*    */   }
/*    */   public void setEstado(Integer estado) {
/* 83 */     this.estado = estado;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 87 */     return "PersonaEntity [id=" + this.id + ", ci=" + this.ci + ", nombrecompleto=" + this.nombrecompleto + ", email=" + this.email + ", celular=" + this.celular + ", estado=" + this.estado + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\PersonaEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */