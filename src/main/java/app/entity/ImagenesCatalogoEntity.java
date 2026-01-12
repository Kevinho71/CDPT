/*     */ package BOOT-INF.classes.app.entity;
/*     */ 
/*     */ import app.entity.CatalogoEntity;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.CascadeType;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.JoinColumn;
/*     */ import javax.persistence.ManyToOne;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.Transient;
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
/*     */ @Table(name = "imagencatalogo")
/*     */ public class ImagenesCatalogoEntity
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   private Integer id;
/*     */   @Column(name = "codigo")
/*     */   private Integer codigo;
/*     */   @Column(name = "imagen")
/*     */   private String imagen;
/*     */   @Column(name = "imagenDriveId")
/*     */   private String imagenDriveId;
/*     */   @Column(name = "estado")
/*     */   private Integer estado;
/*     */   @Transient
/*     */   @ManyToOne(cascade = {CascadeType.REFRESH})
/*     */   @JoinColumn(name = "fk_catalogo")
/*     */   private CatalogoEntity catalogo;
/*     */   
/*     */   public ImagenesCatalogoEntity() {}
/*     */   
/*     */   public ImagenesCatalogoEntity(Integer id, Integer codigo, String imagen, String imagenDriveId, Integer estado, CatalogoEntity catalogo) {
/*  49 */     this.id = id;
/*  50 */     this.codigo = codigo;
/*  51 */     this.imagen = imagen;
/*  52 */     this.imagenDriveId = imagenDriveId;
/*  53 */     this.estado = estado;
/*  54 */     this.catalogo = catalogo;
/*     */   }
/*     */   
/*     */   public Integer getId() {
/*  58 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/*  62 */     this.id = id;
/*     */   }
/*     */   
/*     */   public Integer getCodigo() {
/*  66 */     return this.codigo;
/*     */   }
/*     */   
/*     */   public void setCodigo(Integer codigo) {
/*  70 */     this.codigo = codigo;
/*     */   }
/*     */   
/*     */   public String getImagen() {
/*  74 */     return this.imagen;
/*     */   }
/*     */   
/*     */   public void setImagen(String imagen) {
/*  78 */     this.imagen = imagen;
/*     */   }
/*     */   
/*     */   public String getImagenDriveId() {
/*  82 */     return this.imagenDriveId;
/*     */   }
/*     */   
/*     */   public void setImagenDriveId(String imagenDriveId) {
/*  86 */     this.imagenDriveId = imagenDriveId;
/*     */   }
/*     */   
/*     */   public Integer getEstado() {
/*  90 */     return this.estado;
/*     */   }
/*     */   
/*     */   public void setEstado(Integer estado) {
/*  94 */     this.estado = estado;
/*     */   }
/*     */   
/*     */   public CatalogoEntity getCatalogo() {
/*  98 */     return this.catalogo;
/*     */   }
/*     */   
/*     */   public void setCatalogo(CatalogoEntity catalogo) {
/* 102 */     this.catalogo = catalogo;
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\ImagenesCatalogoEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */