 package app.core.entity;
 
 import java.io.Serializable;
 import jakarta.persistence.Column;
 import jakarta.persistence.Entity;
 import jakarta.persistence.Id;
 import jakarta.persistence.Table;


 @Entity
 @Table(name = "persona")
 public class PersonaEntity
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   @Id
   private Integer id;
   @Column(name = "ci")
   private String ci;
   @Column(name = "nombrecompleto")
   private String nombrecompleto;
   @Column(name = "email")
   private String email;
   @Column(name = "celular")
   private Integer celular;
   @Column(name = "estado")
   private Integer estado;
   
   public PersonaEntity() {}
   
   public PersonaEntity(Integer id, String ci, String nombrecompleto, String email, Integer celular, Integer estado) {
     this.id = id;
     this.ci = ci;
     this.nombrecompleto = nombrecompleto;
     this.email = email;
     this.celular = celular;
     this.estado = estado;
   }
   public Integer getId() {
     return this.id;
   }
   public void setId(Integer id) {
     this.id = id;
   }
   public String getCi() {
     return this.ci;
   }
   public void setCi(String ci) {
     this.ci = ci;
   }
   public String getNombrecompleto() {
     return this.nombrecompleto;
   }
   public void setNombrecompleto(String nombrecompleto) {
     this.nombrecompleto = nombrecompleto;
   }
   public String getEmail() {
     return this.email;
   }
   public void setEmail(String email) {
     this.email = email;
   }
   public Integer getCelular() {
     return this.celular;
   }
   public void setCelular(Integer celular) {
     this.celular = celular;
   }
   public Integer getEstado() {
     return this.estado;
   }
   public void setEstado(Integer estado) {
     this.estado = estado;
   }
   
   public String toString() {
     return "PersonaEntity [id=" + this.id + ", ci=" + this.ci + ", nombrecompleto=" + this.nombrecompleto + ", email=" + this.email + ", celular=" + this.celular + ", estado=" + this.estado + "]";
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\PersonaEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */