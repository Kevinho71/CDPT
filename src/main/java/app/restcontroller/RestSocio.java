/*     */ package BOOT-INF.classes.app.restcontroller;
/*     */ 
/*     */ import app.config.GoogleDriveConfig;
/*     */ import app.dto.SocioDTO;
/*     */ import app.entity.PersonaEntity;
/*     */ import app.entity.SocioEntity;
/*     */ import app.restcontroller.RestControllerGenericNormalImpl;
/*     */ import app.service.ArchivoService;
/*     */ import app.service.SocioServiceImpl;
/*     */ import app.util.Constantes;
/*     */ import com.google.api.services.drive.Drive;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Serializable;
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
/*     */ import org.springframework.web.bind.annotation.PutMapping;
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
/*     */ @RestController
/*     */ @RequestMapping({"/RestSocios"})
/*     */ public class RestSocio
/*     */   extends RestControllerGenericNormalImpl<SocioEntity, SocioServiceImpl>
/*     */ {
/*     */   @Autowired
/*     */   private ArchivoService archivoService;
/*     */   @Autowired
/*     */   private GoogleDriveConfig googleDriveConfig;
/*     */   
/*     */   private Drive getDriveService() throws IOException, GeneralSecurityException {
/*  60 */     return this.googleDriveConfig.getDriveService();
/*     */   }
/*     */ 
/*     */   
/*     */   @GetMapping({"/listar"})
/*     */   public ResponseEntity<?> getAll(HttpServletRequest request, @Param("draw") int draw, @Param("length") int length, @Param("start") int start, @Param("estado") int estado) throws IOException {
/*  66 */     String total = "";
/*  67 */     Map<String, Object> Data = new HashMap<>();
/*     */     try {
/*  69 */       System.out.println("***************MODIFICANDO CODIGO V2 LISTANDO *****************************************");
/*  70 */       String search = request.getParameter("search[value]");
/*  71 */       int tot = Constantes.NUM_MAX_DATATABLE.intValue();
/*  72 */       System.out.println("tot:" + tot + "estado:" + estado + "search:" + search + "length:" + length + "start:" + start);
/*  73 */       List<?> lista = ((SocioServiceImpl)this.servicio).findAll(estado, search, length, start);
/*  74 */       System.out.println("listar:" + lista.toString());
/*     */       
/*     */       try {
/*  77 */         total = String.valueOf(((SocioServiceImpl)this.servicio).getTotAll(search, estado));
/*     */       }
/*  79 */       catch (Exception e) {
/*  80 */         total = "0";
/*     */       } 
/*  82 */       Data.put("draw", Integer.valueOf(draw));
/*  83 */       Data.put("recordsTotal", total);
/*  84 */       Data.put("data", lista);
/*  85 */       if (!search.equals("")) {
/*  86 */         Data.put("recordsFiltered", Integer.valueOf(lista.size()));
/*     */       } else {
/*  88 */         Data.put("recordsFiltered", total);
/*  89 */       }  System.out.println("***************MODIFICANDO CODIGO V2 LISTANDO *****************************************");
/*  90 */       return ResponseEntity.status(HttpStatus.OK).body(Data);
/*  91 */     } catch (Exception e) {
/*  92 */       e.printStackTrace();
/*  93 */       System.out.println(e.getMessage());
/*     */       
/*  95 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Data);
/*     */     } 
/*     */   }
/*     */   
/*     */   @PostMapping({"/guardar"})
/*     */   public ResponseEntity<?> save(SocioDTO socioDTO, @RequestParam("logo") MultipartFile file) {
/* 101 */     System.out.println("EntidadSave LLEGO:" + socioDTO.toString());
/*     */     
/*     */     try {
/* 104 */       PersonaEntity personaEntity = new PersonaEntity();
/* 105 */       personaEntity.setCi(socioDTO.getCi());
/* 106 */       personaEntity.setNombrecompleto(socioDTO.getNombrecompleto());
/* 107 */       personaEntity.setEmail(socioDTO.getEmail());
/* 108 */       personaEntity.setCelular(socioDTO.getCelular());
/* 109 */       personaEntity.setEstado(Integer.valueOf(1));
/*     */       
/* 111 */       SocioEntity socioEntity = new SocioEntity();
/* 112 */       socioEntity.setNrodocumento(socioDTO.getNrodocumento());
/* 113 */       socioEntity.setMatricula(socioDTO.getMatricula());
/* 114 */       socioEntity.setNombresocio(socioDTO.getNombresocio());
/* 115 */       socioEntity.setFechaemision(socioDTO.getFechaemision());
/* 116 */       socioEntity.setFechaexpiracion(socioDTO.getFechaexpiracion());
/* 117 */       socioEntity.setEstado(Integer.valueOf(1));
/* 118 */       socioEntity.setLejendario(socioDTO.getLejendario());
/*     */       
/* 120 */       socioEntity.setPersona(personaEntity);
/* 121 */       socioEntity.setLogo(socioDTO.getLogo());
/*     */ 
/*     */       
/* 124 */       System.out.println("***************************LOGOO:" + socioEntity.getLogo().getOriginalFilename());
/* 125 */       System.out.println("***************************LEGENDARIO:" + socioDTO.getLejendario());
/*     */       
/* 127 */       return ResponseEntity.status(HttpStatus.OK).body(((SocioServiceImpl)this.servicio).save(socioEntity));
/* 128 */     } catch (Exception e) {
/* 129 */       e.printStackTrace();
/* 130 */       System.out.println(e.getMessage());
/* 131 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @PutMapping({"/catalogos/{id}"})
/*     */   public ResponseEntity<?> update(@PathVariable Integer id, SocioEntity entidad) {
/* 138 */     System.out.println("EntidadModificar LLEGO:" + entidad.toString());
/*     */ 
/*     */     
/*     */     try {
/* 142 */       return ResponseEntity.status(HttpStatus.OK).body(((SocioServiceImpl)this.servicio).updatecatalogos(id, entidad));
/* 143 */     } catch (Exception e) {
/* 144 */       e.printStackTrace();
/* 145 */       System.out.println(e.getMessage());
/* 146 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @PostMapping({"/modificar/{id}"})
/*     */   public ResponseEntity<?> update(@PathVariable Integer id, SocioDTO socioDTO, @RequestParam("logo") MultipartFile file) {
/* 153 */     System.out.println("EntidadModificar LLEGO:" + socioDTO.toString());
/*     */     
/*     */     try {
/* 156 */       PersonaEntity personaEntity = new PersonaEntity();
/* 157 */       personaEntity.setCi(socioDTO.getCi());
/* 158 */       personaEntity.setCelular(socioDTO.getCelular());
/* 159 */       personaEntity.setEstado(Integer.valueOf(1));
/*     */       
/* 161 */       SocioEntity socioEntity = new SocioEntity();
/* 162 */       socioEntity.setId(socioDTO.getId());
/* 163 */       socioEntity.setCodigo(socioDTO.getCodigo());
/* 164 */       socioEntity.setMatricula(socioDTO.getMatricula());
/* 165 */       socioEntity.setNombresocio(socioDTO.getNombresocio());
/* 166 */       socioEntity.setFechaemision(socioDTO.getFechaemision());
/* 167 */       socioEntity.setFechaexpiracion(socioDTO.getFechaexpiracion());
/* 168 */       socioEntity.setEstado(Integer.valueOf(1));
/* 169 */       socioEntity.setLejendario(socioDTO.getLejendario());
/*     */       
/* 171 */       socioEntity.setPersona(personaEntity);
/* 172 */       socioEntity.setLogo(socioDTO.getLogo());
/*     */ 
/*     */       
/* 175 */       return ResponseEntity.status(HttpStatus.OK).body(((SocioServiceImpl)this.servicio).update(id, socioEntity));
/* 176 */     } catch (Exception e) {
/* 177 */       e.printStackTrace();
/* 178 */       System.out.println(e.getMessage());
/* 179 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*     */     } 
/*     */   }
/*     */   @PostMapping({"/updateStatus"})
/*     */   public ResponseEntity<?> updateStatus(@RequestBody SocioEntity entity) {
/*     */     try {
/* 185 */       System.out.println("Entidad:" + entity.toString());
/* 186 */       ((SocioServiceImpl)this.servicio).updateStatus(entity.getEstado().intValue(), entity.getId().intValue());
/* 187 */       SocioEntity entity2 = (SocioEntity)((SocioServiceImpl)this.servicio).findById(entity.getId());
/* 188 */       return ResponseEntity.status(HttpStatus.OK).body(entity2);
/* 189 */     } catch (Exception e) {
/* 190 */       System.out.println(e.getMessage());
/* 191 */       e.printStackTrace();
/* 192 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
/*     */     } 
/*     */   }
/*     */   @GetMapping({"/findByNrodocumento/{id}"})
/*     */   public ResponseEntity<?> buscar(@PathVariable String id) {
/*     */     try {
/* 198 */       System.out.println("ID A BUSCAR");
/* 199 */       SocioEntity entity = new SocioEntity();
/* 200 */       entity = ((SocioServiceImpl)this.servicio).findByNrodocumento(id);
/* 201 */       if (entity != null) {
/* 202 */         System.out.println("Socio encontrado:" + entity.toString());
/*     */       }
/*     */       
/* 205 */       return ResponseEntity.status(HttpStatus.OK).body(entity);
/* 206 */     } catch (Exception e) {
/* 207 */       System.out.println(e.getMessage());
/* 208 */       e.printStackTrace();
/* 209 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
/*     */     } 
/*     */   }
/*     */   
/*     */   @PostMapping({"modificarqr/{id}"})
/*     */   public ResponseEntity<?> modificarqr(@PathVariable Integer id, @RequestBody SocioEntity entidad) {
/*     */     try {
/* 216 */       return ResponseEntity.status(HttpStatus.OK).body(((SocioServiceImpl)this.servicio).renovarQR(id, entidad));
/* 217 */     } catch (Exception e) {
/* 218 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
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
/*     */   @GetMapping({"/logo_socio/{filename}"})
/*     */   public ResponseEntity<Resource> getFile_logo_socio_drive(@PathVariable String filename) {
/*     */     try {
/* 242 */       String folderId = this.archivoService.getOrCreateFolder(Constantes.nameFolderLogoSocio);
/*     */ 
/*     */       
/* 245 */       String fileId = this.archivoService.obtenerIdArchivoDrivePorNombre(filename, folderId);
/*     */       
/* 247 */       if (fileId != null) {
/*     */         
/* 249 */         Drive driveService = getDriveService();
/*     */ 
/*     */         
/* 252 */         InputStream inputStream = driveService.files().get(fileId).executeMediaAsInputStream();
/* 253 */         InputStreamResource resource = new InputStreamResource(inputStream);
/*     */ 
/*     */         
/* 256 */         String contentType = "application/octet-stream";
/*     */         try {
/* 258 */           contentType = Files.probeContentType(Paths.get(filename, new String[0]));
/* 259 */         } catch (IOException e) {
/* 260 */           System.out.println("No se pudo determinar el tipo de archivo.");
/*     */         } 
/*     */ 
/*     */         
/* 264 */         return ((ResponseEntity.BodyBuilder)ResponseEntity.ok()
/* 265 */           .contentType(MediaType.parseMediaType(contentType))
/* 266 */           .header("Content-Disposition", new String[] { "inline; filename=\"" + filename + "\""
/* 267 */             })).body(resource);
/*     */       } 
/* 269 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
/*     */     }
/* 271 */     catch (Exception e) {
/* 272 */       e.printStackTrace();
/* 273 */       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
/*     */   @GetMapping({"/qr_socio/{filename}"})
/*     */   public ResponseEntity<Resource> qr_socio(@PathVariable String filename) {
/*     */     try {
/* 379 */       System.out.println("filenameQR:" + filename);
/*     */       
/* 381 */       String folderId = this.archivoService.getOrCreateFolder(Constantes.nameFolderQrSocio);
/*     */ 
/*     */       
/* 384 */       String fileId = this.archivoService.obtenerIdArchivoDrivePorNombre(filename, folderId);
/*     */       
/* 386 */       if (fileId != null) {
/*     */         
/* 388 */         Drive driveService = getDriveService();
/*     */ 
/*     */         
/* 391 */         InputStream inputStream = driveService.files().get(fileId).executeMediaAsInputStream();
/* 392 */         InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
/*     */ 
/*     */         
/* 395 */         String contentType = "application/octet-stream";
/*     */         try {
/* 397 */           contentType = Files.probeContentType(Paths.get(filename, new String[0]));
/* 398 */         } catch (IOException e) {
/* 399 */           System.out.println("No se pudo determinar el tipo de archivo.");
/*     */         } 
/*     */ 
/*     */         
/* 403 */         return ((ResponseEntity.BodyBuilder)ResponseEntity.ok()
/* 404 */           .contentType(MediaType.parseMediaType(contentType))
/* 405 */           .header("Content-Disposition", new String[] { "inline; filename=\"" + filename + "\""
/* 406 */             })).body(inputStreamResource);
/*     */       } 
/* 408 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
/*     */     }
/* 410 */     catch (Exception e) {
/* 411 */       e.printStackTrace();
/* 412 */       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\RestSocio.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */