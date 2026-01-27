package app.core.dto;

import java.util.List;

/**
 * DTO unificado para devolver todos los datos geográficos
 * (países, departamentos y provincias) en una sola respuesta
 */
public class LocationDataResponseDTO {
    
    private List<PaisDTO> paises;
    private List<DepartamentoDTO> departamentos;
    private List<ProvinciaDTO> provincias;
    
    public LocationDataResponseDTO() {}
    
    public LocationDataResponseDTO(List<PaisDTO> paises, List<DepartamentoDTO> departamentos, 
                                   List<ProvinciaDTO> provincias) {
        this.paises = paises;
        this.departamentos = departamentos;
        this.provincias = provincias;
    }
    
    // Inner class para País
    public static class PaisDTO {
        private Integer id;
        private String nombre;
        private Integer estado;
        
        public PaisDTO() {}
        
        public PaisDTO(Integer id, String nombre, Integer estado) {
            this.id = id;
            this.nombre = nombre;
            this.estado = estado;
        }
        
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public Integer getEstado() { return estado; }
        public void setEstado(Integer estado) { this.estado = estado; }
    }
    
    // Inner class para Departamento
    public static class DepartamentoDTO {
        private Integer id;
        private String nombre;
        private String abreviacion;
        private Integer estado;
        private Integer paisId;
        
        public DepartamentoDTO() {}
        
        public DepartamentoDTO(Integer id, String nombre, String abreviacion, Integer estado, Integer paisId) {
            this.id = id;
            this.nombre = nombre;
            this.abreviacion = abreviacion;
            this.estado = estado;
            this.paisId = paisId;
        }
        
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public String getAbreviacion() { return abreviacion; }
        public void setAbreviacion(String abreviacion) { this.abreviacion = abreviacion; }
        
        public Integer getEstado() { return estado; }
        public void setEstado(Integer estado) { this.estado = estado; }
        
        public Integer getPaisId() { return paisId; }
        public void setPaisId(Integer paisId) { this.paisId = paisId; }
    }
    
    // Inner class para Provincia
    public static class ProvinciaDTO {
        private Integer id;
        private String nombre;
        private Integer estado;
        private Integer departamentoId;
        
        public ProvinciaDTO() {}
        
        public ProvinciaDTO(Integer id, String nombre, Integer estado, Integer departamentoId) {
            this.id = id;
            this.nombre = nombre;
            this.estado = estado;
            this.departamentoId = departamentoId;
        }
        
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public Integer getEstado() { return estado; }
        public void setEstado(Integer estado) { this.estado = estado; }
        
        public Integer getDepartamentoId() { return departamentoId; }
        public void setDepartamentoId(Integer departamentoId) { this.departamentoId = departamentoId; }
    }
    
    // Getters y Setters principales
    public List<PaisDTO> getPaises() { return paises; }
    public void setPaises(List<PaisDTO> paises) { this.paises = paises; }
    
    public List<DepartamentoDTO> getDepartamentos() { return departamentos; }
    public void setDepartamentos(List<DepartamentoDTO> departamentos) { this.departamentos = departamentos; }
    
    public List<ProvinciaDTO> getProvincias() { return provincias; }
    public void setProvincias(List<ProvinciaDTO> provincias) { this.provincias = provincias; }
}
