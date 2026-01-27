 package app.core.entity;
 
 import app.core.entity.ProvinciaEntity;
 import java.io.Serializable;
 import jakarta.persistence.Column;
 import jakarta.persistence.Entity;
 import jakarta.persistence.Id;
 import jakarta.persistence.JoinColumn;
 import jakarta.persistence.ManyToOne;
 import jakarta.persistence.Table;


 @Entity
 @Table(name = "institucion")
 public class InstitucionEntity
   implements Serializable
 {
   private static final long serialVersionUID = 1L;
   @Id
   private Integer id;
   @Column(name = "nit")
   private String nit;
   @Column(name = "compania")
   private String compania;
   @Column(name = "institucion")
   private String institucion;
   @Column(name = "representante")
   private String representante;
   @Column(name = "correo")
   private String correo;
   @Column(name = "direccion")
   private String direccion;
   @Column(name = "telefono")
   private String telefono;
   @Column(name = "fax")
   private String fax;
   @Column(name = "host")
   private String host;
   @Column(name = "port")
   private String port;
   @Column(name = "estado")
   private Integer estado;
   @ManyToOne(optional = false)
   @JoinColumn(name = "fk_provincia")
   private ProvinciaEntity provincia;
   
   public InstitucionEntity() {}
   
   public InstitucionEntity(Integer id, String nit, String compania, String institucion, String representante, String correo, String direccion, String telefono, String fax, String host, String port, Integer estado, ProvinciaEntity provincia) {
     this.id = id;
     this.nit = nit;
     this.compania = compania;
     this.institucion = institucion;
     this.representante = representante;
     this.correo = correo;
     this.direccion = direccion;
     this.telefono = telefono;
     this.fax = fax;
     this.host = host;
     this.port = port;
     this.estado = estado;
     this.provincia = provincia;
   }


   public Integer getId() {
     return this.id;
   }


   public void setId(Integer id) {
     this.id = id;
   }


   public String getNit() {
     return this.nit;
   }


   public void setNit(String nit) {
     this.nit = nit;
   }


   public String getCompania() {
     return this.compania;
   }


   public void setCompania(String compania) {
     this.compania = compania;
   }


   public String getInstitucion() {
     return this.institucion;
   }


   public void setInstitucion(String institucion) {
     this.institucion = institucion;
   }


   public String getRepresentante() {
     return this.representante;
   }


   public void setRepresentante(String representante) {
     this.representante = representante;
   }


   public String getCorreo() {
     return this.correo;
   }


   public void setCorreo(String correo) {
     this.correo = correo;
   }


   public String getDireccion() {
     return this.direccion;
   }


   public void setDireccion(String direccion) {
     this.direccion = direccion;
   }


   public String getTelefono() {
     return this.telefono;
   }


   public void setTelefono(String telefono) {
     this.telefono = telefono;
   }


   public String getFax() {
     return this.fax;
   }


   public void setFax(String fax) {
     this.fax = fax;
   }


   public String getHost() {
     return this.host;
   }


   public void setHost(String host) {
     this.host = host;
   }


   public String getPort() {
     return this.port;
   }


   public void setPort(String port) {
     this.port = port;
   }


   public Integer getEstado() {
     return this.estado;
   }


   public void setEstado(Integer estado) {
     this.estado = estado;
   }


   public ProvinciaEntity getProvincia() {
     return this.provincia;
   }


   public void setProvincia(ProvinciaEntity provincia) {
     this.provincia = provincia;
   }


   public String toString() {
     return "InstitucionEntity [id=" + this.id + ", nit=" + this.nit + ", compania=" + this.compania + ", institucion=" + this.institucion + ", representante=" + this.representante + ", correo=" + this.correo + ", direccion=" + this.direccion + ", telefono=" + this.telefono + ", fax=" + this.fax + ", host=" + this.host + ", port=" + this.port + ", estado=" + this.estado + ", provincia=" + this.provincia + "]";
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\entity\InstitucionEntity.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */