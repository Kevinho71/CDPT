 package app.entity;
 
 import java.io.Serializable;
 import jakarta.persistence.GeneratedValue;
 import jakarta.persistence.GenerationType;
 import jakarta.persistence.Id;
 import jakarta.persistence.MappedSuperclass;


 @MappedSuperclass
 public class GenericEntity
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   public GenericEntity() {}
   
   public GenericEntity(Long id) {
     this.id = id;
   }
   public Long getId() {
     return this.id;
   }
   public void setId(Long id) {
     this.id = id;
   }
   public static long getSerialversionuid() {
     return 1L;
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\GenericEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */