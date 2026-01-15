 package app.common.util;
 
 import app.common.util.ArchivoService;
 import app.common.util.Constantes;
 import app.common.util.URIS;
 import com.google.zxing.BarcodeFormat;
 import com.google.zxing.MultiFormatWriter;
 import com.google.zxing.WriterException;
 import com.google.zxing.client.j2se.MatrixToImageWriter;
 import com.google.zxing.common.BitMatrix;
 import java.awt.image.BufferedImage;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.nio.file.Files;
 import java.nio.file.Path;
 import java.nio.file.Paths;
 import java.nio.file.attribute.FileAttribute;
 import java.security.GeneralSecurityException;
 import javax.imageio.ImageIO;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.io.DefaultResourceLoader;
 import org.springframework.core.io.ResourceLoader;
 import org.springframework.stereotype.Service;


 @Service
 public class QRCodeGeneratorService
 {
   private static final String CHARSET = "UTF-8";
   private static final String FILE_EXTENSION = "png";
   private static final int QR_WIDTH = 600;
   private static final int QR_HEIGHT = 600;
   @Autowired
   private ArchivoService archivoService;
   private ResourceLoader resourceLoader = (ResourceLoader)new DefaultResourceLoader();
   private String ruta_logos = "";


   public void generateQRCode(String message, String nombre) {
     System.out.println("### Generating QRCode ###");
     try {
       String finalMessage = message;
       System.out.println("Final Input Message: " + finalMessage);
       String outputPath = prepareOutputFileName(nombre);


       processQRCode(finalMessage, outputPath, "UTF-8", 600, 600);


       System.out.println("QR Code generado localmente en: " + outputPath);
     }
     catch (WriterException|IOException e) {
       e.printStackTrace();
     }
   }


   private String prepareOutputFileName(String nombre) throws IOException {
     String ruta = obtenerRutaArchivos(Constantes.nameFolderQrSocio);
     
     if (ruta == null || ruta.isEmpty()) {
       throw new IOException("No se pudo determinar la ruta de almacenamiento.");
     }
     
     Path rutaDirectorio = Paths.get(ruta, new String[0]).toAbsolutePath();
     if (!Files.exists(rutaDirectorio, new java.nio.file.LinkOption[0])) {
       System.out.println("*********CREANDO CARPETA DE QRS");
       Files.createDirectories(rutaDirectorio, (FileAttribute<?>[])new FileAttribute[0]);
     } 
     
     this.ruta_logos = Paths.get(ruta, new String[0]).toAbsolutePath().resolve(nombre).toString();
     System.out.println("RUTA DE QR FOLDER: " + this.ruta_logos);
     return this.ruta_logos + ".png";
   }


   private void processQRCode(String data, String path, String charset, int height, int width) throws WriterException, IOException {
     BitMatrix matrix = (new MultiFormatWriter()).encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
     MatrixToImageWriter.writeToFile(matrix, "png", new File(path));
   }


   public String obtenerRutaArchivos(String carpeta) {
     URIS uris = new URIS();
     String sistemaOperativo = uris.checkOS();
     System.out.println("SISTEMA OPERATIVO: " + sistemaOperativo);
     String rutaCarpeta = "";
     
     try {
       if (sistemaOperativo.contains("Linux")) {
         darPermisosCarpeta("/home");
         rutaCarpeta = Paths.get("/home", new String[] { carpeta }).toString();
       } else if (sistemaOperativo.contains("Windows")) {
         rutaCarpeta = Paths.get("C:\\", new String[] { carpeta }).toString();
       } 
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println("Error al obtener la ruta de archivos: " + e.getMessage());
     } 
     
     return rutaCarpeta;
   }


   private void darPermisosCarpeta(String rutaBase) throws IOException {
     Process p = Runtime.getRuntime().exec("chmod -R 755 " + rutaBase);
     try {
       p.waitFor();
     } catch (InterruptedException e) {
       e.printStackTrace();
     } 
   }


   public void updateQRCodeContent(String newContent, String nombre) {
     try {
       String rutaCatalogos = obtenerRutaArchivos(Constantes.nameFolderQrSocio);
       
       if (rutaCatalogos == null || rutaCatalogos.isEmpty()) {
         throw new IOException("No se pudo determinar la ruta de almacenamiento.");
       }
       
       Path rutaDirectorio = Paths.get(rutaCatalogos, new String[0]).toAbsolutePath();
       if (!Files.exists(rutaDirectorio, new java.nio.file.LinkOption[0])) {
         Files.createDirectories(rutaDirectorio, (FileAttribute<?>[])new FileAttribute[0]);
       }
       
       this.ruta_logos = Paths.get(rutaCatalogos, new String[0]).toAbsolutePath().resolve(nombre + ".png").toString();
       
       if (!this.ruta_logos.isEmpty()) {
         File qrCodeFile = new File(this.ruta_logos);
         if (qrCodeFile.exists()) {
           BufferedImage qrCodeImage = ImageIO.read(qrCodeFile);
           BitMatrix bitMatrix = (new MultiFormatWriter()).encode(newContent, BarcodeFormat.QR_CODE, qrCodeImage.getWidth(), qrCodeImage.getHeight());
           FileOutputStream fos = new FileOutputStream(qrCodeFile); 
           try { MatrixToImageWriter.writeToStream(bitMatrix, "png", fos);
             fos.close(); } catch (Throwable throwable) { try { fos.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
            System.out.println("QR code updated successfully.");
         } else {
           System.out.println("El archivo QR no existe, generando uno nuevo.");
           generateQRCode(newContent, nombre);
         } 
       } else {
         System.out.println("NO SE ENCONTRO EL QR: " + this.ruta_logos);
         generateQRCode(newContent, nombre);
       }
     
     } catch (IOException|WriterException e) {
       e.printStackTrace();
     } 
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\ap\\util\QRCodeGeneratorService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */