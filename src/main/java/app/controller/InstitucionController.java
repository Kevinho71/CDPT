 package app.controller;
 
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @RequestMapping({"/institucion/"})
 @Controller
 public class InstitucionController {
   @RequestMapping({"gestion"})
   public String gestion() {
     return "institucion/gestion";
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\controller\InstitucionController.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */