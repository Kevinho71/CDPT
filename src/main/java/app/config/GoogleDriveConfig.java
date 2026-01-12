/*     */ package BOOT-INF.classes.app.config;
/*     */ 
/*     */ import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
/*     */ import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
/*     */ import com.google.api.client.http.HttpRequestInitializer;
/*     */ import com.google.api.client.http.HttpTransport;
/*     */ import com.google.api.client.http.javanet.NetHttpTransport;
/*     */ import com.google.api.client.json.JsonFactory;
/*     */ import com.google.api.client.json.jackson2.JacksonFactory;
/*     */ import com.google.api.services.drive.Drive;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Collections;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Configuration
/*     */ public class GoogleDriveConfig
/*     */ {
/*     */   private static final String APPLICATION_NAME = "Google Drive API Java";
/*  85 */   private static final JsonFactory JSON_FACTORY = (JsonFactory)JacksonFactory.getDefaultInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String CREDENTIALS_FILE_PATH = "/cadet-sistema-97e2062b979e.json";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Bean
/*     */   public Drive getDriveService() throws IOException, GeneralSecurityException {
/* 107 */     NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
/* 108 */     InputStream in = app.config.GoogleDriveConfig.class.getResourceAsStream("/cadet-sistema-97e2062b979e.json");
/* 109 */     if (in == null) {
/* 110 */       throw new IOException("Resource not found: /cadet-sistema-97e2062b979e.json");
/*     */     }
/*     */     
/* 113 */     GoogleCredential credential = GoogleCredential.fromStream(in).createScoped(Collections.singleton("https://www.googleapis.com/auth/drive"));
/* 114 */     return (new Drive.Builder((HttpTransport)HTTP_TRANSPORT, JSON_FACTORY, (HttpRequestInitializer)credential))
/* 115 */       .setApplicationName("Google Drive API Java")
/* 116 */       .build();
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\config\GoogleDriveConfig.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */