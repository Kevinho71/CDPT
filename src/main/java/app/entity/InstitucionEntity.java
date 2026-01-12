/*     */ package BOOT-INF.classes.app.entity;
/*     */ 
/*     */ import app.entity.ProvinciaEntity;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.JoinColumn;
/*     */ import javax.persistence.ManyToOne;
/*     */ import javax.persistence.Table;
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
/*     */ @Table(name = "institucion")
/*     */ public class InstitucionEntity
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   private Integer id;
/*     */   @Column(name = "nit")
/*     */   private String nit;
/*     */   @Column(name = "compania")
/*     */   private String compania;
/*     */   @Column(name = "institucion")
/*     */   private String institucion;
/*     */   @Column(name = "representante")
/*     */   private String representante;
/*     */   @Column(name = "correo")
/*     */   private String correo;
/*     */   @Column(name = "direccion")
/*     */   private String direccion;
/*     */   @Column(name = "telefono")
/*     */   private String telefono;
/*     */   @Column(name = "fax")
/*     */   private String fax;
/*     */   @Column(name = "host")
/*     */   private String host;
/*     */   @Column(name = "port")
/*     */   private String port;
/*     */   @Column(name = "estado")
/*     */   private Integer estado;
/*     */   @ManyToOne(optional = false)
/*     */   @JoinColumn(name = "fk_provincia")
/*     */   private ProvinciaEntity provincia;
/*     */   
/*     */   public InstitucionEntity() {}
/*     */   
/*     */   public InstitucionEntity(Integer id, String nit, String compania, String institucion, String representante, String correo, String direccion, String telefono, String fax, String host, String port, Integer estado, ProvinciaEntity provincia) {
/*  78 */     this.id = id;
/*  79 */     this.nit = nit;
/*  80 */     this.compania = compania;
/*  81 */     this.institucion = institucion;
/*  82 */     this.representante = representante;
/*  83 */     this.correo = correo;
/*  84 */     this.direccion = direccion;
/*  85 */     this.telefono = telefono;
/*  86 */     this.fax = fax;
/*  87 */     this.host = host;
/*  88 */     this.port = port;
/*  89 */     this.estado = estado;
/*  90 */     this.provincia = provincia;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getId() {
/*  96 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(Integer id) {
/* 102 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNit() {
/* 108 */     return this.nit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNit(String nit) {
/* 114 */     this.nit = nit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCompania() {
/* 120 */     return this.compania;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCompania(String compania) {
/* 126 */     this.compania = compania;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInstitucion() {
/* 132 */     return this.institucion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInstitucion(String institucion) {
/* 138 */     this.institucion = institucion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRepresentante() {
/* 144 */     return this.representante;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRepresentante(String representante) {
/* 150 */     this.representante = representante;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCorreo() {
/* 156 */     return this.correo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCorreo(String correo) {
/* 162 */     this.correo = correo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDireccion() {
/* 168 */     return this.direccion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDireccion(String direccion) {
/* 174 */     this.direccion = direccion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTelefono() {
/* 180 */     return this.telefono;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTelefono(String telefono) {
/* 186 */     this.telefono = telefono;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFax() {
/* 192 */     return this.fax;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFax(String fax) {
/* 198 */     this.fax = fax;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHost() {
/* 204 */     return this.host;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHost(String host) {
/* 210 */     this.host = host;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPort() {
/* 216 */     return this.port;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPort(String port) {
/* 222 */     this.port = port;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getEstado() {
/* 228 */     return this.estado;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEstado(Integer estado) {
/* 234 */     this.estado = estado;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ProvinciaEntity getProvincia() {
/* 240 */     return this.provincia;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProvincia(ProvinciaEntity provincia) {
/* 246 */     this.provincia = provincia;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 253 */     return "InstitucionEntity [id=" + this.id + ", nit=" + this.nit + ", compania=" + this.compania + ", institucion=" + this.institucion + ", representante=" + this.representante + ", correo=" + this.correo + ", direccion=" + this.direccion + ", telefono=" + this.telefono + ", fax=" + this.fax + ", host=" + this.host + ", port=" + this.port + ", estado=" + this.estado + ", provincia=" + this.provincia + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\InstitucionEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */