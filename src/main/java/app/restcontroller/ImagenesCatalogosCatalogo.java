/*     */ package BOOT-INF.classes.app.restcontroller;
/*     */ 
/*     */ import app.entity.ImagenesCatalogoEntity;
/*     */ import app.restcontroller.RestControllerGenericNormalImpl;
/*     */ import app.service.ImagenCatalogoServiceImpl;
/*     */ import app.util.Constantes;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.springframework.data.repository.query.Param;
/*     */ import org.springframework.http.HttpStatus;
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
/*     */ @RestController
/*     */ @RequestMapping({"/RestImagenesCatalogos"})
/*     */ public class ImagenesCatalogosCatalogo
/*     */   extends RestControllerGenericNormalImpl<ImagenesCatalogoEntity, ImagenCatalogoServiceImpl>
/*     */ {
/*     */   @GetMapping({"/listar"})
/*     */   public ResponseEntity<?> getAll(HttpServletRequest request, @Param("draw") int draw, @Param("length") int length, @Param("start") int start, @Param("estado") int estado) throws IOException {
/*  35 */     String total = "";
/*  36 */     Map<String, Object> Data = new HashMap<>();
/*     */     
/*     */     try {
/*  39 */       String search = request.getParameter("search[value]");
/*  40 */       int tot = Constantes.NUM_MAX_DATATABLE.intValue();
/*  41 */       System.out.println("tot:" + tot + "estado:" + estado + "search:" + search + "length:" + length + "start:" + start);
/*  42 */       List<?> lista = ((ImagenCatalogoServiceImpl)this.servicio).findAll(estado, search, length, start);
/*  43 */       System.out.println("listar:" + lista.toString());
/*     */       
/*     */       try {
/*  46 */         total = String.valueOf(((ImagenCatalogoServiceImpl)this.servicio).getTotAll(search, estado));
/*     */       }
/*  48 */       catch (Exception e) {
/*  49 */         total = "0";
/*     */       } 
/*  51 */       Data.put("draw", Integer.valueOf(draw));
/*  52 */       Data.put("recordsTotal", total);
/*  53 */       Data.put("data", lista);
/*  54 */       if (!search.equals("")) {
/*  55 */         Data.put("recordsFiltered", Integer.valueOf(lista.size()));
/*     */       } else {
/*  57 */         Data.put("recordsFiltered", total);
/*     */       } 
/*  59 */       return ResponseEntity.status(HttpStatus.OK).body(Data);
/*  60 */     } catch (Exception e) {
/*  61 */       e.printStackTrace();
/*  62 */       System.out.println(e.getMessage());
/*     */       
/*  64 */       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Data);
/*     */     } 
/*     */   }
/*     */   
/*     */   @PostMapping({"/updateStatus"})
/*     */   public ResponseEntity<?> updateStatus(@RequestBody ImagenesCatalogoEntity entity) {
/*     */     try {
/*  71 */       System.out.println("Entidad:" + entity.toString());
/*  72 */       ((ImagenCatalogoServiceImpl)this.servicio).updateStatus(entity.getEstado().intValue(), entity.getId().intValue());
/*  73 */       ImagenesCatalogoEntity entity2 = (ImagenesCatalogoEntity)((ImagenCatalogoServiceImpl)this.servicio).findById(entity.getId());
/*  74 */       return ResponseEntity.status(HttpStatus.OK).body(entity2);
/*  75 */     } catch (Exception e) {
/*  76 */       System.out.println(e.getMessage());
/*  77 */       e.printStackTrace();
/*  78 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @PostMapping({"/guardar"})
/*     */   public ResponseEntity<?> save(ImagenesCatalogoEntity entidad, @RequestParam("logo") MultipartFile file, @RequestParam("catalogo") MultipartFile catalogo) {
/*  86 */     System.out.println("EntidadSave LLEGO:" + entidad.toString());
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  91 */       return ResponseEntity.status(HttpStatus.OK).body(((ImagenCatalogoServiceImpl)this.servicio).save(entidad));
/*  92 */     } catch (Exception e) {
/*  93 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*     */     } 
/*     */   }
/*     */   @PostMapping({"/modificar/{id}"})
/*     */   public ResponseEntity<?> update(@PathVariable Integer id, ImagenesCatalogoEntity entidad, @RequestParam("logo") MultipartFile file, @RequestParam("catalogo") MultipartFile catalogo) {
/*     */     try {
/*  99 */       System.out.println("EntidadModificar LLEGO:" + entidad.toString());
/* 100 */       return ResponseEntity.status(HttpStatus.OK).body(((ImagenCatalogoServiceImpl)this.servicio).update(id, entidad));
/* 101 */     } catch (Exception e) {
/* 102 */       e.printStackTrace();
/* 103 */       System.out.println(e.getMessage());
/* 104 */       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\ImagenesCatalogosCatalogo.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */