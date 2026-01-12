/*    */ package BOOT-INF.classes.app.restcontroller;
/*    */ 
/*    */ import app.service.GoogleDriveService;
/*    */ import org.springframework.web.bind.annotation.GetMapping;
/*    */ import org.springframework.web.bind.annotation.RequestParam;
/*    */ import org.springframework.web.bind.annotation.RestController;
/*    */ 
/*    */ 
/*    */ @RestController
/*    */ public class RestGoogleDriveController
/*    */ {
/*    */   private final GoogleDriveService googleDriveService;
/*    */   
/*    */   public RestGoogleDriveController(GoogleDriveService googleDriveService) {
/* 15 */     this.googleDriveService = googleDriveService;
/*    */   }
/*    */   
/*    */   @GetMapping({"/createFolder"})
/*    */   public String createFolder(@RequestParam String folderName) {
/*    */     try {
/* 21 */       return this.googleDriveService.createFolder(folderName);
/* 22 */     } catch (Exception e) {
/* 23 */       e.printStackTrace();
/* 24 */       return "Error creating folder: " + e.getMessage();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\restcontroller\RestGoogleDriveController.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */