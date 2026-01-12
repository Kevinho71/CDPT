/*    */ package BOOT-INF.classes.app.controller;
/*    */ 
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.Model;
/*    */ import org.springframework.web.bind.annotation.PathVariable;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ @RequestMapping({"/socios"})
/*    */ public class SocioController
/*    */ {
/*    */   @RequestMapping({"/gestion"})
/*    */   public String alamat(Model model) {
/* 24 */     System.out.println("LLEGO SOC");
/* 25 */     return "socios/gestion";
/*    */   }
/*    */ 
/*    */   
/*    */   @RequestMapping({"estadosocio/{id}"})
/*    */   public String generateImageQRCode(@PathVariable("id") String id, Model modelo) {
/* 31 */     System.out.println("DATO:" + id);
/* 32 */     modelo.addAttribute("codigosocio", id);
/* 33 */     return "socios/estadosociodrive";
/*    */   }
/*    */   
/*    */   @RequestMapping({"/empresas/{id}"})
/*    */   public String showCatalogoSocio(@PathVariable("id") String id, Model modelo) {
/* 38 */     System.out.println("DATO:" + id);
/* 39 */     modelo.addAttribute("codigosocio", id);
/* 40 */     return "socios/catalogosociodrive";
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\controller\SocioController.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */