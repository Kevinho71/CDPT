 package app.config;
 
 import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
 import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
 import com.google.api.client.http.HttpRequestInitializer;
 import com.google.api.client.http.HttpTransport;
 import com.google.api.client.http.javanet.NetHttpTransport;
 import com.google.api.client.json.JsonFactory;
 import com.google.api.client.json.gson.GsonFactory;
 import com.google.api.services.drive.Drive;
 import java.io.IOException;
 import java.io.InputStream;
 import java.security.GeneralSecurityException;
 import java.util.Collections;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;


 @Configuration
 public class GoogleDriveConfig
 {
   private static final String APPLICATION_NAME = "Google Drive API Java";
   private static final JsonFactory JSON_FACTORY = (JsonFactory)GsonFactory.getDefaultInstance();


   private static final String CREDENTIALS_FILE_PATH = "/cadet-sistema-97e2062b979e.json";


   @Bean
   public Drive getDriveService() throws IOException, GeneralSecurityException {
     NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
     InputStream in = app.config.GoogleDriveConfig.class.getResourceAsStream("/cadet-sistema-97e2062b979e.json");
     if (in == null) {
       throw new IOException("Resource not found: /cadet-sistema-97e2062b979e.json");
     }
     
     GoogleCredential credential = GoogleCredential.fromStream(in).createScoped(Collections.singleton("https://www.googleapis.com/auth/drive"));
     return (new Drive.Builder((HttpTransport)HTTP_TRANSPORT, JSON_FACTORY, (HttpRequestInitializer)credential))
       .setApplicationName("Google Drive API Java")
       .build();
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\config\GoogleDriveConfig.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */