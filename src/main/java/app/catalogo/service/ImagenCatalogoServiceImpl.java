 package app.catalogo.service;
 
 import app.catalogo.entity.ImagenesCatalogoEntity;
 import app.common.util.GenericRepositoryNormal;
 import app.catalogo.repository.ImagenCatalogoRepository;
 import app.common.util.ArchivoService;
 import app.common.util.GenericServiceImplNormal;
 import app.catalogo.service.ImagenCatalogoService;
 import java.io.Serializable;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 import org.springframework.web.multipart.MultipartFile;


 @Service
 public class ImagenCatalogoServiceImpl
   extends GenericServiceImplNormal<ImagenesCatalogoEntity, Integer>
   implements ImagenCatalogoService
 {
   @Autowired
   private ImagenCatalogoRepository ImagenCatalogoRepository;
   @Autowired
   private ArchivoService archivoService;
   
   // Cloudinary folder names
   private static final String FOLDER_EMPRESA_LOGO = "EMPRESA_LOGO";
   private static final String FOLDER_EMPRESA_BANNER = "EMPRESA_BANNER";
   private static final String FOLDER_EMPRESA_GALERIA = "EMPRESA_GALERIA";
   
   // Image type constants
   private static final String TIPO_PERFIL = "PERFIL";
   private static final String TIPO_BANNER = "BANNER";
   private static final String TIPO_GALERIA = "GALERIA";
   
   private static final int MAX_GALERIA_IMAGES = 3;
   
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
   
   @Override
   @Transactional
   public List<ImagenesCatalogoEntity> findByCatalogoAndTipo(Integer catalogoId, String tipo) throws Exception {
     try {
       return this.ImagenCatalogoRepository.findByCatalogoAndTipo(catalogoId, tipo);
     } catch (Exception e) {
       System.err.println("Error al buscar imágenes por catálogo y tipo: " + e.getMessage());
       throw new Exception("Error al buscar imágenes: " + e.getMessage());
     }
   }
   
   @Override
   @Transactional
   public ImagenesCatalogoEntity guardarImagen(Integer catalogoId, String tipo, MultipartFile archivo) throws Exception {
     try {
       if (archivo == null || archivo.isEmpty()) {
         throw new Exception("El archivo de imagen está vacío");
       }
       
       // Validate gallery image limit
       if (TIPO_GALERIA.equals(tipo) && !puedeAgregarImagenGaleria(catalogoId)) {
         throw new Exception("No se pueden agregar más de " + MAX_GALERIA_IMAGES + " imágenes a la galería");
       }
       
       // Determine the Cloudinary folder based on tipo
       String folder = getFolderByTipo(tipo);
       
       // Find existing image of same type (for PERFIL and BANNER, delete old one)
       List<ImagenesCatalogoEntity> existingImages = findByCatalogoAndTipo(catalogoId, tipo);
       
       if (!TIPO_GALERIA.equals(tipo) && !existingImages.isEmpty()) {
         // For PERFIL and BANNER, delete the old image before uploading new one
         ImagenesCatalogoEntity oldImage = existingImages.get(0);
         if (oldImage.getImagen() != null && !oldImage.getImagen().isEmpty()) {
           try {
             archivoService.eliminarImagen(oldImage.getImagen());
           } catch (Exception e) {
             System.err.println("Error al eliminar imagen anterior: " + e.getMessage());
           }
         }
       }
       
       // Generate public_id for Cloudinary
       String publicId = "empresa_" + catalogoId + "_" + tipo.toLowerCase() + "_" + System.currentTimeMillis();
       
       // Upload to Cloudinary
       String cloudinaryPublicId = archivoService.subirImagen(folder, archivo, publicId);
       
       // Create or update entity
       ImagenesCatalogoEntity imagenEntity;
       
       if (!TIPO_GALERIA.equals(tipo) && !existingImages.isEmpty()) {
         // Update existing PERFIL or BANNER
         imagenEntity = existingImages.get(0);
         imagenEntity.setImagen(cloudinaryPublicId);
       } else {
         // Create new entity for GALERIA or first PERFIL/BANNER
         imagenEntity = new ImagenesCatalogoEntity();
         imagenEntity.setId(getIdPrimaryKey());
         imagenEntity.setCodigo(getCodigo());
         imagenEntity.setImagen(cloudinaryPublicId);
         imagenEntity.setTipo(tipo);
         imagenEntity.setEstado(1);
       }
       
       // Save to database
       return (ImagenesCatalogoEntity) this.genericRepository.save(imagenEntity);
       
     } catch (Exception e) {
       System.err.println("Error al guardar imagen: " + e.getMessage());
       e.printStackTrace();
       throw new Exception("Error al guardar imagen: " + e.getMessage());
     }
   }
   
   @Override
   @Transactional
   public void eliminarImagen(Integer id) throws Exception {
     try {
       ImagenesCatalogoEntity imagen = (ImagenesCatalogoEntity) this.genericRepository.findById(id)
         .orElseThrow(() -> new Exception("Imagen no encontrada con id: " + id));
       
       // Delete from Cloudinary
       if (imagen.getImagen() != null && !imagen.getImagen().isEmpty()) {
         try {
           archivoService.eliminarImagen(imagen.getImagen());
         } catch (Exception e) {
           System.err.println("Error al eliminar de Cloudinary: " + e.getMessage());
         }
       }
       
       // Delete from database
       this.genericRepository.deleteById(id);
       
     } catch (Exception e) {
       System.err.println("Error al eliminar imagen: " + e.getMessage());
       throw new Exception("Error al eliminar imagen: " + e.getMessage());
     }
   }
   
   @Override
   public boolean puedeAgregarImagenGaleria(Integer catalogoId) throws Exception {
     try {
       Integer count = this.ImagenCatalogoRepository.countGaleriaImagesByCatalogo(catalogoId);
       return count < MAX_GALERIA_IMAGES;
     } catch (Exception e) {
       System.err.println("Error al verificar límite de galería: " + e.getMessage());
       return false;
     }
   }
   
   /**
    * Determines the Cloudinary folder based on image type
    */
   private String getFolderByTipo(String tipo) {
     switch (tipo) {
       case TIPO_PERFIL:
         return FOLDER_EMPRESA_LOGO;
       case TIPO_BANNER:
         return FOLDER_EMPRESA_BANNER;
       case TIPO_GALERIA:
         return FOLDER_EMPRESA_GALERIA;
       default:
         return FOLDER_EMPRESA_LOGO;
     }
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\ImagenCatalogoServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */