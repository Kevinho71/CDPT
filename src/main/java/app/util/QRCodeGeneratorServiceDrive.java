/*     */ package BOOT-INF.classes.app.util;
/*     */ 
/*     */ import app.service.ArchivoService;
/*     */ import app.util.Constantes;
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.EncodeHintType;
/*     */ import com.google.zxing.MultiFormatWriter;
/*     */ import com.google.zxing.WriterException;
/*     */ import com.google.zxing.client.j2se.MatrixToImageWriter;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.io.DefaultResourceLoader;
/*     */ import org.springframework.core.io.ResourceLoader;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class QRCodeGeneratorServiceDrive
/*     */ {
/*     */   private static final String CHARSET = "UTF-8";
/*     */   private static final String FILE_EXTENSION = "png";
/*     */   private static final int QR_WIDTH = 600;
/*     */   private static final int QR_HEIGHT = 600;
/*     */   @Autowired
/*     */   private ArchivoService archivoService;
/*  39 */   private ResourceLoader resourceLoader = (ResourceLoader)new DefaultResourceLoader();
/*  40 */   private String ruta_logos = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateQRCode(String message, String nombre) {
/*  49 */     System.out.println("### Generating QRCode ###");
/*     */     
/*     */     try {
/*  52 */       String outputPath = prepareOutputFileName(nombre);
/*     */ 
/*     */       
/*  55 */       File localQRCodeFile = generateQRCodeFile(message, outputPath);
/*     */ 
/*     */       
/*  58 */       this.archivoService.guargarArchivoDriveFile(Constantes.nameFolderQrSocio, localQRCodeFile, nombre + ".png");
/*     */ 
/*     */       
/*  61 */       Files.deleteIfExists(localQRCodeFile.toPath());
/*     */     }
/*  63 */     catch (WriterException|IOException e) {
/*  64 */       e.printStackTrace();
/*  65 */     } catch (GeneralSecurityException e) {
/*  66 */       e.printStackTrace();
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
/*     */   private String prepareOutputFileName(String nombre) throws IOException {
/*  78 */     String ruta = obtenerRutaArchivos(Constantes.nameFolderQrSocio);
/*     */     
/*  80 */     if (ruta == null || ruta.isEmpty()) {
/*  81 */       throw new IOException("No se pudo determinar la ruta de almacenamiento.");
/*     */     }
/*     */     
/*  84 */     Path rutaDirectorio = Paths.get(ruta, new String[0]).toAbsolutePath();
/*  85 */     if (!Files.exists(rutaDirectorio, new java.nio.file.LinkOption[0])) {
/*  86 */       System.out.println("*********CREANDO CARPETA DE QRS");
/*  87 */       Files.createDirectories(rutaDirectorio, (FileAttribute<?>[])new FileAttribute[0]);
/*     */     } 
/*     */     
/*  90 */     this.ruta_logos = Paths.get(ruta, new String[0]).toAbsolutePath().resolve(nombre).toString();
/*  91 */     System.out.println("RUTA DE QR FOLDER: " + this.ruta_logos);
/*  92 */     return this.ruta_logos + ".png";
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
/*     */   private File generateQRCodeFile(String data, String path) throws WriterException, IOException {
/* 107 */     Map<EncodeHintType, Object> hints = new HashMap<>();
/* 108 */     hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
/* 109 */     BitMatrix matrix = (new MultiFormatWriter()).encode(new String(data.getBytes("UTF-8"), "UTF-8"), BarcodeFormat.QR_CODE, 600, 600, hints);
/* 110 */     File qrFile = new File(path);
/* 111 */     MatrixToImageWriter.writeToFile(matrix, "png", qrFile);
/* 112 */     return qrFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String obtenerRutaArchivos(String carpeta) {
/* 122 */     String sistemaOperativo = System.getProperty("os.name").toLowerCase();
/* 123 */     System.out.println("SISTEMA OPERATIVO: " + sistemaOperativo);
/* 124 */     String rutaCarpeta = "";
/*     */     
/*     */     try {
/* 127 */       if (sistemaOperativo.contains("linux")) {
/* 128 */         rutaCarpeta = Paths.get("/home", new String[] { carpeta }).toString();
/* 129 */       } else if (sistemaOperativo.contains("windows")) {
/* 130 */         rutaCarpeta = Paths.get("C:\\", new String[] { carpeta }).toString();
/*     */       } 
/* 132 */     } catch (Exception e) {
/* 133 */       e.printStackTrace();
/* 134 */       System.out.println("Error al obtener la ruta de archivos: " + e.getMessage());
/*     */     } 
/*     */     
/* 137 */     return rutaCarpeta;
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\ap\\util\QRCodeGeneratorServiceDrive.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */