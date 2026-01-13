package app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ArchivoService {
  String guargarArchivo(String paramString1, MultipartFile paramMultipartFile, String paramString2) throws IOException;
  
  String guargarMultipleArchivos(List<MultipartFile> paramList) throws IOException;
  
  String getOrCreateFolder(String paramString) throws IOException, GeneralSecurityException;
  
  String obtenerIdArchivoDrivePorNombre(String paramString1, String paramString2) throws IOException, GeneralSecurityException;
  
  Path linkArchivo(String paramString1, String paramString2) throws IOException;
  
  void eliminarArchivo(String paramString1, String paramString2) throws IOException;
  
  String guargarArchivoDrive(String paramString1, MultipartFile paramMultipartFile, String paramString2) throws IOException, GeneralSecurityException;
  
  String guargarArchivoDriveFile(String paramString1, File paramFile, String paramString2) throws IOException, GeneralSecurityException;
  
  void eliminarArchivoDrive(String paramString1, String paramString2) throws IOException, GeneralSecurityException;
  
  String obtenerRutaCarpetaRecursos(String paramString);
  
  String obtenerRutaArchivos(String paramString);
  
  void eliminarCarpetaDrive(String paramString) throws IOException, GeneralSecurityException;
}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\ArchivoService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */