package app.perfil.service;

import app.common.exception.InvalidDataException;
import app.common.exception.ResourceNotFoundException;
import app.common.util.ArchivoService;
import app.common.util.Constantes;
import app.perfil.dto.*;
import app.perfil.entity.*;
import app.perfil.repository.*;
import app.socio.entity.SocioEntity;
import app.socio.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilSocioServiceImpl implements PerfilSocioService {
    
    @Autowired
    private PerfilSocioRepository perfilSocioRepository;
    
    @Autowired
    private SocioRepository socioRepository;
    
    @Autowired
    private IdiomaRepository idiomaRepository;
    
    @Autowired
    private SectorRepository sectorRepository;
    
    @Autowired
    private ServicioRepository servicioRepository;
    
    @Autowired
    private EspecialidadRepository especialidadRepository;
    
    @Autowired
    private ArchivoService archivoService;
    
    @Override
    @Transactional
    public PerfilSocioResponseDTO createOrUpdatePerfil(PerfilSocioDTO dto, MultipartFile fotoPerfil, MultipartFile fotoBanner) {
        validateData(dto);
        
        // Verificar que el socio existe
        SocioEntity socio = socioRepository.findById(dto.getSocioId())
                .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", dto.getSocioId()));
        
        // Buscar si ya existe un perfil para este socio
        PerfilSocioEntity perfil = perfilSocioRepository.findBySocio_Id(dto.getSocioId())
                .orElse(new PerfilSocioEntity());
        
        boolean isNew = perfil.getId() == null;
        
        // Mapear datos del DTO a la entidad
        perfil.setSocio(socio);
        // Llenar automáticamente desde persona asociada al socio
        if (socio.getPersona() != null) {
            perfil.setNombreCompleto(socio.getPersona().getNombrecompleto());
            perfil.setEmail(socio.getPersona().getEmail());
            perfil.setTelefono(socio.getPersona().getCelular() != null ? socio.getPersona().getCelular().toString() : null);
        }
        perfil.setTituloProfesional(dto.getTituloProfesional());
        perfil.setAnosExperiencia(dto.getAnosExperiencia());
        perfil.setResumenProfesional(dto.getResumenProfesional());
        perfil.setModalidadTrabajo(dto.getModalidadTrabajo());
        perfil.setCiudad(dto.getCiudad());
        perfil.setZona(dto.getZona());
        perfil.setLinkedinUrl(dto.getLinkedinUrl());
        perfil.setFacebookUrl(dto.getFacebookUrl());
        perfil.setTwitterUrl(dto.getTwitterUrl());
        perfil.setInstagramUrl(dto.getInstagramUrl());
        perfil.setWhatsapp(dto.getWhatsapp());
        perfil.setSitioWeb(dto.getSitioWeb());
        perfil.setPerfilPublico(dto.getPerfilPublico() != null ? dto.getPerfilPublico() : true);
        perfil.setPermiteContacto(dto.getPermiteContacto() != null ? dto.getPermiteContacto() : true);
        perfil.setEstado(dto.getEstado() != null ? dto.getEstado() : 1);
        perfil.setFechaActualizacion(LocalDateTime.now());
        
        // Manejar idiomas con nivel y orden
        if (dto.getIdiomas() != null) {
            perfil.getSocioIdiomas().clear();
            if (!isNew) perfilSocioRepository.flush(); // Forzar eliminación antes de insertar
            for (SocioIdiomaDTO idiomaDTO : dto.getIdiomas()) {
                IdiomaEntity idioma = idiomaRepository.findById(idiomaDTO.getIdiomaId())
                        .orElseThrow(() -> new ResourceNotFoundException("Idioma", "id", idiomaDTO.getIdiomaId()));
                
                SocioIdiomaEntity socioIdioma = new SocioIdiomaEntity();
                socioIdioma.setPerfilSocio(perfil);
                socioIdioma.setIdioma(idioma);
                socioIdioma.setNivel(idiomaDTO.getNivel());
                perfil.getSocioIdiomas().add(socioIdioma);
            }
        }
        
        // Manejar sectores
        if (dto.getSectorIds() != null) {
            perfil.getSocioSectores().clear();
            if (!isNew) perfilSocioRepository.flush(); // Forzar eliminación antes de insertar
            for (Integer sectorId : dto.getSectorIds()) {
                SectorEntity sector = sectorRepository.findById(sectorId)
                        .orElseThrow(() -> new ResourceNotFoundException("Sector", "id", sectorId));
                
                SocioSectorEntity socioSector = new SocioSectorEntity();
                socioSector.setPerfilSocio(perfil);
                socioSector.setSector(sector);
                perfil.getSocioSectores().add(socioSector);
            }
        }
        
        // Manejar servicios
        if (dto.getServicioIds() != null) {
            perfil.getSocioServicios().clear();
            if (!isNew) perfilSocioRepository.flush(); // Forzar eliminación antes de insertar
            for (Integer servicioId : dto.getServicioIds()) {
                ServicioEntity servicio = servicioRepository.findById(servicioId)
                        .orElseThrow(() -> new ResourceNotFoundException("Servicio", "id", servicioId));
                
                SocioServicioEntity socioServicio = new SocioServicioEntity();
                socioServicio.setPerfilSocio(perfil);
                socioServicio.setServicio(servicio);
                perfil.getSocioServicios().add(socioServicio);
            }
        }
        
        // Manejar especialidades
        if (dto.getEspecialidadIds() != null) {
            perfil.getSocioEspecialidades().clear();
            if (!isNew) perfilSocioRepository.flush(); // Forzar eliminación antes de insertar
            for (Integer especialidadId : dto.getEspecialidadIds()) {
                EspecialidadEntity especialidad = especialidadRepository.findById(especialidadId)
                        .orElseThrow(() -> new ResourceNotFoundException("Especialidad", "id", especialidadId));
                
                SocioEspecialidadEntity socioEspecialidad = new SocioEspecialidadEntity();
                socioEspecialidad.setPerfilSocio(perfil);
                socioEspecialidad.setEspecialidad(especialidad);
                perfil.getSocioEspecialidades().add(socioEspecialidad);
            }
        }
        
        if (isNew) {
            perfil.setFechaCreacion(LocalDateTime.now());
            perfil.setVisualizaciones(0);
        }
        
        // Manejar foto de perfil
        if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
            if (perfil.getFotoPerfil() != null) {
                try {
                    archivoService.eliminarArchivo(Constantes.nameFolderFotoPerfil, perfil.getFotoPerfil());
                } catch (Exception e) {
                    // Log error but continue
                }
            }
            
            try {
                String nombreArchivo = "PERFIL-" + dto.getSocioId() + "-" + fotoPerfil.getOriginalFilename();
                String rutaArchivo = archivoService.guargarArchivo(Constantes.nameFolderFotoPerfil, fotoPerfil, nombreArchivo);
                perfil.setFotoPerfil(rutaArchivo);
            } catch (Exception e) {
                throw new InvalidDataException("Error al guardar foto de perfil: " + e.getMessage());
            }
        }
        
        // Manejar foto de banner
        if (fotoBanner != null && !fotoBanner.isEmpty()) {
            if (perfil.getFotoBanner() != null) {
                try {
                    archivoService.eliminarArchivo(Constantes.nameFolderFotoBanner, perfil.getFotoBanner());
                } catch (Exception e) {
                    // Log error but continue
                }
            }
            
            try {
                String nombreArchivo = "BANNER-" + dto.getSocioId() + "-" + fotoBanner.getOriginalFilename();
                String rutaArchivo = archivoService.guargarArchivo(Constantes.nameFolderFotoBanner, fotoBanner, nombreArchivo);
                perfil.setFotoBanner(rutaArchivo);
            } catch (Exception e) {
                throw new InvalidDataException("Error al guardar foto de banner: " + e.getMessage());
            }
        }
        
        PerfilSocioEntity savedPerfil = perfilSocioRepository.save(perfil);
        return toResponseDTO(savedPerfil);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PerfilSocioResponseDTO findByIdDTO(Integer id) {
        PerfilSocioEntity perfil = perfilSocioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PerfilSocio", "id", id));
        return toResponseDTO(perfil);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PerfilSocioResponseDTO findBySocioIdDTO(Integer socioId) {
        PerfilSocioEntity perfil = perfilSocioRepository.findBySocio_Id(socioId)
                .orElseThrow(() -> new ResourceNotFoundException("PerfilSocio", "socioId", socioId));
        return toResponseDTO(perfil);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PerfilSocioResponseDTO> findAllDTO() {
        return perfilSocioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PerfilSocioResponseDTO> findAllDTO(Integer estado) {
        return perfilSocioRepository.findByEstado(estado).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PerfilSocioResponseDTO> findPublicProfiles() {
        return perfilSocioRepository.findByPerfilPublicoTrueAndEstado(1).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public PerfilSocioResponseDTO changeStatusPerfil(Integer id) {
        PerfilSocioEntity perfil = perfilSocioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PerfilSocio", "id", id));
        
        perfil.setEstado(perfil.getEstado() == 1 ? 0 : 1);
        perfil.setFechaActualizacion(LocalDateTime.now());
        PerfilSocioEntity updatedPerfil = perfilSocioRepository.save(perfil);
        return toResponseDTO(updatedPerfil);
    }
    
    @Override
    @Transactional
    public void incrementarVisualizaciones(Integer id) {
        PerfilSocioEntity perfil = perfilSocioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PerfilSocio", "id", id));
        
        perfil.setVisualizaciones(perfil.getVisualizaciones() + 1);
        perfilSocioRepository.save(perfil);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PerfilSocioResponseDTO> findByCiudad(String ciudad) {
        return perfilSocioRepository.findByCiudadContainingIgnoreCaseAndEstado(ciudad, 1).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public PerfilSocioResponseDTO deleteFotoPerfil(Integer id) {
        PerfilSocioEntity perfil = perfilSocioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PerfilSocio", "id", id));
        
        if (perfil.getFotoPerfil() != null) {
            try {
                archivoService.eliminarArchivo(Constantes.nameFolderFotoPerfil, perfil.getFotoPerfil());
            } catch (Exception e) {
                // Log error but continue
            }
            perfil.setFotoPerfil(null);
            perfil.setFechaActualizacion(LocalDateTime.now());
            perfilSocioRepository.save(perfil);
        }
        
        return toResponseDTO(perfil);
    }
    
    @Override
    @Transactional
    public PerfilSocioResponseDTO deleteFotoBanner(Integer id) {
        PerfilSocioEntity perfil = perfilSocioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PerfilSocio", "id", id));
        
        if (perfil.getFotoBanner() != null) {
            try {
                archivoService.eliminarArchivo(Constantes.nameFolderFotoBanner, perfil.getFotoBanner());
            } catch (Exception e) {
                // Log error but continue
            }
            perfil.setFotoBanner(null);
            perfil.setFechaActualizacion(LocalDateTime.now());
            perfilSocioRepository.save(perfil);
        }
        
        return toResponseDTO(perfil);
    }
    
    private void validateData(PerfilSocioDTO dto) {
        if (dto.getSocioId() == null) {
            throw new InvalidDataException("El ID del socio es requerido");
        }
        
        if (dto.getEstado() != null && dto.getEstado() != 0 && dto.getEstado() != 1) {
            throw new InvalidDataException("El estado debe ser 0 (inactivo) o 1 (activo)");
        }
    }
    
    private PerfilSocioResponseDTO toResponseDTO(PerfilSocioEntity perfil) {
        PerfilSocioResponseDTO dto = new PerfilSocioResponseDTO();
        dto.setId(perfil.getId());
        dto.setSocioId(perfil.getSocio().getId());
        dto.setNombre(perfil.getNombreCompleto());
        dto.setEmail(perfil.getEmail());
        dto.setTelefono(perfil.getTelefono());
        dto.setTituloProfesional(perfil.getTituloProfesional());
        dto.setAnosExperiencia(perfil.getAnosExperiencia());
        dto.setResumenProfesional(perfil.getResumenProfesional());
        dto.setModalidadTrabajo(perfil.getModalidadTrabajo());
        dto.setCiudad(perfil.getCiudad());
        dto.setZona(perfil.getZona());
        dto.setLinkedinUrl(perfil.getLinkedinUrl());
        dto.setFacebookUrl(perfil.getFacebookUrl());
        dto.setTwitterUrl(perfil.getTwitterUrl());
        dto.setInstagramUrl(perfil.getInstagramUrl());
        dto.setWhatsapp(perfil.getWhatsapp());
        dto.setSitioWeb(perfil.getSitioWeb());
        dto.setPerfilPublico(perfil.getPerfilPublico());
        dto.setPermiteContacto(perfil.getPermiteContacto());
        dto.setVisualizaciones(perfil.getVisualizaciones());
        dto.setFechaCreacion(perfil.getFechaCreacion());
        dto.setFechaActualizacion(perfil.getFechaActualizacion());
        dto.setEstado(perfil.getEstado());
        
        // Mapear idiomas con nivel y orden
        if (perfil.getSocioIdiomas() != null && !perfil.getSocioIdiomas().isEmpty()) {
            dto.setIdiomas(perfil.getSocioIdiomas().stream()
                .map(this::toSocioIdiomaResponseDTO)
                .collect(Collectors.toList()));
        }
        
        // Mapear sectores
        if (perfil.getSocioSectores() != null && !perfil.getSocioSectores().isEmpty()) {
            dto.setSectores(perfil.getSocioSectores().stream()
                .map(ss -> toSectorResponseDTO(ss.getSector()))
                .collect(Collectors.toList()));
        }
        
        // Mapear servicios
        if (perfil.getSocioServicios() != null && !perfil.getSocioServicios().isEmpty()) {
            dto.setServicios(perfil.getSocioServicios().stream()
                .map(ss -> toServicioResponseDTO(ss.getServicio()))
                .collect(Collectors.toList()));
        }
        
        // Mapear especialidades
        if (perfil.getSocioEspecialidades() != null && !perfil.getSocioEspecialidades().isEmpty()) {
            dto.setEspecialidades(perfil.getSocioEspecialidades().stream()
                .map(se -> toEspecialidadResponseDTO(se.getEspecialidad()))
                .collect(Collectors.toList()));
        }
        
        // Construir URLs de las fotos (relativas)
        if (perfil.getFotoPerfil() != null) {
            dto.setFotoPerfilUrl("/api/perfiles/foto-perfil/" + perfil.getFotoPerfil());
        }
        
        if (perfil.getFotoBanner() != null) {
            dto.setFotoBannerUrl("/api/perfiles/foto-banner/" + perfil.getFotoBanner());
        }
        
        return dto;
    }
    
    private SocioIdiomaResponseDTO toSocioIdiomaResponseDTO(SocioIdiomaEntity socioIdioma) {
        SocioIdiomaResponseDTO dto = new SocioIdiomaResponseDTO();
        dto.setId(socioIdioma.getId());
        dto.setNivel(socioIdioma.getNivel());
        
        IdiomaResponseDTO idiomaDTO = new IdiomaResponseDTO();
        idiomaDTO.setId(socioIdioma.getIdioma().getId());
        idiomaDTO.setNombre(socioIdioma.getIdioma().getNombre());
        idiomaDTO.setEstado(socioIdioma.getIdioma().getEstado());
        dto.setIdioma(idiomaDTO);
        
        return dto;
    }
    
    private SectorResponseDTO toSectorResponseDTO(SectorEntity sector) {
        SectorResponseDTO dto = new SectorResponseDTO();
        dto.setId(sector.getId());
        dto.setNombre(sector.getNombre());
        dto.setDescripcion(sector.getDescripcion());
        dto.setIcono(sector.getIcono());
        dto.setEstado(sector.getEstado());
        return dto;
    }
    
    private ServicioResponseDTO toServicioResponseDTO(ServicioEntity servicio) {
        ServicioResponseDTO dto = new ServicioResponseDTO();
        dto.setId(servicio.getId());
        dto.setNombre(servicio.getNombre());
        dto.setDescripcion(servicio.getDescripcion());
        dto.setCategoria(servicio.getCategoria());
        dto.setEstado(servicio.getEstado());
        return dto;
    }
    
    private EspecialidadResponseDTO toEspecialidadResponseDTO(EspecialidadEntity especialidad) {
        EspecialidadResponseDTO dto = new EspecialidadResponseDTO();
        dto.setId(especialidad.getId());
        dto.setNombre(especialidad.getNombre());
        dto.setDescripcion(especialidad.getDescripcion());
        dto.setEstado(especialidad.getEstado());
        return dto;
    }
}
