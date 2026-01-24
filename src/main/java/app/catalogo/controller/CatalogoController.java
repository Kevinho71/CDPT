 package app.catalogo.controller;
 
 import app.catalogo.dto.CatalogoDTO;
 import app.catalogo.dto.CatalogoResponseDTO;
 import app.common.util.ArchivoService;
 import app.catalogo.service.CatalogoServiceImpl;
 import app.common.util.Constantes;
 import jakarta.validation.Valid;
 import java.io.InputStream;
 import java.nio.file.Files;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.io.InputStreamResource;
 import org.springframework.core.io.Resource;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.MediaType;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.multipart.MultipartFile;

/**
 * REST Controller para gestión de catálogos (empresas)
 * Base path: /api/catalogos
 */
@RestController
@RequestMapping("/api/catalogos")
public class CatalogoController {
   
   @Autowired
   private CatalogoServiceImpl catalogoService;
   
   @Autowired
   private ArchivoService archivoService;
   
   /**
    * Lista todos los catálogos
    * GET /api/catalogos
    */
   @GetMapping
   public ResponseEntity<List<CatalogoResponseDTO>> getAll() {
       return ResponseEntity.ok(catalogoService.findAllDTO());
   }
   
   /**
    * Lista solo catálogos activos (estado = 1)
    * GET /api/catalogos/active
    */
   @GetMapping("/active")
   public ResponseEntity<List<CatalogoResponseDTO>> getAllActive() {
       return ResponseEntity.ok(catalogoService.findAllDTO(1));
   }
   
   /**
    * Lista catálogos por estado
    * GET /api/catalogos?estado=1
    */
   @GetMapping(params = "estado")
   public ResponseEntity<List<CatalogoResponseDTO>> getAllByEstado(@RequestParam int estado) {
       return ResponseEntity.ok(catalogoService.findAllDTO(estado));
   }
   
   /**
    * Obtiene un catálogo por ID
    * GET /api/catalogos/{id}
    */
   @GetMapping("/{id}")
   public ResponseEntity<CatalogoResponseDTO> getOne(@PathVariable Integer id) {
       return ResponseEntity.ok(catalogoService.findByIdDTO(id));
   }
   
   /**
    * Crea un nuevo catálogo con logo, banner e imágenes de galería
    * POST /api/catalogos (multipart/form-data)
    * Banner: codigo=0, tipo=BANNER (máximo 1)
    * Galería: codigo=1,2,3, tipo=GALERIA (máximo 3)
    */
   @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<CatalogoResponseDTO> create(
           @Valid @ModelAttribute CatalogoDTO catalogoDTO,
           @RequestParam(value = "logo", required = false) MultipartFile logo,
           @RequestParam(value = "banner", required = false) MultipartFile banner,
           @RequestParam(value = "galeria1", required = false) MultipartFile galeria1,
           @RequestParam(value = "galeria2", required = false) MultipartFile galeria2,
           @RequestParam(value = "galeria3", required = false) MultipartFile galeria3) {
       
       CatalogoResponseDTO result = catalogoService.create(catalogoDTO, logo, banner, galeria1, galeria2, galeria3);
       return ResponseEntity.status(HttpStatus.CREATED).body(result);
   }
   
   /**
    * Actualiza un catálogo existente con logo, banner e imágenes
    * PUT /api/catalogos/{id} (multipart/form-data)
    * Si se envía banner (codigo=0), reemplaza el anterior
    * Si se envía galeria1/2/3 (codigo=1/2/3), reemplaza la correspondiente
    */
   @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<CatalogoResponseDTO> update(
           @PathVariable Integer id,
           @Valid @ModelAttribute CatalogoDTO catalogoDTO,
           @RequestParam(value = "logo", required = false) MultipartFile logo,
           @RequestParam(value = "banner", required = false) MultipartFile banner,
           @RequestParam(value = "galeria1", required = false) MultipartFile galeria1,
           @RequestParam(value = "galeria2", required = false) MultipartFile galeria2,
           @RequestParam(value = "galeria3", required = false) MultipartFile galeria3) {
       
       // Log para debug - verificar qué está llegando
       System.out.println("=== UPDATE CATALOGO DEBUG ===");
       System.out.println("Logo: " + (logo == null ? "null" : (logo.isEmpty() ? "empty" : "file: " + logo.getOriginalFilename())));
       System.out.println("Banner: " + (banner == null ? "null" : (banner.isEmpty() ? "empty" : "file: " + banner.getOriginalFilename())));
       System.out.println("Galeria1: " + (galeria1 == null ? "null" : (galeria1.isEmpty() ? "empty" : "file: " + galeria1.getOriginalFilename())));
       System.out.println("Galeria2: " + (galeria2 == null ? "null" : (galeria2.isEmpty() ? "empty" : "file: " + galeria2.getOriginalFilename())));
       System.out.println("Galeria3: " + (galeria3 == null ? "null" : (galeria3.isEmpty() ? "empty" : "file: " + galeria3.getOriginalFilename())));
       System.out.println("============================");
       
       CatalogoResponseDTO result = catalogoService.updateCatalogo(id, catalogoDTO, logo, banner, galeria1, galeria2, galeria3);
       return ResponseEntity.ok(result);
   }
   
   /**
    * Cambia el estado de un catálogo (activo/inactivo)
    * PATCH /api/catalogos/{id}/status
    */
   @PatchMapping("/{id}/status")
   public ResponseEntity<CatalogoResponseDTO> changeStatus(@PathVariable Integer id) {
       CatalogoResponseDTO result = catalogoService.changeStatusCatalogo(id);
       return ResponseEntity.ok(result);
   }
   
   /**
    * Obtiene el logo de un catálogo (redirige a Cloudinary)
    * GET /api/catalogos/logo/{filename}
    * DEPRECATED: Las imágenes ahora se acceden directamente desde Cloudinary mediante URLs en el DTO
    */
   @GetMapping("/logo/{filename}")
   public ResponseEntity<String> getLogo(@PathVariable String filename) {
       // Ahora las imágenes están en Cloudinary y se acceden mediante URLs directas
       // Este endpoint está deprecated, usar la URL directa del catalogo.logo
       return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
           .body("Este endpoint está deprecated. Use la URL de Cloudinary que viene en el DTO del catálogo.");
   }
   
   /**
    * Obtiene una imagen del catálogo (redirige a Cloudinary)
    * GET /api/catalogos/imagenes/{filename}
    * DEPRECATED: Las imágenes ahora se acceden directamente desde Cloudinary mediante URLs en el DTO
    */
   @GetMapping("/imagenes/{filename}")
   public ResponseEntity<String> getImagen(@PathVariable String filename) {
       // Ahora las imágenes están en Cloudinary y se acceden mediante URLs directas
       // Este endpoint está deprecated, usar las URLs directas de las imágenes en el DTO
       return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
           .body("Este endpoint está deprecated. Use las URLs de Cloudinary que vienen en el DTO del catálogo.");
   }
   
   // Método serveFile eliminado - Las imágenes ahora se sirven directamente desde Cloudinary
}