/*    */ package BOOT-INF.classes.app.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.GeneratedValue;
/*    */ import javax.persistence.GenerationType;
/*    */ import javax.persistence.Id;
/*    */ import javax.persistence.MappedSuperclass;
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
/*    */ @MappedSuperclass
/*    */ public class GenericEntity
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @Id
/*    */   @GeneratedValue(strategy = GenerationType.IDENTITY)
/*    */   private Long id;
/*    */   
/*    */   public GenericEntity() {}
/*    */   
/*    */   public GenericEntity(Long id) {
/* 30 */     this.id = id;
/*    */   }
/*    */   public Long getId() {
/* 33 */     return this.id;
/*    */   }
/*    */   public void setId(Long id) {
/* 36 */     this.id = id;
/*    */   }
/*    */   public static long getSerialversionuid() {
/* 39 */     return 1L;
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\GenericEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */