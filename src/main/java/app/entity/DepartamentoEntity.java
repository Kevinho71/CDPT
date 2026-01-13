 package app.entity;
 
 import app.entity.PaisEntity;
 import java.io.Serializable;
 import jakarta.persistence.Column;
 import jakarta.persistence.Entity;
 import jakarta.persistence.Id;
 import jakarta.persistence.JoinColumn;
 import jakarta.persistence.ManyToOne;
 import jakarta.persistence.Table;


 @Entity
 @Table(name = "departamento")
 public class DepartamentoEntity
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   @Id
   private Integer id;
   @Column(name = "abreviacion")
   private String abreviacion;
   @Column(name = "nombre")
   private String nombre;
   @Column(name = "estado")
   private Integer estado;
   @ManyToOne(optional = false)
   @JoinColumn(name = "fk_pais")
   private PaisEntity pais;
   
   public DepartamentoEntity() {}
   
   public DepartamentoEntity(Integer id, String abreviacion, String nombre, Integer estado, PaisEntity pais) {
     this.id = id;
     this.abreviacion = abreviacion;
     this.nombre = nombre;
     this.estado = estado;
     this.pais = pais;
   }
   
   public Integer getId() {
     return this.id;
   }
   
   public void setId(Integer id) {
     this.id = id;
   }
   
   public String getAbreviacion() {
     return this.abreviacion;
   }
   
   public void setAbreviacion(String abreviacion) {
     this.abreviacion = abreviacion;
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
   
   public PaisEntity getPais() {
     return this.pais;
   }
   
   public void setPais(PaisEntity pais) {
     this.pais = pais;
   }


   public String toString() {
     return "DepartamentoEntity [id=" + this.id + ", abreviacion=" + this.abreviacion + ", nombre=" + this.nombre + ", estado=" + this.estado + ", pais=" + this.pais + "]";
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\DepartamentoEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */