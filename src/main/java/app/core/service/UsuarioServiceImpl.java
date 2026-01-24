 package app.core.service;
 
 import app.core.dto.RolResponseDTO;
 import app.core.dto.UsuarioDTO;
 import app.core.dto.UsuarioUpdateDTO;
 import app.core.dto.UsuarioResponseDTO;
 import app.core.entity.PersonaEntity;
 import app.core.entity.RolEntity;
 import app.core.entity.UsuarioEntity;
 import app.common.exception.*;
 import app.common.util.GenericRepositoryNormal;
 import app.core.repository.PersonaRepository;
 import app.core.repository.RolRepository;
 import app.core.repository.UsuarioRepository;
 import app.common.util.GenericServiceImplNormal;
 import app.core.service.PersonaService;
 import app.core.service.UsuarioService;
 import java.io.Serializable;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.Optional;
 import java.util.stream.Collectors;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 public class UsuarioServiceImpl
   extends GenericServiceImplNormal<UsuarioEntity, Integer>
   implements UsuarioService
 {
   @Autowired
   private UsuarioRepository usuarioRepository;
   
   @Autowired
   private PersonaRepository personaRepository;
   
   @Autowired
   private RolRepository rolRepository;
   
   UsuarioServiceImpl(GenericRepositoryNormal<UsuarioEntity, Integer> genericRepository) {
     super(genericRepository);
   }
   
   @Autowired
   private PersonaService personaService;
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;
   
   @Transactional
   public List<UsuarioEntity> findAll(int estado, String search, int length, int start) throws Exception {
     try {
       List<UsuarioEntity> entities = this.usuarioRepository.findAll(estado, search, length, start);
       return entities;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional
   public void updateStatus(int status, int id) throws Exception {
     try {
       System.out.println("estado:" + status + " id:" + id);
       this.usuarioRepository.updateStatus(status, id);
     }
     catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
       throw new Exception(e.getMessage());
     } 
   }


   public int getIdPrimaryKey() throws Exception {
     try {
       int id = this.usuarioRepository.getIdPrimaryKey();
       return id;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return 0;
     } 
   }
   
   @Transactional
   public UsuarioEntity save(UsuarioEntity entity) throws Exception {
     try {
       System.out.println("EntitySAVE_Servicio:" + entity.toString());


       PersonaEntity persona = new PersonaEntity();
       persona.setId(Integer.valueOf(this.personaService.getIdPrimaryKey()));
       persona.setCi(entity.getPersona().getCi());
       persona.setNombrecompleto(entity.getPersona().getNombrecompleto());
       persona.setEmail(entity.getPersona().getEmail());
       persona.setCelular(entity.getPersona().getCelular());
       persona.setEstado(Integer.valueOf(1));
       PersonaEntity persona2 = (PersonaEntity)this.personaService.save(persona);
       
       entity.setPersona(persona2);
       entity.setId(Integer.valueOf(this.usuarioRepository.getIdPrimaryKey()));
       entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
       System.out.println("EntityUFVPost:" + entity.toString());
       entity = (UsuarioEntity)this.usuarioRepository.save(entity);
       return entity;
     } catch (Exception e) {
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional
   public UsuarioEntity update(Integer id, UsuarioEntity entidad) throws Exception {
     try {
       System.out.println("UsuarioModificar:" + entidad.toString());
       
       Optional<UsuarioEntity> entitiOptional = this.genericRepository.findById(id);
       UsuarioEntity usuario = entitiOptional.get();
       
       if (!entidad.getPassword().equals(usuario.getPassword())) {
         entidad.setPassword(this.passwordEncoder.encode(entidad.getPassword()));
       }
       
       usuario = (UsuarioEntity)this.genericRepository.save(entidad);
       return usuario;
     } catch (Exception e) {
       throw new Exception(e.getMessage());
     } 
   }


   public Integer getTotAll(String search, int estado) throws Exception {
     try {
       int total = this.usuarioRepository.getTotAll(search, estado).intValue();
       return Integer.valueOf(total);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return Integer.valueOf(0);
     } 
   }
   
   /**
    * Obtiene datos para DataTables con paginación
    */
   @Transactional(readOnly = true)
   public Map<String, Object> getDataTableData(int draw, int length, int start, int estado, String search) {
       try {
           List<UsuarioEntity> lista = findAll(estado, search, length, start);
           String total = String.valueOf(getTotAll(search, estado));
           
           Map<String, Object> data = new HashMap<>();
           data.put("draw", draw);
           data.put("recordsTotal", total);
           data.put("data", lista);
           
           if (search != null && !search.isEmpty()) {
               data.put("recordsFiltered", lista.size());
           } else {
               data.put("recordsFiltered", total);
           }
           
           return data;
       } catch (Exception e) {
           throw new RuntimeException("Error al obtener datos para DataTable: " + e.getMessage(), e);
       }
   }
   
   public UsuarioEntity getUserByLogin(String valor) throws Exception {
     UsuarioEntity entity = new UsuarioEntity();
     try {
       entity = this.usuarioRepository.getUserByLogin(valor);
       return entity;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return null;
     } 
   }


   public UsuarioEntity findByEmail(String username) throws Exception {
     UsuarioEntity entity = new UsuarioEntity();
     try {
       entity = this.usuarioRepository.findByUsername(username);
       return entity;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       return null;
     } 
   }

   // ==================== NUEVOS MÉTODOS CON DTOs ====================
   
   /**
    * Crea un nuevo usuario
    */
   @Override
   @Transactional
   public UsuarioResponseDTO create(UsuarioDTO dto) {
       // 1. Validar datos
       validateData(dto);
       
       // 2. Verificar duplicados
       if (usuarioRepository.existsByUsername(dto.getUsername())) {
           throw new DuplicateResourceException("Usuario", "username", dto.getUsername());
       }
       
       // 3. Verificar existencia de persona
       PersonaEntity persona = personaRepository.findById(dto.getPersonaId())
           .orElseThrow(() -> new ResourceNotFoundException("Persona", "id", dto.getPersonaId()));
       
       // 4. Verificar y obtener roles
       List<RolEntity> roles = rolRepository.findAllById(dto.getRoleIds());
       if (roles.size() != dto.getRoleIds().size()) {
           throw new ResourceNotFoundException("Uno o más roles no existen");
       }
       
       // 5. Crear usuario
       UsuarioEntity usuario = new UsuarioEntity();
       try {
           usuario.setId(usuarioRepository.getIdPrimaryKey());
       } catch (Exception e) {
           throw new RuntimeException("Error al obtener ID de usuario", e);
       }
       usuario.setUsername(dto.getUsername());
       usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
       usuario.setEstado(dto.getEstado());
       usuario.setPersona(persona);
       usuario.setRoles(roles);
       
       usuario = usuarioRepository.save(usuario);
       return toResponseDTO(usuario);
   }
   
   /**
    * Actualiza un usuario existente
    */
   @Override
   @Transactional
   public UsuarioResponseDTO updateUsuario(Integer id, UsuarioUpdateDTO dto) {
       // 1. Buscar usuario existente
       UsuarioEntity usuario = usuarioRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
       
       // 2. Verificar duplicado de username (excepto el mismo usuario)
       UsuarioEntity existingByUsername = usuarioRepository.findByUsername(dto.getUsername());
       if (existingByUsername != null && !existingByUsername.getId().equals(id)) {
           throw new DuplicateResourceException("Usuario", "username", dto.getUsername());
       }
       
       // 3. Verificar existencia de persona
       PersonaEntity persona = personaRepository.findById(dto.getPersonaId())
           .orElseThrow(() -> new ResourceNotFoundException("Persona", "id", dto.getPersonaId()));
       
       // 4. Actualizar datos básicos
       usuario.setUsername(dto.getUsername());
       usuario.setEstado(dto.getEstado());
       usuario.setPersona(persona);
       
       // 5. Solo actualizar password si se proveyó uno nuevo
       if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
           usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
       }
       
       // 6. Solo actualizar roles si se proveyeron
       if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
           List<RolEntity> roles = rolRepository.findAllById(dto.getRoleIds());
           if (roles.size() != dto.getRoleIds().size()) {
               throw new ResourceNotFoundException("Uno o más roles no existen");
           }
           usuario.setRoles(roles);
       }
       
       usuario = usuarioRepository.save(usuario);
       return toResponseDTO(usuario);
   }
   
   /**
    * Encuentra un usuario por ID y lo convierte a DTO
    */
   @Override
   @Transactional(readOnly = true)
   public UsuarioResponseDTO findByIdDTO(Integer id) {
       UsuarioEntity usuario = usuarioRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
       return toResponseDTO(usuario);
   }
   
   /**
    * Encuentra todos los usuarios y los convierte a DTOs
    */
   @Override
   @Transactional(readOnly = true)
   public List<UsuarioResponseDTO> findAllDTO() {
       try {
           return findAll().stream()
               .map(this::toResponseDTO)
               .collect(Collectors.toList());
       } catch (Exception e) {
           throw new RuntimeException("Error al listar usuarios", e);
       }
   }
   
   /**
    * Encuentra usuarios por estado y los convierte a DTOs
    */
   @Override
   @Transactional(readOnly = true)
   public List<UsuarioResponseDTO> findAllDTO(int estado) {
       List<UsuarioEntity> usuarios = usuarioRepository.findByEstado(estado);
       return usuarios.stream()
           .map(this::toResponseDTO)
           .collect(Collectors.toList());
   }
   
   /**
    * Cambia el estado de un usuario (alterna entre activo/inactivo)
    */
   @Override
   @Transactional
   public UsuarioResponseDTO changeStatusUsuario(Integer id) {
       UsuarioEntity usuario = usuarioRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
       
       // Alternar estado
       int nuevoEstado = usuario.getEstado() == 1 ? 0 : 1;
       usuario.setEstado(nuevoEstado);
       usuario = usuarioRepository.save(usuario);
       
       // Forzar carga de relaciones lazy antes de convertir a DTO
       if (usuario.getPersona() != null) {
           usuario.getPersona().getNombrecompleto(); // Trigger lazy loading
       }
       if (usuario.getRoles() != null) {
           usuario.getRoles().size(); // Trigger lazy loading
       }
       
       return toResponseDTO(usuario);
   }
   
   /**
    * Valida los datos del DTO
    */
   private void validateData(UsuarioDTO dto) {
       if (dto.getUsername() == null || dto.getUsername().trim().isEmpty()) {
           throw new InvalidDataException("El username es requerido");
       }
       if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
           throw new InvalidDataException("La contraseña es requerida");
       }
       if (dto.getPassword().length() < 4) {
           throw new InvalidDataException("La contraseña debe tener al menos 4 caracteres");
       }
       if (dto.getEstado() == null) {
           throw new InvalidDataException("El estado es requerido");
       }
       if (dto.getEstado() != 0 && dto.getEstado() != 1) {
           throw new InvalidDataException("El estado debe ser 0 (inactivo) o 1 (activo)");
       }
       if (dto.getPersonaId() == null) {
           throw new InvalidDataException("La persona es requerida");
       }
       if (dto.getRoleIds() == null || dto.getRoleIds().isEmpty()) {
           throw new InvalidDataException("Debe asignar al menos un rol");
       }
   }
   
   /**
    * Convierte UsuarioEntity a UsuarioResponseDTO
    */
   private UsuarioResponseDTO toResponseDTO(UsuarioEntity usuario) {
       UsuarioResponseDTO dto = new UsuarioResponseDTO();
       
       dto.setId(usuario.getId());
       dto.setUsername(usuario.getUsername());
       dto.setEstado(usuario.getEstado());
       
       // Datos de persona
       if (usuario.getPersona() != null) {
           dto.setPersonaId(usuario.getPersona().getId());
           dto.setPersonaCi(usuario.getPersona().getCi());
           dto.setPersonaNombre(usuario.getPersona().getNombrecompleto());
           dto.setPersonaEmail(usuario.getPersona().getEmail());
       }
       
       // Roles
       if (usuario.getRoles() != null) {
           List<RolResponseDTO> rolesDTO = usuario.getRoles().stream()
               .map(rol -> {
                   RolResponseDTO rolDTO = new RolResponseDTO();
                   rolDTO.setId(rol.getId());
                   rolDTO.setNombre(rol.getNombre());
                   rolDTO.setEstado(rol.getEstado());
                   return rolDTO;
               })
               .collect(Collectors.toList());
           dto.setRoles(rolesDTO);
       }
       
       return dto;
   }
   
   /**
    * Busca un usuario por persona ID
    */
   @Transactional(readOnly = true)
   public UsuarioResponseDTO findByPersonaIdDTO(Integer personaId) {
       UsuarioEntity usuario = usuarioRepository.findByPersonaId(personaId);
       if (usuario == null) {
           throw new ResourceNotFoundException("Usuario no encontrado para persona con ID: " + personaId);
       }
       return toResponseDTO(usuario);
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\UsuarioServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */