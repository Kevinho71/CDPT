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
    * Crea un nuevo catálogo con logo e imágenes
    * POST /api/catalogos (multipart/form-data)
    */
   @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<CatalogoResponseDTO> create(
           @Valid @ModelAttribute CatalogoDTO catalogoDTO,
           @RequestParam(value = "logo", required = false) MultipartFile logo,
           @RequestParam(value = "imagenes", required = false) List<MultipartFile> imagenes) {
       
       CatalogoResponseDTO result = catalogoService.create(catalogoDTO, logo, imagenes);
       return ResponseEntity.status(HttpStatus.CREATED).body(result);
   }
   
   /**
    * Actualiza un catálogo existente con logo e imágenes
    * PUT /api/catalogos/{id} (multipart/form-data)
    */
   @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<CatalogoResponseDTO> update(
           @PathVariable Integer id,
           @Valid @ModelAttribute CatalogoDTO catalogoDTO,
           @RequestParam(value = "logo", required = false) MultipartFile logo,
           @RequestParam(value = "imagenes", required = false) List<MultipartFile> imagenes) {
       
       CatalogoResponseDTO result = catalogoService.updateCatalogo(id, catalogoDTO, logo, imagenes);
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
    * Obtiene el logo de un catálogo
    * GET /api/catalogos/logo/{filename}
    */
   @GetMapping("/logo/{filename}")
   public ResponseEntity<Resource> getLogo(@PathVariable String filename) {
       return serveFile(filename, Constantes.nameFolderLogoCatalogo, "Logo");
   }
   
   /**
    * Obtiene una imagen del catálogo
    * GET /api/catalogos/imagenes/{filename}
    */
   @GetMapping("/imagenes/{filename}")
   public ResponseEntity<Resource> getImagen(@PathVariable String filename) {
       return serveFile(filename, Constantes.nameFolderImgCatalogo, "Imagen");
   }
   
   /**
    * Método privado para servir archivos (logos, imágenes) con validación y cache
    */
   private ResponseEntity<Resource> serveFile(String filename, String folder, String fileType) {
       // Validar filename (seguridad: prevenir path traversal)
       if (filename == null || filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
           throw new app.common.exception.InvalidDataException("Nombre de archivo inválido");
       }

       try {
           java.nio.file.Path filePath = archivoService.linkArchivo(folder, filename);

           if (filePath == null || !Files.exists(filePath)) {
               throw new app.common.exception.ResourceNotFoundException(fileType, "filename", filename);
           }

           // Usar PathResource en lugar de InputStreamResource (más eficiente)
           org.springframework.core.io.PathResource resource = 
               new org.springframework.core.io.PathResource(filePath);

           // Determinar Content-Type
           String contentType;
           try {
               contentType = Files.probeContentType(filePath);
           } catch (java.io.IOException e) {
               contentType = null;
           }
           
           if (contentType == null) {
               // Default para imágenes comunes
               if (filename.toLowerCase().endsWith(".png")) {
                   contentType = "image/png";
               } else if (filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg")) {
                   contentType = "image/jpeg";
               } else {
                   contentType = "application/octet-stream";
               }
           }

           return ResponseEntity.ok()
                   .contentType(MediaType.parseMediaType(contentType))
                   .header("Content-Disposition", "inline; filename=\"" + filename + "\"")
                   .cacheControl(org.springframework.http.CacheControl.maxAge(7, java.util.concurrent.TimeUnit.DAYS))
                   .body(resource);

       } catch (Exception e) {
           throw new app.common.exception.FileStorageException("Error al leer el archivo de " + fileType, e);
       }
   }
}