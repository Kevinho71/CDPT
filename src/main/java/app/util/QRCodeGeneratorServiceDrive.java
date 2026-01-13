 package app.util;
 
 import app.service.ArchivoService;
 import app.util.Constantes;
 import com.google.zxing.BarcodeFormat;
 import com.google.zxing.EncodeHintType;
 import com.google.zxing.MultiFormatWriter;
 import com.google.zxing.WriterException;
 import com.google.zxing.client.j2se.MatrixToImageWriter;
 import com.google.zxing.common.BitMatrix;
 import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
 import java.io.File;
 import java.io.IOException;
 import java.nio.file.Files;
 import java.nio.file.Path;
 import java.nio.file.Paths;
 import java.nio.file.attribute.FileAttribute;
 import java.security.GeneralSecurityException;
 import java.util.HashMap;
 import java.util.Map;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.io.DefaultResourceLoader;
 import org.springframework.core.io.ResourceLoader;
 import org.springframework.stereotype.Service;


 @Service
 public class QRCodeGeneratorServiceDrive
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
       String outputPath = prepareOutputFileName(nombre);


       File localQRCodeFile = generateQRCodeFile(message, outputPath);


       this.archivoService.guargarArchivoDriveFile(Constantes.nameFolderQrSocio, localQRCodeFile, nombre + ".png");


       Files.deleteIfExists(localQRCodeFile.toPath());
     }
     catch (WriterException|IOException e) {
       e.printStackTrace();
     } catch (GeneralSecurityException e) {
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


   private File generateQRCodeFile(String data, String path) throws WriterException, IOException {
     Map<EncodeHintType, Object> hints = new HashMap<>();
     hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
     BitMatrix matrix = (new MultiFormatWriter()).encode(new String(data.getBytes("UTF-8"), "UTF-8"), BarcodeFormat.QR_CODE, 600, 600, hints);
     File qrFile = new File(path);
     MatrixToImageWriter.writeToFile(matrix, "png", qrFile);
     return qrFile;
   }


   public String obtenerRutaArchivos(String carpeta) {
     String sistemaOperativo = System.getProperty("os.name").toLowerCase();
     System.out.println("SISTEMA OPERATIVO: " + sistemaOperativo);
     String rutaCarpeta = "";
     
     try {
       if (sistemaOperativo.contains("linux")) {
         rutaCarpeta = Paths.get("/home", new String[] { carpeta }).toString();
       } else if (sistemaOperativo.contains("windows")) {
         rutaCarpeta = Paths.get("C:\\", new String[] { carpeta }).toString();
       } 
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println("Error al obtener la ruta de archivos: " + e.getMessage());
     } 
     
     return rutaCarpeta;
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\ap\\util\QRCodeGeneratorServiceDrive.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */