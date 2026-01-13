 package app.util;
 
 import com.google.zxing.BarcodeFormat;
 import com.google.zxing.WriterException;
 import com.google.zxing.client.j2se.MatrixToImageConfig;
 import com.google.zxing.client.j2se.MatrixToImageWriter;
 import com.google.zxing.common.BitMatrix;
 import com.google.zxing.qrcode.QRCodeWriter;
 import java.io.ByteArrayOutputStream;
 import java.nio.file.FileSystems;


 public class MethodUtils
 {
   private static final String charset = "UTF-8";
   
   public static byte[] generateByteQRCode(String text, int width, int height) {
     ByteArrayOutputStream outputStream = null;
     QRCodeWriter qrCodeWriter = new QRCodeWriter();
     try {
       outputStream = new ByteArrayOutputStream();
       BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
       MatrixToImageConfig config = new MatrixToImageConfig(-16777216, -1);
       MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, config);
     } catch (WriterException|java.io.IOException e) {
       e.printStackTrace();
     } 
     return outputStream.toByteArray();
   }
   
   public static void generateImageQRCode(String text, int width, int height, String path) {
     QRCodeWriter qrCodeWriter = new QRCodeWriter();
     try {
       BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
       MatrixToImageWriter.writeToPath(bitMatrix, "PNG", FileSystems.getDefault().getPath(path, new String[0]));
     
     }
     catch (WriterException|java.io.IOException e) {
       e.printStackTrace();
     } 
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\ap\\util\MethodUtils.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */