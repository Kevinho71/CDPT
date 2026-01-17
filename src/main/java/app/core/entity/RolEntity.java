 package app.core.entity;
 
 import app.common.entity.GenericEntity;
 import jakarta.persistence.Entity;
 import jakarta.persistence.Table;


 @Entity
 @Table(name = "rol")
 public class RolEntity
   extends GenericEntity
 {
   private static final long serialVersionUID = 1L;
   private String nombre;
   private int estado;
   
   public RolEntity() {}
   
   public RolEntity(String nombre, int estado) {
     this.nombre = nombre;
     this.estado = estado;
   }


   public RolEntity(String nombre) {
     this.nombre = nombre;
   }
   
   public RolEntity(Integer id) {
     super(id);
   }

   public String getNombre() {
     return this.nombre;
   }
   
   public void setNombre(String nombre) {
     this.nombre = nombre;
   }
   
   public int getEstado() {
     return this.estado;
   }
   
   public void setEstado(int estado) {
     this.estado = estado;
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\RolEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */