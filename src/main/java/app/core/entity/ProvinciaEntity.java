 package app.core.entity;
 
 import app.core.entity.DepartamentoEntity;
 import java.io.Serializable;
 import jakarta.persistence.Column;
 import jakarta.persistence.Entity;
 import jakarta.persistence.Id;
 import jakarta.persistence.JoinColumn;
 import jakarta.persistence.ManyToOne;
 import jakarta.persistence.Table;


 @Entity
 @Table(name = "provincia")
 public class ProvinciaEntity
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   @Id
   private Integer id;
   @Column(name = "nombre")
   private String nombre;
   @Column(name = "estado")
   private Integer estado;
   @ManyToOne(optional = false)
   @JoinColumn(name = "fk_departamento")
   private DepartamentoEntity departamento;
   
   public ProvinciaEntity() {}
   
   public ProvinciaEntity(Integer id, String nombre, Integer estado, DepartamentoEntity departamento) {
     this.id = id;
     this.nombre = nombre;
     this.estado = estado;
     this.departamento = departamento;
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
   
   public DepartamentoEntity getDepartamento() {
     return this.departamento;
   }
   
   public void setDepartamento(DepartamentoEntity departamento) {
     this.departamento = departamento;
   }


   public String toString() {
     return "ProvinciaEntity [id=" + this.id + ", nombre=" + this.nombre + ", estado=" + this.estado + ", departamento=" + this.departamento + "]";
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\ProvinciaEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */