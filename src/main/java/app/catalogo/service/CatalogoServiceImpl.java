 package app.catalogo.service;
 
 import app.catalogo.dto.CatalogoDTO;
 import app.catalogo.dto.CatalogoResponseDTO;
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
 import app.common.exception.*;
 import com.fasterxml.jackson.core.type.TypeReference;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.stream.Collectors;
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
   
   // ==================== NUEVOS MÉTODOS CON DTOs ====================
   
   private final ObjectMapper objectMapper = new ObjectMapper();
   
   /**
    * Crea un nuevo catálogo
    */
   @Override
   @Transactional
   public CatalogoResponseDTO create(CatalogoDTO dto, MultipartFile logo, List<MultipartFile> imagenes) {
       // 1. Validar datos
       validateData(dto);
       
       // 2. Crear entidad
       CatalogoEntity catalogo = new CatalogoEntity();
       try {
           catalogo.setId(catalogoRepository.getIdPrimaryKey());
           catalogo.setCodigo(catalogoRepository.getCodigo());
       } catch (Exception e) {
           throw new RuntimeException("Error al obtener ID de catálogo", e);
       }
       
       catalogo.setNit(dto.getNit());
       catalogo.setNombre(dto.getNombre());
       catalogo.setDescripcion(dto.getDescripcion());
       catalogo.setDireccion(dto.getDireccion());
       catalogo.setTipo(dto.getTipo());
       catalogo.setLongitud(dto.getLongitud());
       catalogo.setLatitud(dto.getLatitud());
       catalogo.setEstado(dto.getEstado());
       
       // 3. Convertir List<String> descuentos a JSON
       try {
           if (dto.getDescuentos() != null && !dto.getDescuentos().isEmpty()) {
               String descuentoJson = objectMapper.writeValueAsString(dto.getDescuentos());
               catalogo.setDescuento(descuentoJson);
           }
       } catch (Exception e) {
           throw new InvalidDataException("Error al procesar descuentos: " + e.getMessage());
       }
       
       // 4. Procesar logo si existe
       if (logo != null && !logo.isEmpty()) {
           try {
               String nombreLogo = dto.getNit() + "-" + dto.getNit();
               catalogo.setNombrelogo(nombreLogo);
               archivoService.guargarArchivo(Constantes.nameFolderLogoCatalogo, logo, nombreLogo);
           } catch (Exception e) {
               throw new FileStorageException("Error al guardar el logo", e);
           }
       }
       
       // 5. Procesar imágenes adicionales si existen
       List<ImagenesCatalogoEntity> imagenesEntities = new ArrayList<>();
       if (imagenes != null && !imagenes.isEmpty()) {
           for (MultipartFile imagen : imagenes) {
               try {
                   ImagenesCatalogoEntity imagenEntity = new ImagenesCatalogoEntity();
                   imagenEntity.setId(ImagenCatalogoRepository.getIdPrimaryKey());
                   imagenEntity.setCodigo(ImagenCatalogoRepository.getCodigo());
                   
                   String nombreImagen = dto.getNit() + "-" + imagenEntity.getCodigo();
                   imagenEntity.setImagen(nombreImagen);
                   imagenEntity.setEstado(1);
                   
                   archivoService.guargarArchivo(Constantes.nameFolderImgCatalogo, imagen, nombreImagen);
                   imagenEntity = ImagenCatalogoRepository.save(imagenEntity);
                   imagenesEntities.add(imagenEntity);
               } catch (Exception e) {
                   throw new FileStorageException("Error al guardar imágenes del catálogo", e);
               }
           }
       }
       catalogo.setImagenesCatalogos(imagenesEntities);
       
       // 6. Guardar catálogo
       catalogo = catalogoRepository.save(catalogo);
       return toResponseDTO(catalogo);
   }
   
   /**
    * Actualiza un catálogo existente
    */
   @Override
   @Transactional
   public CatalogoResponseDTO updateCatalogo(Integer id, CatalogoDTO dto, MultipartFile logo, List<MultipartFile> imagenes) {
       // 1. Buscar catálogo existente
       CatalogoEntity catalogo = catalogoRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Catálogo", "id", id));
       
       // 2. Validar datos
       validateData(dto);
       
       // 3. Actualizar datos básicos
       catalogo.setNit(dto.getNit());
       catalogo.setNombre(dto.getNombre());
       catalogo.setDescripcion(dto.getDescripcion());
       catalogo.setDireccion(dto.getDireccion());
       catalogo.setTipo(dto.getTipo());
       catalogo.setLongitud(dto.getLongitud());
       catalogo.setLatitud(dto.getLatitud());
       catalogo.setEstado(dto.getEstado());
       
       // 4. Actualizar descuentos JSON
       try {
           if (dto.getDescuentos() != null && !dto.getDescuentos().isEmpty()) {
               String descuentoJson = objectMapper.writeValueAsString(dto.getDescuentos());
               catalogo.setDescuento(descuentoJson);
           } else {
               catalogo.setDescuento(null);
           }
       } catch (Exception e) {
           throw new InvalidDataException("Error al procesar descuentos: " + e.getMessage());
       }
       
       // 5. Actualizar logo si se proporciona uno nuevo
       if (logo != null && !logo.isEmpty()) {
           try {
               // Eliminar logo anterior
               if (catalogo.getNombrelogo() != null) {
                   archivoService.eliminarArchivo(Constantes.nameFolderLogoCatalogo, catalogo.getNombrelogo());
               }
               
               String nombreLogo = dto.getNit() + "-" + dto.getNit();
               catalogo.setNombrelogo(nombreLogo);
               archivoService.guargarArchivo(Constantes.nameFolderLogoCatalogo, logo, nombreLogo);
           } catch (Exception e) {
               throw new FileStorageException("Error al actualizar el logo", e);
           }
       }
       
       // 6. Actualizar imágenes si se proporcionan nuevas
       if (imagenes != null && !imagenes.isEmpty()) {
           try {
               // Eliminar imágenes anteriores
               for (ImagenesCatalogoEntity img : catalogo.getImagenesCatalogos()) {
                   archivoService.eliminarArchivo(Constantes.nameFolderImgCatalogo, img.getImagen());
                   ImagenCatalogoRepository.delete(img);
               }
               
               // Agregar nuevas imágenes
               List<ImagenesCatalogoEntity> nuevasImagenes = new ArrayList<>();
               for (MultipartFile imagen : imagenes) {
                   ImagenesCatalogoEntity imagenEntity = new ImagenesCatalogoEntity();
                   imagenEntity.setId(ImagenCatalogoRepository.getIdPrimaryKey());
                   imagenEntity.setCodigo(ImagenCatalogoRepository.getCodigo());
                   
                   String nombreImagen = dto.getNit() + "-" + imagenEntity.getCodigo();
                   imagenEntity.setImagen(nombreImagen);
                   imagenEntity.setEstado(1);
                   
                   archivoService.guargarArchivo(Constantes.nameFolderImgCatalogo, imagen, nombreImagen);
                   imagenEntity = ImagenCatalogoRepository.save(imagenEntity);
                   nuevasImagenes.add(imagenEntity);
               }
               catalogo.setImagenesCatalogos(nuevasImagenes);
           } catch (Exception e) {
               throw new FileStorageException("Error al actualizar imágenes del catálogo", e);
           }
       }
       
       // 7. Guardar cambios
       catalogo = catalogoRepository.save(catalogo);
       return toResponseDTO(catalogo);
   }
   
   /**
    * Encuentra un catálogo por ID y lo convierte a DTO
    */
   @Override
   @Transactional(readOnly = true)
   public CatalogoResponseDTO findByIdDTO(Integer id) {
       CatalogoEntity catalogo = catalogoRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Catálogo", "id", id));
       return toResponseDTO(catalogo);
   }
   
   /**
    * Encuentra todos los catálogos y los convierte a DTOs
    */
   @Override
   @Transactional(readOnly = true)
   public List<CatalogoResponseDTO> findAllDTO() {
       try {
           return findAll().stream()
               .map(this::toResponseDTO)
               .collect(Collectors.toList());
       } catch (Exception e) {
           throw new RuntimeException("Error al listar catálogos", e);
       }
   }
   
   /**
    * Encuentra catálogos por estado y los convierte a DTOs
    */
   @Override
   @Transactional(readOnly = true)
   public List<CatalogoResponseDTO> findAllDTO(int estado) {
       List<CatalogoEntity> catalogos = catalogoRepository.findByEstado(estado);
       return catalogos.stream()
           .map(this::toResponseDTO)
           .collect(Collectors.toList());
   }
   
   /**
    * Cambia el estado de un catálogo (alterna entre activo/inactivo)
    */
   @Override
   @Transactional
   public CatalogoResponseDTO changeStatusCatalogo(Integer id) {
       CatalogoEntity catalogo = catalogoRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Catálogo", "id", id));
       
       // Alternar estado
       int nuevoEstado = catalogo.getEstado() == 1 ? 0 : 1;
       catalogo.setEstado(nuevoEstado);
       catalogo = catalogoRepository.save(catalogo);
       
       return toResponseDTO(catalogo);
   }
   
   /**
    * Valida los datos del DTO
    */
   private void validateData(CatalogoDTO dto) {
       if (dto.getNit() == null || dto.getNit().trim().isEmpty()) {
           throw new InvalidDataException("El NIT es requerido");
       }
       if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
           throw new InvalidDataException("El nombre es requerido");
       }
       if (dto.getEstado() == null) {
           throw new InvalidDataException("El estado es requerido");
       }
       if (dto.getEstado() != 0 && dto.getEstado() != 1) {
           throw new InvalidDataException("El estado debe ser 0 (inactivo) o 1 (activo)");
       }
   }
   
   /**
    * Convierte CatalogoEntity a CatalogoResponseDTO
    */
   private CatalogoResponseDTO toResponseDTO(CatalogoEntity catalogo) {
       CatalogoResponseDTO dto = new CatalogoResponseDTO();
       
       dto.setId(catalogo.getId());
       dto.setCodigo(catalogo.getCodigo());
       dto.setNit(catalogo.getNit());
       dto.setNombre(catalogo.getNombre());
       dto.setDescripcion(catalogo.getDescripcion());
       dto.setDireccion(catalogo.getDireccion());
       dto.setTipo(catalogo.getTipo());
       dto.setLongitud(catalogo.getLongitud());
       dto.setLatitud(catalogo.getLatitud());
       dto.setEstado(catalogo.getEstado());
       
       // Convertir JSON descuento a List<String>
       if (catalogo.getDescuento() != null) {
           try {
               List<String> descuentos = objectMapper.readValue(
                   catalogo.getDescuento(), 
                   new TypeReference<List<String>>() {}
               );
               dto.setDescuentos(descuentos);
           } catch (Exception e) {
               // Si falla, devolver lista vacía
               dto.setDescuentos(new ArrayList<>());
           }
       }
       
       // URL del logo
       if (catalogo.getNombrelogo() != null) {
           dto.setLogoUrl("/api/catalogos/logo/" + catalogo.getNombrelogo());
       }
       
       // URLs de imágenes adicionales
       if (catalogo.getImagenesCatalogos() != null && !catalogo.getImagenesCatalogos().isEmpty()) {
           List<String> imagenesUrls = catalogo.getImagenesCatalogos().stream()
               .map(img -> "/api/catalogos/imagenes/" + img.getImagen())
               .collect(Collectors.toList());
           dto.setImagenesUrls(imagenesUrls);
       }
       
       return dto;
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\CatalogoServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */