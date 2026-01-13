 package app;
 
 import app.CadetappApplication;
 import org.springframework.boot.builder.SpringApplicationBuilder;
 import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
 
 public class ServletInitializer
   extends SpringBootServletInitializer {
   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
     return application.sources(new Class[] { CadetappApplication.class });
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\ServletInitializer.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */