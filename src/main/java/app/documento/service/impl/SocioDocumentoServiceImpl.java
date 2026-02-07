package app.documento.service.impl;

import app.common.exception.ResourceNotFoundException;
import app.common.util.ArchivoService;
import app.documento.dto.*;
import app.documento.entity.DocumentoProfesionalEntity;
import app.documento.entity.SocioDocumentoEntity;
import app.documento.repository.DocumentoProfesionalRepository;
import app.documento.repository.SocioDocumentoRepository;
import app.documento.service.SocioDocumentoService;
import app.perfil.entity.PerfilSocioEntity;
import app.perfil.repository.PerfilSocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SocioDocumentoServiceImpl implements SocioDocumentoService {

    @Autowired
    private SocioDocumentoRepository repository;

    @Autowired
    private PerfilSocioRepository perfilSocioRepository;

    @Autowired
    private DocumentoProfesionalRepository documentoProfesionalRepository;

    @Autowired
    private ArchivoService archivoService;

    // ==========================================
    // MÉTODOS CRUD BÁSICOS (ADMINISTRATIVOS)
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<SocioDocumentoResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocioDocumentoResponseDTO> findByPerfilSocio(Integer perfilSocioId) {
        return repository.findByFkPerfilSocio_Id(perfilSocioId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocioDocumentoResponseDTO> findByPerfilSocioVisible(Integer perfilSocioId) {
        return repository.findByFkPerfilSocio_IdAndEsVisibleTrue(perfilSocioId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SocioDocumentoResponseDTO findById(Integer id) {
        SocioDocumentoEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relación socio-documento no encontrada con ID: " + id));
        return toResponseDTO(entity);
    }

    @Override
    public SocioDocumentoResponseDTO create(SocioDocumentoCreateDTO dto) {
        SocioDocumentoEntity entity = new SocioDocumentoEntity();

        PerfilSocioEntity perfilSocio = perfilSocioRepository.findById(dto.getFkPerfilSocio())
                .orElseThrow(() -> new ResourceNotFoundException("Perfil socio no encontrado con ID: " + dto.getFkPerfilSocio()));
        entity.setPerfilSocio(perfilSocio);

        DocumentoProfesionalEntity documento = documentoProfesionalRepository.findById(dto.getFkDocumento())
                .orElseThrow(() -> new ResourceNotFoundException("Documento profesional no encontrado con ID: " + dto.getFkDocumento()));
        entity.setDocumento(documento);

        entity.setOrden(dto.getOrden());
        entity.setEsVisible(dto.getEsVisible() != null ? dto.getEsVisible() : true);

        SocioDocumentoEntity saved = repository.save(entity);
        return toResponseDTO(saved);
    }

    @Override
    public SocioDocumentoResponseDTO update(Integer id, SocioDocumentoUpdateDTO dto) {
        SocioDocumentoEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relación socio-documento no encontrada con ID: " + id));

        if (dto.getOrden() != null) entity.setOrden(dto.getOrden());
        if (dto.getEsVisible() != null) entity.setEsVisible(dto.getEsVisible());

        SocioDocumentoEntity updated = repository.save(entity);
        return toResponseDTO(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Relación socio-documento no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }

    // ========================================================
    // NUEVOS MÉTODOS PARA GESTIÓN DESDE EL PERFIL DEL SOCIO
    // ========================================================

    @Override
    public SocioDocumentoCompleteDTO uploadDocumento(Integer perfilSocioId, SocioDocumentoUploadDTO dto) {
        // Validar que el perfil exista
        PerfilSocioEntity perfilSocio = perfilSocioRepository.findById(perfilSocioId)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil socio no encontrado con ID: " + perfilSocioId));

        MultipartFile archivo = dto.getArchivo();
        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo es obligatorio");
        }

        // Detectar tipo de archivo
        String contentType = archivo.getContentType();
        String tipoArchivo = detectarTipoArchivo(contentType, archivo.getOriginalFilename());

        try {
            // 1. Subir archivo a Cloudinary (o local)
            String archivoUrl = archivoService.uploadFile(archivo, "SOCIO_DOCUMENTOS_PERFIL");

            // 2. Crear el documento profesional
            DocumentoProfesionalEntity documentoEntity = new DocumentoProfesionalEntity();
            documentoEntity.setTitulo(dto.getTitulo());
            documentoEntity.setDescripcion(dto.getDescripcion());
            documentoEntity.setArchivoUrl(archivoUrl);
            documentoEntity.setTipoArchivo(tipoArchivo);
            documentoEntity.setEstado(1);
            // documentEntity.setFechaSubida(...) // Generalmente se pone por defecto en la BD o @PrePersist

            DocumentoProfesionalEntity savedDocumento = documentoProfesionalRepository.save(documentoEntity);

            // 3. Crear la relación socio_documentos
            SocioDocumentoEntity relacionEntity = new SocioDocumentoEntity();
            relacionEntity.setPerfilSocio(perfilSocio);
            relacionEntity.setDocumento(savedDocumento);
            relacionEntity.setOrden(dto.getOrden() != null ? dto.getOrden() : 0);
            relacionEntity.setEsVisible(dto.getEsVisible() != null ? dto.getEsVisible() : true);

            SocioDocumentoEntity savedRelacion = repository.save(relacionEntity);

            // 4. Retornar DTO completo
            return toCompleteDTO(savedRelacion);

        } catch (IOException e) {
            throw new RuntimeException("Error al subir el archivo: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocioDocumentoCompleteDTO> findDocumentosByPerfil(Integer perfilSocioId, boolean soloVisibles) {
        List<SocioDocumentoEntity> documentos;

        if (soloVisibles) {
            documentos = repository.findByPerfilSocio_IdAndEsVisibleTrueOrderByOrdenAsc(perfilSocioId);
        } else {
            documentos = repository.findByPerfilSocio_IdOrderByOrdenAsc(perfilSocioId);
        }

        return documentos.stream()
                .map(this::toCompleteDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SocioDocumentoCompleteDTO updateDocumento(Integer perfilSocioId, Integer socioDocumentoId, SocioDocumentoEditDTO dto) {
        // Buscar la relación
        SocioDocumentoEntity relacionEntity = repository.findById(socioDocumentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Documento no encontrado con ID: " + socioDocumentoId));

        // Validar que pertenece al perfil correcto
        if (!relacionEntity.getPerfilSocio().getId().equals(perfilSocioId)) {
            throw new IllegalArgumentException("El documento no pertenece al perfil especificado");
        }

        // Actualizar campos de la relación
        if (dto.getOrden() != null) {
            relacionEntity.setOrden(dto.getOrden());
        }
        if (dto.getEsVisible() != null) {
            relacionEntity.setEsVisible(dto.getEsVisible());
        }

        // Actualizar campos del documento profesional si se proporcionan
        DocumentoProfesionalEntity documentoEntity = relacionEntity.getDocumento();
        if (dto.getTitulo() != null && !dto.getTitulo().isEmpty()) {
            documentoEntity.setTitulo(dto.getTitulo());
        }
        if (dto.getDescripcion() != null) {
            documentoEntity.setDescripcion(dto.getDescripcion());
        }

        // Guardar cambios
        documentoProfesionalRepository.save(documentoEntity);
        SocioDocumentoEntity updatedRelacion = repository.save(relacionEntity);

        return toCompleteDTO(updatedRelacion);
    }

    @Override
    public void deleteDocumento(Integer perfilSocioId, Integer socioDocumentoId) {
        // Buscar la relación
        SocioDocumentoEntity relacionEntity = repository.findById(socioDocumentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Documento no encontrado con ID: " + socioDocumentoId));

        // Validar que pertenece al perfil correcto
        if (!relacionEntity.getPerfilSocio().getId().equals(perfilSocioId)) {
            throw new IllegalArgumentException("El documento no pertenece al perfil especificado");
        }

        DocumentoProfesionalEntity documento = relacionEntity.getDocumento();

        // Eliminar la relación
        repository.delete(relacionEntity);

        // Eliminar el documento profesional y su archivo
        if (documento != null) {
            try {
                // Eliminar archivo de Cloudinary
                if (documento.getArchivoUrl() != null && !documento.getArchivoUrl().isEmpty()) {
                    archivoService.eliminarImagen(documento.getArchivoUrl());
                }
            } catch (IOException e) {
                System.err.println("Error al eliminar archivo de Cloudinary: " + e.getMessage());
            }

            // Eliminar el documento de la base de datos
            documentoProfesionalRepository.delete(documento);
        }
    }

    // ==========================================
    // MÉTODOS PRIVADOS / HELPERS
    // ==========================================

    private SocioDocumentoResponseDTO toResponseDTO(SocioDocumentoEntity entity) {
        SocioDocumentoResponseDTO dto = new SocioDocumentoResponseDTO();
        dto.setId(entity.getId());
        dto.setFkPerfilSocio(entity.getPerfilSocio() != null ? entity.getPerfilSocio().getId() : null);
        dto.setFkDocumento(entity.getDocumento() != null ? entity.getDocumento().getId() : null);
        dto.setDocumentoTitulo(entity.getDocumento() != null ? entity.getDocumento().getTitulo() : null);
        dto.setDocumentoUrl(entity.getDocumento() != null ? entity.getDocumento().getArchivoUrl() : null);
        dto.setDocumentoTipo(entity.getDocumento() != null ? entity.getDocumento().getTipoArchivo() : null);
        dto.setOrden(entity.getOrden());
        dto.setEsVisible(entity.getEsVisible());
        // Aquí estaba el error: el método no se cerraba y los métodos nuevos estaban dentro
        return dto;
    }

    private SocioDocumentoCompleteDTO toCompleteDTO(SocioDocumentoEntity entity) {
        SocioDocumentoCompleteDTO dto = new SocioDocumentoCompleteDTO();
        dto.setId(entity.getId());
        dto.setOrden(entity.getOrden());
        dto.setEsVisible(entity.getEsVisible());
        dto.setFechaAsociacion(entity.getFechaAsociacion());

        if (entity.getDocumento() != null) {
            DocumentoProfesionalEntity doc = entity.getDocumento();
            dto.setDocumentoId(doc.getId());
            dto.setTitulo(doc.getTitulo());
            dto.setDescripcion(doc.getDescripcion());
            dto.setArchivoUrl(doc.getArchivoUrl());
            dto.setTipoArchivo(doc.getTipoArchivo());
            dto.setFechaSubida(doc.getFechaSubida());
        }

        return dto;
    }

    private String detectarTipoArchivo(String contentType, String filename) {
        if (contentType != null) {
            if (contentType.contains("pdf")) {
                return "PDF";
            } else if (contentType.contains("image")) {
                if (contentType.contains("jpeg") || contentType.contains("jpg")) {
                    return "JPG";
                } else if (contentType.contains("png")) {
                    return "PNG";
                } else if (contentType.contains("gif")) {
                    return "GIF";
                }
                return "IMAGEN";
            }
        }

        // Fallback: usar extensión del archivo
        if (filename != null) {
            String lower = filename.toLowerCase();
            if (lower.endsWith(".pdf")) return "PDF";
            if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return "JPG";
            if (lower.endsWith(".png")) return "PNG";
            if (lower.endsWith(".gif")) return "GIF";
        }

        return "DESCONOCIDO";
    }
}