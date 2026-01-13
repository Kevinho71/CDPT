 package app.service;
 
 import com.google.api.services.drive.Drive;
 import com.google.api.services.drive.model.File;
 import java.io.IOException;
 import java.util.Collections;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;


 @Service
 public class GoogleDriveService
 {
   String folderId = "172Z1cDf3Wa4W9ByDxcF8oG9kx5Kn_XAC";
   
   private final Drive driveService;
   
   @Autowired
   public GoogleDriveService(Drive driveService) {
     this.driveService = driveService;
   }
   
   public String createFolder(String folderName) throws IOException {
     File fileMetadata = new File();
     fileMetadata.setName(folderName);
     fileMetadata.setParents(Collections.singletonList(this.folderId));
     fileMetadata.setMimeType("application/vnd.google-apps.folder");


     File file = (File)this.driveService.files().create(fileMetadata).setFields("id").execute();
     return file.getId();
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\GoogleDriveService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */