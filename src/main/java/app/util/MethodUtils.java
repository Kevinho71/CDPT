/*    */ package BOOT-INF.classes.app.util;
/*    */ 
/*    */ import com.google.zxing.BarcodeFormat;
/*    */ import com.google.zxing.WriterException;
/*    */ import com.google.zxing.client.j2se.MatrixToImageConfig;
/*    */ import com.google.zxing.client.j2se.MatrixToImageWriter;
/*    */ import com.google.zxing.common.BitMatrix;
/*    */ import com.google.zxing.qrcode.QRCodeWriter;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.nio.file.FileSystems;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MethodUtils
/*    */ {
/*    */   private static final String charset = "UTF-8";
/*    */   
/*    */   public static byte[] generateByteQRCode(String text, int width, int height) {
/* 24 */     ByteArrayOutputStream outputStream = null;
/* 25 */     QRCodeWriter qrCodeWriter = new QRCodeWriter();
/*    */     try {
/* 27 */       outputStream = new ByteArrayOutputStream();
/* 28 */       BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
/* 29 */       MatrixToImageConfig config = new MatrixToImageConfig(-16777216, -1);
/* 30 */       MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, config);
/* 31 */     } catch (WriterException|java.io.IOException e) {
/* 32 */       e.printStackTrace();
/*    */     } 
/* 34 */     return outputStream.toByteArray();
/*    */   }
/*    */   
/*    */   public static void generateImageQRCode(String text, int width, int height, String path) {
/* 38 */     QRCodeWriter qrCodeWriter = new QRCodeWriter();
/*    */     try {
/* 40 */       BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
/* 41 */       MatrixToImageWriter.writeToPath(bitMatrix, "PNG", FileSystems.getDefault().getPath(path, new String[0]));
/*    */     
/*    */     }
/* 44 */     catch (WriterException|java.io.IOException e) {
/* 45 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\ap\\util\MethodUtils.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */