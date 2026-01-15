package app.common.util;

import app.common.util.URIS;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
 import org.springframework.core.io.DefaultResourceLoader;
 import org.springframework.core.io.Resource;
 import org.springframework.core.io.ResourceLoader;
 import org.springframework.stereotype.Service;
 import org.springframework.web.multipart.MultipartFile;
 
 @Service
 public class ArchivoServiceImpl
   implements ArchivoService {
   private ResourceLoader resourceLoader = (ResourceLoader)new DefaultResourceLoader();


   public String guargarArchivo(String nameFolder, MultipartFile archivo, String nombre) throws IOException {
     if (!archivo.isEmpty()) {
       String rutaCatalogos = obtenerRutaArchivos(nameFolder);
       
       if (rutaCatalogos == null || rutaCatalogos.isEmpty()) {
         throw new IOException("No se pudo determinar la ruta de almacenamiento.");
       }
       
       Path rutaDirectorio = Paths.get(rutaCatalogos, new String[0]).toAbsolutePath();
       
       if (!Files.exists(rutaDirectorio, new java.nio.file.LinkOption[0])) {
         Files.createDirectories(rutaDirectorio, (java.nio.file.attribute.FileAttribute<?>[])new java.nio.file.attribute.FileAttribute[0]);
       }
       
       Path rutaArchivo = rutaDirectorio.resolve(nombre);
       System.out.println("Guardando archivo en la ruta: " + rutaArchivo);
       
       Files.copy(archivo.getInputStream(), rutaArchivo, new java.nio.file.CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
       return nombre;
     } 
     System.out.println("El archivo está vacío.");
     return null;
   }


   public String guargarMultipleArchivos(List<MultipartFile> archivos) throws IOException {
     for (MultipartFile archivo : archivos) {
       guargarArchivo("multiple_files_folder", archivo, archivo.getOriginalFilename());
     }
     return "Archivos guardados exitosamente";
   }


   public Path linkArchivo(String folder, String nombreArchivo) throws IOException {
     System.out.println("Nombre de archivo a buscar link antes de eliminar: " + nombreArchivo);
     String rutaCatalogos = obtenerRutaArchivos(folder);
     
     if (rutaCatalogos == null || rutaCatalogos.isEmpty()) {
       throw new IOException("No se pudo determinar la ruta de almacenamiento.");
     }
     
     Path rutaDirectorio = Paths.get(rutaCatalogos, new String[0]).toAbsolutePath();
     
     if (!Files.exists(rutaDirectorio, new java.nio.file.LinkOption[0])) {
       Files.createDirectories(rutaDirectorio, (java.nio.file.attribute.FileAttribute<?>[])new java.nio.file.attribute.FileAttribute[0]);
     }
     
     if (nombreArchivo != null) {
       Path rutaArchivo = rutaDirectorio.resolve(nombreArchivo);
       System.out.println("Ruta de archivo encontrado: " + rutaArchivo);
       if (Files.exists(rutaArchivo, new java.nio.file.LinkOption[0])) {
         return rutaArchivo;
       }
       return null;
     } 
     
     return null;
   }


   public void eliminarArchivo(String folder, String nombreArchivo) throws IOException {
     System.out.println("INTENTANDO ELIMINAR ARCHIVO: " + nombreArchivo);
     Path archivo = linkArchivo(folder, nombreArchivo);
     System.out.println("INTENTANDO ELIMINAR PATH: " + archivo);
     try {
       if (archivo != null) {
         System.out.println("**********ELIMINANDO ARCHIVO: " + archivo);
         Files.deleteIfExists(archivo);
       } 
     } catch (Exception e) {
       System.out.println(e);
     } 
   }


   public String obtenerRutaCarpetaRecursos(String nombreCarpeta) {
     try {
       Resource resource = this.resourceLoader.getResource("classpath:static/" + nombreCarpeta);
       return resource.getFile().getAbsolutePath();
     } catch (Exception e) {
       System.out.println("Error al obtener la ruta de la carpeta de recursos: " + e.getMessage());
       return null;
     } 
   }


   public String obtenerRutaArchivos(String carpeta) {
     URIS uris = new URIS();
     String sistemaOperativo = uris.checkOS();
     System.out.println("INICIANDO APP");
     System.out.println("SISTEMA OPERATIVO: " + sistemaOperativo);
     String rutaCarpeta = "";
     
     try {
       if (sistemaOperativo.contains("Linux")) {
         System.out.println("DANDO PERMISOS A LA CARPETA DE ARCHIVOS");
         darPermisosCarpeta("/home");
         rutaCarpeta = Paths.get("/home", new String[] { carpeta }).toString();
       } else if (sistemaOperativo.contains("Windows")) {
         rutaCarpeta = Paths.get("C:\\", new String[] { carpeta }).toString();
       } 
     } catch (Exception e) {
       e.printStackTrace();
       System.out.println("Error al obtener la ruta de archivos: " + e.getMessage());
     } 
     
     return rutaCarpeta;
   }
   
   private void darPermisosCarpeta(String rutaBase) throws IOException {
     Process p = Runtime.getRuntime().exec("chmod -R 777 " + rutaBase);
     try {
       p.waitFor();
     } catch (InterruptedException e) {
       e.printStackTrace();
     } 
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\ArchivoServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */