 package app.socio.dto;
 
 import java.time.LocalDate;
 import jakarta.persistence.Transient;
 import org.springframework.format.annotation.DateTimeFormat;
 import org.springframework.web.multipart.MultipartFile;


 public class SocioDTO
 {
   private Integer id;
   private Integer codigo;
   private String nrodocumento;
   private String imagen;
   private String qr;
   private String linkqr;
   private String matricula;
   private String nombresocio;
   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
   private LocalDate fechaemision;
   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
   private LocalDate fechaexpiracion;
   private Integer lejendario;
   private String ci;
   private String nombrecompleto;
   private String email;
   private Integer celular;
   private Integer profesionId;  // Nuevo campo
   private Integer institucionId; // Nuevo campo
   @Transient
   private MultipartFile logo;
   
   public SocioDTO() {}
   
   public SocioDTO(Integer id, Integer codigo, String nrodocumento, String imagen, String qr, String linkqr, String matricula, String nombresocio, LocalDate fechaemision, LocalDate fechaexpiracion, Integer lejendario, String ci, String nombrecompleto, String email, Integer celular, MultipartFile logo) {
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
     this.ci = ci;
     this.nombrecompleto = nombrecompleto;
     this.email = email;
     this.celular = celular;
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
   public String getCi() {
     return this.ci;
   }
   public void setCi(String ci) {
     this.ci = ci;
   }
   public String getNombrecompleto() {
     return this.nombrecompleto;
   }
   public void setNombrecompleto(String nombrecompleto) {
     this.nombrecompleto = nombrecompleto;
   }
   public String getEmail() {
     return this.email;
   }
   public void setEmail(String email) {
     this.email = email;
   }
   public Integer getCelular() {
     return this.celular;
   }
   public void setCelular(Integer celular) {
     this.celular = celular;
   }
   public MultipartFile getLogo() {
     return this.logo;
   }
   public void setLogo(MultipartFile logo) {
     this.logo = logo;
   }
   
   public Integer getProfesionId() {
     return this.profesionId;
   }
   public void setProfesionId(Integer profesionId) {
     this.profesionId = profesionId;
   }
   
   public Integer getInstitucionId() {
     return this.institucionId;
   }
   public void setInstitucionId(Integer institucionId) {
     this.institucionId = institucionId;
   }
   
   public String toString() {
     return "SocioDTO [id=" + this.id + ", codigo=" + this.codigo + ", nrodocumento=" + this.nrodocumento + ", imagen=" + this.imagen + ", qr=" + this.qr + ", linkqr=" + this.linkqr + ", matricula=" + this.matricula + ", nombresocio=" + this.nombresocio + ", fechaemision=" + this.fechaemision + ", fechaexpiracion=" + this.fechaexpiracion + ", lejendario=" + this.lejendario + ", ci=" + this.ci + ", nombrecompleto=" + this.nombrecompleto + ", email=" + this.email + ", celular=" + this.celular + ", profesionId=" + this.profesionId + ", institucionId=" + this.institucionId + ", logo=" + this.logo + "]";
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\dto\SocioDTO.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */