package app.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ArchivoService {
  String guargarArchivo(String paramString1, MultipartFile paramMultipartFile, String paramString2) throws IOException;
  
  String guargarMultipleArchivos(List<MultipartFile> paramList) throws IOException;
  
  Path linkArchivo(String paramString1, String paramString2) throws IOException;
  
  void eliminarArchivo(String paramString1, String paramString2) throws IOException;
  
  String obtenerRutaCarpetaRecursos(String paramString);
  
  String obtenerRutaArchivos(String paramString);
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\ArchivoService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */