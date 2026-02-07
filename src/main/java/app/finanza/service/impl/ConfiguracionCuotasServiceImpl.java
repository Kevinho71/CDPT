package app.finanza.service.impl;

import app.common.exception.ResourceNotFoundException;
import app.finanza.dto.ConfiguracionCuotasCreateDTO;
import app.finanza.dto.ConfiguracionCuotasResponseDTO;
import app.finanza.dto.ConfiguracionCuotasUpdateDTO;
import app.finanza.entity.ConfiguracionCuotasEntity;
import app.finanza.repository.ConfiguracionCuotasRepository;
import app.finanza.service.ConfiguracionCuotasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConfiguracionCuotasServiceImpl implements ConfiguracionCuotasService {
    
    @Autowired
    private ConfiguracionCuotasRepository repository;
    
    @Override
    @Transactional(readOnly = true)
    public List<ConfiguracionCuotasResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public ConfiguracionCuotasResponseDTO findById(Integer id) {
        ConfiguracionCuotasEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuración de cuotas no encontrada con ID: " + id));
        return toResponseDTO(entity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ConfiguracionCuotasResponseDTO findByGestion(Integer gestion) {
        ConfiguracionCuotasEntity entity = repository.findByGestion(gestion)
                .orElseThrow(() -> new ResourceNotFoundException("Configuración no encontrada para gestión: " + gestion));
        return toResponseDTO(entity);
    }
    
    @Override
    public ConfiguracionCuotasResponseDTO create(ConfiguracionCuotasCreateDTO dto) {
        ConfiguracionCuotasEntity entity = new ConfiguracionCuotasEntity();
        
        entity.setGestion(dto.getGestion());
        entity.setMontoMatricula(dto.getMontoMatricula());
        entity.setMontoMensualidad(dto.getMontoMensualidad());
        entity.setDiaLimitePago(dto.getDiaLimitePago() != null ? dto.getDiaLimitePago() : 10);
        entity.setEstado(1); // Activo por defecto
        
        ConfiguracionCuotasEntity saved = repository.save(entity);
        return toResponseDTO(saved);
    }
    
    @Override
    public ConfiguracionCuotasResponseDTO update(Integer id, ConfiguracionCuotasUpdateDTO dto) {
        ConfiguracionCuotasEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuración de cuotas no encontrada con ID: " + id));
        
        if (dto.getMontoMatricula() != null) entity.setMontoMatricula(dto.getMontoMatricula());
        if (dto.getMontoMensualidad() != null) entity.setMontoMensualidad(dto.getMontoMensualidad());
        if (dto.getDiaLimitePago() != null) entity.setDiaLimitePago(dto.getDiaLimitePago());
        if (dto.getEstado() != null) entity.setEstado(dto.getEstado());
        
        ConfiguracionCuotasEntity updated = repository.save(entity);
        return toResponseDTO(updated);
    }
    
    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Configuración de cuotas no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }
    
    private ConfiguracionCuotasResponseDTO toResponseDTO(ConfiguracionCuotasEntity entity) {
        ConfiguracionCuotasResponseDTO dto = new ConfiguracionCuotasResponseDTO();
        dto.setId(entity.getId());
        dto.setGestion(entity.getGestion());
        dto.setMontoMatricula(entity.getMontoMatricula());
        dto.setMontoMensualidad(entity.getMontoMensualidad());
        dto.setDiaLimitePago(entity.getDiaLimitePago());
        dto.setEstado(entity.getEstado());
        return dto;
    }
}
