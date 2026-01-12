/*     */ package BOOT-INF.classes.app.entity;
/*     */ 
/*     */ import app.entity.PersonaEntity;
/*     */ import app.entity.RolEntity;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import javax.persistence.CascadeType;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.FetchType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.JoinColumn;
/*     */ import javax.persistence.JoinTable;
/*     */ import javax.persistence.ManyToMany;
/*     */ import javax.persistence.ManyToOne;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.UniqueConstraint;
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
/*     */ @Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
/*     */ public class UsuarioEntity
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @Id
/*     */   private Integer id;
/*     */   private String username;
/*     */   private String password;
/*     */   private Integer estado;
/*     */   @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
/*     */   @JoinTable(name = "usuarios_roles", joinColumns = {@JoinColumn(name = "usuario_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "rol_id", referencedColumnName = "id")})
/*     */   private Collection<RolEntity> roles;
/*     */   @ManyToOne(optional = false)
/*     */   @JoinColumn(name = "fk_persona")
/*     */   private PersonaEntity persona;
/*     */   
/*     */   public UsuarioEntity() {}
/*     */   
/*     */   public UsuarioEntity(Integer id, String username, String password, Integer estado, Collection<RolEntity> roles, PersonaEntity persona) {
/*  61 */     this.id = id;
/*  62 */     this.username = username;
/*  63 */     this.password = password;
/*  64 */     this.estado = estado;
/*  65 */     this.roles = roles;
/*  66 */     this.persona = persona;
/*     */   }
/*     */   
/*     */   public Integer getId() {
/*  70 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/*  74 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getUsername() {
/*  78 */     return this.username;
/*     */   }
/*     */   
/*     */   public void setUsername(String username) {
/*  82 */     this.username = username;
/*     */   }
/*     */   
/*     */   public String getPassword() {
/*  86 */     return this.password;
/*     */   }
/*     */   
/*     */   public void setPassword(String password) {
/*  90 */     this.password = password;
/*     */   }
/*     */   
/*     */   public Integer getEstado() {
/*  94 */     return this.estado;
/*     */   }
/*     */   
/*     */   public void setEstado(Integer estado) {
/*  98 */     this.estado = estado;
/*     */   }
/*     */   
/*     */   public Collection<RolEntity> getRoles() {
/* 102 */     return this.roles;
/*     */   }
/*     */   
/*     */   public void setRoles(Collection<RolEntity> roles) {
/* 106 */     this.roles = roles;
/*     */   }
/*     */   
/*     */   public PersonaEntity getPersona() {
/* 110 */     return this.persona;
/*     */   }
/*     */   
/*     */   public void setPersona(PersonaEntity persona) {
/* 114 */     this.persona = persona;
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\UsuarioEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */