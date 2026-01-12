/*     */ package BOOT-INF.classes.app.dto;
/*     */ 
/*     */ import java.time.LocalDate;
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
/*     */ public class SocioDTO
/*     */ {
/*     */   private Integer id;
/*     */   private Integer codigo;
/*     */   private String nrodocumento;
/*     */   private String imagen;
/*     */   private String qr;
/*     */   private String linkqr;
/*     */   private String matricula;
/*     */   private String nombresocio;
/*     */   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
/*     */   private LocalDate fechaemision;
/*     */   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
/*     */   private LocalDate fechaexpiracion;
/*     */   private Integer lejendario;
/*     */   private String ci;
/*     */   private String nombrecompleto;
/*     */   private String email;
/*     */   private Integer celular;
/*     */   @Transient
/*     */   private MultipartFile logo;
/*     */   
/*     */   public SocioDTO() {}
/*     */   
/*     */   public SocioDTO(Integer id, Integer codigo, String nrodocumento, String imagen, String qr, String linkqr, String matricula, String nombresocio, LocalDate fechaemision, LocalDate fechaexpiracion, Integer lejendario, String ci, String nombrecompleto, String email, Integer celular, MultipartFile logo) {
/*  48 */     this.id = id;
/*  49 */     this.codigo = codigo;
/*  50 */     this.nrodocumento = nrodocumento;
/*  51 */     this.imagen = imagen;
/*  52 */     this.qr = qr;
/*  53 */     this.linkqr = linkqr;
/*  54 */     this.matricula = matricula;
/*  55 */     this.nombresocio = nombresocio;
/*  56 */     this.fechaemision = fechaemision;
/*  57 */     this.fechaexpiracion = fechaexpiracion;
/*  58 */     this.lejendario = lejendario;
/*  59 */     this.ci = ci;
/*  60 */     this.nombrecompleto = nombrecompleto;
/*  61 */     this.email = email;
/*  62 */     this.celular = celular;
/*  63 */     this.logo = logo;
/*     */   }
/*     */   public Integer getId() {
/*  66 */     return this.id;
/*     */   }
/*     */   public void setId(Integer id) {
/*  69 */     this.id = id;
/*     */   }
/*     */   public Integer getCodigo() {
/*  72 */     return this.codigo;
/*     */   }
/*     */   public void setCodigo(Integer codigo) {
/*  75 */     this.codigo = codigo;
/*     */   }
/*     */   public String getNrodocumento() {
/*  78 */     return this.nrodocumento;
/*     */   }
/*     */   public void setNrodocumento(String nrodocumento) {
/*  81 */     this.nrodocumento = nrodocumento;
/*     */   }
/*     */   public String getImagen() {
/*  84 */     return this.imagen;
/*     */   }
/*     */   public void setImagen(String imagen) {
/*  87 */     this.imagen = imagen;
/*     */   }
/*     */   public String getQr() {
/*  90 */     return this.qr;
/*     */   }
/*     */   public void setQr(String qr) {
/*  93 */     this.qr = qr;
/*     */   }
/*     */   public String getLinkqr() {
/*  96 */     return this.linkqr;
/*     */   }
/*     */   public void setLinkqr(String linkqr) {
/*  99 */     this.linkqr = linkqr;
/*     */   }
/*     */   public String getMatricula() {
/* 102 */     return this.matricula;
/*     */   }
/*     */   public void setMatricula(String matricula) {
/* 105 */     this.matricula = matricula;
/*     */   }
/*     */   public String getNombresocio() {
/* 108 */     return this.nombresocio;
/*     */   }
/*     */   public void setNombresocio(String nombresocio) {
/* 111 */     this.nombresocio = nombresocio;
/*     */   }
/*     */   public LocalDate getFechaemision() {
/* 114 */     return this.fechaemision;
/*     */   }
/*     */   public void setFechaemision(LocalDate fechaemision) {
/* 117 */     this.fechaemision = fechaemision;
/*     */   }
/*     */   public LocalDate getFechaexpiracion() {
/* 120 */     return this.fechaexpiracion;
/*     */   }
/*     */   public void setFechaexpiracion(LocalDate fechaexpiracion) {
/* 123 */     this.fechaexpiracion = fechaexpiracion;
/*     */   }
/*     */   public Integer getLejendario() {
/* 126 */     return this.lejendario;
/*     */   }
/*     */   public void setLejendario(Integer lejendario) {
/* 129 */     this.lejendario = lejendario;
/*     */   }
/*     */   public String getCi() {
/* 132 */     return this.ci;
/*     */   }
/*     */   public void setCi(String ci) {
/* 135 */     this.ci = ci;
/*     */   }
/*     */   public String getNombrecompleto() {
/* 138 */     return this.nombrecompleto;
/*     */   }
/*     */   public void setNombrecompleto(String nombrecompleto) {
/* 141 */     this.nombrecompleto = nombrecompleto;
/*     */   }
/*     */   public String getEmail() {
/* 144 */     return this.email;
/*     */   }
/*     */   public void setEmail(String email) {
/* 147 */     this.email = email;
/*     */   }
/*     */   public Integer getCelular() {
/* 150 */     return this.celular;
/*     */   }
/*     */   public void setCelular(Integer celular) {
/* 153 */     this.celular = celular;
/*     */   }
/*     */   public MultipartFile getLogo() {
/* 156 */     return this.logo;
/*     */   }
/*     */   public void setLogo(MultipartFile logo) {
/* 159 */     this.logo = logo;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 163 */     return "SocioDTO [id=" + this.id + ", codigo=" + this.codigo + ", nrodocumento=" + this.nrodocumento + ", imagen=" + this.imagen + ", qr=" + this.qr + ", linkqr=" + this.linkqr + ", matricula=" + this.matricula + ", nombresocio=" + this.nombresocio + ", fechaemision=" + this.fechaemision + ", fechaexpiracion=" + this.fechaexpiracion + ", lejendario=" + this.lejendario + ", ci=" + this.ci + ", nombrecompleto=" + this.nombrecompleto + ", email=" + this.email + ", celular=" + this.celular + ", logo=" + this.logo + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\dto\SocioDTO.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */