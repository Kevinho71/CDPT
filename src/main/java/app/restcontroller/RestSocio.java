 package app.restcontroller;
 
 import app.config.GoogleDriveConfig;
 import app.dto.SocioDTO;
 import app.entity.PersonaEntity;
 import app.entity.SocioEntity;
 import app.restcontroller.RestControllerGenericNormalImpl;
 import app.service.ArchivoService;
 import app.service.SocioServiceImpl;
 import app.util.Constantes;
 import com.google.api.services.drive.Drive;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.Serializable;
 import java.nio.file.Files;
 import java.nio.file.Paths;
 import java.security.GeneralSecurityException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import jakarta.servlet.http.HttpServletRequest;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.io.InputStreamResource;
 import org.springframework.core.io.Resource;
 import org.springframework.data.repository.query.Param;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.MediaType;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.PutMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;
 import org.springframework.web.multipart.MultipartFile;


 @RestController
 @RequestMapping({"/RestSocios"})
 public class RestSocio
   extends RestControllerGenericNormalImpl<SocioEntity, SocioServiceImpl>
 {
   @Autowired
   private ArchivoService archivoService;
   @Autowired
   private GoogleDriveConfig googleDriveConfig;
   
   private Drive getDriveService() throws IOException, GeneralSecurityException {
     return this.googleDriveConfig.getDriveService();
   }


   @GetMapping({"/listar"})
   public ResponseEntity<?> getAll(HttpServletRequest request, @Param("draw") int draw, @Param("length") int length, @Param("start") int start, @Param("estado") int estado) throws IOException {
     String total = "";
     Map<String, Object> Data = new HashMap<>();
     try {
       System.out.println("***************MODIFICANDO CODIGO V2 LISTANDO *****************************************");
       String search = request.getParameter("search[value]");
       int tot = Constantes.NUM_MAX_DATATABLE.intValue();
       System.out.println("tot:" + tot + "estado:" + estado + "search:" + search + "length:" + length + "start:" + start);
       List<?> lista = ((SocioServiceImpl)this.servicio).findAll(estado, search, length, start);
       System.out.println("listar:" + lista.toString());
       
       try {
         total = String.valueOf(((SocioServiceImpl)this.servicio).getTotAll(search, estado));
       }
       catch (Exception e) {
         total = "0";
       } 
       Data.put("draw", Integer.valueOf(draw));
       Data.put("recordsTotal", total);
       Data.put("data", lista);
       if (!search.equals("")) {
         Data.put("recordsFiltered", Integer.valueOf(lista.size()));
       } else {
         Data.put("recordsFiltered", total);
       }  System.out.println("***************MODIFICANDO CODIGO V2 LISTANDO *****************************************");
       return ResponseEntity.status(HttpStatus.OK).body(Data);
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Data);
     } 
   }
   
   @PostMapping({"/guardar"})
   public ResponseEntity<?> save(SocioDTO socioDTO, @RequestParam("logo") MultipartFile file) {
     System.out.println("EntidadSave LLEGO:" + socioDTO.toString());
     
     try {
       PersonaEntity personaEntity = new PersonaEntity();
       personaEntity.setCi(socioDTO.getCi());
       personaEntity.setNombrecompleto(socioDTO.getNombrecompleto());
       personaEntity.setEmail(socioDTO.getEmail());
       personaEntity.setCelular(socioDTO.getCelular());
       personaEntity.setEstado(Integer.valueOf(1));
       
       SocioEntity socioEntity = new SocioEntity();
       socioEntity.setNrodocumento(socioDTO.getNrodocumento());
       socioEntity.setMatricula(socioDTO.getMatricula());
       socioEntity.setNombresocio(socioDTO.getNombresocio());
       socioEntity.setFechaemision(socioDTO.getFechaemision());
       socioEntity.setFechaexpiracion(socioDTO.getFechaexpiracion());
       socioEntity.setEstado(Integer.valueOf(1));
       socioEntity.setLejendario(socioDTO.getLejendario());
       
       socioEntity.setPersona(personaEntity);
       socioEntity.setLogo(socioDTO.getLogo());


       System.out.println("***************************LOGOO:" + socioEntity.getLogo().getOriginalFilename());
       System.out.println("***************************LEGENDARIO:" + socioDTO.getLejendario());
       
       return ResponseEntity.status(HttpStatus.OK).body(((SocioServiceImpl)this.servicio).save(socioEntity));
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
     } 
   }


   @PutMapping({"/catalogos/{id}"})
   public ResponseEntity<?> update(@PathVariable Integer id, SocioEntity entidad) {
     System.out.println("EntidadModificar LLEGO:" + entidad.toString());


     try {
       return ResponseEntity.status(HttpStatus.OK).body(((SocioServiceImpl)this.servicio).updatecatalogos(id, entidad));
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
     } 
   }


   @PostMapping({"/modificar/{id}"})
   public ResponseEntity<?> update(@PathVariable Integer id, SocioDTO socioDTO, @RequestParam("logo") MultipartFile file) {
     System.out.println("EntidadModificar LLEGO:" + socioDTO.toString());
     
     try {
       PersonaEntity personaEntity = new PersonaEntity();
       personaEntity.setCi(socioDTO.getCi());
       personaEntity.setCelular(socioDTO.getCelular());
       personaEntity.setEstado(Integer.valueOf(1));
       
       SocioEntity socioEntity = new SocioEntity();
       socioEntity.setId(socioDTO.getId());
       socioEntity.setCodigo(socioDTO.getCodigo());
       socioEntity.setMatricula(socioDTO.getMatricula());
       socioEntity.setNombresocio(socioDTO.getNombresocio());
       socioEntity.setFechaemision(socioDTO.getFechaemision());
       socioEntity.setFechaexpiracion(socioDTO.getFechaexpiracion());
       socioEntity.setEstado(Integer.valueOf(1));
       socioEntity.setLejendario(socioDTO.getLejendario());
       
       socioEntity.setPersona(personaEntity);
       socioEntity.setLogo(socioDTO.getLogo());


       return ResponseEntity.status(HttpStatus.OK).body(((SocioServiceImpl)this.servicio).update(id, socioEntity));
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
     } 
   }
   @PostMapping({"/updateStatus"})
   public ResponseEntity<?> updateStatus(@RequestBody SocioEntity entity) {
     try {
       System.out.println("Entidad:" + entity.toString());
       ((SocioServiceImpl)this.servicio).updateStatus(entity.getEstado().intValue(), entity.getId().intValue());
       SocioEntity entity2 = (SocioEntity)((SocioServiceImpl)this.servicio).findById(entity.getId());
       return ResponseEntity.status(HttpStatus.OK).body(entity2);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
     } 
   }
   @GetMapping({"/findByNrodocumento/{id}"})
   public ResponseEntity<?> buscar(@PathVariable String id) {
     try {
       System.out.println("ID A BUSCAR");
       SocioEntity entity = new SocioEntity();
       entity = ((SocioServiceImpl)this.servicio).findByNrodocumento(id);
       if (entity != null) {
         System.out.println("Socio encontrado:" + entity.toString());
       }
       
       return ResponseEntity.status(HttpStatus.OK).body(entity);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
     } 
   }
   
   @PostMapping({"modificarqr/{id}"})
   public ResponseEntity<?> modificarqr(@PathVariable Integer id, @RequestBody SocioEntity entidad) {
     try {
       return ResponseEntity.status(HttpStatus.OK).body(((SocioServiceImpl)this.servicio).renovarQR(id, entidad));
     } catch (Exception e) {
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
     } 
   }


   @GetMapping({"/logo_socio/{filename}"})
   public ResponseEntity<Resource> getFile_logo_socio_drive(@PathVariable String filename) {
     try {
       String folderId = this.archivoService.getOrCreateFolder(Constantes.nameFolderLogoSocio);


       String fileId = this.archivoService.obtenerIdArchivoDrivePorNombre(filename, folderId);
       
       if (fileId != null) {
         
         Drive driveService = getDriveService();


         InputStream inputStream = driveService.files().get(fileId).executeMediaAsInputStream();
         InputStreamResource resource = new InputStreamResource(inputStream);


         String contentType = "application/octet-stream";
         try {
           contentType = Files.probeContentType(Paths.get(filename, new String[0]));
         } catch (IOException e) {
           System.out.println("No se pudo determinar el tipo de archivo.");
         } 


         return ((ResponseEntity.BodyBuilder)ResponseEntity.ok()
           .contentType(MediaType.parseMediaType(contentType))
           .header("Content-Disposition", new String[] { "inline; filename=\"" + filename + "\""
             })).body(resource);
       } 
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
     }
     catch (Exception e) {
       e.printStackTrace();
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
     } 
   }


   @GetMapping({"/qr_socio/{filename}"})
   public ResponseEntity<Resource> qr_socio(@PathVariable String filename) {
     try {
       System.out.println("filenameQR:" + filename);
       
       String folderId = this.archivoService.getOrCreateFolder(Constantes.nameFolderQrSocio);


       String fileId = this.archivoService.obtenerIdArchivoDrivePorNombre(filename, folderId);
       
       if (fileId != null) {
         
         Drive driveService = getDriveService();


         InputStream inputStream = driveService.files().get(fileId).executeMediaAsInputStream();
         InputStreamResource inputStreamResource = new InputStreamResource(inputStream);


         String contentType = "application/octet-stream";
         try {
           contentType = Files.probeContentType(Paths.get(filename, new String[0]));
         } catch (IOException e) {
           System.out.println("No se pudo determinar el tipo de archivo.");
         } 


         return ((ResponseEntity.BodyBuilder)ResponseEntity.ok()
           .contentType(MediaType.parseMediaType(contentType))
           .header("Content-Disposition", new String[] { "inline; filename=\"" + filename + "\""
             })).body(inputStreamResource);
       } 
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
     }
     catch (Exception e) {
       e.printStackTrace();
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
     } 
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\RestSocio.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */