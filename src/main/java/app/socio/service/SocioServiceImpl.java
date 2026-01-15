 package app.socio.service;
 
 import app.core.entity.InstitucionEntity;
 import app.core.entity.PersonaEntity;
import app.core.entity.ProfesionEntity;
import app.socio.entity.SocioEntity;
 import app.socio.dto.SocioDTO;
 import app.socio.dto.SocioResponseDTO;
 import app.catalogo.repository.CatalogoRepository;
 import app.common.util.GenericRepositoryNormal;
 import app.core.repository.InstitucionRepository;
 import app.core.repository.ProfesionRepository;
 import app.core.repository.PersonaRepository;
 import app.socio.repository.SocioRepository;
 import app.common.util.ArchivoService;
 import app.common.util.GenericServiceImplNormal;
 import app.core.service.PersonaService;
 import app.socio.service.SocioService;
 import app.common.util.Constantes;
 import app.common.util.QRCodeGeneratorService;
 import app.common.exception.*;
 import java.io.Serializable;
 import java.util.List;
 import java.util.Map;
 import java.util.HashMap;
 import java.util.UUID;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 import org.springframework.web.multipart.MultipartFile;


 @Service
 public class SocioServiceImpl
   extends GenericServiceImplNormal<SocioEntity, Integer>
   implements SocioService
 {
   @Autowired
   private SocioRepository socioRepository;
   @Autowired
   private CatalogoRepository catalogoRepository;
   @Autowired
   private InstitucionRepository institucionRepository;
   @Autowired
   private ProfesionRepository profesionRepository;
   @Autowired
   private PersonaRepository personaRepository;
   @Autowired
   private PersonaService personaService;
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;
   @Autowired
   private ArchivoService archivoService;
   @Autowired
   private QRCodeGeneratorService qrCodeGeneratorService;
   @Value("${server.port}")
   private static String puertoservidor;
   private String IPPUBLICA = "";
   private String PORT = "";

   SocioServiceImpl(GenericRepositoryNormal<SocioEntity, Integer> genericRepository) {
     super(genericRepository);
   }

   /**
    * Sobrescribe findAll() para no lanzar checked exception
    */
   @Override
   @Transactional(readOnly = true)
   public List<SocioEntity> findAll() {
     try {
       return super.findAll();
     } catch (Exception e) {
       throw new RuntimeException("Error al listar socios: " + e.getMessage(), e);
     }
   }

   /**
    * Sobrescribe findById() para no lanzar checked exception
    */
   @Override
   @Transactional(readOnly = true)
   public SocioEntity findById(Integer id) {
     try {
       SocioEntity socio = super.findById(id);
       if (socio == null) {
         throw new ResourceNotFoundException("Socio", "id", id);
       }
       return socio;
     } catch (Exception e) {
       throw new ResourceNotFoundException("Socio", "id", id);
     }
   }


   public int getIdPrimaryKey() throws Exception {
     try {
       int id = this.socioRepository.getIdPrimaryKey();
       return id;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return 0;
     } 
   }


   public Integer getCodigo() throws Exception {
     try {
       int total = this.socioRepository.getCodigo().intValue();
       return Integer.valueOf(total);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return Integer.valueOf(0);
     } 
   }


   @Transactional(readOnly = true)
   public List<SocioEntity> findAll(int estado, String search, int length, int start) {
     try {
       return this.socioRepository.findAll(estado, search, length, start);
     } catch (Exception e) {
       throw new RuntimeException("Error al listar socios: " + e.getMessage(), e);
     } 
   }


   @Transactional
   public void updateStatus(int status, int id) {
     try {
       this.socioRepository.updateStatus(Integer.valueOf(status), Integer.valueOf(id));
     } catch (Exception e) {
       throw new RuntimeException("Error al actualizar estado: " + e.getMessage(), e);
     } 
   }


   public Integer getTotAll(String search, int estado) {
     try {
       int total = this.socioRepository.getTotAll(search, Integer.valueOf(estado)).intValue();
       return Integer.valueOf(total);
     } catch (Exception e) {
       return Integer.valueOf(0);
     } 
   }


   @Transactional
   public SocioEntity save(SocioEntity entity) throws Exception {
     try {
       System.out.println("EntitySAVE_Servicio:" + entity.toString());
       
       if (entity.getId() == null) {
         System.out.println("IMAGEN:" + entity.getLogo().getOriginalFilename());


         PersonaEntity persona = new PersonaEntity();
         persona.setId(Integer.valueOf(this.personaService.getIdPrimaryKey()));
         persona.setCi(entity.getPersona().getCi());
         persona.setNombrecompleto(entity.getPersona().getNombrecompleto());
         persona.setEmail(entity.getPersona().getEmail());
         persona.setCelular(entity.getPersona().getCelular());
         persona.setEstado(Integer.valueOf(1));
         PersonaEntity persona2 = (PersonaEntity)this.personaService.save(persona);
         
         entity.setId(Integer.valueOf(this.socioRepository.getIdPrimaryKey()));
         entity.setCodigo(this.socioRepository.getCodigo());
         
         String codigoDocumento = this.passwordEncoder.encode("" + this.socioRepository.getCodigo());
         codigoDocumento = codigoDocumento.replace("/", "c");
         codigoDocumento = codigoDocumento.replace(".", "a");
         codigoDocumento = codigoDocumento.replace("$", "d");
         entity.setNrodocumento(codigoDocumento);
         entity.setLinkqr("QR - " + entity.getPersona().getCi() + ".png");
         
         InstitucionEntity institucionEntity = this.institucionRepository.findById(Integer.valueOf(1)).get();
         String bodyQR = institucionEntity.getHost() + "/socios/estadosocio/" + entity.getNrodocumento();

         this.qrCodeGeneratorService.generateQRCode(bodyQR, "QR - " + entity.getPersona().getCi());
         
         entity.setPersona(persona2);
         entity.setProfesion(this.profesionRepository.findById(Integer.valueOf(1)).get());
         entity.setInstitucion(this.institucionRepository.findById(Integer.valueOf(1)).get());
         entity.setCatalogos(this.catalogoRepository.findByEstado(1));


         if (!entity.getLogo().isEmpty()) {


           String nombre = "SOCIO - " + entity.getPersona().getCi() + entity.getLogo().getOriginalFilename().substring(entity.getLogo().getOriginalFilename().lastIndexOf('.'));
           System.out.println("NOMBRE SOCIO LOGO:" + nombre);


           entity.setImagen(nombre);


           this.archivoService.guargarArchivo(Constantes.nameFolderLogoSocio, entity.getLogo(), nombre);
         } 
         
         System.out.println("EntityPost:" + entity.toString());


         entity = (SocioEntity)this.socioRepository.save(entity);
       } else {
         
         entity = (SocioEntity)this.socioRepository.save(entity);
       } 
       
       return entity;
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional
   public SocioEntity updatecatalogos(Integer id, SocioEntity entidad) {
     SocioEntity entitymod = this.socioRepository.findById(id)
         .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", id));
     
     entitymod.getPersona().setCi(entidad.getPersona().getCi());
     entitymod.setMatricula(entidad.getMatricula());
     entitymod.setNombresocio(entidad.getNombresocio());
     entitymod.getPersona().setCelular(entidad.getPersona().getCelular());
     entitymod.setFechaemision(entidad.getFechaemision());
     entitymod.setFechaexpiracion(entidad.getFechaexpiracion());
     
     if (entidad.getCatalogos() != null) {
       entitymod.setCatalogos(entidad.getCatalogos());
     }

     return (SocioEntity)this.genericRepository.save(entitymod);
   }


   @Transactional
   public SocioEntity update(Integer id, SocioEntity entidad) throws Exception {
     try {
       System.out.println("Modificar Entity:" + entidad.toString());


       SocioEntity entitymod = (SocioEntity)this.socioRepository.findById(id).orElseThrow(() -> new Exception("Socio no encontrado"));


       entitymod.getPersona().setCi(entidad.getPersona().getCi());
       entitymod.setMatricula(entidad.getMatricula());
       entitymod.setNombresocio(entidad.getNombresocio());
       entitymod.getPersona().setCelular(entidad.getPersona().getCelular());
       entitymod.setFechaemision(entidad.getFechaemision());
       entitymod.setFechaexpiracion(entidad.getFechaexpiracion());
       entitymod.setLejendario(entidad.getLejendario());


       if (!entidad.getLogo().isEmpty()) {


         this.archivoService.eliminarArchivo(Constantes.nameFolderLogoSocio, entitymod.getImagen());


         String nombre = "SOCIO - " + entitymod.getPersona().getCi() + entidad.getLogo().getOriginalFilename().substring(entidad.getLogo().getOriginalFilename().lastIndexOf('.'));


         entitymod.setImagen(nombre);


         this.archivoService.guargarArchivo(Constantes.nameFolderLogoSocio, entidad.getLogo(), nombre);
       } 


       entitymod = (SocioEntity)this.genericRepository.save(entitymod);
       return entitymod;
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional
   public SocioEntity renovarQR(Integer id, SocioEntity entidad) {
     SocioEntity entitymod = this.socioRepository.findById(id)
         .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", id));
     
     entitymod.getPersona().setCi(entidad.getPersona().getCi());
     entitymod.setMatricula(entidad.getMatricula());
     entitymod.setNombresocio(entidad.getNombresocio());
     entitymod.getPersona().setCelular(entidad.getPersona().getCelular());
     entitymod.setFechaemision(entidad.getFechaemision());
     entitymod.setFechaexpiracion(entidad.getFechaexpiracion());

     InstitucionEntity institucionEntity = this.institucionRepository.findById(Integer.valueOf(1))
         .orElseThrow(() -> new ResourceNotFoundException("Institución", "id", 1));
     
     String bodyQR = institucionEntity.getHost() + "/socios/estadosocio/" + entitymod.getNrodocumento();

     try {
       if (entitymod.getLinkqr() != null) {
         this.archivoService.eliminarArchivo(Constantes.nameFolderQrSocio, entitymod.getLinkqr());
       }
     } catch (Exception e) {
       // Ignorar si no se puede eliminar
     }

     String qr_nuevo = "QR - " + entitymod.getPersona().getCi();
     
     try {
       this.qrCodeGeneratorService.generateQRCode(bodyQR, qr_nuevo);
     } catch (Exception e) {
       throw new FileStorageException("Error al generar código QR", e);
     }

     entitymod.setLinkqr(qr_nuevo + ".png");
     return (SocioEntity)this.genericRepository.save(entitymod);
   }


   public SocioEntity findByNrodocumento(String codigo) {
     try {
       SocioEntity entity = this.socioRepository.findByNrodocumento(codigo);
       if (entity == null) {
         throw new ResourceNotFoundException("Socio", "nrodocumento", codigo);
       }
       return entity;
     } catch (Exception e) {
       throw new ResourceNotFoundException("Socio", "nrodocumento", codigo);
     } 
   }

   /**
    * Obtiene datos para DataTables con paginación
    */
   @Transactional(readOnly = true)
   public Map<String, Object> getDataTableData(int draw, int length, int start, int estado, String search) {
     try {
       List<SocioEntity> lista = findAll(estado, search, length, start);
       String total = String.valueOf(getTotAll(search, estado));
       
       Map<String, Object> data = new HashMap<>();
       data.put("draw", draw);
       data.put("recordsTotal", total);
       data.put("data", lista);
       
       if (search != null && !search.isEmpty()) {
         data.put("recordsFiltered", lista.size());
       } else {
         data.put("recordsFiltered", total);
       }
       
       return data;
     } catch (Exception e) {
       throw new RuntimeException("Error al obtener datos para DataTable: " + e.getMessage(), e);
     }
   }

   /**
    * Crea un nuevo socio con toda la lógica de negocio
    * Incluye validaciones y transacción atómica
    */
   @Transactional
   public SocioResponseDTO createSocio(SocioDTO dto, MultipartFile logo) {
       // 1. Validaciones
       validateSocioData(dto);
       
       // 2. Verificar duplicados
       if (personaRepository.existsByCi(dto.getCi())) {
           throw new DuplicateResourceException("Persona", "CI", dto.getCi());
       }
       
       // 3. Verificar existencia de profesión e institución
       var profesion = profesionRepository.findById(dto.getProfesionId())
           .orElseThrow(() -> new ResourceNotFoundException("Profesión", "id", dto.getProfesionId()));
       
       // Usar institución 1 (CADET) por defecto si no se proporciona
       Integer institucionId = dto.getInstitucionId() != null ? dto.getInstitucionId() : 1;
       var institucion = institucionRepository.findById(institucionId)
           .orElseThrow(() -> new ResourceNotFoundException("Institución", "id", institucionId));
       
       // 4. Crear PersonaEntity
       PersonaEntity persona = new PersonaEntity();
       try {
           persona.setId(personaService.getIdPrimaryKey());
       } catch (Exception e) {
           throw new RuntimeException("Error al obtener ID de persona", e);
       }
       persona.setCi(dto.getCi());
       persona.setNombrecompleto(dto.getNombrecompleto());
       persona.setEmail(dto.getEmail());
       persona.setCelular(dto.getCelular());
       persona.setEstado(1);
       persona = personaRepository.save(persona);
       
       // 5. Crear SocioEntity
       SocioEntity socio = new SocioEntity();
       socio.setId(socioRepository.getIdPrimaryKey());
       socio.setCodigo(socioRepository.getCodigo());
       socio.setNrodocumento(generateDocumentNumber(socio.getCodigo()));
       socio.setMatricula(dto.getMatricula());
       socio.setNombresocio(dto.getNombresocio());
       socio.setFechaemision(dto.getFechaemision());
       socio.setFechaexpiracion(dto.getFechaexpiracion());
       socio.setLejendario(dto.getLejendario());
       socio.setEstado(1);
       socio.setPersona(persona);
       socio.setProfesion(profesion);
       socio.setInstitucion(institucion);
       socio.setCatalogos(catalogoRepository.findByEstado(1));
       
       // 6. Procesar logo si existe
       if (logo != null && !logo.isEmpty()) {
           try {
               String logoNombre = "SOCIO - " + persona.getCi() + 
                   logo.getOriginalFilename().substring(logo.getOriginalFilename().lastIndexOf('.'));
               socio.setImagen(logoNombre);
               archivoService.guargarArchivo(Constantes.nameFolderLogoSocio, logo, logoNombre);
           } catch (Exception e) {
               throw new FileStorageException("Error al guardar el logo", e);
           }
       }
       
       // 7. Generar QR
       try {
           String qrNombre = "QR - " + persona.getCi();
           String bodyQR = institucion.getHost() + "/socios/estadosocio/" + socio.getNrodocumento();
           qrCodeGeneratorService.generateQRCode(bodyQR, qrNombre);
           socio.setLinkqr(qrNombre + ".png");
       } catch (Exception e) {
           throw new FileStorageException("Error al generar código QR", e);
       }
       
       // 8. Guardar socio
       socio = socioRepository.save(socio);
       
       // 9. Convertir a DTO de respuesta
       return toResponseDTO(socio);
   }

   /**
    * Actualiza un socio existente con toda la lógica de negocio
    */
   @Transactional
   public SocioResponseDTO updateSocio(Integer id, SocioDTO dto, MultipartFile logo) {
       // 1. Buscar socio existente
       SocioEntity socio = socioRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", id));
       
       // 2. Validar datos
       validateSocioData(dto);
       
       // 3. Verificar CI duplicado (solo si cambió)
       if (!socio.getPersona().getCi().equals(dto.getCi())) {
           if (personaRepository.existsByCi(dto.getCi())) {
               throw new DuplicateResourceException("Persona", "CI", dto.getCi());
           }
       }
       
       // 4. Actualizar PersonaEntity
       socio.getPersona().setCi(dto.getCi());
       socio.getPersona().setNombrecompleto(dto.getNombrecompleto());
       socio.getPersona().setEmail(dto.getEmail());
       socio.getPersona().setCelular(dto.getCelular());
       
       // 5. Verificar y actualizar profesión
       ProfesionEntity profesion = profesionRepository.findById(dto.getProfesionId())
           .orElseThrow(() -> new ResourceNotFoundException("Profesion", "id", dto.getProfesionId()));
       socio.setProfesion(profesion);
       
       // 6. Verificar y actualizar institución
       InstitucionEntity institucion = institucionRepository.findById(dto.getInstitucionId())
           .orElseThrow(() -> new ResourceNotFoundException("Institucion", "id", dto.getInstitucionId()));
       socio.setInstitucion(institucion);
       
       // 7. Actualizar SocioEntity
       socio.setMatricula(dto.getMatricula());
       socio.setNombresocio(dto.getNombresocio());
       socio.setFechaemision(dto.getFechaemision());
       socio.setFechaexpiracion(dto.getFechaexpiracion());
       socio.setLejendario(dto.getLejendario());
       
       // 8. Actualizar logo si viene uno nuevo
       if (logo != null && !logo.isEmpty()) {
           try {
               // Eliminar logo anterior si existe
               if (socio.getImagen() != null) {
                   archivoService.eliminarArchivo(Constantes.nameFolderLogoSocio, socio.getImagen());
               }
               
               String logoNombre = "SOCIO - " + socio.getPersona().getCi() + 
                   logo.getOriginalFilename().substring(logo.getOriginalFilename().lastIndexOf('.'));
               socio.setImagen(logoNombre);
               archivoService.guargarArchivo(Constantes.nameFolderLogoSocio, logo, logoNombre);
           } catch (Exception e) {
               throw new FileStorageException("Error al actualizar el logo", e);
           }
       }
       
       // 9. Guardar
       socio = socioRepository.save(socio);
       
       // 10. Convertir a DTO de respuesta
       return toResponseDTO(socio);
   }

   /**
    * Valida los datos del socio
    */
   private void validateSocioData(SocioDTO dto) {
       if (dto.getCi() == null || dto.getCi().trim().isEmpty()) {
           throw new InvalidDataException("El CI es requerido");
       }
       if (dto.getNombrecompleto() == null || dto.getNombrecompleto().trim().isEmpty()) {
           throw new InvalidDataException("El nombre completo es requerido");
       }
       if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
           throw new InvalidDataException("El email es requerido");
       }
       if (dto.getProfesionId() == null) {
           throw new InvalidDataException("La profesión es requerida");
       }
       // InstitucionId es opcional, se usa 1 por defecto
       if (dto.getFechaemision() == null) {
           throw new InvalidDataException("La fecha de emisión es requerida");
       }
       if (dto.getFechaexpiracion() == null) {
           throw new InvalidDataException("La fecha de expiración es requerida");
       }
       if (dto.getFechaexpiracion().isBefore(dto.getFechaemision())) {
           throw new InvalidDataException("La fecha de expiración debe ser posterior a la fecha de emisión");
       }
   }

   /**
    * Genera un número de documento único usando UUID en lugar de BCrypt
    */
   private String generateDocumentNumber(Integer codigo) {
       return "SOC-" + codigo + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
   }

   /**
    * Convierte SocioEntity a SocioResponseDTO
    */
   private SocioResponseDTO toResponseDTO(SocioEntity socio) {
       SocioResponseDTO dto = new SocioResponseDTO();
       
       // Datos del socio
       dto.setId(socio.getId());
       dto.setCodigo(socio.getCodigo());
       dto.setNrodocumento(socio.getNrodocumento());
       dto.setMatricula(socio.getMatricula());
       dto.setNombresocio(socio.getNombresocio());
       dto.setFechaemision(socio.getFechaemision());
       dto.setFechaexpiracion(socio.getFechaexpiracion());
       dto.setLejendario(socio.getLejendario());
       dto.setEstado(socio.getEstado());
       
       // Datos de persona
       dto.setPersonaId(socio.getPersona().getId());
       dto.setCi(socio.getPersona().getCi());
       dto.setNombrecompleto(socio.getPersona().getNombrecompleto());
       dto.setEmail(socio.getPersona().getEmail());
       dto.setCelular(socio.getPersona().getCelular());
       
       // Datos de profesión
       dto.setProfesionId(socio.getProfesion().getId());
       dto.setProfesionNombre(socio.getProfesion().getNombre());
       
       // Datos de institución
       dto.setInstitucionId(socio.getInstitucion().getId());
       dto.setInstitucionNombre(socio.getInstitucion().getInstitucion());
       
       // URLs de recursos
       if (socio.getImagen() != null) {
           dto.setLogoUrl("/api/partners/logo/" + socio.getImagen());
       }
       if (socio.getLinkqr() != null) {
           dto.setQrUrl("/api/partners/qr/" + socio.getLinkqr());
       }
       
       return dto;
   }

   /**
    * Cambia el estado de un socio
    */
   @Transactional
   public SocioResponseDTO changeStatus(Integer id, Integer newStatus) {
       updateStatus(newStatus, id);
       SocioEntity socio = socioRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", id));
       return toResponseDTO(socio);
   }

   /**
    * Encuentra todos los socios y los convierte a DTOs
    */
   @Override
   @Transactional(readOnly = true)
   public List<SocioResponseDTO> findAllDTO() {
       return findAll().stream()
           .map(this::toResponseDTO)
           .toList();
   }

   /**
    * Encuentra un socio por ID y lo convierte a DTO
    */
   @Override
   @Transactional(readOnly = true)
   public SocioResponseDTO findByIdDTO(Integer id) {
       SocioEntity socio = socioRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", id));
       return toResponseDTO(socio);
   }

   /**
    * Encuentra un socio por número de documento y lo convierte a DTO
    */
   @Override
   @Transactional(readOnly = true)
   public SocioResponseDTO findByNrodocumentoDTO(String nrodocumento) {
       SocioEntity socio = findByNrodocumento(nrodocumento);
       return toResponseDTO(socio);
   }

   /**
    * Renueva el código QR de un socio (versión con DTO)
    */
   @Override
   @Transactional
   public SocioResponseDTO renovarQRDTO(Integer id) {
       SocioEntity socio = socioRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", id));
       
       InstitucionEntity institucion = socio.getInstitucion();
       String bodyQR = institucion.getHost() + "/socios/estadosocio/" + socio.getNrodocumento();
       String qrNombre = "QR - " + socio.getPersona().getCi();
       
       try {
           qrCodeGeneratorService.generateQRCode(bodyQR, qrNombre);
           socio.setLinkqr(qrNombre + ".png");
           socio = socioRepository.save(socio);
           return toResponseDTO(socio);
       } catch (Exception e) {
           throw new FileStorageException("Error al generar código QR", e);
       }
   }

   /**
    * Actualiza los catálogos de un socio (versión con DTO)
    */
   @Override
   @Transactional
   public SocioResponseDTO updateCatalogosDTO(Integer id, List<Integer> catalogIds) {
       SocioEntity socio = socioRepository.findById(id)
           .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", id));
       
       var catalogos = catalogoRepository.findAllById(catalogIds);
       if (catalogos.size() != catalogIds.size()) {
           throw new ResourceNotFoundException("Uno o más catálogos no existen");
       }
       
       socio.setCatalogos(catalogos);
       socio = socioRepository.save(socio);
       return toResponseDTO(socio);
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\SocioServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */