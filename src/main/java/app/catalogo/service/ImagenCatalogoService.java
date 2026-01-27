package app.catalogo.service;

import app.catalogo.entity.ImagenesCatalogoEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface ImagenCatalogoService {
  // CRUD methods
  Optional<ImagenesCatalogoEntity> findById(Integer id) throws Exception;
  
  ImagenesCatalogoEntity save(ImagenesCatalogoEntity entity) throws Exception;
  
  List<ImagenesCatalogoEntity> findAll() throws Exception;
  
  void delete(ImagenesCatalogoEntity entity) throws Exception;
  
  void deleteById(Integer id) throws Exception;
  
  // Custom methods
  int getIdPrimaryKey() throws Exception;
  
  Integer getCodigo() throws Exception;
  
  List<ImagenesCatalogoEntity> findAll(int paramInt1, String paramString, int paramInt2, int paramInt3) throws Exception;
  
  void updateStatus(int paramInt1, int paramInt2) throws Exception;
  
  Integer getTotAll(String paramString, int paramInt) throws Exception;
  
  /**
   * Finds all images of a specific catalog and type
   * @param catalogoId The catalog ID
   * @param tipo The image type (PERFIL, BANNER, GALERIA)
   * @return List of images
   */
  List<ImagenesCatalogoEntity> findByCatalogoAndTipo(Integer catalogoId, String tipo) throws Exception;
  
  /**
   * Saves or updates an image for a catalog with Cloudinary integration
   * @param catalogoId The catalog ID
   * @param tipo The image type (PERFIL, BANNER, GALERIA)
   * @param archivo The image file
   * @return The saved entity
   */
  ImagenesCatalogoEntity guardarImagen(Integer catalogoId, String tipo, MultipartFile archivo) throws Exception;
  
  /**
   * Deletes an image and removes it from Cloudinary
   * @param id The image ID
   */
  void eliminarImagen(Integer id) throws Exception;
  
  /**
   * Validates if a catalog can add more gallery images (max 3)
   * @param catalogoId The catalog ID
   * @return true if can add more, false otherwise
   */
  boolean puedeAgregarImagenGaleria(Integer catalogoId) throws Exception;
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\ImagenCatalogoService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */