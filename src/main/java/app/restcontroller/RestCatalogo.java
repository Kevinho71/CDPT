 package app.restcontroller;
 
 import app.config.GoogleDriveConfig;
 import app.entity.CatalogoEntity;
 import app.restcontroller.RestControllerGenericNormalImpl;
 import app.service.ArchivoService;
 import app.service.CatalogoServiceImpl;
 import app.util.Constantes;
 import com.google.api.services.drive.Drive;
 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
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
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;
 import org.springframework.web.multipart.MultipartFile;


 @RestController
 @RequestMapping({"/RestCatalogos"})
 public class RestCatalogo
   extends RestControllerGenericNormalImpl<CatalogoEntity, CatalogoServiceImpl>
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
       String search = request.getParameter("search[value]");
       int tot = Constantes.NUM_MAX_DATATABLE.intValue();
       System.out.println("tot:" + tot + "estado:" + estado + "search:" + search + "length:" + length + "start:" + start);
       List<?> lista = ((CatalogoServiceImpl)this.servicio).findAll(estado, search, length, start);
       System.out.println("listar:" + lista.toString());
       
       try {
         total = String.valueOf(((CatalogoServiceImpl)this.servicio).getTotAll(search, estado));
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
       } 
       return ResponseEntity.status(HttpStatus.OK).body(Data);
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Data);
     } 
   }
   
   @PostMapping({"/updateStatus"})
   public ResponseEntity<?> updateStatus(@RequestBody CatalogoEntity entity) {
     try {
       System.out.println("Entidad:" + entity.toString());
       ((CatalogoServiceImpl)this.servicio).updateStatus(entity.getEstado().intValue(), entity.getId().intValue());
       CatalogoEntity entity2 = (CatalogoEntity)((CatalogoServiceImpl)this.servicio).findById(entity.getId());
       return ResponseEntity.status(HttpStatus.OK).body(entity2);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
     } 
   }


   @PostMapping({"/guardar"})
   public ResponseEntity<?> save(CatalogoEntity entidad, @RequestParam("logo") MultipartFile file, @RequestParam("catalogo") MultipartFile catalogo) {
     System.out.println("EntidadSave LLEGO:" + entidad.toString());


     try {
       return ResponseEntity.status(HttpStatus.OK).body(((CatalogoServiceImpl)this.servicio).save(entidad));
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
     } 
   }
   @PostMapping({"/modificar/{id}"})
   public ResponseEntity<?> update(@PathVariable Integer id, CatalogoEntity entidad, @RequestParam("logo") MultipartFile file, @RequestParam("catalogo") MultipartFile catalogo) {
     try {
       System.out.println("EntidadModificar LLEGO:" + entidad.toString());
       return ResponseEntity.status(HttpStatus.OK).body(((CatalogoServiceImpl)this.servicio).update(id, entidad));
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
     } 
   }
   @GetMapping({"/buscar/{id}"})
   public ResponseEntity<?> buscar(@PathVariable String id) {
     try {
       System.out.println("ID A BUSCAR");
       CatalogoEntity entity = new CatalogoEntity();
       entity = (CatalogoEntity)((CatalogoServiceImpl)this.servicio).findById(Integer.valueOf(Integer.parseInt(id)));
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


   @GetMapping({"/logo_empresa/{filename}"})
   public ResponseEntity<Resource> getFile_logo_empresa(@PathVariable String filename) {
     try {
       String folderId = this.archivoService.getOrCreateFolder(Constantes.nameFolderLogoCatalogo);


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


   @GetMapping({"/img_catalogos_empresa/{filename}"})
   public ResponseEntity<Resource> getFileImgCatalogosEmpresa(@PathVariable String filename) {
     try {
       String folderId = this.archivoService.getOrCreateFolder(Constantes.nameFolderImgCatalogo);


       String fileId = this.archivoService.obtenerIdArchivoDrivePorNombre(filename, folderId);
       
       if (fileId != null) {
         
         Drive driveService = getDriveService();


         OutputStream outputStream = new ByteArrayOutputStream();
         driveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);


         ByteArrayInputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream)outputStream).toByteArray());
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


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\RestCatalogo.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */