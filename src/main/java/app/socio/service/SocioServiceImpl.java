 package app.socio.service;
 
 import app.core.entity.InstitucionEntity;
 import app.core.entity.PersonaEntity;
 import app.socio.entity.SocioEntity;
 import app.catalogo.repository.CatalogoRepository;
 import app.common.util.GenericRepositoryNormal;
 import app.core.repository.InstitucionRepository;
 import app.core.repository.ProfesionRepository;
 import app.socio.repository.SocioRepository;
 import app.common.util.ArchivoService;
 import app.common.util.GenericServiceImplNormal;
 import app.core.service.PersonaService;
 import app.socio.service.SocioService;
 import app.common.util.Constantes;
 import app.common.util.QRCodeGeneratorService;
 import java.io.Serializable;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;


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
   public List<SocioEntity> findAll(int estado, String search, int length, int start) throws Exception {
     try {
       List<SocioEntity> entities = this.socioRepository.findAll(estado, search, length, start);
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
       this.socioRepository.updateStatus(Integer.valueOf(status), Integer.valueOf(id));
     }
     catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
       throw new Exception(e.getMessage());
     } 
   }


   public Integer getTotAll(String search, int estado) throws Exception {
     try {
       int total = this.socioRepository.getTotAll(search, Integer.valueOf(estado)).intValue();
       return Integer.valueOf(total);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
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
         String bodyQR = institucionEntity.getHost() + "/socios/estadosocio/" + institucionEntity.getHost();


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
   public SocioEntity updatecatalogos(Integer id, SocioEntity entidad) throws Exception {
     try {
       System.out.println("Modificar Entity:" + entidad.toString());


       SocioEntity entitymod = this.socioRepository.findById(id).get();
       
       entitymod.getPersona().setCi(entidad.getPersona().getCi());
       entitymod.setMatricula(entidad.getMatricula());
       entitymod.setNombresocio(entidad.getNombresocio());
       entitymod.getPersona().setCelular(entidad.getPersona().getCelular());
       entitymod.setFechaemision(entidad.getFechaemision());
       entitymod.setFechaexpiracion(entidad.getFechaexpiracion());
       
       if (entidad.getCatalogos() != null) {
         entitymod.setCatalogos(entidad.getCatalogos());
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


   public SocioEntity renovarQR(Integer id, SocioEntity entidad) throws Exception {
     try {
       System.out.println("Modificar Entity QR*****************:" + entidad.toString());


       SocioEntity entitymod = (SocioEntity)this.socioRepository.findById(id).orElseThrow(() -> new Exception("Socio no encontrado"));
       entitymod.getPersona().setCi(entidad.getPersona().getCi());
       entitymod.setMatricula(entidad.getMatricula());
       entitymod.setNombresocio(entidad.getNombresocio());
       entitymod.getPersona().setCelular(entidad.getPersona().getCelular());
       entitymod.setFechaemision(entidad.getFechaemision());
       entitymod.setFechaexpiracion(entidad.getFechaexpiracion());


       String codigoDocumento = entitymod.getNrodocumento();
       System.out.println("************************** NUMERO DE DOCUMENTO UPDATE QR:" + codigoDocumento);


       InstitucionEntity institucionEntity = (InstitucionEntity)this.institucionRepository.findById(Integer.valueOf(1)).orElseThrow(() -> new Exception("Institución no encontrada"));
       System.out.println("*************INSTITUCION:" + institucionEntity.toString());
       String bodyQR = institucionEntity.getHost() + "/socios/estadosocio/" + institucionEntity.getHost();
       System.out.println("****************CONTENIDO QR:" + bodyQR);


       try {
         System.out.println("************* ELIMINANDO QR: " + entitymod.getLinkqr());
         this.archivoService.eliminarArchivo(Constantes.nameFolderQrSocio, entitymod.getLinkqr());
       } catch (Exception e) {
         System.out.println("No se pudo eliminar el QR anterior: " + e.getMessage());
       } 


       String codigoDocumento_md = codigoDocumento.replace("/", "c").replace(".", "a").replace("$", "d");


       String qr_nuevo = "QR - " + entitymod.getPersona().getCi();


       this.qrCodeGeneratorService.generateQRCode(bodyQR, qr_nuevo);


       entitymod.setLinkqr(qr_nuevo + ".png");
       entitymod = (SocioEntity)this.genericRepository.save(entitymod);
       
       System.out.println("**************************** QR NUEVO MODIFICADO: " + qr_nuevo + ".png");
       
       return entitymod;
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println(e.getMessage());
       throw new Exception(e.getMessage());
     } 
   }


   public SocioEntity findByNrodocumento(String codigo) throws Exception {
     SocioEntity entity = new SocioEntity();
     try {
       entity = this.socioRepository.findByNrodocumento(codigo);
       return entity;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       return null;
     } 
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\SocioServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */