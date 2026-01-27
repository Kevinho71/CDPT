 package app.common.util;
 
 import jakarta.servlet.http.HttpServletRequest;
 import org.springframework.core.io.DefaultResourceLoader;
 import org.springframework.core.io.Resource;
 import org.springframework.core.io.ResourceLoader;


 public class URIS
 {
   private ResourceLoader resourceLoader = (ResourceLoader)new DefaultResourceLoader();
   public static String jasperReport = "/static/jasperreports/";


   public static String imgJasperReport = "/static/img/report/";


   public String GetDirecccionCsvs(HttpServletRequest req) {
     return obtenerRutaCarpetaRecursos("csvs");
   }
   
   public String GetDirecccionExcels(HttpServletRequest req) {
     return obtenerRutaCarpetaRecursos("excels");
   }
   
   public String GetDirecccionTextos(HttpServletRequest req) {
     return obtenerRutaCarpetaRecursos("textos");
   }
   
   public String GetDirecccionFotos(HttpServletRequest req) {
     return obtenerRutaCarpetaRecursos("fotos");
   }


   public String obtenerRutaCarpetaRecursos(String nombreCarpeta) {
     try {
       Resource resource = this.resourceLoader.getResource("classpath:static/archivos/" + nombreCarpeta);
       return resource.getFile().getAbsolutePath();
     } catch (Exception e) {
       System.out.println("Error al obtener la ruta de la carpeta de recursos: " + e.getMessage());
       return null;
     } 
   }


   public boolean setPermitionFolfer(String ruta) {
     if (checkOS().contains("Windows")) {
       System.out.println("ENTRO WINDOWS");
       
       try {
         Process p = Runtime.getRuntime().exec("takeown /f " + ruta + " /r");
         return true;
       }
       catch (Exception err) {
         System.out.println(err.getMessage());
         
         return false;
       } 
     } 
     if (checkOS().contains("Linux")) {
       try {
         System.out.println("ENTRO linux permisos");
         Process p = Runtime.getRuntime().exec("chmod -R 777 /opt/");
         Process p1 = Runtime.getRuntime().exec("chown -R postgres:postgres /opt/");
         return true;
       }
       catch (Exception err) {
         System.out.println(err.getMessage());
         
         return false;
       } 
     }
     return false;
   }


   public String checkOS() {
     String os = System.getProperty("os.name");
     return os;
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\ap\\util\URIS.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */