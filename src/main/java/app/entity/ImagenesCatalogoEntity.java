 package app.entity;
 
 import app.entity.CatalogoEntity;
 import java.io.Serializable;
 import jakarta.persistence.CascadeType;
 import jakarta.persistence.Column;
 import jakarta.persistence.Entity;
 import jakarta.persistence.Id;
 import jakarta.persistence.JoinColumn;
 import jakarta.persistence.ManyToOne;
 import jakarta.persistence.Table;
 import jakarta.persistence.Transient;


 @Entity
 @Table(name = "imagencatalogo")
 public class ImagenesCatalogoEntity
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   @Id
   private Integer id;
   @Column(name = "codigo")
   private Integer codigo;
   @Column(name = "imagen")
   private String imagen;
   @Column(name = "estado")
   private Integer estado;
   @Transient
   @ManyToOne(cascade = {CascadeType.REFRESH})
   @JoinColumn(name = "fk_catalogo")
   private CatalogoEntity catalogo;
   
   public ImagenesCatalogoEntity() {}
   
   public ImagenesCatalogoEntity(Integer id, Integer codigo, String imagen, Integer estado, CatalogoEntity catalogo) {
     this.id = id;
     this.codigo = codigo;
     this.imagen = imagen;
     this.estado = estado;
     this.catalogo = catalogo;
   }
   
   public Integer getId() {
     return this.id;
   }
   
   public void setId(Integer id) {
     this.id = id;
   }
   
   public Integer getCodigo() {
     return this.codigo;
   }
   
   public void setCodigo(Integer codigo) {
     this.codigo = codigo;
   }
   
   public String getImagen() {
     return this.imagen;
   }
   
   public void setImagen(String imagen) {
     this.imagen = imagen;
   }
   
   public Integer getEstado() {
     return this.estado;
   }
   
   public void setEstado(Integer estado) {
     this.estado = estado;
   }
   
   public CatalogoEntity getCatalogo() {
     return this.catalogo;
   }
   
   public void setCatalogo(CatalogoEntity catalogo) {
     this.catalogo = catalogo;
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\ImagenesCatalogoEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */