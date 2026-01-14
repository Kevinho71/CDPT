 package app.entity;
 
 import app.entity.CatalogoEntity;
 import app.entity.InstitucionEntity;
 import app.entity.PersonaEntity;
 import app.entity.ProfesionEntity;
 import com.fasterxml.jackson.annotation.JsonFormat;
 import java.io.Serializable;
 import java.time.LocalDate;
 import java.util.Collection;
 import jakarta.persistence.CascadeType;
 import jakarta.persistence.Column;
 import jakarta.persistence.Entity;
 import jakarta.persistence.FetchType;
 import jakarta.persistence.Id;
 import jakarta.persistence.JoinColumn;
 import jakarta.persistence.JoinTable;
 import jakarta.persistence.ManyToMany;
 import jakarta.persistence.ManyToOne;
 import jakarta.persistence.OneToOne;
 import jakarta.persistence.Table;
 import jakarta.persistence.Transient;
 import org.springframework.format.annotation.DateTimeFormat;
 import org.springframework.web.multipart.MultipartFile;


 @Entity
 @Table(name = "socio")
 public class SocioEntity
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   @Id
   private Integer id;
   @Column(name = "codigo")
   private Integer codigo;
   @Column(name = "nrodocumento")
   private String nrodocumento;
   @Column(name = "imagen")
   private String imagen;
   @Column(name = "qr")
   private String qr;
   @Column(name = "linkqr")
   private String linkqr;
   @Column(name = "matricula")
   private String matricula;
   @Column(name = "nombresocio")
   private String nombresocio;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
   @Column(name = "fechaemision")
   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
   private LocalDate fechaemision;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
   @Column(name = "fechaexpiracion")
   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
   private LocalDate fechaexpiracion;
   @Column(name = "lejendario")
   private Integer lejendario;
   @Column(name = "estado")
   private Integer estado;
   @ManyToOne(optional = false)
   @JoinColumn(name = "fk_profesion")
   private ProfesionEntity profesion;
   @OneToOne
   @JoinColumn(name = "fk_persona")
   private PersonaEntity persona;
   @ManyToOne(optional = false)
   @JoinColumn(name = "fk_institucion")
   private InstitucionEntity institucion;
   @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
   @JoinTable(name = "socio_catalogos", joinColumns = {@JoinColumn(name = "socio_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "catalogo_id", referencedColumnName = "id")})
   private Collection<CatalogoEntity> catalogos;
   @Transient
   private MultipartFile logo;
   
   public SocioEntity() {}
   
   public SocioEntity(Integer id, Integer codigo, String nrodocumento, String imagen, String qr, String linkqr, String matricula, String nombresocio, LocalDate fechaemision, LocalDate fechaexpiracion, Integer lejendario, Integer estado, ProfesionEntity profesion, PersonaEntity persona, InstitucionEntity institucion, Collection<CatalogoEntity> catalogos, MultipartFile logo) {
     this.id = id;
     this.codigo = codigo;
     this.nrodocumento = nrodocumento;
     this.imagen = imagen;
     this.qr = qr;
     this.linkqr = linkqr;
     this.matricula = matricula;
     this.nombresocio = nombresocio;
     this.fechaemision = fechaemision;
     this.fechaexpiracion = fechaexpiracion;
     this.lejendario = lejendario;
     this.estado = estado;
     this.profesion = profesion;
     this.persona = persona;
     this.institucion = institucion;
     this.catalogos = catalogos;
     this.logo = logo;
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
   
   public String getNrodocumento() {
     return this.nrodocumento;
   }
   
   public void setNrodocumento(String nrodocumento) {
     this.nrodocumento = nrodocumento;
   }
   
   public String getImagen() {
     return this.imagen;
   }
   
   public void setImagen(String imagen) {
     this.imagen = imagen;
   }
   
   public String getQr() {
     return this.qr;
   }
   
   public void setQr(String qr) {
     this.qr = qr;
   }
   
   public String getLinkqr() {
     return this.linkqr;
   }
   
   public void setLinkqr(String linkqr) {
     this.linkqr = linkqr;
   }
   
   public String getMatricula() {
     return this.matricula;
   }
   
   public void setMatricula(String matricula) {
     this.matricula = matricula;
   }
   
   public String getNombresocio() {
     return this.nombresocio;
   }
   
   public void setNombresocio(String nombresocio) {
     this.nombresocio = nombresocio;
   }
   
   public LocalDate getFechaemision() {
     return this.fechaemision;
   }
   
   public void setFechaemision(LocalDate fechaemision) {
     this.fechaemision = fechaemision;
   }
   
   public LocalDate getFechaexpiracion() {
     return this.fechaexpiracion;
   }
   
   public void setFechaexpiracion(LocalDate fechaexpiracion) {
     this.fechaexpiracion = fechaexpiracion;
   }
   
   public Integer getLejendario() {
     return this.lejendario;
   }
   
   public void setLejendario(Integer lejendario) {
     this.lejendario = lejendario;
   }
   
   public Integer getEstado() {
     return this.estado;
   }
   
   public void setEstado(Integer estado) {
     this.estado = estado;
   }
   
   public ProfesionEntity getProfesion() {
     return this.profesion;
   }
   
   public void setProfesion(ProfesionEntity profesion) {
     this.profesion = profesion;
   }
   
   public PersonaEntity getPersona() {
     return this.persona;
   }
   
   public void setPersona(PersonaEntity persona) {
     this.persona = persona;
   }
   
   public InstitucionEntity getInstitucion() {
     return this.institucion;
   }
   
   public void setInstitucion(InstitucionEntity institucion) {
     this.institucion = institucion;
   }
   
   public Collection<CatalogoEntity> getCatalogos() {
     return this.catalogos;
   }
   
   public void setCatalogos(Collection<CatalogoEntity> catalogos) {
     this.catalogos = catalogos;
   }
   
   public MultipartFile getLogo() {
     return this.logo;
   }
   
   public void setLogo(MultipartFile logo) {
     this.logo = logo;
   }


   public String toString() {
     return "SocioEntity [id=" + this.id + ", codigo=" + this.codigo + ", nrodocumento=" + this.nrodocumento + ", imagen=" + this.imagen + ", qr=" + this.qr + ", linkqr=" + this.linkqr + ", matricula=" + this.matricula + ", nombresocio=" + this.nombresocio + ", fechaemision=" + this.fechaemision + ", fechaexpiracion=" + this.fechaexpiracion + ", lejendario=" + this.lejendario + ", estado=" + this.estado + ", profesion=" + this.profesion + ", persona=" + this.persona + ", institucion=" + this.institucion + ", catalogos=" + this.catalogos + ", logo=" + this.logo + "]";
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\SocioEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */