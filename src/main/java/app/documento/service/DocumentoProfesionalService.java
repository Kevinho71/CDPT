package app.documento.service;

import app.documento.dto.DocumentoProfesionalCreateDTO;
import app.documento.dto.DocumentoProfesionalResponseDTO;
import app.documento.dto.DocumentoProfesionalUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentoProfesionalService {
    
    List<DocumentoProfesionalResponseDTO> findAll();
    
    List<DocumentoProfesionalResponseDTO> findByTipo(String tipoDocumento);
    
    List<DocumentoProfesionalResponseDTO> findByEstado(Integer estado);
    
    DocumentoProfesionalResponseDTO findById(Integer id);
    
    DocumentoProfesionalResponseDTO create(DocumentoProfesionalCreateDTO dto, MultipartFile archivo);
    
    DocumentoProfesionalResponseDTO update(Integer id, DocumentoProfesionalUpdateDTO dto, MultipartFile archivo);
    
    void delete(Integer id);
}
