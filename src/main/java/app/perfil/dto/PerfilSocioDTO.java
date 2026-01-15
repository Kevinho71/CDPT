package app.perfil.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class PerfilSocioDTO {
    
    @NotNull(message = "El ID del socio es requerido")
    private Integer socioId;
    
    private String tituloProfesional;
    private Integer anosExperiencia;
    private String resumenProfesional;
    private String modalidadTrabajo;
    private String ciudad;
    private String zona;
    private String linkedinUrl;
    private String facebookUrl;
    private String twitterUrl;
    private String instagramUrl;
    private String whatsapp;
    private String sitioWeb;
    private Boolean perfilPublico = true;
    private Boolean permiteContacto = true;
    private Integer estado = 1;
    
    // Relaciones con datos adicionales
    private List<SocioIdiomaDTO> idiomas;
    private List<Integer> sectorIds;
    private List<Integer> servicioIds;
    private List<Integer> especialidadIds;
    
    // Constructor vacío
    public PerfilSocioDTO() {}
    
    // Getters y Setters
    public Integer getSocioId() {
        return socioId;
    }
    
    public void setSocioId(Integer socioId) {
        this.socioId = socioId;
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
    
    public Integer getEstado() {
        return estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    
    public List<SocioIdiomaDTO> getIdiomas() {
        return idiomas;
    }
    
    public void setIdiomas(List<SocioIdiomaDTO> idiomas) {
        this.idiomas = idiomas;
    }
    
    public List<Integer> getSectorIds() {
        return sectorIds;
    }
    
    public void setSectorIds(List<Integer> sectorIds) {
        this.sectorIds = sectorIds;
    }
    
    public List<Integer> getServicioIds() {
        return servicioIds;
    }
    
    public void setServicioIds(List<Integer> servicioIds) {
        this.servicioIds = servicioIds;
    }
    
    public List<Integer> getEspecialidadIds() {
        return especialidadIds;
    }
    
    public void setEspecialidadIds(List<Integer> especialidadIds) {
        this.especialidadIds = especialidadIds;
    }
}
