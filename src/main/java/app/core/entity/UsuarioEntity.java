 package app.core.entity;
 
 import app.core.entity.PersonaEntity;
 import app.core.entity.RolEntity;
 import app.posts.entity.PostEntity;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.List;
 import jakarta.persistence.CascadeType;
 import jakarta.persistence.Entity;
 import jakarta.persistence.FetchType;
 import jakarta.persistence.Id;
 import jakarta.persistence.JoinColumn;
 import jakarta.persistence.JoinTable;
 import jakarta.persistence.ManyToMany;
 import jakarta.persistence.ManyToOne;
 import jakarta.persistence.OneToMany;
 import jakarta.persistence.Table;
 import jakarta.persistence.UniqueConstraint;


 @Entity
 @Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
 public class UsuarioEntity
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   @Id
   private Integer id;
   private String username;
   private String password;
   private Integer estado;
   @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
   @JoinTable(name = "usuarios_roles", joinColumns = {@JoinColumn(name = "usuario_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "rol_id", referencedColumnName = "id")})
   private Collection<RolEntity> roles;
   @ManyToOne(optional = false)
   @JoinColumn(name = "fk_persona")
   private PersonaEntity persona;
   
   @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private List<PostEntity> posts = new ArrayList<>();
   
   public UsuarioEntity() {}
   
   public UsuarioEntity(Integer id, String username, String password, Integer estado, Collection<RolEntity> roles, PersonaEntity persona) {
     this.id = id;
     this.username = username;
     this.password = password;
     this.estado = estado;
     this.roles = roles;
     this.persona = persona;
   }
   
   public Integer getId() {
     return this.id;
   }
   
   public void setId(Integer id) {
     this.id = id;
   }
   
   public String getUsername() {
     return this.username;
   }
   
   public void setUsername(String username) {
     this.username = username;
   }
   
   public String getPassword() {
     return this.password;
   }
   
   public void setPassword(String password) {
     this.password = password;
   }
   
   public Integer getEstado() {
     return this.estado;
   }
   
   public void setEstado(Integer estado) {
     this.estado = estado;
   }
   
   public Collection<RolEntity> getRoles() {
     return this.roles;
   }
   
   public void setRoles(Collection<RolEntity> roles) {
     this.roles = roles;
   }
   
   public PersonaEntity getPersona() {
     return this.persona;
   }
   
   public void setPersona(PersonaEntity persona) {
     this.persona = persona;
   }
   
   public List<PostEntity> getPosts() {
     return this.posts;
   }
   
   public void setPosts(List<PostEntity> posts) {
     this.posts = posts;
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\UsuarioEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */