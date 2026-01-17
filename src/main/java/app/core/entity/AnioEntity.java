 package app.core.entity;
 
 import app.common.entity.GenericEntity;
 import app.core.entity.InstitucionEntity;
 import jakarta.persistence.Column;
 import jakarta.persistence.Entity;
 import jakarta.persistence.JoinColumn;
 import jakarta.persistence.ManyToOne;
 import jakarta.persistence.Table;


 @Entity
 @Table(name = "anio")
 public class AnioEntity
   extends GenericEntity
 {
   private static final Integer serialVersionUID = 1;
   @Column(name = "nombre")
   private String nombre;
   @Column(name = "estado")
   private Integer estado;
   @ManyToOne(optional = false)
   @JoinColumn(name = "fk_institucion")
   private InstitucionEntity institucion;
   
   public AnioEntity() {}
   
   public AnioEntity(String nombre, Integer estado, InstitucionEntity institucion) {
     this.nombre = nombre;
     this.estado = estado;
     this.institucion = institucion;
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
   
   public InstitucionEntity getInstitucion() {
     return this.institucion;
   }
   
   public void setInstitucion(InstitucionEntity institucion) {
     this.institucion = institucion;
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\AnioEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */