/*    */ package BOOT-INF.classes.app.service;
/*    */ 
/*    */ import com.google.api.services.drive.Drive;
/*    */ import com.google.api.services.drive.model.File;
/*    */ import java.io.IOException;
/*    */ import java.util.Collections;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class GoogleDriveService
/*    */ {
/* 20 */   String folderId = "172Z1cDf3Wa4W9ByDxcF8oG9kx5Kn_XAC";
/*    */   
/*    */   private final Drive driveService;
/*    */   
/*    */   @Autowired
/*    */   public GoogleDriveService(Drive driveService) {
/* 26 */     this.driveService = driveService;
/*    */   }
/*    */   
/*    */   public String createFolder(String folderName) throws IOException {
/* 30 */     File fileMetadata = new File();
/* 31 */     fileMetadata.setName(folderName);
/* 32 */     fileMetadata.setParents(Collections.singletonList(this.folderId));
/* 33 */     fileMetadata.setMimeType("application/vnd.google-apps.folder");
/*    */ 
/*    */ 
/*    */     
/* 37 */     File file = (File)this.driveService.files().create(fileMetadata).setFields("id").execute();
/* 38 */     return file.getId();
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\GoogleDriveService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */