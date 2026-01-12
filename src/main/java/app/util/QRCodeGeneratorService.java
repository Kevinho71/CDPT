/*     */ package BOOT-INF.classes.app.util;
/*     */ 
/*     */ import app.service.ArchivoService;
/*     */ import app.util.Constantes;
/*     */ import app.util.URIS;
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.MultiFormatWriter;
/*     */ import com.google.zxing.WriterException;
/*     */ import com.google.zxing.client.j2se.MatrixToImageWriter;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.security.GeneralSecurityException;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.io.DefaultResourceLoader;
/*     */ import org.springframework.core.io.ResourceLoader;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class QRCodeGeneratorService
/*     */ {
/*     */   private static final String CHARSET = "UTF-8";
/*     */   private static final String FILE_EXTENSION = "png";
/*     */   private static final int QR_WIDTH = 600;
/*     */   private static final int QR_HEIGHT = 600;
/*     */   @Autowired
/*     */   private ArchivoService archivoService;
/*  46 */   private ResourceLoader resourceLoader = (ResourceLoader)new DefaultResourceLoader();
/*  47 */   private String ruta_logos = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateQRCode(String message, String nombre) {
/*  58 */     System.out.println("### Generating QRCode ###");
/*     */     try {
/*  60 */       String finalMessage = message;
/*  61 */       System.out.println("Final Input Message: " + finalMessage);
/*  62 */       String outputPath = prepareOutputFileName(nombre);
/*     */ 
/*     */       
/*  65 */       processQRCode(finalMessage, outputPath, "UTF-8", 600, 600);
/*     */ 
/*     */       
/*  68 */       File qrFile = new File(outputPath);
/*  69 */       this.archivoService.guargarArchivoDriveFile(Constantes.nameFolderQrSocio, qrFile, nombre + ".png");
/*     */     }
/*  71 */     catch (WriterException|IOException e) {
/*  72 */       e.printStackTrace();
/*  73 */     } catch (GeneralSecurityException e) {
/*     */       
/*  75 */       e.printStackTrace();
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
/*     */   private String prepareOutputFileName(String nombre) throws IOException {
/*  89 */     String ruta = obtenerRutaArchivos(Constantes.nameFolderQrSocio);
/*     */     
/*  91 */     if (ruta == null || ruta.isEmpty()) {
/*  92 */       throw new IOException("No se pudo determinar la ruta de almacenamiento.");
/*     */     }
/*     */     
/*  95 */     Path rutaDirectorio = Paths.get(ruta, new String[0]).toAbsolutePath();
/*  96 */     if (!Files.exists(rutaDirectorio, new java.nio.file.LinkOption[0])) {
/*  97 */       System.out.println("*********CREANDO CARPETA DE QRS");
/*  98 */       Files.createDirectories(rutaDirectorio, (FileAttribute<?>[])new FileAttribute[0]);
/*     */     } 
/*     */     
/* 101 */     this.ruta_logos = Paths.get(ruta, new String[0]).toAbsolutePath().resolve(nombre).toString();
/* 102 */     System.out.println("RUTA DE QR FOLDER: " + this.ruta_logos);
/* 103 */     return this.ruta_logos + ".png";
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
/*     */   private void processQRCode(String data, String path, String charset, int height, int width) throws WriterException, IOException {
/* 118 */     BitMatrix matrix = (new MultiFormatWriter()).encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
/* 119 */     MatrixToImageWriter.writeToFile(matrix, "png", new File(path));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String obtenerRutaArchivos(String carpeta) {
/* 129 */     URIS uris = new URIS();
/* 130 */     String sistemaOperativo = uris.checkOS();
/* 131 */     System.out.println("SISTEMA OPERATIVO: " + sistemaOperativo);
/* 132 */     String rutaCarpeta = "";
/*     */     
/*     */     try {
/* 135 */       if (sistemaOperativo.contains("Linux")) {
/* 136 */         darPermisosCarpeta("/home");
/* 137 */         rutaCarpeta = Paths.get("/home", new String[] { carpeta }).toString();
/* 138 */       } else if (sistemaOperativo.contains("Windows")) {
/* 139 */         rutaCarpeta = Paths.get("C:\\", new String[] { carpeta }).toString();
/*     */       } 
/* 141 */     } catch (Exception e) {
/* 142 */       e.printStackTrace();
/* 143 */       System.out.println("Error al obtener la ruta de archivos: " + e.getMessage());
/*     */     } 
/*     */     
/* 146 */     return rutaCarpeta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void darPermisosCarpeta(String rutaBase) throws IOException {
/* 156 */     Process p = Runtime.getRuntime().exec("chmod -R 755 " + rutaBase);
/*     */     try {
/* 158 */       p.waitFor();
/* 159 */     } catch (InterruptedException e) {
/* 160 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateQRCodeContent(String newContent, String nombre) {
/*     */     try {
/* 172 */       String rutaCatalogos = obtenerRutaArchivos(Constantes.nameFolderQrSocio);
/*     */       
/* 174 */       if (rutaCatalogos == null || rutaCatalogos.isEmpty()) {
/* 175 */         throw new IOException("No se pudo determinar la ruta de almacenamiento.");
/*     */       }
/*     */       
/* 178 */       Path rutaDirectorio = Paths.get(rutaCatalogos, new String[0]).toAbsolutePath();
/* 179 */       if (!Files.exists(rutaDirectorio, new java.nio.file.LinkOption[0])) {
/* 180 */         Files.createDirectories(rutaDirectorio, (FileAttribute<?>[])new FileAttribute[0]);
/*     */       }
/*     */       
/* 183 */       this.ruta_logos = Paths.get(rutaCatalogos, new String[0]).toAbsolutePath().resolve(nombre + ".png").toString();
/*     */       
/* 185 */       if (!this.ruta_logos.isEmpty()) {
/* 186 */         File qrCodeFile = new File(this.ruta_logos);
/* 187 */         if (qrCodeFile.exists()) {
/* 188 */           BufferedImage qrCodeImage = ImageIO.read(qrCodeFile);
/* 189 */           BitMatrix bitMatrix = (new MultiFormatWriter()).encode(newContent, BarcodeFormat.QR_CODE, qrCodeImage.getWidth(), qrCodeImage.getHeight());
/* 190 */           FileOutputStream fos = new FileOutputStream(qrCodeFile); 
/* 191 */           try { MatrixToImageWriter.writeToStream(bitMatrix, "png", fos);
/* 192 */             fos.close(); } catch (Throwable throwable) { try { fos.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 193 */            System.out.println("QR code updated successfully.");
/*     */         } else {
/* 195 */           System.out.println("El archivo QR no existe, generando uno nuevo.");
/* 196 */           generateQRCode(newContent, nombre);
/*     */         } 
/*     */       } else {
/* 199 */         System.out.println("NO SE ENCONTRO EL QR: " + this.ruta_logos);
/* 200 */         generateQRCode(newContent, nombre);
/*     */       }
/*     */     
/* 203 */     } catch (IOException|WriterException e) {
/* 204 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\ap\\util\QRCodeGeneratorService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */