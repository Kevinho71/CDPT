/*     */ package BOOT-INF.classes.app.entity;
/*     */ 
/*     */ import app.entity.CatalogoEntity;
/*     */ import app.entity.InstitucionEntity;
/*     */ import app.entity.PersonaEntity;
/*     */ import app.entity.ProfesionEntity;
/*     */ import com.fasterxml.jackson.annotation.JsonFormat;
/*     */ import java.io.Serializable;
/*     */ import java.time.LocalDate;
/*     */ import java.util.Collection;
/*     */ import javax.persistence.CascadeType;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.JoinColumn;
/*     */ import javax.persistence.JoinTable;
/*     */ import javax.persistence.ManyToMany;
/*     */ import javax.persistence.ManyToOne;
/*     */ import javax.persistence.OneToOne;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.Transient;
/*     */ import org.springframework.format.annotation.DateTimeFormat;
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
/*     */ @Table(name = "socio")
/*     */ public class SocioEntity
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   private Integer id;
/*     */   @Column(name = "codigo")
/*     */   private Integer codigo;
/*     */   @Column(name = "nrodocumento")
/*     */   private String nrodocumento;
/*     */   @Column(name = "imagen")
/*     */   private String imagen;
/*     */   @Column(name = "imagenDriveId")
/*     */   private String imagenDriveId;
/*     */   @Column(name = "qr")
/*     */   private String qr;
/*     */   @Column(name = "linkqr")
/*     */   private String linkqr;
/*     */   @Column(name = "matricula")
/*     */   private String matricula;
/*     */   @Column(name = "nombresocio")
/*     */   private String nombresocio;
/*     */   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
/*     */   @Column(name = "fechaemision")
/*     */   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
/*     */   private LocalDate fechaemision;
/*     */   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
/*     */   @Column(name = "fechaexpiracion")
/*     */   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
/*     */   private LocalDate fechaexpiracion;
/*     */   @Column(name = "lejendario")
/*     */   private Integer lejendario;
/*     */   @Column(name = "estado")
/*     */   private Integer estado;
/*     */   @ManyToOne(optional = false)
/*     */   @JoinColumn(name = "fk_profesion")
/*     */   private ProfesionEntity profesion;
/*     */   @OneToOne
/*     */   @JoinColumn(name = "fk_persona")
/*     */   private PersonaEntity persona;
/*     */   @ManyToOne(optional = false)
/*     */   @JoinColumn(name = "fk_institucion")
/*     */   private InstitucionEntity institucion;
/*     */   @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
/*     */   @JoinTable(name = "socio_catalogos", joinColumns = {@JoinColumn(name = "socio_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "catalogo_id", referencedColumnName = "id")})
/*     */   private Collection<CatalogoEntity> catalogos;
/*     */   @Transient
/*     */   private MultipartFile logo;
/*     */   
/*     */   public SocioEntity() {}
/*     */   
/*     */   public SocioEntity(Integer id, Integer codigo, String nrodocumento, String imagen, String imagenDriveId, String qr, String linkqr, String matricula, String nombresocio, LocalDate fechaemision, LocalDate fechaexpiracion, Integer lejendario, Integer estado, ProfesionEntity profesion, PersonaEntity persona, InstitucionEntity institucion, Collection<CatalogoEntity> catalogos, MultipartFile logo) {
/* 118 */     this.id = id;
/* 119 */     this.codigo = codigo;
/* 120 */     this.nrodocumento = nrodocumento;
/* 121 */     this.imagen = imagen;
/* 122 */     this.imagenDriveId = imagenDriveId;
/* 123 */     this.qr = qr;
/* 124 */     this.linkqr = linkqr;
/* 125 */     this.matricula = matricula;
/* 126 */     this.nombresocio = nombresocio;
/* 127 */     this.fechaemision = fechaemision;
/* 128 */     this.fechaexpiracion = fechaexpiracion;
/* 129 */     this.lejendario = lejendario;
/* 130 */     this.estado = estado;
/* 131 */     this.profesion = profesion;
/* 132 */     this.persona = persona;
/* 133 */     this.institucion = institucion;
/* 134 */     this.catalogos = catalogos;
/* 135 */     this.logo = logo;
/*     */   }
/*     */   
/*     */   public Integer getId() {
/* 139 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/* 143 */     this.id = id;
/*     */   }
/*     */   
/*     */   public Integer getCodigo() {
/* 147 */     return this.codigo;
/*     */   }
/*     */   
/*     */   public void setCodigo(Integer codigo) {
/* 151 */     this.codigo = codigo;
/*     */   }
/*     */   
/*     */   public String getNrodocumento() {
/* 155 */     return this.nrodocumento;
/*     */   }
/*     */   
/*     */   public void setNrodocumento(String nrodocumento) {
/* 159 */     this.nrodocumento = nrodocumento;
/*     */   }
/*     */   
/*     */   public String getImagen() {
/* 163 */     return this.imagen;
/*     */   }
/*     */   
/*     */   public void setImagen(String imagen) {
/* 167 */     this.imagen = imagen;
/*     */   }
/*     */   
/*     */   public String getImagenDriveId() {
/* 171 */     return this.imagenDriveId;
/*     */   }
/*     */   
/*     */   public void setImagenDriveId(String imagenDriveId) {
/* 175 */     this.imagenDriveId = imagenDriveId;
/*     */   }
/*     */   
/*     */   public String getQr() {
/* 179 */     return this.qr;
/*     */   }
/*     */   
/*     */   public void setQr(String qr) {
/* 183 */     this.qr = qr;
/*     */   }
/*     */   
/*     */   public String getLinkqr() {
/* 187 */     return this.linkqr;
/*     */   }
/*     */   
/*     */   public void setLinkqr(String linkqr) {
/* 191 */     this.linkqr = linkqr;
/*     */   }
/*     */   
/*     */   public String getMatricula() {
/* 195 */     return this.matricula;
/*     */   }
/*     */   
/*     */   public void setMatricula(String matricula) {
/* 199 */     this.matricula = matricula;
/*     */   }
/*     */   
/*     */   public String getNombresocio() {
/* 203 */     return this.nombresocio;
/*     */   }
/*     */   
/*     */   public void setNombresocio(String nombresocio) {
/* 207 */     this.nombresocio = nombresocio;
/*     */   }
/*     */   
/*     */   public LocalDate getFechaemision() {
/* 211 */     return this.fechaemision;
/*     */   }
/*     */   
/*     */   public void setFechaemision(LocalDate fechaemision) {
/* 215 */     this.fechaemision = fechaemision;
/*     */   }
/*     */   
/*     */   public LocalDate getFechaexpiracion() {
/* 219 */     return this.fechaexpiracion;
/*     */   }
/*     */   
/*     */   public void setFechaexpiracion(LocalDate fechaexpiracion) {
/* 223 */     this.fechaexpiracion = fechaexpiracion;
/*     */   }
/*     */   
/*     */   public Integer getLejendario() {
/* 227 */     return this.lejendario;
/*     */   }
/*     */   
/*     */   public void setLejendario(Integer lejendario) {
/* 231 */     this.lejendario = lejendario;
/*     */   }
/*     */   
/*     */   public Integer getEstado() {
/* 235 */     return this.estado;
/*     */   }
/*     */   
/*     */   public void setEstado(Integer estado) {
/* 239 */     this.estado = estado;
/*     */   }
/*     */   
/*     */   public ProfesionEntity getProfesion() {
/* 243 */     return this.profesion;
/*     */   }
/*     */   
/*     */   public void setProfesion(ProfesionEntity profesion) {
/* 247 */     this.profesion = profesion;
/*     */   }
/*     */   
/*     */   public PersonaEntity getPersona() {
/* 251 */     return this.persona;
/*     */   }
/*     */   
/*     */   public void setPersona(PersonaEntity persona) {
/* 255 */     this.persona = persona;
/*     */   }
/*     */   
/*     */   public InstitucionEntity getInstitucion() {
/* 259 */     return this.institucion;
/*     */   }
/*     */   
/*     */   public void setInstitucion(InstitucionEntity institucion) {
/* 263 */     this.institucion = institucion;
/*     */   }
/*     */   
/*     */   public Collection<CatalogoEntity> getCatalogos() {
/* 267 */     return this.catalogos;
/*     */   }
/*     */   
/*     */   public void setCatalogos(Collection<CatalogoEntity> catalogos) {
/* 271 */     this.catalogos = catalogos;
/*     */   }
/*     */   
/*     */   public MultipartFile getLogo() {
/* 275 */     return this.logo;
/*     */   }
/*     */   
/*     */   public void setLogo(MultipartFile logo) {
/* 279 */     this.logo = logo;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 284 */     return "SocioEntity [id=" + this.id + ", codigo=" + this.codigo + ", nrodocumento=" + this.nrodocumento + ", imagen=" + this.imagen + ", imagenDriveId=" + this.imagenDriveId + ", qr=" + this.qr + ", linkqr=" + this.linkqr + ", matricula=" + this.matricula + ", nombresocio=" + this.nombresocio + ", fechaemision=" + this.fechaemision + ", fechaexpiracion=" + this.fechaexpiracion + ", lejendario=" + this.lejendario + ", estado=" + this.estado + ", profesion=" + this.profesion + ", persona=" + this.persona + ", institucion=" + this.institucion + ", catalogos=" + this.catalogos + ", logo=" + this.logo + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\SocioEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */