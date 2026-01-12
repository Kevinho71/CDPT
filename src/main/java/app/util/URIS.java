/*    */ package BOOT-INF.classes.app.util;
/*    */ 
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.springframework.core.io.DefaultResourceLoader;
/*    */ import org.springframework.core.io.Resource;
/*    */ import org.springframework.core.io.ResourceLoader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class URIS
/*    */ {
/* 15 */   private ResourceLoader resourceLoader = (ResourceLoader)new DefaultResourceLoader();
/* 16 */   public static String jasperReport = "/static/jasperreports/";
/*    */ 
/*    */   
/* 19 */   public static String imgJasperReport = "/static/img/report/";
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
/*    */   public String GetDirecccionCsvs(HttpServletRequest req) {
/* 31 */     return obtenerRutaCarpetaRecursos("csvs");
/*    */   }
/*    */   
/*    */   public String GetDirecccionExcels(HttpServletRequest req) {
/* 35 */     return obtenerRutaCarpetaRecursos("excels");
/*    */   }
/*    */   
/*    */   public String GetDirecccionTextos(HttpServletRequest req) {
/* 39 */     return obtenerRutaCarpetaRecursos("textos");
/*    */   }
/*    */   
/*    */   public String GetDirecccionFotos(HttpServletRequest req) {
/* 43 */     return obtenerRutaCarpetaRecursos("fotos");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String obtenerRutaCarpetaRecursos(String nombreCarpeta) {
/*    */     try {
/* 50 */       Resource resource = this.resourceLoader.getResource("classpath:static/archivos/" + nombreCarpeta);
/* 51 */       return resource.getFile().getAbsolutePath();
/* 52 */     } catch (Exception e) {
/* 53 */       System.out.println("Error al obtener la ruta de la carpeta de recursos: " + e.getMessage());
/* 54 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean setPermitionFolfer(String ruta) {
/* 61 */     if (checkOS().contains("Windows")) {
/* 62 */       System.out.println("ENTRO WINDOWS");
/*    */       
/*    */       try {
/* 65 */         Process p = Runtime.getRuntime().exec("takeown /f " + ruta + " /r");
/* 66 */         return true;
/*    */       }
/* 68 */       catch (Exception err) {
/* 69 */         System.out.println(err.getMessage());
/*    */         
/* 71 */         return false;
/*    */       } 
/*    */     } 
/* 74 */     if (checkOS().contains("Linux")) {
/*    */       try {
/* 76 */         System.out.println("ENTRO linux permisos");
/* 77 */         Process p = Runtime.getRuntime().exec("chmod -R 777 /opt/");
/* 78 */         Process p1 = Runtime.getRuntime().exec("chown -R postgres:postgres /opt/");
/* 79 */         return true;
/*    */       }
/* 81 */       catch (Exception err) {
/* 82 */         System.out.println(err.getMessage());
/*    */         
/* 84 */         return false;
/*    */       } 
/*    */     }
/* 87 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String checkOS() {
/* 93 */     String os = System.getProperty("os.name");
/* 94 */     return os;
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\ap\\util\URIS.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */