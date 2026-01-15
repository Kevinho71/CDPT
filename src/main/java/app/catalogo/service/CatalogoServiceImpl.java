 package app.catalogo.service;
 
 import app.catalogo.entity.CatalogoEntity;
 import app.catalogo.entity.ImagenesCatalogoEntity;
 import app.catalogo.repository.CatalogoRepository;
 import app.common.util.GenericRepositoryNormal;
 import app.catalogo.repository.ImagenCatalogoRepository;
 import app.common.util.ArchivoService;
 import app.catalogo.service.CatalogoService;
 import app.common.util.GenericServiceImplNormal;
 import app.common.util.Constantes;
 import app.common.util.QRCodeGeneratorService;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 import org.springframework.web.multipart.MultipartFile;


 @Service
 public class CatalogoServiceImpl
   extends GenericServiceImplNormal<CatalogoEntity, Integer>
   implements CatalogoService
 {
   @Autowired
   private CatalogoRepository catalogoRepository;
   @Autowired
   private ImagenCatalogoRepository ImagenCatalogoRepository;
   @Autowired
   private ArchivoService archivoService;
   @Autowired
   QRCodeGeneratorService qrCodeGeneratorService;
   @Value("${server.port}")
   private static String puertoservidor;
   
   CatalogoServiceImpl(GenericRepositoryNormal<CatalogoEntity, Integer> genericRepository) {
     super(genericRepository);
   }


   public int getIdPrimaryKey() throws Exception {
     try {
       int id = this.catalogoRepository.getIdPrimaryKey();
       return id;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return 0;
     } 
   }


   public Integer getCodigo() throws Exception {
     try {
       int total = this.catalogoRepository.getCodigo().intValue();
       return Integer.valueOf(total);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return Integer.valueOf(0);
     } 
   }


   @Transactional
   public List<CatalogoEntity> findAll(int estado, String search, int length, int start) throws Exception {
     try {
       List<CatalogoEntity> entities = this.catalogoRepository.findAll(estado, search, length, start);
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
       this.catalogoRepository.updateStatus(Integer.valueOf(status), Integer.valueOf(id));
     }
     catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
       throw new Exception(e.getMessage());
     } 
   }


   public Integer getTotAll(String search, int estado) throws Exception {
     try {
       int total = this.catalogoRepository.getTotAll(search, Integer.valueOf(estado)).intValue();
       return Integer.valueOf(total);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return Integer.valueOf(0);
     } 
   }


   @Transactional
   public CatalogoEntity save(CatalogoEntity entity) throws Exception {
     try {
       System.out.println("EntitySAVE_Servicio:" + entity.toString());
       
       List<ImagenesCatalogoEntity> array_imagenes_catalogo = new ArrayList<>();


       if (entity.getCatalogo() != null && !entity.getCatalogo().isEmpty()) {
         for (int i = 0; i < entity.getCatalogo().size(); i++) {
           ImagenesCatalogoEntity imagenesCatalogoEntity = new ImagenesCatalogoEntity();
           imagenesCatalogoEntity.setId(Integer.valueOf(this.ImagenCatalogoRepository.getIdPrimaryKey()));
           imagenesCatalogoEntity.setCodigo(this.ImagenCatalogoRepository.getCodigo());
           
           MultipartFile catalogoitem = entity.getCatalogo().get(i);
           System.out.println("CATALOGO:" + catalogoitem.getOriginalFilename());


           String nombreLocal = entity.getNit() + " - " + entity.getNit() + this.ImagenCatalogoRepository.getCodigo();


           imagenesCatalogoEntity.setImagen(nombreLocal);


           this.archivoService.guargarArchivo(Constantes.nameFolderImgCatalogo, catalogoitem, nombreLocal);
           
           imagenesCatalogoEntity.setEstado(Integer.valueOf(1));
           
           ImagenesCatalogoEntity imagenesCatalogoEntity2 = (ImagenesCatalogoEntity)this.ImagenCatalogoRepository.save(imagenesCatalogoEntity);
           array_imagenes_catalogo.add(imagenesCatalogoEntity2);
         } 
       }


       entity.setImagenesCatalogos(array_imagenes_catalogo);
       System.out.println("IMAGENES DE CATALOGO ASIGNADOS");
       entity.setId(Integer.valueOf(this.catalogoRepository.getIdPrimaryKey()));
       entity.setCodigo(this.catalogoRepository.getCodigo());


       if (!entity.getLogo().isEmpty()) {
         
         String nombreLocal = entity.getNit() + entity.getNit();


         entity.setNombrelogo(nombreLocal);


         this.archivoService.guargarArchivo(Constantes.nameFolderLogoCatalogo, entity.getLogo(), nombreLocal);
       } 
       
       System.out.println("EntityPost:" + entity.toString());
       entity = (CatalogoEntity)this.catalogoRepository.save(entity);
       return entity;
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(" catalogo service save err:" + e.getMessage());
       
       throw new Exception("Error al guardar el catálogo: " + e.getMessage(), e);
     } 
   }


   @Transactional
   public CatalogoEntity update(Integer id, CatalogoEntity entity) throws Exception {
     try {
       System.out.println("Modificar Entity Service: " + entity.toString());
       
       CatalogoEntity catalogoEntity2 = this.catalogoRepository.findById(id).get();
       System.out.println("CATALOGO BD: " + catalogoEntity2.toString());
       List<ImagenesCatalogoEntity> array_imagenes_catalogo = new ArrayList<>();


       if (entity.getCatalogo() != null && 
         !entity.getCatalogo().isEmpty()) {
         
         for (ImagenesCatalogoEntity imgEntity : catalogoEntity2.getImagenesCatalogos()) {
           String nombreImagen = imgEntity.getImagen();


           this.archivoService.eliminarArchivo(Constantes.nameFolderImgCatalogo, nombreImagen);
           this.ImagenCatalogoRepository.delete(imgEntity);
         } 
         
         System.out.println("LISTA CATALOGOS TAM: " + entity.getCatalogo().size());
         
         for (MultipartFile catalogoitem : entity.getCatalogo()) {
           ImagenesCatalogoEntity imagenesCatalogoEntity = new ImagenesCatalogoEntity();
           imagenesCatalogoEntity.setId(Integer.valueOf(this.ImagenCatalogoRepository.getIdPrimaryKey()));
           imagenesCatalogoEntity.setCodigo(this.ImagenCatalogoRepository.getCodigo());
           
           String nombre = catalogoEntity2.getNit() + "-" + catalogoEntity2.getNit() + this.ImagenCatalogoRepository.getCodigo();
           System.out.println("NOMBRECATALOGOLOG: " + nombre);


           imagenesCatalogoEntity.setImagen(nombre);
           
           this.archivoService.guargarArchivo(Constantes.nameFolderImgCatalogo, catalogoitem, nombre);


           imagenesCatalogoEntity.setEstado(Integer.valueOf(1));
           
           ImagenesCatalogoEntity imagenesCatalogoEntity2 = (ImagenesCatalogoEntity)this.ImagenCatalogoRepository.save(imagenesCatalogoEntity);
           array_imagenes_catalogo.add(imagenesCatalogoEntity2);
         } 
       } 


       entity.setImagenesCatalogos(array_imagenes_catalogo);


       if (!entity.getLogo().isEmpty()) {
         
         this.archivoService.eliminarArchivo(Constantes.nameFolderLogoCatalogo, catalogoEntity2.getNombrelogo());
         
         String nombre = catalogoEntity2.getNit() + catalogoEntity2.getNit();


         entity.setNombrelogo(nombre);
         
         this.archivoService.guargarArchivo(Constantes.nameFolderLogoCatalogo, entity.getLogo(), nombre);
       } 


       entity = (CatalogoEntity)this.genericRepository.save(entity);
       return entity;
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       throw new Exception(e.getMessage());
     } 
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\CatalogoServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */