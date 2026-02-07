package app.documento.service.impl;

import app.common.exception.ResourceNotFoundException;
import app.common.util.ArchivoService;
import app.documento.dto.DocumentoProfesionalCreateDTO;
import app.documento.dto.DocumentoProfesionalResponseDTO;
import app.documento.dto.DocumentoProfesionalUpdateDTO;
import app.documento.entity.DocumentoProfesionalEntity;
import app.documento.repository.DocumentoProfesionalRepository;
import app.documento.service.DocumentoProfesionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DocumentoProfesionalServiceImpl implements DocumentoProfesionalService {

    @Autowired
    private DocumentoProfesionalRepository repository;

    @Autowired
    private ArchivoService archivoService;

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoProfesionalResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoProfesionalResponseDTO> findByTipo(String tipoDocumento) {
        return repository.findByTipoArchivo(tipoDocumento).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoProfesionalResponseDTO> findByEstado(Integer estado) {
        return repository.findByEstado(estado).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentoProfesionalResponseDTO findById(Integer id) {
        DocumentoProfesionalEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento profesional no encontrado con ID: " + id));
        return toResponseDTO(entity);
    }

    @Override
    public DocumentoProfesionalResponseDTO create(DocumentoProfesionalCreateDTO dto, MultipartFile archivo) {
        DocumentoProfesionalEntity entity = new DocumentoProfesionalEntity();

        entity.setTitulo(dto.getTitulo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setTipoArchivo(dto.getTipoArchivo());

        // Subir archivo o usar URL proporcionada
        if (archivo != null && !archivo.isEmpty()) {
            try {
                entity.setArchivoUrl(archivoService.uploadFile(archivo, "SOCIO_DOCUMENTOS_PERFIL"));
            } catch (IOException e) {
                throw new RuntimeException("Error técnico al procesar el archivo: " + e.getMessage());
            }
        } else if (dto.getArchivoUrl() != null) {
            entity.setArchivoUrl(dto.getArchivoUrl());
        }

        entity.setEstado(1); // Activo por defecto

        DocumentoProfesionalEntity saved = repository.save(entity);
        return toResponseDTO(saved);
    }

    @Override
    public DocumentoProfesionalResponseDTO update(Integer id, DocumentoProfesionalUpdateDTO dto,
            MultipartFile archivo) {
        DocumentoProfesionalEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento profesional no encontrado con ID: " + id));

        if (dto.getTitulo() != null)
            entity.setTitulo(dto.getTitulo());
        if (dto.getDescripcion() != null)
            entity.setDescripcion(dto.getDescripcion());
        if (dto.getTipoArchivo() != null)
            entity.setTipoArchivo(dto.getTipoArchivo());
        if (dto.getEstado() != null)
            entity.setEstado(dto.getEstado());

        // Actualizar archivo si se proporciona uno nuevo
        if (archivo != null && !archivo.isEmpty()) {
            try {
                entity.setArchivoUrl(archivoService.uploadFile(archivo, "SOCIO_DOCUMENTOS_PERFIL"));
            } catch (IOException e) {
                throw new RuntimeException("Error técnico al procesar el archivo: " + e.getMessage());
            }
        } else if (dto.getArchivoUrl() != null) {
            entity.setArchivoUrl(dto.getArchivoUrl());
        }

        DocumentoProfesionalEntity updated = repository.save(entity);
        return toResponseDTO(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Documento profesional no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    private DocumentoProfesionalResponseDTO toResponseDTO(DocumentoProfesionalEntity entity) {
        DocumentoProfesionalResponseDTO dto = new DocumentoProfesionalResponseDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setTipoArchivo(entity.getTipoArchivo());
        dto.setArchivoUrl(entity.getArchivoUrl());
        dto.setFechaSubida(entity.getFechaSubida());
        dto.setEstado(entity.getEstado());
        return dto;
    }
}
