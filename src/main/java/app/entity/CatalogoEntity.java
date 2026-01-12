/*     */ package BOOT-INF.classes.app.entity;
/*     */ 
/*     */ import app.entity.ImagenesCatalogoEntity;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.persistence.CascadeType;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.JoinColumn;
/*     */ import javax.persistence.OneToMany;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.Transient;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ @Table(name = "catalogo")
/*     */ public class CatalogoEntity
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   private Integer id;
/*     */   @Column(name = "codigo")
/*     */   private Integer codigo;
/*     */   @Column(name = "nit")
/*     */   private String nit;
/*     */   @Column(name = "nombre")
/*     */   private String nombre;
/*     */   @Column(name = "descripcion", length = 800)
/*     */   private String descripcion;
/*     */   @Column(name = "direccion")
/*     */   private String direccion;
/*     */   @Column(name = "descuento")
/*     */   private String descuento;
/*     */   @Column(name = "tipo")
/*     */   private String tipo;
/*     */   @Column(name = "nombrelogo")
/*     */   private String nombrelogo;
/*     */   @Column(name = "nombrelogoDriveId")
/*     */   private String nombrelogoDriveId;
/*     */   @Column(name = "longitud")
/*     */   private String longitud;
/*     */   @Column(name = "latitud")
/*     */   private String latitud;
/*     */   @Column(name = "estado")
/*     */   private Integer estado;
/*     */   @OneToMany(cascade = {CascadeType.REFRESH})
/*     */   @JoinColumn(name = "fk_catalogo")
/*  72 */   private List<ImagenesCatalogoEntity> imagenesCatalogos = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transient
/*     */   private MultipartFile logo;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transient
/*     */   private List<MultipartFile> catalogo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CatalogoEntity() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CatalogoEntity(Integer id, Integer codigo, String nit, String nombre, String descripcion, String direccion, String descuento, String tipo, String nombrelogo, String nombrelogoDriveId, String longitud, String latitud, Integer estado, List<ImagenesCatalogoEntity> imagenesCatalogos, MultipartFile logo, List<MultipartFile> catalogo) {
/*  96 */     this.id = id;
/*  97 */     this.codigo = codigo;
/*  98 */     this.nit = nit;
/*  99 */     this.nombre = nombre;
/* 100 */     this.descripcion = descripcion;
/* 101 */     this.direccion = direccion;
/* 102 */     this.descuento = descuento;
/* 103 */     this.tipo = tipo;
/* 104 */     this.nombrelogo = nombrelogo;
/* 105 */     this.nombrelogoDriveId = nombrelogoDriveId;
/* 106 */     this.longitud = longitud;
/* 107 */     this.latitud = latitud;
/* 108 */     this.estado = estado;
/* 109 */     this.imagenesCatalogos = imagenesCatalogos;
/* 110 */     this.logo = logo;
/* 111 */     this.catalogo = catalogo;
/*     */   }
/*     */   
/*     */   public Integer getId() {
/* 115 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/* 119 */     this.id = id;
/*     */   }
/*     */   
/*     */   public Integer getCodigo() {
/* 123 */     return this.codigo;
/*     */   }
/*     */   
/*     */   public void setCodigo(Integer codigo) {
/* 127 */     this.codigo = codigo;
/*     */   }
/*     */   
/*     */   public String getNit() {
/* 131 */     return this.nit;
/*     */   }
/*     */   
/*     */   public void setNit(String nit) {
/* 135 */     this.nit = nit;
/*     */   }
/*     */   
/*     */   public String getNombre() {
/* 139 */     return this.nombre;
/*     */   }
/*     */   
/*     */   public void setNombre(String nombre) {
/* 143 */     this.nombre = nombre;
/*     */   }
/*     */   
/*     */   public String getDescripcion() {
/* 147 */     return this.descripcion;
/*     */   }
/*     */   
/*     */   public void setDescripcion(String descripcion) {
/* 151 */     this.descripcion = descripcion;
/*     */   }
/*     */   
/*     */   public String getDireccion() {
/* 155 */     return this.direccion;
/*     */   }
/*     */   
/*     */   public void setDireccion(String direccion) {
/* 159 */     this.direccion = direccion;
/*     */   }
/*     */   
/*     */   public String getDescuento() {
/* 163 */     return this.descuento;
/*     */   }
/*     */   
/*     */   public void setDescuento(String descuento) {
/* 167 */     this.descuento = descuento;
/*     */   }
/*     */   
/*     */   public String getTipo() {
/* 171 */     return this.tipo;
/*     */   }
/*     */   
/*     */   public void setTipo(String tipo) {
/* 175 */     this.tipo = tipo;
/*     */   }
/*     */   
/*     */   public String getNombrelogo() {
/* 179 */     return this.nombrelogo;
/*     */   }
/*     */   
/*     */   public void setNombrelogo(String nombrelogo) {
/* 183 */     this.nombrelogo = nombrelogo;
/*     */   }
/*     */   
/*     */   public String getNombrelogoDriveId() {
/* 187 */     return this.nombrelogoDriveId;
/*     */   }
/*     */   
/*     */   public void setNombrelogoDriveId(String nombrelogoDriveId) {
/* 191 */     this.nombrelogoDriveId = nombrelogoDriveId;
/*     */   }
/*     */   
/*     */   public String getLongitud() {
/* 195 */     return this.longitud;
/*     */   }
/*     */   
/*     */   public void setLongitud(String longitud) {
/* 199 */     this.longitud = longitud;
/*     */   }
/*     */   
/*     */   public String getLatitud() {
/* 203 */     return this.latitud;
/*     */   }
/*     */   
/*     */   public void setLatitud(String latitud) {
/* 207 */     this.latitud = latitud;
/*     */   }
/*     */   
/*     */   public Integer getEstado() {
/* 211 */     return this.estado;
/*     */   }
/*     */   
/*     */   public void setEstado(Integer estado) {
/* 215 */     this.estado = estado;
/*     */   }
/*     */   
/*     */   public List<ImagenesCatalogoEntity> getImagenesCatalogos() {
/* 219 */     return this.imagenesCatalogos;
/*     */   }
/*     */   
/*     */   public void setImagenesCatalogos(List<ImagenesCatalogoEntity> imagenesCatalogos) {
/* 223 */     this.imagenesCatalogos = imagenesCatalogos;
/*     */   }
/*     */   
/*     */   public MultipartFile getLogo() {
/* 227 */     return this.logo;
/*     */   }
/*     */   
/*     */   public void setLogo(MultipartFile logo) {
/* 231 */     this.logo = logo;
/*     */   }
/*     */   
/*     */   public List<MultipartFile> getCatalogo() {
/* 235 */     return this.catalogo;
/*     */   }
/*     */   
/*     */   public void setCatalogo(List<MultipartFile> catalogo) {
/* 239 */     this.catalogo = catalogo;
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\CatalogoEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */