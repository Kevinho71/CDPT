/*     */ package BOOT-INF.classes.app.restcontroller;
/*     */ 
/*     */ import app.config.GoogleDriveConfig;
/*     */ import app.entity.CatalogoEntity;
/*     */ import app.restcontroller.RestControllerGenericNormalImpl;
/*     */ import app.service.ArchivoService;
/*     */ import app.service.CatalogoServiceImpl;
/*     */ import app.util.Constantes;
/*     */ import com.google.api.services.drive.Drive;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Paths;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.io.InputStreamResource;
/*     */ import org.springframework.core.io.Resource;
/*     */ import org.springframework.data.repository.query.Param;
/*     */ import org.springframework.http.HttpStatus;
/*     */ import org.springframework.http.MediaType;
/*     */ import org.springframework.http.ResponseEntity;
/*     */ import org.springframework.web.bind.annotation.GetMapping;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.PostMapping;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @RestController
/*     */ @RequestMapping({"/RestCatalogos"})
/*     */ public class RestCatalogo
/*     */   extends RestControllerGenericNormalImpl<CatalogoEntity, CatalogoServiceImpl>
/*     */ {
/*     */   @Autowired
/*     */   private ArchivoService archivoService;
/*     */   @Autowired
/*     */   private GoogleDriveConfig googleDriveConfig;
/*     */   
/*     */   private Drive getDriveService() throws IOException, GeneralSecurityException {
/*  62 */     return this.googleDriveConfig.getDriveService();
/*     */   }
/*     */   
/*     */   @GetMapping({"/listar"})
/*     */   public ResponseEntity<?> getAll(HttpServletRequest request, @Param("draw") int draw, @Param("length") int length, @Param("start") int start, @Param("estado") int estado) throws IOException {
/*  67 */     String total = "";
/*  68 */     Map<String, Object> Data = new HashMap<>();
/*     */     
/*     */     try {
/*  71 */       String search = request.getParameter("search[value]");
/*  72 */       int tot = Constantes.NUM_MAX_DATATABLE.intValue();
/*  73 */       System.out.println("tot:" + tot + "estado:" + estado + "search:" + search + "length:" + length + "start:" + start);
/*  74 */       List<?> lista = ((CatalogoServiceImpl)this.servicio).findAll(estado, search, length, start);
/*  75 */       System.out.println("listar:" + lista.toString());
/*     */       
/*     */       try {
/*  78 */         total = String.valueOf(((CatalogoServiceImpl)this.servicio).getTotAll(search, estado));
/*     */       }
/*  80 */       catch (Exception e) {
/*  81 */         total = "0";
/*     */       } 
/*  83 */       Data.put("draw", Integer.valueOf(draw));
/*  84 */       Data.put("recordsTotal", total);
/*  85 */       Data.put("data", lista);
/*  86 */       if (!search.equals("")) {
/*  87 */         Data.put("recordsFiltered", Integer.valueOf(lista.size()));
/*     */       } else {
/*  89 */         Data.put("recordsFiltered", total);
/*     */       } 
/*  91 */       return ResponseEntity.status(HttpStatus.OK).body(Data);
/*  92 */     } catch (Exception e) {
/*  93 */       e.printStackTrace();
/*  94 */       System.out.println(e.getMessage());
/*     */       
/*  96 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Data);
/*     */     } 
/*     */   }
/*     */   
/*     */   @PostMapping({"/updateStatus"})
/*     */   public ResponseEntity<?> updateStatus(@RequestBody CatalogoEntity entity) {
/*     */     try {
/* 103 */       System.out.println("Entidad:" + entity.toString());
/* 104 */       ((CatalogoServiceImpl)this.servicio).updateStatus(entity.getEstado().intValue(), entity.getId().intValue());
/* 105 */       CatalogoEntity entity2 = (CatalogoEntity)((CatalogoServiceImpl)this.servicio).findById(entity.getId());
/* 106 */       return ResponseEntity.status(HttpStatus.OK).body(entity2);
/* 107 */     } catch (Exception e) {
/* 108 */       System.out.println(e.getMessage());
/* 109 */       e.printStackTrace();
/* 110 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @PostMapping({"/guardar"})
/*     */   public ResponseEntity<?> save(CatalogoEntity entidad, @RequestParam("logo") MultipartFile file, @RequestParam("catalogo") MultipartFile catalogo) {
/* 118 */     System.out.println("EntidadSave LLEGO:" + entidad.toString());
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 123 */       return ResponseEntity.status(HttpStatus.OK).body(((CatalogoServiceImpl)this.servicio).save(entidad));
/* 124 */     } catch (Exception e) {
/* 125 */       e.printStackTrace();
/* 126 */       System.out.println(e.getMessage());
/*     */       
/* 128 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*     */     } 
/*     */   }
/*     */   @PostMapping({"/modificar/{id}"})
/*     */   public ResponseEntity<?> update(@PathVariable Integer id, CatalogoEntity entidad, @RequestParam("logo") MultipartFile file, @RequestParam("catalogo") MultipartFile catalogo) {
/*     */     try {
/* 134 */       System.out.println("EntidadModificar LLEGO:" + entidad.toString());
/* 135 */       return ResponseEntity.status(HttpStatus.OK).body(((CatalogoServiceImpl)this.servicio).update(id, entidad));
/* 136 */     } catch (Exception e) {
/* 137 */       e.printStackTrace();
/* 138 */       System.out.println(e.getMessage());
/* 139 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*     */     } 
/*     */   }
/*     */   @GetMapping({"/buscar/{id}"})
/*     */   public ResponseEntity<?> buscar(@PathVariable String id) {
/*     */     try {
/* 145 */       System.out.println("ID A BUSCAR");
/* 146 */       CatalogoEntity entity = new CatalogoEntity();
/* 147 */       entity = (CatalogoEntity)((CatalogoServiceImpl)this.servicio).findById(Integer.valueOf(Integer.parseInt(id)));
/* 148 */       if (entity != null) {
/* 149 */         System.out.println("Socio encontrado:" + entity.toString());
/*     */       }
/*     */       
/* 152 */       return ResponseEntity.status(HttpStatus.OK).body(entity);
/* 153 */     } catch (Exception e) {
/* 154 */       System.out.println(e.getMessage());
/* 155 */       e.printStackTrace();
/* 156 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GetMapping({"/logo_empresa/{filename}"})
/*     */   public ResponseEntity<Resource> getFile_logo_empresa(@PathVariable String filename) {
/*     */     try {
/* 299 */       String folderId = this.archivoService.getOrCreateFolder(Constantes.nameFolderLogoCatalogo);
/*     */ 
/*     */       
/* 302 */       String fileId = this.archivoService.obtenerIdArchivoDrivePorNombre(filename, folderId);
/*     */       
/* 304 */       if (fileId != null) {
/*     */         
/* 306 */         Drive driveService = getDriveService();
/*     */ 
/*     */         
/* 309 */         InputStream inputStream = driveService.files().get(fileId).executeMediaAsInputStream();
/* 310 */         InputStreamResource resource = new InputStreamResource(inputStream);
/*     */ 
/*     */         
/* 313 */         String contentType = "application/octet-stream";
/*     */         try {
/* 315 */           contentType = Files.probeContentType(Paths.get(filename, new String[0]));
/* 316 */         } catch (IOException e) {
/* 317 */           System.out.println("No se pudo determinar el tipo de archivo.");
/*     */         } 
/*     */ 
/*     */         
/* 321 */         return ((ResponseEntity.BodyBuilder)ResponseEntity.ok()
/* 322 */           .contentType(MediaType.parseMediaType(contentType))
/* 323 */           .header("Content-Disposition", new String[] { "inline; filename=\"" + filename + "\""
/* 324 */             })).body(resource);
/*     */       } 
/* 326 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
/*     */     }
/* 328 */     catch (Exception e) {
/* 329 */       e.printStackTrace();
/* 330 */       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GetMapping({"/img_catalogos_empresa/{filename}"})
/*     */   public ResponseEntity<Resource> getFileImgCatalogosEmpresa(@PathVariable String filename) {
/*     */     try {
/* 339 */       String folderId = this.archivoService.getOrCreateFolder(Constantes.nameFolderImgCatalogo);
/*     */ 
/*     */       
/* 342 */       String fileId = this.archivoService.obtenerIdArchivoDrivePorNombre(filename, folderId);
/*     */       
/* 344 */       if (fileId != null) {
/*     */         
/* 346 */         Drive driveService = getDriveService();
/*     */ 
/*     */         
/* 349 */         OutputStream outputStream = new ByteArrayOutputStream();
/* 350 */         driveService.files().get(fileId).executeMediaAndDownloadTo(outputStream);
/*     */ 
/*     */         
/* 353 */         ByteArrayInputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream)outputStream).toByteArray());
/* 354 */         InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
/*     */ 
/*     */         
/* 357 */         String contentType = "application/octet-stream";
/*     */         try {
/* 359 */           contentType = Files.probeContentType(Paths.get(filename, new String[0]));
/* 360 */         } catch (IOException e) {
/* 361 */           System.out.println("No se pudo determinar el tipo de archivo.");
/*     */         } 
/*     */ 
/*     */         
/* 365 */         return ((ResponseEntity.BodyBuilder)ResponseEntity.ok()
/* 366 */           .contentType(MediaType.parseMediaType(contentType))
/* 367 */           .header("Content-Disposition", new String[] { "inline; filename=\"" + filename + "\""
/* 368 */             })).body(inputStreamResource);
/*     */       } 
/* 370 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
/*     */     }
/* 372 */     catch (Exception e) {
/* 373 */       e.printStackTrace();
/* 374 */       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\RestCatalogo.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */