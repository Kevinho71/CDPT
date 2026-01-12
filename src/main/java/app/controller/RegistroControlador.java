/*    */ package BOOT-INF.classes.app.controller;
/*    */ 
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.Model;
/*    */ import org.springframework.web.bind.annotation.GetMapping;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ public class RegistroControlador
/*    */ {
/*    */   @GetMapping({"/login"})
/*    */   public String iniciarSesion() {
/* 18 */     return "login";
/*    */   }
/*    */   
/*    */   @GetMapping({"/"})
/*    */   public String verPaginaInicio(Model model) {
/* 23 */     return "inicio";
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\controller\RegistroControlador.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */