 package app.core.controller;
 
 import app.core.dto.UsuarioDTO;
 import app.core.dto.UsuarioUpdateDTO;
 import app.core.dto.UsuarioResponseDTO;
 import app.core.service.UsuarioServiceImpl;
 import jakarta.validation.Valid;
 import java.util.List;
 import java.util.Map;
 import jakarta.servlet.http.HttpServletRequest;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.repository.query.Param;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

/**
 * REST Controller para gestión de usuarios
 * Base path: /api/usuarios
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
   
   @Autowired
   private UsuarioServiceImpl usuarioService;
   
   /**
    * Lista todos los usuarios
    * GET /api/usuarios
    */
   @GetMapping
   public ResponseEntity<List<UsuarioResponseDTO>> getAll() {
       return ResponseEntity.ok(usuarioService.findAllDTO());
   }
   
   /**
    * Lista usuarios por estado
    * GET /api/usuarios?estado=1
    */
   @GetMapping(params = "estado")
   public ResponseEntity<List<UsuarioResponseDTO>> getAllByEstado(@RequestParam int estado) {
       return ResponseEntity.ok(usuarioService.findAllDTO(estado));
   }
   
   /**
    * Lista usuarios con paginación y filtros (DataTables)
    * GET /api/usuarios/datatable
    */
   @GetMapping("/datatable")
   public ResponseEntity<Map<String, Object>> getDataTable(
           HttpServletRequest request,
           @Param("draw") int draw,
           @Param("length") int length,
           @Param("start") int start,
           @Param("estado") int estado) {
       
       String search = request.getParameter("search[value]");
       Map<String, Object> data = usuarioService.getDataTableData(draw, length, start, estado, search);
       return ResponseEntity.ok(data);
   }
   
   /**
    * Obtiene un usuario por ID
    * GET /api/usuarios/{id}
    */
   @GetMapping("/{id}")
   public ResponseEntity<UsuarioResponseDTO> getOne(@PathVariable Integer id) {
       return ResponseEntity.ok(usuarioService.findByIdDTO(id));
   }
   
   /**
    * Obtiene usuario por persona ID
    * GET /api/usuarios/persona/{personaId}
    */
   @GetMapping("/persona/{personaId}")
   public ResponseEntity<UsuarioResponseDTO> getByPersonaId(@PathVariable Integer personaId) {
       return ResponseEntity.ok(usuarioService.findByPersonaIdDTO(personaId));
   }
   
   /**
    * Crea un nuevo usuario
    * POST /api/usuarios
    */
   @PostMapping
   public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioDTO dto) {
       UsuarioResponseDTO result = usuarioService.create(dto);
       return ResponseEntity.status(HttpStatus.CREATED).body(result);
   }
   
   /**
    * Actualiza un usuario existente
    * PUT /api/usuarios/{id}
    */
   @PutMapping("/{id}")
   public ResponseEntity<UsuarioResponseDTO> update(
           @PathVariable Integer id,
           @Valid @RequestBody UsuarioUpdateDTO dto) {
       
       UsuarioResponseDTO result = usuarioService.updateUsuario(id, dto);
       return ResponseEntity.ok(result);
   }
   
   /**
    * Cambia el estado de un usuario (activo/inactivo)
    * PATCH /api/usuarios/{id}/status
    */
   @PatchMapping("/{id}/status")
   public ResponseEntity<UsuarioResponseDTO> changeStatus(@PathVariable Integer id) {
       UsuarioResponseDTO result = usuarioService.changeStatusUsuario(id);
       return ResponseEntity.ok(result);
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\RestUsuario.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */