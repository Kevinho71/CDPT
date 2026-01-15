package app.perfil.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PerfilSocioResponseDTO {
    
    private Integer id;
    private Integer socioId;
    private String nombre;
    private String email;
    private String telefono;
    private String tituloProfesional;
    private Integer anosExperiencia;
    private String resumenProfesional;
    private String modalidadTrabajo;
    private String ciudad;
    private String zona;
    private String fotoPerfilUrl;
    private String fotoBannerUrl;
    private String linkedinUrl;
    private String facebookUrl;
    private String twitterUrl;
    private String instagramUrl;
    private String whatsapp;
    private String sitioWeb;
    private Boolean perfilPublico;
    private Boolean permiteContacto;
    private Integer visualizaciones;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private Integer estado;
    
    // Relaciones con tablas intermedias completas
    private List<SocioIdiomaResponseDTO> idiomas;
    private List<SectorResponseDTO> sectores;
    private List<ServicioResponseDTO> servicios;
    private List<EspecialidadResponseDTO> especialidades;
    
    // Constructor vacío
    public PerfilSocioResponseDTO() {}
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getSocioId() {
        return socioId;
    }
    
    public void setSocioId(Integer socioId) {
        this.socioId = socioId;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }
    
    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
    
    public String getFotoBannerUrl() {
        return fotoBannerUrl;
    }
    
    public void setFotoBannerUrl(String fotoBannerUrl) {
        this.fotoBannerUrl = fotoBannerUrl;
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
    
    public List<SocioIdiomaResponseDTO> getIdiomas() {
        return idiomas;
    }
    
    public void setIdiomas(List<SocioIdiomaResponseDTO> idiomas) {
        this.idiomas = idiomas;
    }
    
    public List<SectorResponseDTO> getSectores() {
        return sectores;
    }
    
    public void setSectores(List<SectorResponseDTO> sectores) {
        this.sectores = sectores;
    }
    
    public List<ServicioResponseDTO> getServicios() {
        return servicios;
    }
    
    public void setServicios(List<ServicioResponseDTO> servicios) {
        this.servicios = servicios;
    }
    
    public List<EspecialidadResponseDTO> getEspecialidades() {
        return especialidades;
    }
    
    public void setEspecialidades(List<EspecialidadResponseDTO> especialidades) {
        this.especialidades = especialidades;
    }
}
