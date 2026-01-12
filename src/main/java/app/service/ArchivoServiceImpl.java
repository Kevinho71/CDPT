/*     */ package BOOT-INF.classes.app.service;
/*     */ 
/*     */ import app.config.GoogleDriveConfig;
/*     */ import app.service.ArchivoService;
/*     */ import app.util.URIS;
/*     */ import com.google.api.client.http.AbstractInputStreamContent;
/*     */ import com.google.api.client.http.FileContent;
/*     */ import com.google.api.client.http.InputStreamContent;
/*     */ import com.google.api.services.drive.Drive;
/*     */ import com.google.api.services.drive.model.File;
/*     */ import com.google.api.services.drive.model.FileList;
/*     */ import com.google.api.services.drive.model.Permission;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.io.DefaultResourceLoader;
/*     */ import org.springframework.core.io.Resource;
/*     */ import org.springframework.core.io.ResourceLoader;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ 
/*     */ @Service
/*     */ public class ArchivoServiceImpl
/*     */   implements ArchivoService {
/*     */   @Autowired
/*     */   private GoogleDriveConfig googleDriveConfig;
/*  37 */   private ResourceLoader resourceLoader = (ResourceLoader)new DefaultResourceLoader();
/*     */ 
/*     */   
/*  40 */   String folderIdroot = "172Z1cDf3Wa4W9ByDxcF8oG9kx5Kn_XAC";
/*     */   private Drive getDriveService() throws IOException, GeneralSecurityException {
/*  42 */     return this.googleDriveConfig.getDriveService();
/*     */   }
/*     */   
/*     */   private void makePublic(String fileId) throws IOException, GeneralSecurityException {
/*  46 */     Permission permission = new Permission();
/*  47 */     permission.setType("anyone");
/*  48 */     permission.setRole("reader");
/*  49 */     getDriveService().permissions().create(fileId, permission).execute();
/*     */   }
/*     */ 
/*     */   
/*     */   public String guargarArchivo(String nameFolder, MultipartFile archivo, String nombre) throws IOException {
/*  54 */     if (!archivo.isEmpty()) {
/*  55 */       String rutaCatalogos = obtenerRutaArchivos(nameFolder);
/*     */       
/*  57 */       if (rutaCatalogos == null || rutaCatalogos.isEmpty()) {
/*  58 */         throw new IOException("No se pudo determinar la ruta de almacenamiento.");
/*     */       }
/*     */       
/*  61 */       Path rutaDirectorio = Paths.get(rutaCatalogos, new String[0]).toAbsolutePath();
/*     */       
/*  63 */       if (!Files.exists(rutaDirectorio, new java.nio.file.LinkOption[0])) {
/*  64 */         Files.createDirectories(rutaDirectorio, (FileAttribute<?>[])new FileAttribute[0]);
/*     */       }
/*     */       
/*  67 */       Path rutaArchivo = rutaDirectorio.resolve(nombre);
/*  68 */       System.out.println("Guardando archivo en la ruta: " + rutaArchivo);
/*     */       
/*  70 */       Files.copy(archivo.getInputStream(), rutaArchivo, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*  71 */       return nombre;
/*     */     } 
/*  73 */     System.out.println("El archivo está vacío.");
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String guargarMultipleArchivos(List<MultipartFile> archivos) throws IOException {
/*  80 */     for (MultipartFile archivo : archivos) {
/*  81 */       guargarArchivo("multiple_files_folder", archivo, archivo.getOriginalFilename());
/*     */     }
/*  83 */     return "Archivos guardados exitosamente";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOrCreateFolder(String folderName) throws IOException, GeneralSecurityException {
/*  88 */     Drive driveService = getDriveService();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     FileList result = (FileList)driveService.files().list().setQ("mimeType='application/vnd.google-apps.folder' and name='" + folderName + "' and trashed=false and '" + this.folderIdroot + "' in parents").setSpaces("drive").setFields("files(id, name)").execute();
/*     */     
/* 103 */     List<File> files = result.getFiles();
/* 104 */     if (!files.isEmpty()) {
/* 105 */       String str = ((File)files.get(0)).getId();
/* 106 */       System.out.println("***********LA CARPETA YA EXISTE");
/*     */       
/* 108 */       return str;
/*     */     } 
/* 110 */     System.out.println("****************CREANDO LA CARPETA EN DRIVE");
/* 111 */     File fileMetadata = new File();
/* 112 */     fileMetadata.setName(folderName);
/* 113 */     fileMetadata.setParents(Collections.singletonList(this.folderIdroot));
/* 114 */     fileMetadata.setMimeType("application/vnd.google-apps.folder");
/*     */ 
/*     */ 
/*     */     
/* 118 */     File folder = (File)driveService.files().create(fileMetadata).setFields("id").execute();
/*     */     
/* 120 */     String folderId = folder.getId();
/* 121 */     makePublic(folderId);
/*     */     
/* 123 */     return folderId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void eliminarCarpetaDrive(String folderId) throws IOException, GeneralSecurityException {
/* 128 */     Drive driveService = getDriveService();
/*     */ 
/*     */     
/* 131 */     eliminarArchivosYCarpetasEnCarpeta(driveService, folderId);
/*     */ 
/*     */     
/*     */     try {
/* 135 */       driveService.files().delete(folderId).execute();
/* 136 */       System.out.println("Carpeta eliminada: " + folderId);
/* 137 */     } catch (IOException e) {
/* 138 */       System.err.println("Error al eliminar la carpeta: " + folderId);
/* 139 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void eliminarArchivosYCarpetasEnCarpeta(Drive driveService, String folderId) throws IOException, GeneralSecurityException {
/* 149 */     FileList result = (FileList)driveService.files().list().setQ("'" + folderId + "' in parents and trashed = false").setSpaces("drive").setFields("files(id, name, mimeType)").execute();
/*     */     
/* 151 */     List<File> files = result.getFiles();
/*     */     
/* 153 */     for (File file : files) {
/* 154 */       if ("application/vnd.google-apps.folder".equals(file.getMimeType())) {
/*     */         
/* 156 */         eliminarCarpetaDrive(file.getId());
/*     */         continue;
/*     */       } 
/*     */       try {
/* 160 */         driveService.files().delete(file.getId()).execute();
/* 161 */         System.out.println("Archivo eliminado: " + file.getName() + " (ID: " + file.getId() + ")");
/* 162 */       } catch (IOException e) {
/* 163 */         System.err.println("Error al eliminar el archivo: " + file.getName() + " (ID: " + file.getId() + ")");
/* 164 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String obtenerIdArchivoDrivePorNombre(String nombreArchivo, String folderId) throws IOException, GeneralSecurityException {
/* 172 */     Drive driveService = getDriveService();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     FileList result = (FileList)driveService.files().list().setQ("mimeType!='application/vnd.google-apps.folder' and name='" + nombreArchivo + "' and '" + folderId + "' in parents and trashed=false").setSpaces("drive").setFields("files(id, name)").execute();
/*     */     
/* 180 */     List<File> files = result.getFiles();
/* 181 */     if (!files.isEmpty()) {
/* 182 */       return ((File)files.get(0)).getId();
/*     */     }
/*     */     
/* 185 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String guargarArchivoDrive(String nameFolder, MultipartFile archivo, String nombre) throws IOException, GeneralSecurityException {
/* 190 */     if (!archivo.isEmpty()) {
/* 191 */       Drive driveService = getDriveService();
/*     */       
/* 193 */       String folderId = getOrCreateFolder(nameFolder);
/* 194 */       System.out.println("ID CARPETA: " + folderId);
/*     */       
/* 196 */       File fileMetadata = new File();
/* 197 */       fileMetadata.setName(nombre);
/* 198 */       fileMetadata.setParents(Collections.singletonList(folderId));
/*     */       
/* 200 */       InputStream inputStream = archivo.getInputStream();
/* 201 */       InputStreamContent mediaContent = new InputStreamContent(archivo.getContentType(), inputStream);
/*     */ 
/*     */ 
/*     */       
/* 205 */       File fileUploaded = (File)driveService.files().create(fileMetadata, (AbstractInputStreamContent)mediaContent).setFields("id").execute();
/*     */       
/* 207 */       inputStream.close();
/*     */       
/* 209 */       return fileUploaded.getId();
/*     */     } 
/* 211 */     System.out.println("El archivo está vacío.");
/* 212 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String guargarArchivoDriveFile(String nameFolder, File archivo, String nombreArchivo) throws IOException, GeneralSecurityException {
/* 218 */     Drive driveService = getDriveService();
/*     */     
/* 220 */     String folderId = getOrCreateFolder(nameFolder);
/* 221 */     System.out.println("ID CARPETA: " + folderId);
/*     */     
/* 223 */     File fileMetadata = new File();
/* 224 */     fileMetadata.setName(nombreArchivo);
/* 225 */     fileMetadata.setParents(Collections.singletonList(folderId));
/*     */     
/* 227 */     Path filePath = Paths.get(archivo.getAbsolutePath(), new String[0]);
/*     */     
/* 229 */     String mimeType = Files.probeContentType(filePath);
/* 230 */     if (mimeType == null) {
/* 231 */       mimeType = "application/octet-stream";
/*     */     }
/*     */     
/* 234 */     FileContent mediaContent = new FileContent(mimeType, archivo);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     File fileUploaded = (File)driveService.files().create(fileMetadata, (AbstractInputStreamContent)mediaContent).setFields("id").execute();
/*     */     
/* 241 */     return fileUploaded.getId();
/*     */   }
/*     */ 
/*     */   
/*     */   public Path linkArchivo(String folder, String nombreArchivo) throws IOException {
/* 246 */     System.out.println("Nombre de archivo a buscar link antes de eliminar: " + nombreArchivo);
/* 247 */     String rutaCatalogos = obtenerRutaArchivos(folder);
/*     */     
/* 249 */     if (rutaCatalogos == null || rutaCatalogos.isEmpty()) {
/* 250 */       throw new IOException("No se pudo determinar la ruta de almacenamiento.");
/*     */     }
/*     */     
/* 253 */     Path rutaDirectorio = Paths.get(rutaCatalogos, new String[0]).toAbsolutePath();
/*     */     
/* 255 */     if (!Files.exists(rutaDirectorio, new java.nio.file.LinkOption[0])) {
/* 256 */       Files.createDirectories(rutaDirectorio, (FileAttribute<?>[])new FileAttribute[0]);
/*     */     }
/*     */     
/* 259 */     if (nombreArchivo != null) {
/* 260 */       Path rutaArchivo = rutaDirectorio.resolve(nombreArchivo);
/* 261 */       System.out.println("Ruta de archivo encontrado: " + rutaArchivo);
/* 262 */       if (Files.exists(rutaArchivo, new java.nio.file.LinkOption[0])) {
/* 263 */         return rutaArchivo;
/*     */       }
/* 265 */       return null;
/*     */     } 
/*     */     
/* 268 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void eliminarArchivo(String folder, String nombreArchivo) throws IOException {
/* 274 */     System.out.println("INTENTANDO ELIMINAR ARCHIVO: " + nombreArchivo);
/* 275 */     Path archivo = linkArchivo(folder, nombreArchivo);
/* 276 */     System.out.println("INTENTANDO ELIMINAR PATH: " + archivo);
/*     */     try {
/* 278 */       if (archivo != null) {
/* 279 */         System.out.println("**********ELIMINANDO ARCHIVO: " + archivo);
/* 280 */         Files.deleteIfExists(archivo);
/*     */       } 
/* 282 */     } catch (Exception e) {
/* 283 */       System.out.println(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void eliminarArchivoDrive(String folderName, String nombreArchivo) throws IOException, GeneralSecurityException {
/* 289 */     Drive driveService = getDriveService();
/*     */     
/* 291 */     String folderId = getOrCreateFolder(folderName);
/* 292 */     System.out.println("ID de la carpeta: " + folderId);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     FileList result = (FileList)driveService.files().list().setQ("mimeType!='application/vnd.google-apps.folder' and name='" + nombreArchivo + "' and '" + folderId + "' in parents and trashed=false").setSpaces("drive").setFields("files(id, name)").execute();
/*     */     
/* 300 */     List<File> files = result.getFiles();
/* 301 */     if (!files.isEmpty()) {
/* 302 */       for (File file : files) {
/*     */         try {
/* 304 */           driveService.files().delete(file.getId()).execute();
/* 305 */           System.out.println("Archivo eliminado: " + nombreArchivo + " (ID: " + file.getId() + ")");
/* 306 */         } catch (IOException e) {
/* 307 */           System.err.println("Error al eliminar el archivo: " + nombreArchivo + " (ID: " + file.getId() + ")");
/* 308 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } else {
/* 312 */       System.out.println("Archivo no encontrado en la carpeta: " + nombreArchivo);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String obtenerRutaCarpetaRecursos(String nombreCarpeta) {
/*     */     try {
/* 319 */       Resource resource = this.resourceLoader.getResource("classpath:static/" + nombreCarpeta);
/* 320 */       return resource.getFile().getAbsolutePath();
/* 321 */     } catch (Exception e) {
/* 322 */       System.out.println("Error al obtener la ruta de la carpeta de recursos: " + e.getMessage());
/* 323 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String obtenerRutaArchivos(String carpeta) {
/* 329 */     URIS uris = new URIS();
/* 330 */     String sistemaOperativo = uris.checkOS();
/* 331 */     System.out.println("INICIANDO APP");
/* 332 */     System.out.println("SISTEMA OPERATIVO: " + sistemaOperativo);
/* 333 */     String rutaCarpeta = "";
/*     */     
/*     */     try {
/* 336 */       if (sistemaOperativo.contains("Linux")) {
/* 337 */         System.out.println("DANDO PERMISOS A LA CARPETA DE ARCHIVOS");
/* 338 */         darPermisosCarpeta("/home");
/* 339 */         rutaCarpeta = Paths.get("/home", new String[] { carpeta }).toString();
/* 340 */       } else if (sistemaOperativo.contains("Windows")) {
/* 341 */         rutaCarpeta = Paths.get("C:\\", new String[] { carpeta }).toString();
/*     */       } 
/* 343 */     } catch (Exception e) {
/* 344 */       e.printStackTrace();
/* 345 */       System.out.println("Error al obtener la ruta de archivos: " + e.getMessage());
/*     */     } 
/*     */     
/* 348 */     return rutaCarpeta;
/*     */   }
/*     */   
/*     */   private void darPermisosCarpeta(String rutaBase) throws IOException {
/* 352 */     Process p = Runtime.getRuntime().exec("chmod -R 777 " + rutaBase);
/*     */     try {
/* 354 */       p.waitFor();
/* 355 */     } catch (InterruptedException e) {
/* 356 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\ArchivoServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */