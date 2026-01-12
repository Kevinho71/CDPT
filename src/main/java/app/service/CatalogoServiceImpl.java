/*     */ package BOOT-INF.classes.app.service;
/*     */ 
/*     */ import app.entity.CatalogoEntity;
/*     */ import app.entity.ImagenesCatalogoEntity;
/*     */ import app.repository.CatalogoRepository;
/*     */ import app.repository.GenericRepositoryNormal;
/*     */ import app.repository.ImagenCatalogoRepository;
/*     */ import app.service.ArchivoService;
/*     */ import app.service.CatalogoService;
/*     */ import app.service.GenericServiceImplNormal;
/*     */ import app.util.Constantes;
/*     */ import app.util.QRCodeGeneratorService;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class CatalogoServiceImpl
/*     */   extends GenericServiceImplNormal<CatalogoEntity, Integer>
/*     */   implements CatalogoService
/*     */ {
/*     */   @Autowired
/*     */   private CatalogoRepository catalogoRepository;
/*     */   @Autowired
/*     */   private ImagenCatalogoRepository ImagenCatalogoRepository;
/*     */   @Autowired
/*     */   private ArchivoService archivoService;
/*     */   @Autowired
/*     */   QRCodeGeneratorService qrCodeGeneratorService;
/*     */   @Value("${server.port}")
/*     */   private static String puertoservidor;
/*     */   
/*     */   CatalogoServiceImpl(GenericRepositoryNormal<CatalogoEntity, Integer> genericRepository) {
/*  45 */     super(genericRepository);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIdPrimaryKey() throws Exception {
/*     */     try {
/*  54 */       int id = this.catalogoRepository.getIdPrimaryKey();
/*  55 */       return id;
/*  56 */     } catch (Exception e) {
/*  57 */       System.out.println(e.getMessage());
/*     */       
/*  59 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getCodigo() throws Exception {
/*     */     try {
/*  66 */       int total = this.catalogoRepository.getCodigo().intValue();
/*  67 */       return Integer.valueOf(total);
/*  68 */     } catch (Exception e) {
/*  69 */       System.out.println(e.getMessage());
/*     */       
/*  71 */       return Integer.valueOf(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public List<CatalogoEntity> findAll(int estado, String search, int length, int start) throws Exception {
/*     */     try {
/*  81 */       List<CatalogoEntity> entities = this.catalogoRepository.findAll(estado, search, length, start);
/*  82 */       return entities;
/*  83 */     } catch (Exception e) {
/*  84 */       System.out.println(e.getMessage());
/*  85 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public void updateStatus(int status, int id) throws Exception {
/*     */     try {
/*  95 */       System.out.println("estado:" + status + " id:" + id);
/*  96 */       this.catalogoRepository.updateStatus(Integer.valueOf(status), Integer.valueOf(id));
/*     */     }
/*  98 */     catch (Exception e) {
/*  99 */       System.out.println(e.getMessage());
/* 100 */       e.printStackTrace();
/* 101 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getTotAll(String search, int estado) throws Exception {
/*     */     try {
/* 109 */       int total = this.catalogoRepository.getTotAll(search, Integer.valueOf(estado)).intValue();
/* 110 */       return Integer.valueOf(total);
/* 111 */     } catch (Exception e) {
/* 112 */       System.out.println(e.getMessage());
/*     */       
/* 114 */       return Integer.valueOf(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public CatalogoEntity save(CatalogoEntity entity) throws Exception {
/*     */     try {
/* 125 */       System.out.println("EntitySAVE_Servicio:" + entity.toString());
/*     */       
/* 127 */       List<ImagenesCatalogoEntity> array_imagenes_catalogo = new ArrayList<>();
/*     */ 
/*     */       
/* 130 */       if (entity.getCatalogo() != null && !entity.getCatalogo().isEmpty()) {
/* 131 */         for (int i = 0; i < entity.getCatalogo().size(); i++) {
/* 132 */           ImagenesCatalogoEntity imagenesCatalogoEntity = new ImagenesCatalogoEntity();
/* 133 */           imagenesCatalogoEntity.setId(Integer.valueOf(this.ImagenCatalogoRepository.getIdPrimaryKey()));
/* 134 */           imagenesCatalogoEntity.setCodigo(this.ImagenCatalogoRepository.getCodigo());
/*     */           
/* 136 */           MultipartFile catalogoitem = entity.getCatalogo().get(i);
/* 137 */           System.out.println("CATALOGO:" + catalogoitem.getOriginalFilename());
/*     */ 
/*     */ 
/*     */           
/* 141 */           String nombreLocal = entity.getNit() + " - " + entity.getNit() + this.ImagenCatalogoRepository.getCodigo();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 146 */           imagenesCatalogoEntity.setImagen(nombreLocal);
/*     */ 
/*     */           
/* 149 */           String idArchivoCatalogoDrive = this.archivoService.guargarArchivoDrive(Constantes.nameFolderImgCatalogo, catalogoitem, nombreLocal);
/* 150 */           imagenesCatalogoEntity.setImagenDriveId(idArchivoCatalogoDrive);
/*     */           
/* 152 */           imagenesCatalogoEntity.setEstado(Integer.valueOf(1));
/*     */           
/* 154 */           ImagenesCatalogoEntity imagenesCatalogoEntity2 = (ImagenesCatalogoEntity)this.ImagenCatalogoRepository.save(imagenesCatalogoEntity);
/* 155 */           array_imagenes_catalogo.add(imagenesCatalogoEntity2);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 160 */       entity.setImagenesCatalogos(array_imagenes_catalogo);
/* 161 */       System.out.println("IMAGENES DE CATALOGO ASIGNADOS");
/* 162 */       entity.setId(Integer.valueOf(this.catalogoRepository.getIdPrimaryKey()));
/* 163 */       entity.setCodigo(this.catalogoRepository.getCodigo());
/*     */ 
/*     */       
/* 166 */       if (!entity.getLogo().isEmpty()) {
/*     */         
/* 168 */         String nombreLocal = entity.getNit() + entity.getNit();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 173 */         entity.setNombrelogo(nombreLocal);
/*     */ 
/*     */         
/* 176 */         String idArchivoLogoDrive = this.archivoService.guargarArchivoDrive(Constantes.nameFolderLogoCatalogo, entity.getLogo(), nombreLocal);
/* 177 */         entity.setNombrelogoDriveId(idArchivoLogoDrive);
/*     */       } 
/*     */       
/* 180 */       System.out.println("EntityPost:" + entity.toString());
/* 181 */       entity = (CatalogoEntity)this.catalogoRepository.save(entity);
/* 182 */       return entity;
/* 183 */     } catch (Exception e) {
/* 184 */       e.printStackTrace();
/* 185 */       System.out.println(" catalogo service save err:" + e.getMessage());
/*     */       
/* 187 */       throw new Exception("Error al guardar el catálogo: " + e.getMessage(), e);
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
/*     */   public CatalogoEntity update(Integer id, CatalogoEntity entity) throws Exception {
/*     */     try {
/* 310 */       System.out.println("Modificar Entity Service: " + entity.toString());
/*     */       
/* 312 */       CatalogoEntity catalogoEntity2 = this.catalogoRepository.findById(id).get();
/* 313 */       System.out.println("CATALOGO BD: " + catalogoEntity2.toString());
/* 314 */       List<ImagenesCatalogoEntity> array_imagenes_catalogo = new ArrayList<>();
/*     */ 
/*     */       
/* 317 */       if (entity.getCatalogo() != null && 
/* 318 */         !entity.getCatalogo().isEmpty()) {
/*     */         
/* 320 */         for (ImagenesCatalogoEntity imgEntity : catalogoEntity2.getImagenesCatalogos()) {
/* 321 */           String nombreImagen = imgEntity.getImagen();
/*     */ 
/*     */ 
/*     */           
/* 325 */           this.archivoService.eliminarArchivoDrive(Constantes.nameFolderImgCatalogo, nombreImagen);
/* 326 */           this.ImagenCatalogoRepository.delete(imgEntity);
/*     */         } 
/*     */         
/* 329 */         System.out.println("LISTA CATALOGOS TAM: " + entity.getCatalogo().size());
/*     */         
/* 331 */         for (MultipartFile catalogoitem : entity.getCatalogo()) {
/* 332 */           ImagenesCatalogoEntity imagenesCatalogoEntity = new ImagenesCatalogoEntity();
/* 333 */           imagenesCatalogoEntity.setId(Integer.valueOf(this.ImagenCatalogoRepository.getIdPrimaryKey()));
/* 334 */           imagenesCatalogoEntity.setCodigo(this.ImagenCatalogoRepository.getCodigo());
/*     */           
/* 336 */           String nombre = catalogoEntity2.getNit() + "-" + catalogoEntity2.getNit() + this.ImagenCatalogoRepository.getCodigo();
/* 337 */           System.out.println("NOMBRECATALOGOLOG: " + nombre);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 342 */           imagenesCatalogoEntity.setImagen(nombre);
/*     */           
/* 344 */           String idArchivoCatalogoDrive = this.archivoService.guargarArchivoDrive(Constantes.nameFolderImgCatalogo, catalogoitem, nombre);
/* 345 */           imagenesCatalogoEntity.setImagenDriveId(idArchivoCatalogoDrive);
/*     */ 
/*     */           
/* 348 */           imagenesCatalogoEntity.setEstado(Integer.valueOf(1));
/*     */           
/* 350 */           ImagenesCatalogoEntity imagenesCatalogoEntity2 = (ImagenesCatalogoEntity)this.ImagenCatalogoRepository.save(imagenesCatalogoEntity);
/* 351 */           array_imagenes_catalogo.add(imagenesCatalogoEntity2);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 356 */       entity.setImagenesCatalogos(array_imagenes_catalogo);
/*     */ 
/*     */       
/* 359 */       if (!entity.getLogo().isEmpty()) {
/*     */         
/* 361 */         this.archivoService.eliminarArchivo(Constantes.nameFolderLogoCatalogo, catalogoEntity2.getNombrelogo());
/*     */         
/* 363 */         this.archivoService.eliminarArchivoDrive(Constantes.nameFolderLogoCatalogo, catalogoEntity2.getNombrelogo());
/*     */         
/* 365 */         String nombre = catalogoEntity2.getNit() + catalogoEntity2.getNit();
/*     */ 
/*     */ 
/*     */         
/* 369 */         entity.setNombrelogo(nombre);
/*     */         
/* 371 */         String idArchivoLogoDrive = this.archivoService.guargarArchivoDrive(Constantes.nameFolderLogoCatalogo, entity.getLogo(), nombre);
/* 372 */         entity.setNombrelogoDriveId(idArchivoLogoDrive);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 377 */       entity = (CatalogoEntity)this.genericRepository.save(entity);
/* 378 */       return entity;
/* 379 */     } catch (Exception e) {
/* 380 */       e.printStackTrace();
/* 381 */       System.out.println(e.getMessage());
/* 382 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\CatalogoServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */