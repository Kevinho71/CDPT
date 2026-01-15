 package app.catalogo.entity;
 
 import app.catalogo.entity.ImagenesCatalogoEntity;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 import jakarta.persistence.CascadeType;
 import jakarta.persistence.Column;
 import jakarta.persistence.Entity;
 import jakarta.persistence.Id;
 import jakarta.persistence.JoinColumn;
 import jakarta.persistence.OneToMany;
 import jakarta.persistence.Table;
 import jakarta.persistence.Transient;
 import org.hibernate.annotations.JdbcTypeCode;
 import org.hibernate.type.SqlTypes;
 import org.springframework.web.multipart.MultipartFile;


 @Entity
 @Table(name = "catalogo")
 public class CatalogoEntity
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   @Id
   private Integer id;
   @Column(name = "codigo")
   private Integer codigo;
   @Column(name = "nit")
   private String nit;
   @Column(name = "nombre")
   private String nombre;
   @Column(name = "descripcion", length = 800)
   private String descripcion;
   @Column(name = "direccion")
   private String direccion;
   
   @Column(name = "descuento", columnDefinition = "jsonb")
   @JdbcTypeCode(SqlTypes.JSON)
   private String descuento;
   
   @Column(name = "tipo")
   private String tipo;
   @Column(name = "nombrelogo")
   private String nombrelogo;
   @Column(name = "longitud")
   private String longitud;
   @Column(name = "latitud")
   private String latitud;
   @Column(name = "estado")
   private Integer estado;
   @OneToMany(cascade = {CascadeType.REFRESH})
   @JoinColumn(name = "fk_catalogo")
   private List<ImagenesCatalogoEntity> imagenesCatalogos = new ArrayList<>();


   @Transient
   private MultipartFile logo;


   @Transient
   private List<MultipartFile> catalogo;


   public CatalogoEntity() {}


   public CatalogoEntity(Integer id, Integer codigo, String nit, String nombre, String descripcion, String direccion, String descuento, String tipo, String nombrelogo, String longitud, String latitud, Integer estado, List<ImagenesCatalogoEntity> imagenesCatalogos, MultipartFile logo, List<MultipartFile> catalogo) {
     this.id = id;
     this.codigo = codigo;
     this.nit = nit;
     this.nombre = nombre;
     this.descripcion = descripcion;
     this.direccion = direccion;
     this.descuento = descuento;
     this.tipo = tipo;
     this.nombrelogo = nombrelogo;
     this.longitud = longitud;
     this.latitud = latitud;
     this.estado = estado;
     this.imagenesCatalogos = imagenesCatalogos;
     this.logo = logo;
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
   
   public String getNit() {
     return this.nit;
   }
   
   public void setNit(String nit) {
     this.nit = nit;
   }
   
   public String getNombre() {
     return this.nombre;
   }
   
   public void setNombre(String nombre) {
     this.nombre = nombre;
   }
   
   public String getDescripcion() {
     return this.descripcion;
   }
   
   public void setDescripcion(String descripcion) {
     this.descripcion = descripcion;
   }
   
   public String getDireccion() {
     return this.direccion;
   }
   
   public void setDireccion(String direccion) {
     this.direccion = direccion;
   }
   
   public String getDescuento() {
     return this.descuento;
   }
   
   public void setDescuento(String descuento) {
     this.descuento = descuento;
   }
   
   public String getTipo() {
     return this.tipo;
   }
   
   public void setTipo(String tipo) {
     this.tipo = tipo;
   }
   
   public String getNombrelogo() {
     return this.nombrelogo;
   }
   
   public void setNombrelogo(String nombrelogo) {
     this.nombrelogo = nombrelogo;
   }
   
   public String getLongitud() {
     return this.longitud;
   }
   
   public void setLongitud(String longitud) {
     this.longitud = longitud;
   }
   
   public String getLatitud() {
     return this.latitud;
   }
   
   public void setLatitud(String latitud) {
     this.latitud = latitud;
   }
   
   public Integer getEstado() {
     return this.estado;
   }
   
   public void setEstado(Integer estado) {
     this.estado = estado;
   }
   
   public List<ImagenesCatalogoEntity> getImagenesCatalogos() {
     return this.imagenesCatalogos;
   }
   
   public void setImagenesCatalogos(List<ImagenesCatalogoEntity> imagenesCatalogos) {
     this.imagenesCatalogos = imagenesCatalogos;
   }
   
   public MultipartFile getLogo() {
     return this.logo;
   }
   
   public void setLogo(MultipartFile logo) {
     this.logo = logo;
   }
   
   public List<MultipartFile> getCatalogo() {
     return this.catalogo;
   }
   
   public void setCatalogo(List<MultipartFile> catalogo) {
     this.catalogo = catalogo;
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\CatalogoEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */