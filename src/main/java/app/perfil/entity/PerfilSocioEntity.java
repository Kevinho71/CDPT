package app.perfil.entity;

import app.socio.entity.SocioEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "perfil_socio")
public class PerfilSocioEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
    @JoinColumn(name = "fk_socio", nullable = false, unique = true)
    private SocioEntity socio;
    
    @Column(name = "nombre_completo", length = 200)
    private String nombreCompleto;
    
    @Column(name = "email", length = 150)
    private String email;
    
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Column(name = "titulo_profesional", length = 100)
    private String tituloProfesional;
    
    @Column(name = "anos_experiencia")
    private Integer anosExperiencia;
    
    @Column(name = "resumen_profesional", columnDefinition = "TEXT")
    private String resumenProfesional;
    
    @Column(name = "modalidad_trabajo", length = 20)
    private String modalidadTrabajo;
    
    @Column(name = "ciudad", length = 100)
    private String ciudad;
    
    @Column(name = "zona", length = 200)
    private String zona;
    
    @Column(name = "foto_perfil", length = 500)
    private String fotoPerfil;
    
    @Column(name = "foto_banner", length = 500)
    private String fotoBanner;
    
    @Column(name = "linkedin_url", length = 300)
    private String linkedinUrl;
    
    @Column(name = "facebook_url", length = 300)
    private String facebookUrl;
    
    @Column(name = "twitter_url", length = 300)
    private String twitterUrl;
    
    @Column(name = "instagram_url", length = 300)
    private String instagramUrl;
    
    @Column(name = "whatsapp", length = 20)
    private String whatsapp;
    
    @Column(name = "sitio_web", length = 300)
    private String sitioWeb;
    
    @Column(name = "perfil_publico")
    private Boolean perfilPublico = true;
    
    @Column(name = "permite_contacto")
    private Boolean permiteContacto = true;
    
    @Column(name = "visualizaciones")
    private Integer visualizaciones = 0;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();
    
    @Column(name = "estado")
    private Integer estado = 1;
    
    // Relaciones One-to-Many con tablas intermedias
    @OneToMany(mappedBy = "perfilSocio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SocioIdiomaEntity> socioIdiomas = new ArrayList<>();

    @OneToMany(mappedBy = "perfilSocio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SocioSectorEntity> socioSectores = new ArrayList<>();

    @OneToMany(mappedBy = "perfilSocio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SocioServicioEntity> socioServicios = new ArrayList<>();

    @OneToMany(mappedBy = "perfilSocio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SocioEspecialidadEntity> socioEspecialidades = new ArrayList<>();
    
    // Constructores
    public PerfilSocioEntity() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public SocioEntity getSocio() {
        return socio;
    }
    
    public void setSocio(SocioEntity socio) {
        this.socio = socio;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getTituloProfesional() {
        return tituloProfesional;
    }
    
    public void setTituloProfesional(String tituloProfesional) {
        this.tituloProfesional = tituloProfesional;
    }
    
    public Integer getAnosExperiencia() {
        return anosExperiencia;
    }
    
    public void setAnosExperiencia(Integer anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }
    
    public String getResumenProfesional() {
        return resumenProfesional;
    }
    
    public void setResumenProfesional(String resumenProfesional) {
        this.resumenProfesional = resumenProfesional;
    }
    
    public String getModalidadTrabajo() {
        return modalidadTrabajo;
    }
    
    public void setModalidadTrabajo(String modalidadTrabajo) {
        this.modalidadTrabajo = modalidadTrabajo;
    }
    
    public String getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    public String getZona() {
        return zona;
    }
    
    public void setZona(String zona) {
        this.zona = zona;
    }
    
    public String getFotoPerfil() {
        return fotoPerfil;
    }
    
    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
    
    public String getFotoBanner() {
        return fotoBanner;
    }
    
    public void setFotoBanner(String fotoBanner) {
        this.fotoBanner = fotoBanner;
    }
    
    public String getLinkedinUrl() {
        return linkedinUrl;
    }
    
    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }
    
    public String getFacebookUrl() {
        return facebookUrl;
    }
    
    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }
    
    public String getTwitterUrl() {
        return twitterUrl;
    }
    
    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }
    
    public String getInstagramUrl() {
        return instagramUrl;
    }
    
    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }
    
    public String getWhatsapp() {
        return whatsapp;
    }
    
    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
    
    public String getSitioWeb() {
        return sitioWeb;
    }
    
    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }
    
    public Boolean getPerfilPublico() {
        return perfilPublico;
    }
    
    public void setPerfilPublico(Boolean perfilPublico) {
        this.perfilPublico = perfilPublico;
    }
    
    public Boolean getPermiteContacto() {
        return permiteContacto;
    }
    
    public void setPermiteContacto(Boolean permiteContacto) {
        this.permiteContacto = permiteContacto;
    }
    
    public Integer getVisualizaciones() {
        return visualizaciones;
    }
    
    public void setVisualizaciones(Integer visualizaciones) {
        this.visualizaciones = visualizaciones;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    public List<SocioIdiomaEntity> getSocioIdiomas() {
        return socioIdiomas;
    }
    
    public void setSocioIdiomas(List<SocioIdiomaEntity> socioIdiomas) {
        this.socioIdiomas = socioIdiomas;
    }
    
    public List<SocioSectorEntity> getSocioSectores() {
        return socioSectores;
    }
    
    public void setSocioSectores(List<SocioSectorEntity> socioSectores) {
        this.socioSectores = socioSectores;
    }
    
    public List<SocioServicioEntity> getSocioServicios() {
        return socioServicios;
    }
    
    public void setSocioServicios(List<SocioServicioEntity> socioServicios) {
        this.socioServicios = socioServicios;
    }
    
    public List<SocioEspecialidadEntity> getSocioEspecialidades() {
        return socioEspecialidades;
    }
    
    public void setSocioEspecialidades(List<SocioEspecialidadEntity> socioEspecialidades) {
        this.socioEspecialidades = socioEspecialidades;
    }
}
