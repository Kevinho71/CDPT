package app.perfil.service;

import org.springframework.web.multipart.MultipartFile;

import app.perfil.dto.DocumentoProfesionalCreateDTO;
import app.perfil.dto.DocumentoProfesionalResponseDTO;
import app.perfil.dto.DocumentoProfesionalUpdateDTO;

import java.io.IOException;
import java.util.List;

public interface DocumentoProfesionalService {
    
    List<DocumentoProfesionalResponseDTO> findAll();
    
    List<DocumentoProfesionalResponseDTO> findByTipo(String tipoDocumento);
    
    List<DocumentoProfesionalResponseDTO> findByEstado(Integer estado);
    
    DocumentoProfesionalResponseDTO findById(Integer id);
    
    DocumentoProfesionalResponseDTO create(DocumentoProfesionalCreateDTO dto, MultipartFile archivo);
    
    DocumentoProfesionalResponseDTO update(Integer id, DocumentoProfesionalUpdateDTO dto, MultipartFile archivo) throws IOException;
    
    void delete(Integer id);
}
