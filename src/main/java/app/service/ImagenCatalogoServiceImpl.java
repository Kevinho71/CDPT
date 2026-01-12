/*     */ package BOOT-INF.classes.app.service;
/*     */ 
/*     */ import app.entity.ImagenesCatalogoEntity;
/*     */ import app.repository.GenericRepositoryNormal;
/*     */ import app.repository.ImagenCatalogoRepository;
/*     */ import app.service.ArchivoService;
/*     */ import app.service.GenericServiceImplNormal;
/*     */ import app.service.ImagenCatalogoService;
/*     */ import app.util.QRCodeGeneratorService;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class ImagenCatalogoServiceImpl
/*     */   extends GenericServiceImplNormal<ImagenesCatalogoEntity, Integer>
/*     */   implements ImagenCatalogoService
/*     */ {
/*     */   @Autowired
/*     */   private ImagenCatalogoRepository ImagenCatalogoRepository;
/*     */   @Autowired
/*     */   private ArchivoService archivoService;
/*     */   @Autowired
/*     */   QRCodeGeneratorService qrCodeGeneratorService;
/*     */   @Value("${server.port}")
/*     */   private static String puertoservidor;
/*     */   
/*     */   ImagenCatalogoServiceImpl(GenericRepositoryNormal<ImagenesCatalogoEntity, Integer> genericRepository) {
/*  41 */     super(genericRepository);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIdPrimaryKey() throws Exception {
/*     */     try {
/*  50 */       int id = this.ImagenCatalogoRepository.getIdPrimaryKey();
/*  51 */       return id;
/*  52 */     } catch (Exception e) {
/*  53 */       System.out.println(e.getMessage());
/*     */       
/*  55 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getCodigo() throws Exception {
/*     */     try {
/*  62 */       int total = this.ImagenCatalogoRepository.getCodigo().intValue();
/*  63 */       return Integer.valueOf(total);
/*  64 */     } catch (Exception e) {
/*  65 */       System.out.println(e.getMessage());
/*     */       
/*  67 */       return Integer.valueOf(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public List<ImagenesCatalogoEntity> findAll(int estado, String search, int length, int start) throws Exception {
/*     */     try {
/*  77 */       List<ImagenesCatalogoEntity> entities = this.ImagenCatalogoRepository.findAll(estado, search, length, start);
/*  78 */       return entities;
/*  79 */     } catch (Exception e) {
/*  80 */       System.out.println(e.getMessage());
/*  81 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public void updateStatus(int status, int id) throws Exception {
/*     */     try {
/*  91 */       System.out.println("estado:" + status + " id:" + id);
/*  92 */       this.ImagenCatalogoRepository.updateStatus(Integer.valueOf(status), Integer.valueOf(id));
/*     */     }
/*  94 */     catch (Exception e) {
/*  95 */       System.out.println(e.getMessage());
/*  96 */       e.printStackTrace();
/*  97 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getTotAll(String search, int estado) throws Exception {
/*     */     try {
/* 105 */       int total = this.ImagenCatalogoRepository.getTotAll(search, Integer.valueOf(estado)).intValue();
/* 106 */       return Integer.valueOf(total);
/* 107 */     } catch (Exception e) {
/* 108 */       System.out.println(e.getMessage());
/*     */       
/* 110 */       return Integer.valueOf(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public ImagenesCatalogoEntity update(Integer id, ImagenesCatalogoEntity entidad) throws Exception {
/*     */     try {
/* 123 */       System.out.println("Modificar1:" + entidad.toString());
/*     */ 
/*     */       
/* 126 */       ImagenesCatalogoEntity entitymod = new ImagenesCatalogoEntity();
/*     */       
/* 128 */       entitymod = (ImagenesCatalogoEntity)this.genericRepository.save(entidad);
/* 129 */       return entitymod;
/* 130 */     } catch (Exception e) {
/* 131 */       e.printStackTrace();
/* 132 */       System.out.println(e.getMessage());
/* 133 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\ImagenCatalogoServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */