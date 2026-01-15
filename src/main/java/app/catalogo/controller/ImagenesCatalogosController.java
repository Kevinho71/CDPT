 package app.catalogo.controller;
 
 import app.catalogo.entity.ImagenesCatalogoEntity;
 import app.common.util.RestControllerGenericNormalImpl;
 import app.catalogo.service.ImagenCatalogoServiceImpl;
 import app.common.util.Constantes;
 import java.io.IOException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import jakarta.servlet.http.HttpServletRequest;
 import org.springframework.data.repository.query.Param;
 import org.springframework.http.HttpStatus;
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
@RequestMapping({"/RestImagenesCatalogos"})
public class ImagenesCatalogosController
   extends RestControllerGenericNormalImpl<ImagenesCatalogoEntity, ImagenCatalogoServiceImpl>
 {
   @GetMapping({"/listar"})
   public ResponseEntity<?> getAll(HttpServletRequest request, @Param("draw") int draw, @Param("length") int length, @Param("start") int start, @Param("estado") int estado) throws IOException {
     String total = "";
     Map<String, Object> Data = new HashMap<>();
     
     try {
       String search = request.getParameter("search[value]");
       int tot = Constantes.NUM_MAX_DATATABLE.intValue();
       System.out.println("tot:" + tot + "estado:" + estado + "search:" + search + "length:" + length + "start:" + start);
       List<?> lista = ((ImagenCatalogoServiceImpl)this.servicio).findAll(estado, search, length, start);
       System.out.println("listar:" + lista.toString());
       
       try {
         total = String.valueOf(((ImagenCatalogoServiceImpl)this.servicio).getTotAll(search, estado));
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
   public ResponseEntity<?> updateStatus(@RequestBody ImagenesCatalogoEntity entity) {
     try {
       System.out.println("Entidad:" + entity.toString());
       ((ImagenCatalogoServiceImpl)this.servicio).updateStatus(entity.getEstado().intValue(), entity.getId().intValue());
       ImagenesCatalogoEntity entity2 = (ImagenesCatalogoEntity)((ImagenCatalogoServiceImpl)this.servicio).findById(entity.getId());
       return ResponseEntity.status(HttpStatus.OK).body(entity2);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
     } 
   }


   @PostMapping({"/guardar"})
   public ResponseEntity<?> save(ImagenesCatalogoEntity entidad, @RequestParam("logo") MultipartFile file, @RequestParam("catalogo") MultipartFile catalogo) {
     System.out.println("EntidadSave LLEGO:" + entidad.toString());


     try {
       return ResponseEntity.status(HttpStatus.OK).body(((ImagenCatalogoServiceImpl)this.servicio).save(entidad));
     } catch (Exception e) {
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
     } 
   }
   @PostMapping({"/modificar/{id}"})
   public ResponseEntity<?> update(@PathVariable Integer id, ImagenesCatalogoEntity entidad, @RequestParam("logo") MultipartFile file, @RequestParam("catalogo") MultipartFile catalogo) {
     try {
       System.out.println("EntidadModificar LLEGO:" + entidad.toString());
       return ResponseEntity.status(HttpStatus.OK).body(((ImagenCatalogoServiceImpl)this.servicio).update(id, entidad));
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, Por favor intente mas tarde. \"}");
     } 
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\ImagenesCatalogosCatalogo.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */