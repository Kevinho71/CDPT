/*     */ package BOOT-INF.classes.app.service;
/*     */ 
/*     */ import app.entity.InstitucionEntity;
/*     */ import app.entity.PersonaEntity;
/*     */ import app.entity.SocioEntity;
/*     */ import app.repository.CatalogoRepository;
/*     */ import app.repository.GenericRepositoryNormal;
/*     */ import app.repository.InstitucionRepository;
/*     */ import app.repository.ProfesionRepository;
/*     */ import app.repository.SocioRepository;
/*     */ import app.service.ArchivoService;
/*     */ import app.service.GenericServiceImplNormal;
/*     */ import app.service.PersonaService;
/*     */ import app.service.SocioService;
/*     */ import app.util.Constantes;
/*     */ import app.util.QRCodeGeneratorServiceDrive;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class SocioServiceImpl
/*     */   extends GenericServiceImplNormal<SocioEntity, Integer>
/*     */   implements SocioService
/*     */ {
/*     */   @Autowired
/*     */   private SocioRepository socioRepository;
/*     */   @Autowired
/*     */   private CatalogoRepository catalogoRepository;
/*     */   @Autowired
/*     */   private InstitucionRepository institucionRepository;
/*     */   @Autowired
/*     */   private ProfesionRepository profesionRepository;
/*     */   @Autowired
/*     */   private PersonaService personaService;
/*     */   @Autowired
/*     */   private BCryptPasswordEncoder passwordEncoder;
/*     */   @Autowired
/*     */   private ArchivoService archivoService;
/*     */   @Autowired
/*     */   private QRCodeGeneratorServiceDrive qrCodeGeneratorService;
/*     */   @Value("${server.port}")
/*     */   private static String puertoservidor;
/*  51 */   private String IPPUBLICA = "";
/*  52 */   private String PORT = "";
/*     */ 
/*     */   
/*     */   SocioServiceImpl(GenericRepositoryNormal<SocioEntity, Integer> genericRepository) {
/*  56 */     super(genericRepository);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIdPrimaryKey() throws Exception {
/*     */     try {
/*  65 */       int id = this.socioRepository.getIdPrimaryKey();
/*  66 */       return id;
/*  67 */     } catch (Exception e) {
/*  68 */       System.out.println(e.getMessage());
/*     */       
/*  70 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getCodigo() throws Exception {
/*     */     try {
/*  77 */       int total = this.socioRepository.getCodigo().intValue();
/*  78 */       return Integer.valueOf(total);
/*  79 */     } catch (Exception e) {
/*  80 */       System.out.println(e.getMessage());
/*     */       
/*  82 */       return Integer.valueOf(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public List<SocioEntity> findAll(int estado, String search, int length, int start) throws Exception {
/*     */     try {
/*  92 */       List<SocioEntity> entities = this.socioRepository.findAll(estado, search, length, start);
/*  93 */       return entities;
/*  94 */     } catch (Exception e) {
/*  95 */       System.out.println(e.getMessage());
/*  96 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public void updateStatus(int status, int id) throws Exception {
/*     */     try {
/* 106 */       System.out.println("estado:" + status + " id:" + id);
/* 107 */       this.socioRepository.updateStatus(Integer.valueOf(status), Integer.valueOf(id));
/*     */     }
/* 109 */     catch (Exception e) {
/* 110 */       System.out.println(e.getMessage());
/* 111 */       e.printStackTrace();
/* 112 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getTotAll(String search, int estado) throws Exception {
/*     */     try {
/* 120 */       int total = this.socioRepository.getTotAll(search, Integer.valueOf(estado)).intValue();
/* 121 */       return Integer.valueOf(total);
/* 122 */     } catch (Exception e) {
/* 123 */       System.out.println(e.getMessage());
/*     */       
/* 125 */       return Integer.valueOf(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public SocioEntity save(SocioEntity entity) throws Exception {
/*     */     try {
/* 215 */       System.out.println("EntitySAVE_Servicio:" + entity.toString());
/*     */       
/* 217 */       if (entity.getId() == null) {
/* 218 */         System.out.println("IMAGEN:" + entity.getLogo().getOriginalFilename());
/*     */ 
/*     */         
/* 221 */         PersonaEntity persona = new PersonaEntity();
/* 222 */         persona.setId(Integer.valueOf(this.personaService.getIdPrimaryKey()));
/* 223 */         persona.setCi(entity.getPersona().getCi());
/* 224 */         persona.setNombrecompleto(entity.getPersona().getNombrecompleto());
/* 225 */         persona.setEmail(entity.getPersona().getEmail());
/* 226 */         persona.setCelular(entity.getPersona().getCelular());
/* 227 */         persona.setEstado(Integer.valueOf(1));
/* 228 */         PersonaEntity persona2 = (PersonaEntity)this.personaService.save(persona);
/*     */         
/* 230 */         entity.setId(Integer.valueOf(this.socioRepository.getIdPrimaryKey()));
/* 231 */         entity.setCodigo(this.socioRepository.getCodigo());
/*     */         
/* 233 */         String codigoDocumento = this.passwordEncoder.encode("" + this.socioRepository.getCodigo());
/* 234 */         codigoDocumento = codigoDocumento.replace("/", "c");
/* 235 */         codigoDocumento = codigoDocumento.replace(".", "a");
/* 236 */         codigoDocumento = codigoDocumento.replace("$", "d");
/* 237 */         entity.setNrodocumento(codigoDocumento);
/* 238 */         entity.setLinkqr("QR - " + entity.getPersona().getCi() + ".png");
/*     */         
/* 240 */         InstitucionEntity institucionEntity = this.institucionRepository.findById(Integer.valueOf(1)).get();
/* 241 */         String bodyQR = institucionEntity.getHost() + "/socios/estadosocio/" + institucionEntity.getHost();
/*     */ 
/*     */ 
/*     */         
/* 245 */         this.qrCodeGeneratorService.generateQRCode(bodyQR, "QR - " + entity.getPersona().getCi());
/*     */         
/* 247 */         entity.setPersona(persona2);
/* 248 */         entity.setProfesion(this.profesionRepository.findById(Integer.valueOf(1)).get());
/* 249 */         entity.setInstitucion(this.institucionRepository.findById(Integer.valueOf(1)).get());
/* 250 */         entity.setCatalogos(this.catalogoRepository.findByEstado(1));
/*     */ 
/*     */         
/* 253 */         if (!entity.getLogo().isEmpty()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 259 */           String nombre = "SOCIO - " + entity.getPersona().getCi() + entity.getLogo().getOriginalFilename().substring(entity.getLogo().getOriginalFilename().lastIndexOf('.'));
/* 260 */           System.out.println("NOMBRE SOCIO LOGO:" + nombre);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 265 */           entity.setImagen(nombre);
/*     */ 
/*     */           
/* 268 */           String idArchivoLogoDrive = this.archivoService.guargarArchivoDrive(Constantes.nameFolderLogoSocio, entity.getLogo(), nombre);
/* 269 */           entity.setImagenDriveId(idArchivoLogoDrive);
/*     */         } 
/*     */         
/* 272 */         System.out.println("EntityPost:" + entity.toString());
/*     */ 
/*     */         
/* 275 */         entity = (SocioEntity)this.socioRepository.save(entity);
/*     */       } else {
/*     */         
/* 278 */         entity = (SocioEntity)this.socioRepository.save(entity);
/*     */       } 
/*     */       
/* 281 */       return entity;
/* 282 */     } catch (Exception e) {
/* 283 */       e.printStackTrace();
/* 284 */       System.out.println(e.getMessage());
/* 285 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public SocioEntity updatecatalogos(Integer id, SocioEntity entidad) throws Exception {
/*     */     try {
/* 294 */       System.out.println("Modificar Entity:" + entidad.toString());
/*     */ 
/*     */       
/* 297 */       SocioEntity entitymod = this.socioRepository.findById(id).get();
/*     */       
/* 299 */       entitymod.getPersona().setCi(entidad.getPersona().getCi());
/* 300 */       entitymod.setMatricula(entidad.getMatricula());
/* 301 */       entitymod.setNombresocio(entidad.getNombresocio());
/* 302 */       entitymod.getPersona().setCelular(entidad.getPersona().getCelular());
/* 303 */       entitymod.setFechaemision(entidad.getFechaemision());
/* 304 */       entitymod.setFechaexpiracion(entidad.getFechaexpiracion());
/*     */       
/* 306 */       if (entidad.getCatalogos() != null) {
/* 307 */         entitymod.setCatalogos(entidad.getCatalogos());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 314 */       entitymod = (SocioEntity)this.genericRepository.save(entitymod);
/* 315 */       return entitymod;
/* 316 */     } catch (Exception e) {
/* 317 */       e.printStackTrace();
/* 318 */       System.out.println(e.getMessage());
/* 319 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public SocioEntity update(Integer id, SocioEntity entidad) throws Exception {
/*     */     try {
/* 364 */       System.out.println("Modificar Entity:" + entidad.toString());
/*     */ 
/*     */       
/* 367 */       SocioEntity entitymod = (SocioEntity)this.socioRepository.findById(id).orElseThrow(() -> new Exception("Socio no encontrado"));
/*     */ 
/*     */       
/* 370 */       entitymod.getPersona().setCi(entidad.getPersona().getCi());
/* 371 */       entitymod.setMatricula(entidad.getMatricula());
/* 372 */       entitymod.setNombresocio(entidad.getNombresocio());
/* 373 */       entitymod.getPersona().setCelular(entidad.getPersona().getCelular());
/* 374 */       entitymod.setFechaemision(entidad.getFechaemision());
/* 375 */       entitymod.setFechaexpiracion(entidad.getFechaexpiracion());
/* 376 */       entitymod.setLejendario(entidad.getLejendario());
/*     */ 
/*     */       
/* 379 */       if (!entidad.getLogo().isEmpty()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 384 */         this.archivoService.eliminarArchivoDrive(Constantes.nameFolderLogoSocio, entitymod.getImagen());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 390 */         String nombre = "SOCIO - " + entitymod.getPersona().getCi() + entidad.getLogo().getOriginalFilename().substring(entidad.getLogo().getOriginalFilename().lastIndexOf('.'));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 395 */         entitymod.setImagen(nombre);
/*     */ 
/*     */         
/* 398 */         String idArchivoLogoDrive = this.archivoService.guargarArchivoDrive(Constantes.nameFolderLogoSocio, entidad.getLogo(), nombre);
/* 399 */         entitymod.setImagenDriveId(idArchivoLogoDrive);
/*     */       } 
/*     */ 
/*     */       
/* 403 */       entitymod = (SocioEntity)this.genericRepository.save(entitymod);
/* 404 */       return entitymod;
/* 405 */     } catch (Exception e) {
/* 406 */       e.printStackTrace();
/* 407 */       System.out.println(e.getMessage());
/* 408 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocioEntity renovarQR(Integer id, SocioEntity entidad) throws Exception {
/*     */     try {
/* 474 */       System.out.println("Modificar Entity QR*****************:" + entidad.toString());
/*     */ 
/*     */ 
/*     */       
/* 478 */       SocioEntity entitymod = (SocioEntity)this.socioRepository.findById(id).orElseThrow(() -> new Exception("Socio no encontrado"));
/* 479 */       entitymod.getPersona().setCi(entidad.getPersona().getCi());
/* 480 */       entitymod.setMatricula(entidad.getMatricula());
/* 481 */       entitymod.setNombresocio(entidad.getNombresocio());
/* 482 */       entitymod.getPersona().setCelular(entidad.getPersona().getCelular());
/* 483 */       entitymod.setFechaemision(entidad.getFechaemision());
/* 484 */       entitymod.setFechaexpiracion(entidad.getFechaexpiracion());
/*     */ 
/*     */       
/* 487 */       String codigoDocumento = entitymod.getNrodocumento();
/* 488 */       System.out.println("************************** NUMERO DE DOCUMENTO UPDATE QR:" + codigoDocumento);
/*     */ 
/*     */       
/* 491 */       InstitucionEntity institucionEntity = (InstitucionEntity)this.institucionRepository.findById(Integer.valueOf(1)).orElseThrow(() -> new Exception("Institución no encontrada"));
/* 492 */       System.out.println("*************INSTITUCION:" + institucionEntity.toString());
/* 493 */       String bodyQR = institucionEntity.getHost() + "/socios/estadosocio/" + institucionEntity.getHost();
/* 494 */       System.out.println("****************CONTENIDO QR:" + bodyQR);
/*     */ 
/*     */       
/*     */       try {
/* 498 */         System.out.println("************* ELIMINANDO QR EN GOOGLE DRIVE: " + entitymod.getLinkqr());
/* 499 */         this.archivoService.eliminarArchivoDrive(Constantes.nameFolderQrSocio, entitymod.getLinkqr());
/* 500 */       } catch (Exception e) {
/* 501 */         System.out.println("No se pudo eliminar el QR anterior en Google Drive: " + e.getMessage());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 507 */       String codigoDocumento_md = codigoDocumento.replace("/", "c").replace(".", "a").replace("$", "d");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 512 */       String qr_nuevo = "QR - " + entitymod.getPersona().getCi();
/*     */ 
/*     */       
/* 515 */       this.qrCodeGeneratorService.generateQRCode(bodyQR, qr_nuevo);
/*     */ 
/*     */       
/* 518 */       entitymod.setLinkqr(qr_nuevo + ".png");
/* 519 */       entitymod = (SocioEntity)this.genericRepository.save(entitymod);
/*     */       
/* 521 */       System.out.println("**************************** QR NUEVO MODIFICADO: " + qr_nuevo + ".png");
/*     */       
/* 523 */       return entitymod;
/* 524 */     } catch (Exception e) {
/* 525 */       e.printStackTrace();
/* 526 */       System.out.println(e.getMessage());
/* 527 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocioEntity findByNrodocumento(String codigo) throws Exception {
/* 535 */     SocioEntity entity = new SocioEntity();
/*     */     try {
/* 537 */       entity = this.socioRepository.findByNrodocumento(codigo);
/* 538 */       return entity;
/* 539 */     } catch (Exception e) {
/* 540 */       System.out.println(e.getMessage());
/* 541 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\SocioServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */