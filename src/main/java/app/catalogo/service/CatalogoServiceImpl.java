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
 import app.common.util.CloudinaryFolders;
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

           try {
             String timestamp = String.valueOf(System.currentTimeMillis());
             String publicId = "empresa_" + entity.getId() + "_galeria_" + imagenesCatalogoEntity.getCodigo() + "_" + timestamp;
             String cloudinaryUrl = this.archivoService.subirImagen(CloudinaryFolders.EMPRESA_GALERIA, catalogoitem, publicId);
             imagenesCatalogoEntity.setImagen(cloudinaryUrl);
           } catch (Exception ex) {
             throw new Exception("Error al subir imagen de galería: " + ex.getMessage());
           }
           
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
         try {
           String timestamp = String.valueOf(System.currentTimeMillis());
           String publicId = "empresa_" + entity.getId() + "_logo_" + timestamp;
           String cloudinaryUrl = this.archivoService.subirImagen(CloudinaryFolders.EMPRESA_LOGO, entity.getLogo(), publicId);
           entity.setNombrelogo(cloudinaryUrl);
         } catch (Exception ex) {
           throw new Exception("Error al subir logo a Cloudinary: " + ex.getMessage());
         }
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
           // Eliminar imagen de Cloudinary si es una URL de Cloudinary
           if (imgEntity.getImagen() != null && imgEntity.getImagen().contains("cloudinary")) {
             try {
               String publicId = extractPublicIdFromUrl(imgEntity.getImagen());
               if (publicId != null) {
                 this.archivoService.eliminarImagen(publicId);
               }
             } catch (Exception ignored) {}
           }
           this.ImagenCatalogoRepository.delete(imgEntity);
         } 
         
         System.out.println("LISTA CATALOGOS TAM: " + entity.getCatalogo().size());
         
         for (MultipartFile catalogoitem : entity.getCatalogo()) {
           ImagenesCatalogoEntity imagenesCatalogoEntity = new ImagenesCatalogoEntity();
           imagenesCatalogoEntity.setId(Integer.valueOf(this.ImagenCatalogoRepository.getIdPrimaryKey()));
           imagenesCatalogoEntity.setCodigo(this.ImagenCatalogoRepository.getCodigo());
           
           try {
             String timestamp = String.valueOf(System.currentTimeMillis());
             String publicId = "empresa_" + catalogoEntity2.getId() + "_galeria_" + imagenesCatalogoEntity.getCodigo() + "_" + timestamp;
             String cloudinaryUrl = this.archivoService.subirImagen(CloudinaryFolders.EMPRESA_GALERIA, catalogoitem, publicId);
             imagenesCatalogoEntity.setImagen(cloudinaryUrl);
           } catch (Exception ex) {
             throw new Exception("Error al subir imagen de galería: " + ex.getMessage());
           }


           imagenesCatalogoEntity.setEstado(Integer.valueOf(1));
           
           ImagenesCatalogoEntity imagenesCatalogoEntity2 = (ImagenesCatalogoEntity)this.ImagenCatalogoRepository.save(imagenesCatalogoEntity);
           array_imagenes_catalogo.add(imagenesCatalogoEntity2);
         } 
       } 


       entity.setImagenesCatalogos(array_imagenes_catalogo);


       if (!entity.getLogo().isEmpty()) {
         try {
           // Eliminar logo antiguo de Cloudinary si existe
           if (catalogoEntity2.getNombrelogo() != null && catalogoEntity2.getNombrelogo().contains("cloudinary")) {
             String oldPublicId = extractPublicIdFromUrl(catalogoEntity2.getNombrelogo());
             try {
               this.archivoService.eliminarImagen(oldPublicId);
             } catch (Exception ignored) {
               // Imagen antigua no existe o no se puede eliminar
             }
           }
           
           // Subir nuevo logo
           String timestamp = String.valueOf(System.currentTimeMillis());
           String publicId = "empresa_" + catalogoEntity2.getId() + "_logo_" + timestamp;
           String cloudinaryUrl = this.archivoService.subirImagen(CloudinaryFolders.EMPRESA_LOGO, entity.getLogo(), publicId);
           entity.setNombrelogo(cloudinaryUrl);
         } catch (Exception ex) {
           throw new Exception("Error al actualizar logo en Cloudinary: " + ex.getMessage());
         }
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
    * Crea un nuevo catálogo con logo, banner y hasta 3 imágenes de galería
    * Lógica de códigos:
    * - Banner: codigo=0, tipo=BANNER (máximo 1)
    * - Galería: codigo=1,2,3, tipo=GALERIA (máximo 3)
    */
   @Override
   @Transactional
   public CatalogoResponseDTO create(CatalogoDTO dto, MultipartFile logo, MultipartFile banner,
                                      MultipartFile galeria1, MultipartFile galeria2, MultipartFile galeria3) {
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
       
       // Mapear campos geográficos
       catalogo.setFkPais(dto.getPaisId());
       catalogo.setFkDepartamento(dto.getDepartamentoId());
       catalogo.setFkProvincia(dto.getProvinciaId());
       
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
               String timestamp = String.valueOf(System.currentTimeMillis());
               String publicId = "empresa_" + catalogo.getId() + "_logo_" + timestamp;
               String cloudinaryUrl = archivoService.subirImagen(CloudinaryFolders.EMPRESA_LOGO, logo, publicId);
               catalogo.setNombrelogo(cloudinaryUrl);
           } catch (Exception e) {
               throw new FileStorageException("Error al guardar el logo", e);
           }
       }
       
       // 5. Procesar imágenes (banner y galería)
       List<ImagenesCatalogoEntity> imagenesEntities = new ArrayList<>();
       
       // Banner: codigo=0, tipo=BANNER
       if (banner != null && !banner.isEmpty()) {
           imagenesEntities.add(crearImagenCatalogo(catalogo.getId(), banner, 0, "BANNER"));
       }
       
       // Galería: codigo=1,2,3, tipo=GALERIA
       if (galeria1 != null && !galeria1.isEmpty()) {
           imagenesEntities.add(crearImagenCatalogo(catalogo.getId(), galeria1, 1, "GALERIA"));
       }
       if (galeria2 != null && !galeria2.isEmpty()) {
           imagenesEntities.add(crearImagenCatalogo(catalogo.getId(), galeria2, 2, "GALERIA"));
       }
       if (galeria3 != null && !galeria3.isEmpty()) {
           imagenesEntities.add(crearImagenCatalogo(catalogo.getId(), galeria3, 3, "GALERIA"));
       }
       
       catalogo.setImagenesCatalogos(imagenesEntities);
       
       // 6. Guardar catálogo
       catalogo = catalogoRepository.save(catalogo);
       return toResponseDTO(catalogo);
   }
   
   /**
    * Actualiza un catálogo existente con logo, banner y galería
    * Lógica de reemplazo por código:
    * - Si se envía banner (codigo=0), reemplaza el banner anterior
    * - Si se envía galeria1 (codigo=1), reemplaza la galeria1 anterior
    * - Si se envía galeria2 (codigo=2), reemplaza la galeria2 anterior
    * - Si se envía galeria3 (codigo=3), reemplaza la galeria3 anterior
    */
   @Override
   @Transactional
   public CatalogoResponseDTO updateCatalogo(Integer id, CatalogoDTO dto, MultipartFile logo, MultipartFile banner,
                                              MultipartFile galeria1, MultipartFile galeria2, MultipartFile galeria3) {
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
       
       // Actualizar campos geográficos
       catalogo.setFkPais(dto.getPaisId());
       catalogo.setFkDepartamento(dto.getDepartamentoId());
       catalogo.setFkProvincia(dto.getProvinciaId());
       
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
       
       // 5. Actualizar logo
       // Si logo viene explícitamente como parámetro (aunque sea null), procesarlo
       if (logo != null && !logo.isEmpty()) {
           try {
               // Eliminar logo antiguo si existe y es de Cloudinary
               if (catalogo.getNombrelogo() != null && catalogo.getNombrelogo().contains("cloudinary")) {
                   String oldPublicId = extractPublicIdFromUrl(catalogo.getNombrelogo());
                   if (oldPublicId != null) {
                       try {
                           archivoService.eliminarImagen(oldPublicId);
                       } catch (Exception ignored) {}
                   }
               }
               
               // Subir nuevo logo
               String timestamp = String.valueOf(System.currentTimeMillis());
               String publicId = "empresa_" + catalogo.getId() + "_logo_" + timestamp;
               String cloudinaryUrl = archivoService.subirImagen(CloudinaryFolders.EMPRESA_LOGO, logo, publicId);
               catalogo.setNombrelogo(cloudinaryUrl);
           } catch (Exception e) {
               throw new FileStorageException("Error al actualizar el logo", e);
           }
       } else if (logo != null && logo.isEmpty()) {
           // Si logo es un archivo vacío (indicador de eliminación)
           // Eliminar logo actual de Cloudinary y poner null en BD
           System.out.println("Logo vacío detectado - procediendo a eliminar");
           if (catalogo.getNombrelogo() != null && catalogo.getNombrelogo().contains("cloudinary")) {
               String oldPublicId = extractPublicIdFromUrl(catalogo.getNombrelogo());
               if (oldPublicId != null) {
                   try {
                       System.out.println("Eliminando logo de Cloudinary: " + oldPublicId);
                       archivoService.eliminarImagen(oldPublicId);
                       System.out.println("Logo eliminado exitosamente");
                   } catch (Exception e) {
                       System.out.println("Error al eliminar logo: " + e.getMessage());
                   }
               }
           }
           catalogo.setNombrelogo(null);
           System.out.println("Logo establecido a null en BD");
       }
       
       // 6. Actualizar banner y/o galería por código
       // Reemplazar banner si se envía (codigo=0, tipo=BANNER) - puede ser empty para eliminar
       if (banner != null) {
           reemplazarImagenPorCodigo(catalogo, banner, 0, "BANNER");
       }
       
       // Reemplazar imágenes de galería si se envían (codigo=1/2/3, tipo=GALERIA) - pueden ser empty para eliminar
       if (galeria1 != null) {
           reemplazarImagenPorCodigo(catalogo, galeria1, 1, "GALERIA");
       }
       if (galeria2 != null) {
           reemplazarImagenPorCodigo(catalogo, galeria2, 2, "GALERIA");
       }
       if (galeria3 != null) {
           reemplazarImagenPorCodigo(catalogo, galeria3, 3, "GALERIA");
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
       
       // IDs geográficos
       dto.setPaisId(catalogo.getFkPais());
       dto.setDepartamentoId(catalogo.getFkDepartamento());
       dto.setProvinciaId(catalogo.getFkProvincia());
       
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
       
       // URL del logo desde el campo nombrelogo de CatalogoEntity
       if (catalogo.getNombrelogo() != null) {
           // Si es URL de Cloudinary (comienza con http), usar directamente
           // Si es nombre de archivo antiguo, construir URL local (compatibilidad)
           if (catalogo.getNombrelogo().startsWith("http")) {
               dto.setLogoUrl(catalogo.getNombrelogo());
           } else {
               dto.setLogoUrl("/api/catalogos/logo/" + catalogo.getNombrelogo());
           }
       }
       
       // Procesar imágenes de ImagenesCatalogoEntity
       if (catalogo.getImagenesCatalogos() != null && !catalogo.getImagenesCatalogos().isEmpty()) {
           // Filtrar y obtener URL del BANNER (solo debe haber 1)
           catalogo.getImagenesCatalogos().stream()
               .filter(img -> "BANNER".equalsIgnoreCase(img.getTipo()))
               .findFirst()
               .ifPresent(banner -> {
                   if (banner.getImagen() != null) {
                       if (banner.getImagen().startsWith("http")) {
                           dto.setBannerUrl(banner.getImagen());
                       } else {
                           dto.setBannerUrl("/api/catalogos/imagenes/" + banner.getImagen());
                       }
                   }
               });
           
           // Filtrar y obtener URLs de GALERIA (máximo 3)
           List<String> galeriaUrls = catalogo.getImagenesCatalogos().stream()
               .filter(img -> "GALERIA".equalsIgnoreCase(img.getTipo()))
               .limit(3) // Limitar a máximo 3
               .map(img -> {
                   if (img.getImagen() != null && img.getImagen().startsWith("http")) {
                       return img.getImagen();
                   }
                   return "/api/catalogos/imagenes/" + img.getImagen();
               })
               .collect(Collectors.toList());
           dto.setGaleriaUrls(galeriaUrls);
       }
       
       return dto;
   }
   
   /**
    * Crea una nueva imagen de catálogo (banner o galería)
    * @param catalogoId ID del catálogo
    * @param archivo Archivo de imagen
    * @param codigo Código de la imagen (0=banner, 1/2/3=galería)
    * @param tipo Tipo de imagen ("BANNER" o "GALERIA")
    */
   private ImagenesCatalogoEntity crearImagenCatalogo(Integer catalogoId, MultipartFile archivo, Integer codigo, String tipo) {
       try {
           ImagenesCatalogoEntity imagenEntity = new ImagenesCatalogoEntity();
           imagenEntity.setId(ImagenCatalogoRepository.getIdPrimaryKey());
           imagenEntity.setCodigo(codigo);
           
           String timestamp = String.valueOf(System.currentTimeMillis());
           String folder = "BANNER".equals(tipo) ? CloudinaryFolders.EMPRESA_BANNER : CloudinaryFolders.EMPRESA_GALERIA;
           String publicId = "empresa_" + catalogoId + "_" + tipo.toLowerCase() + "_" + codigo + "_" + timestamp;
           String cloudinaryUrl = archivoService.subirImagen(folder, archivo, publicId);
           
           imagenEntity.setImagen(cloudinaryUrl);
           imagenEntity.setTipo(tipo);
           imagenEntity.setEstado(1);
           
           return ImagenCatalogoRepository.save(imagenEntity);
       } catch (Exception e) {
           throw new FileStorageException("Error al crear imagen " + tipo + " con codigo " + codigo, e);
       }
   }
   
   /**
    * Reemplaza una imagen existente del catálogo basándose en el código
    * Si existe una imagen con el mismo código y tipo, la elimina y crea una nueva
    * Si archivo es vacío, solo elimina la imagen existente (pone null)
    * @param catalogo Entidad del catálogo
    * @param archivo Nuevo archivo de imagen (puede ser empty para indicar eliminación)
    * @param codigo Código de la imagen (0=banner, 1/2/3=galería)
    * @param tipo Tipo de imagen ("BANNER" o "GALERIA")
    */
   private void reemplazarImagenPorCodigo(CatalogoEntity catalogo, MultipartFile archivo, Integer codigo, String tipo) {
       try {
           System.out.println("=== reemplazarImagenPorCodigo ===");
           System.out.println("Codigo: " + codigo + ", Tipo: " + tipo);
           System.out.println("Archivo: " + (archivo == null ? "null" : (archivo.isEmpty() ? "empty" : archivo.getOriginalFilename())));
           
           // Buscar imagen existente con el mismo código y tipo
           ImagenesCatalogoEntity imagenExistente = catalogo.getImagenesCatalogos().stream()
               .filter(img -> codigo.equals(img.getCodigo()) && tipo.equalsIgnoreCase(img.getTipo()))
               .findFirst()
               .orElse(null);
           
           System.out.println("Imagen existente: " + (imagenExistente != null ? imagenExistente.getId() : "ninguna"));
           
           // Si archivo es vacío, significa que se quiere eliminar la imagen
           if (archivo.isEmpty()) {
               System.out.println("Archivo vacío - procediendo a eliminar");
               if (imagenExistente != null) {
                   // Eliminar de Cloudinary
                   if (imagenExistente.getImagen() != null && imagenExistente.getImagen().startsWith("http")) {
                       try {
                           System.out.println("Eliminando de Cloudinary: " + imagenExistente.getImagen());
                           archivoService.eliminarImagen(imagenExistente.getImagen());
                           System.out.println("Eliminado exitosamente de Cloudinary");
                       } catch (Exception e) {
                           System.err.println("Error al eliminar imagen de Cloudinary: " + e.getMessage());
                       }
                   }
                   // Eliminar de BD
                   System.out.println("Eliminando de BD");
                   ImagenCatalogoRepository.delete(imagenExistente);
                   catalogo.getImagenesCatalogos().remove(imagenExistente);
                   System.out.println("Eliminado exitosamente de BD");
               } else {
                   System.out.println("No hay imagen existente para eliminar");
               }
               return; // No crear nueva imagen
           }
           
           // Si existe, eliminarla de Cloudinary y BD antes de crear la nueva
           if (imagenExistente != null) {
               if (imagenExistente.getImagen() != null && imagenExistente.getImagen().startsWith("http")) {
                   try {
                       archivoService.eliminarImagen(imagenExistente.getImagen());
                   } catch (Exception e) {
                       System.err.println("Error al eliminar imagen anterior: " + e.getMessage());
                   }
               }
               ImagenCatalogoRepository.delete(imagenExistente);
               catalogo.getImagenesCatalogos().remove(imagenExistente);
           }
           
           // Crear y agregar nueva imagen
           ImagenesCatalogoEntity nuevaImagen = crearImagenCatalogo(catalogo.getId(), archivo, codigo, tipo);
           catalogo.getImagenesCatalogos().add(nuevaImagen);
       } catch (Exception e) {
           throw new FileStorageException("Error al reemplazar imagen " + tipo + " con codigo " + codigo, e);
       }
   }
   
   /**
    * Extrae el public_id de una URL de Cloudinary
    */
   private String extractPublicIdFromUrl(String url) {
       if (url == null || !url.contains("cloudinary")) {
           return null;
       }
       // URL típica: https://res.cloudinary.com/CLOUD_NAME/image/upload/v123456/FOLDER/public_id.ext
       // Queremos: FOLDER/public_id
       try {
           int uploadIndex = url.indexOf("/upload/");
           if (uploadIndex == -1) return null;
           
           String afterUpload = url.substring(uploadIndex + 8); // "/upload/" tiene 8 caracteres
           // Remover versión si existe (v123456/)
           if (afterUpload.matches("^v\\d+/.*")) {
               afterUpload = afterUpload.substring(afterUpload.indexOf('/') + 1);
           }
           // Remover extensión
           int lastDot = afterUpload.lastIndexOf('.');
           if (lastDot > 0) {
               afterUpload = afterUpload.substring(0, lastDot);
           }
           return afterUpload;
       } catch (Exception e) {
           return null;
       }
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\CatalogoServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */