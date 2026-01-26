 package app.socio.service;
 
 import app.core.entity.InstitucionEntity;
 import app.core.entity.PersonaEntity;
import app.core.entity.ProfesionEntity;
import app.socio.entity.SocioEntity;
 import app.socio.dto.SocioDTO;
 import app.socio.dto.SocioResponseDTO;
 import app.socio.dto.SocioCompleteResponseDTO;
 import app.catalogo.repository.CatalogoRepository;
 import app.core.repository.InstitucionRepository;
 import app.core.repository.ProfesionRepository;
 import app.core.repository.PersonaRepository;
 import app.socio.repository.SocioRepository;
 import app.core.entity.UsuarioEntity;
 import app.core.entity.RolEntity;
 import app.core.repository.UsuarioRepository;
 import app.common.util.ArchivoService;
 import app.core.service.PersonaService;
 import app.socio.service.SocioService;
 import app.common.util.Constantes;
 import app.common.util.QRCodeGeneratorService;
 import app.common.util.CloudinaryFolders;
 import app.common.util.SocioImagenService;
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
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;


 /**
  * Servicio independiente para Socio
  * No extiende de ningún servicio genérico
  */
 @Service
 public class SocioServiceImpl implements SocioService
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
   private UsuarioRepository usuarioRepository;
   @Autowired
   private PersonaService personaService;
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;
   @Autowired
   private QRCodeGeneratorService qrCodeGeneratorService;
   @Autowired
   private SocioImagenService socioImagenService;


   // ============================================
   // CRUD Básico - Implementación directa
   // ============================================

   /**
    * Lista todos los socios
    */
   @Override
   @Transactional(readOnly = true)
   public List<SocioEntity> findAll() {
     try {
       return socioRepository.findAllByOrderByIdAsc();
     } catch (Exception e) {
       throw new RuntimeException("Error al listar socios: " + e.getMessage(), e);
     }
   }

   /**
    * Busca un socio por ID
    */
   @Override
   @Transactional(readOnly = true)
   public SocioEntity findById(Integer id) {
     try {
       return socioRepository.findById(id)
         .orElseThrow(() -> new ResourceNotFoundException("Socio", "id", id));
     } catch (ResourceNotFoundException e) {
       throw e;
     } catch (Exception e) {
       throw new RuntimeException("Error al buscar socio por ID: " + e.getMessage(), e);
     }
   }

   /**
    * Elimina un socio (cambia su estado a 0)
    */
   @Override
   @Transactional
   public boolean delete(Integer id) {
     try {
       SocioEntity socio = findById(id);
       socio.setEstado(0);
       socioRepository.save(socio);
       return true;
     } catch (Exception e) {
       throw new RuntimeException("Error al eliminar socio: " + e.getMessage(), e);
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

         try {
           byte[] qrCodeBytes = this.qrCodeGeneratorService.generateQRCode(bodyQR, 200, 200);
           String qrFileName = "QR - " + entity.getPersona().getCi() + ".png";
           // Convertir bytes a MultipartFile y subir a Cloudinary
           MultipartFile qrFile = new app.examples.SocioQRCloudinaryExample.MultipartFileFromBytes(
             qrCodeBytes, qrFileName, "image/png"
           );
           String qrPublicId = socioImagenService.actualizarQR(entity.getId(), entity.getQr(), qrFile);
           entity.setQr(qrPublicId);
           entity.setLinkqr(qrPublicId);
         } catch (Exception qrEx) {
           System.out.println("ERROR AL GENERAR QR: " + qrEx.getMessage());
           entity.setLinkqr("QR - " + entity.getPersona().getCi() + ".png");
         }


         if (!entity.getLogo().isEmpty()) {
           try {
             String publicId = socioImagenService.actualizarFotoCredencial(entity.getId(), entity.getImagen(), entity.getLogo());
             entity.setImagen(publicId);
           } catch (Exception logoEx) {
             System.out.println("ERROR AL SUBIR LOGO: " + logoEx.getMessage());
           }
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
     SocioEntity entitymod = socioRepository.findById(id)
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

     return socioRepository.save(entitymod);
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
         try {
           String publicId = socioImagenService.actualizarFotoCredencial(entitymod.getId(), entitymod.getImagen(), entidad.getLogo());
           entitymod.setImagen(publicId);
         } catch (Exception logoEx) {
           System.out.println("ERROR AL ACTUALIZAR LOGO: " + logoEx.getMessage());
         }
       } 


       entitymod = (SocioEntity)socioRepository.save(entitymod);
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

     String qr_nuevo = "QR - " + entitymod.getPersona().getCi();
     
     try {
       byte[] qrCodeBytes = this.qrCodeGeneratorService.generateQRCode(bodyQR, 200, 200);
       // Convertir bytes a MultipartFile y subir a Cloudinary
       MultipartFile qrFile = new app.examples.SocioQRCloudinaryExample.MultipartFileFromBytes(
         qrCodeBytes, qr_nuevo + ".png", "image/png"
       );
       String qrPublicId = socioImagenService.actualizarQR(entitymod.getId(), entitymod.getQr(), qrFile);
       entitymod.setQr(qrPublicId);
       entitymod.setLinkqr(qrPublicId);
     } catch (Exception e) {
       throw new FileStorageException("Error al generar código QR", e);
     }

     return (SocioEntity)socioRepository.save(entitymod);
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
       
       // 3. Usar profesión e institución por defecto (ID 1)
       var profesion = profesionRepository.findById(1)
           .orElseThrow(() -> new ResourceNotFoundException("Profesión por defecto", "id", 1));
       
       var institucion = institucionRepository.findById(1)
           .orElseThrow(() -> new ResourceNotFoundException("Institución por defecto (CADET)", "id", 1));
       
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
       
       // Asignar catálogos: usar los proporcionados o todos los activos por defecto
       if (dto.getCatalogoIds() != null && !dto.getCatalogoIds().isEmpty()) {
           var catalogos = catalogoRepository.findAllById(dto.getCatalogoIds());
           if (catalogos.size() != dto.getCatalogoIds().size()) {
               throw new ResourceNotFoundException("Uno o más catálogos no existen");
           }
           socio.setCatalogos(catalogos);
       } else {
           socio.setCatalogos(catalogoRepository.findByEstadoOrderByIdAsc(1));
       }
       
       // 6. Procesar logo si existe
       if (logo != null && !logo.isEmpty()) {
           try {
               String publicId = socioImagenService.actualizarFotoCredencial(socio.getId(), socio.getImagen(), logo);
               socio.setImagen(publicId);
           } catch (Exception e) {
               throw new FileStorageException("Error al guardar el logo", e);
           }
       }
       
       // 7. Generar QR
       try {
           String qrContent = institucion.getHost() + "/socios/estadosocio/" + socio.getNrodocumento();
           byte[] qrCodeBytes = qrCodeGeneratorService.generateQRCode(qrContent, 200, 200);
           String qrFileName = "QR - " + persona.getCi() + ".png";
           // Convertir bytes a MultipartFile y subir a Cloudinary
           MultipartFile qrFile = new app.examples.SocioQRCloudinaryExample.MultipartFileFromBytes(
             qrCodeBytes, qrFileName, "image/png"
           );
           String qrPublicId = socioImagenService.actualizarQR(socio.getId(), socio.getQr(), qrFile);
           socio.setQr(qrPublicId);
           socio.setLinkqr(qrPublicId);
       } catch (Exception e) {
           throw new FileStorageException("Error al generar el código QR", e);
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
       
       // Nota: Profesión e institución no se actualizan, permanecen como fueron creadas
       
       // 5. Actualizar SocioEntity
       socio.setMatricula(dto.getMatricula());
       socio.setNombresocio(dto.getNombresocio());
       socio.setFechaemision(dto.getFechaemision());
       socio.setFechaexpiracion(dto.getFechaexpiracion());
       socio.setLejendario(dto.getLejendario());
       
       // 8. Actualizar logo si viene uno nuevo
       if (logo != null && !logo.isEmpty()) {
           try {
               String publicId = socioImagenService.actualizarFotoCredencial(socio.getId(), socio.getImagen(), logo);
               socio.setImagen(publicId);
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
       
       // URLs de recursos (Cloudinary devuelve URL completa)
       if (socio.getImagen() != null) {
           // Si es URL de Cloudinary (comienza con http), usar directamente
           // Si es nombre de archivo antiguo, construir URL local (compatibilidad)
           if (socio.getImagen().startsWith("http")) {
               dto.setLogoUrl(socio.getImagen());
           } else {
               dto.setLogoUrl("/api/partners/logo/" + socio.getImagen());
           }
       }
       
       // QR - Usar campo 'qr' que tiene el public_id de Cloudinary
       if (socio.getQr() != null) {
           // Si es URL de Cloudinary, usar directamente
           if (socio.getQr().startsWith("http")) {
               dto.setQrUrl(socio.getQr());
           } else {
               // Fallback a linkqr si qr no es URL
               if (socio.getLinkqr() != null && socio.getLinkqr().startsWith("http")) {
                   dto.setQrUrl(socio.getLinkqr());
               } else {
                   dto.setQrUrl("/api/partners/qr/" + socio.getLinkqr());
               }
           }
       } else if (socio.getLinkqr() != null) {
           // Si qr es null pero linkqr existe
           if (socio.getLinkqr().startsWith("http")) {
               dto.setQrUrl(socio.getLinkqr());
           } else {
               dto.setQrUrl("/api/partners/qr/" + socio.getLinkqr());
           }
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
    * Encuentra todos los socios con información completa (usuario y empresas/catálogos)
    */
   @Transactional(readOnly = true)
   public List<SocioCompleteResponseDTO> findAllComplete() {
       List<SocioEntity> socios = findAll();
       return socios.stream()
           .map(this::toCompleteDTO)
           .toList();
   }

   /**
    * Convierte SocioEntity a SocioCompleteResponseDTO con usuario y empresas
    */
   private SocioCompleteResponseDTO toCompleteDTO(SocioEntity socio) {
       SocioCompleteResponseDTO dto = new SocioCompleteResponseDTO();
       
       // Datos básicos del socio
       dto.setId(socio.getId());
       dto.setCodigo(socio.getCodigo());
       dto.setNrodocumento(socio.getNrodocumento());
       dto.setMatricula(socio.getMatricula());
       dto.setNombresocio(socio.getNombresocio());
       dto.setFechaemision(socio.getFechaemision());
       dto.setFechaexpiracion(socio.getFechaexpiracion());
       dto.setEstado(socio.getEstado());
       dto.setImagen(socio.getImagen());
       
       // Datos de la persona asociada
       PersonaEntity persona = socio.getPersona();
       dto.setPersonaId(persona.getId());
       dto.setPersonaCi(persona.getCi());
       dto.setPersonaNombre(persona.getNombrecompleto());
       dto.setPersonaEmail(persona.getEmail());
       dto.setPersonaCelular(persona.getCelular());
       
       // Datos de profesión
       if (socio.getProfesion() != null) {
           dto.setProfesion(socio.getProfesion().getNombre());
       }
       
       // Datos de institución
       if (socio.getInstitucion() != null) {
           dto.setInstitucion(socio.getInstitucion().getInstitucion());
       }
       
       // Buscar usuario asociado a la persona
       UsuarioEntity usuario = usuarioRepository.findByPersonaId(persona.getId());
       if (usuario != null) {
           SocioCompleteResponseDTO.UsuarioInfo usuarioInfo = new SocioCompleteResponseDTO.UsuarioInfo();
           usuarioInfo.setId(usuario.getId());
           usuarioInfo.setUsername(usuario.getUsername());
           usuarioInfo.setEstado(usuario.getEstado());
           
           // Extraer roles del usuario
           List<String> roles = usuario.getRoles().stream()
               .map(RolEntity::getNombre)
               .toList();
           usuarioInfo.setRoles(roles);
           
           dto.setUsuario(usuarioInfo);
       }
       
       // Mapear catálogos (empresas) asociadas al socio
       if (socio.getCatalogos() != null && !socio.getCatalogos().isEmpty()) {
           List<SocioCompleteResponseDTO.EmpresaInfo> empresas = socio.getCatalogos().stream()
               .map(catalogo -> {
                   SocioCompleteResponseDTO.EmpresaInfo empresaInfo = new SocioCompleteResponseDTO.EmpresaInfo();
                   empresaInfo.setId(catalogo.getId());
                   empresaInfo.setCodigo(catalogo.getCodigo());
                   empresaInfo.setNit(catalogo.getNit());
                   empresaInfo.setNombre(catalogo.getNombre());
                   empresaInfo.setDescripcion(catalogo.getDescripcion());
                   empresaInfo.setDireccion(catalogo.getDireccion());
                   empresaInfo.setTipo(catalogo.getTipo());
                   empresaInfo.setNombrelogo(catalogo.getNombrelogo());
                   empresaInfo.setEstado(catalogo.getEstado());
                   return empresaInfo;
               })
               .toList();
           dto.setEmpresas(empresas);
       }
       
       return dto;
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
           byte[] qrCodeBytes = qrCodeGeneratorService.generateQRCode(bodyQR, 200, 200);
           String qrFileName = qrNombre + ".png";
           // Convertir bytes a MultipartFile y subir a Cloudinary
           MultipartFile qrFile = new app.examples.SocioQRCloudinaryExample.MultipartFileFromBytes(
             qrCodeBytes, qrFileName, "image/png"
           );
           String qrPublicId = socioImagenService.actualizarQR(socio.getId(), socio.getQr(), qrFile);
           socio.setQr(qrPublicId);
           socio.setLinkqr(qrPublicId);
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