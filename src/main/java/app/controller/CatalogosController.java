/*    */ package BOOT-INF.classes.app.controller;
/*    */ 
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.Model;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ @RequestMapping({"/catalogos"})
/*    */ public class CatalogosController
/*    */ {
/*    */   @RequestMapping({"/gestion"})
/*    */   public String alamat(Model model) {
/* 18 */     System.out.println("LLEGOOO cat");
/* 19 */     return "catalogos/gestion_drive";
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\controller\CatalogosController.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */