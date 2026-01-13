 package app.service;
 
 import app.entity.ImagenesCatalogoEntity;
 import app.repository.GenericRepositoryNormal;
 import app.repository.ImagenCatalogoRepository;
 import app.service.ArchivoService;
 import app.service.GenericServiceImplNormal;
 import app.service.ImagenCatalogoService;
 import app.util.QRCodeGeneratorService;
 import java.io.Serializable;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;


 @Service
 public class ImagenCatalogoServiceImpl
   extends GenericServiceImplNormal<ImagenesCatalogoEntity, Integer>
   implements ImagenCatalogoService
 {
   @Autowired
   private ImagenCatalogoRepository ImagenCatalogoRepository;
   @Autowired
   private ArchivoService archivoService;
   @Autowired
   QRCodeGeneratorService qrCodeGeneratorService;
   @Value("${server.port}")
   private static String puertoservidor;
   
   ImagenCatalogoServiceImpl(GenericRepositoryNormal<ImagenesCatalogoEntity, Integer> genericRepository) {
     super(genericRepository);
   }


   public int getIdPrimaryKey() throws Exception {
     try {
       int id = this.ImagenCatalogoRepository.getIdPrimaryKey();
       return id;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return 0;
     } 
   }


   public Integer getCodigo() throws Exception {
     try {
       int total = this.ImagenCatalogoRepository.getCodigo().intValue();
       return Integer.valueOf(total);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return Integer.valueOf(0);
     } 
   }


   @Transactional
   public List<ImagenesCatalogoEntity> findAll(int estado, String search, int length, int start) throws Exception {
     try {
       List<ImagenesCatalogoEntity> entities = this.ImagenCatalogoRepository.findAll(estado, search, length, start);
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
       this.ImagenCatalogoRepository.updateStatus(Integer.valueOf(status), Integer.valueOf(id));
     }
     catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
       throw new Exception(e.getMessage());
     } 
   }


   public Integer getTotAll(String search, int estado) throws Exception {
     try {
       int total = this.ImagenCatalogoRepository.getTotAll(search, Integer.valueOf(estado)).intValue();
       return Integer.valueOf(total);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return Integer.valueOf(0);
     } 
   }


   @Transactional
   public ImagenesCatalogoEntity update(Integer id, ImagenesCatalogoEntity entidad) throws Exception {
     try {
       System.out.println("Modificar1:" + entidad.toString());


       ImagenesCatalogoEntity entitymod = new ImagenesCatalogoEntity();
       
       entitymod = (ImagenesCatalogoEntity)this.genericRepository.save(entidad);
       return entitymod;
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       throw new Exception(e.getMessage());
     } 
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\ImagenCatalogoServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */