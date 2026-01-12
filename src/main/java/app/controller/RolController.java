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
/*    */ @Controller
/*    */ @RequestMapping({"/roles"})
/*    */ public class RolController
/*    */ {
/*    */   @RequestMapping({"/gestion"})
/*    */   public String alamat(Model model) {
/* 17 */     return "roles/gestion";
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\controller\RolController.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */