 package app.core.service;
 
 import app.common.entity.GenericEntity;
 import app.core.entity.RolEntity;
 import app.common.util.GenericRepository;
 import app.core.repository.RolRepository;
 import app.common.util.GenericServiceImpl;
 import app.core.service.RolService;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 public class RolServiceImpl
   extends GenericServiceImpl<RolEntity, Long> implements RolService {
   @Autowired
   private RolRepository rolRepository;
   
   public RolServiceImpl(GenericRepository<RolEntity, Long> genericRepository) {
     super(genericRepository);
   }


   @Transactional
   public List<RolEntity> findAll(String clave, int estado) throws Exception {
     try {
       List<RolEntity> entities = this.rolRepository.findAll(clave, estado);
       return entities;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional(noRollbackFor = {Exception.class})
   public Page<RolEntity> findAll(int estado, String search, Pageable pageable) throws Exception {
     try {
       Page<RolEntity> entities = this.rolRepository.findAll(estado, search, pageable);
       return entities;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional
   public void updateStatus(int status, long id) throws Exception {
     try {
       System.out.println("estado:" + status + " id:" + id);
       this.rolRepository.updateStatus(status, Long.valueOf(id));
     }
     catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
       throw new Exception(e.getMessage());
     } 
   }
   
   @Transactional
   public RolEntity save(RolEntity entidad) throws Exception {
     String nombre = entidad.getNombre();
     nombre = "ROLE_" + nombre;
     entidad.setNombre(nombre);
     try {
       entidad = (RolEntity)this.genericRepository.save(entidad);
       return entidad;
     } catch (Exception e) {
       throw new Exception(e.getMessage());
     } 
   }
   
   // ==================== NUEVOS MÉTODOS CRUD REFACTORIZADOS ====================
   
   @Transactional
   public app.core.dto.RolResponseDTO create(app.core.dto.RolDTO dto) {
     validateData(dto);
     
     List<RolEntity> existing = rolRepository.findAll(dto.getNombre(), -1);
     if (!existing.isEmpty()) {
       throw new app.common.exception.DuplicateResourceException("Rol", "nombre", dto.getNombre());
     }
     
     RolEntity rol = new RolEntity();
     rol.setNombre("ROLE_" + dto.getNombre());
     rol.setEstado(dto.getEstado());
     
     rol = (RolEntity) genericRepository.save(rol);
     return toResponseDTO(rol);
   }
   
   @Transactional
   public app.core.dto.RolResponseDTO updateRol(Long id, app.core.dto.RolDTO dto) {
     RolEntity rol = (RolEntity) genericRepository.findById(id)
         .orElseThrow(() -> new app.common.exception.ResourceNotFoundException("Rol", "id", id));
     
     validateData(dto);
     
     String newName = "ROLE_" + dto.getNombre();
     if (!rol.getNombre().equalsIgnoreCase(newName)) {
       List<RolEntity> existing = rolRepository.findAll(dto.getNombre(), -1);
       if (!existing.isEmpty()) {
         throw new app.common.exception.DuplicateResourceException("Rol", "nombre", dto.getNombre());
       }
     }
     
     rol.setNombre(newName);
     rol.setEstado(dto.getEstado());
     
     rol = (RolEntity) genericRepository.save(rol);
     return toResponseDTO(rol);
   }
   
   @Transactional(readOnly = true)
   public app.core.dto.RolResponseDTO findByIdDTO(Long id) {
     RolEntity rol = (RolEntity) genericRepository.findById(id)
         .orElseThrow(() -> new app.common.exception.ResourceNotFoundException("Rol", "id", id));
     return toResponseDTO(rol);
   }
   
   @Transactional(readOnly = true)
   public List<app.core.dto.RolResponseDTO> findAllDTO() {
     return rolRepository.findAll("", -1).stream()
         .map(this::toResponseDTO)
         .collect(java.util.stream.Collectors.toList());
   }
   
   @Transactional(readOnly = true)
   public List<app.core.dto.RolResponseDTO> findAllDTO(int estado) {
     return rolRepository.findAll("", estado).stream()
         .map(this::toResponseDTO)
         .collect(java.util.stream.Collectors.toList());
   }
   
   @Transactional
   public app.core.dto.RolResponseDTO changeStatusRol(Long id) {
     RolEntity rol = (RolEntity) genericRepository.findById(id)
         .orElseThrow(() -> new app.common.exception.ResourceNotFoundException("Rol", "id", id));
     
     rolRepository.updateStatus(rol.getEstado(), id);
     
     rol = (RolEntity) genericRepository.findById(id)
         .orElseThrow(() -> new app.common.exception.ResourceNotFoundException("Rol", "id", id));
     
     return toResponseDTO(rol);
   }
   
   private void validateData(app.core.dto.RolDTO dto) {
     if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
       throw new app.common.exception.InvalidDataException("El nombre del rol es requerido");
     }
     if (dto.getEstado() == null || (dto.getEstado() != 0 && dto.getEstado() != 1)) {
       throw new app.common.exception.InvalidDataException("El estado debe ser 0 o 1");
     }
   }
   
   private app.core.dto.RolResponseDTO toResponseDTO(RolEntity entity) {
     return new app.core.dto.RolResponseDTO(
         entity.getId(),
         entity.getNombre(),
         entity.getEstado()
     );
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\RolServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */