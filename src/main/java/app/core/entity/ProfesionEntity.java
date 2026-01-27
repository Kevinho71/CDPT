 package app.core.entity;
 
 import java.io.Serializable;
 import jakarta.persistence.Column;
 import jakarta.persistence.Entity;
 import jakarta.persistence.Id;
 import jakarta.persistence.Table;


 @Entity
 @Table(name = "profesion")
 public class ProfesionEntity
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   @Id
   private Integer id;
   @Column(name = "nombre")
   private String nombre;
   @Column(name = "estado")
   private Integer estado;
   
   public ProfesionEntity() {}
   
   public ProfesionEntity(Integer id, String nombre, Integer estado) {
     this.id = id;
     this.nombre = nombre;
     this.estado = estado;
   }
   
   public Integer getId() {
     return this.id;
   }
   
   public void setId(Integer id) {
     this.id = id;
   }
   
   public String getNombre() {
     return this.nombre;
   }
   
   public void setNombre(String nombre) {
     this.nombre = nombre;
   }
   
   public Integer getEstado() {
     return this.estado;
   }
   
   public void setEstado(Integer estado) {
     this.estado = estado;
   }


   public String toString() {
     return "ProfesionEntity [id=" + this.id + ", nombre=" + this.nombre + ", estado=" + this.estado + "]";
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\ProfesionEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */