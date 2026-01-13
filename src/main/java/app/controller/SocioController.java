 package app.controller;
 
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;


 @Controller
 @RequestMapping({"/socios"})
 public class SocioController
 {
   @RequestMapping({"/gestion"})
   public String alamat(Model model) {
     System.out.println("LLEGO SOC");
     return "socios/gestion";
   }


   @RequestMapping({"estadosocio/{id}"})
   public String generateImageQRCode(@PathVariable("id") String id, Model modelo) {
     System.out.println("DATO:" + id);
     modelo.addAttribute("codigosocio", id);
     return "socios/estadosociodrive";
   }
   
   @RequestMapping({"/empresas/{id}"})
   public String showCatalogoSocio(@PathVariable("id") String id, Model modelo) {
     System.out.println("DATO:" + id);
     modelo.addAttribute("codigosocio", id);
     return "socios/catalogosociodrive";
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\controller\SocioController.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */