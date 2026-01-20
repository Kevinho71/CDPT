# Guía de Integración con Cloudinary

## Descripción General

El sistema ahora utiliza **Cloudinary** para el almacenamiento y gestión de todas las imágenes de perfiles, banners y galerías. Esta guía explica cómo funciona la integración y cómo usar los servicios.

## Estructura de Carpetas en Cloudinary

Las imágenes se organizan en las siguientes carpetas:

### Empresas (Catálogos)
- **EMPRESA_LOGO**: Logos/fotos de perfil de empresas
- **EMPRESA_BANNER**: Banners de empresas
- **EMPRESA_GALERIA**: Galería de imágenes de empresas (máximo 3 imágenes)

### Socios (Miembros)
- **SOCIO_PERFIL**: Fotos de perfil de socios
- **SOCIO_BANNER**: Banners de socios
- **SOCIO_LOGO**: Fotos para credenciales de socios
- **SOCIO_QR**: Códigos QR de socios

## Características Principales

### 1. Reemplazo Automático de Imágenes
- **Fotos de Perfil**: Al subir una nueva foto de perfil, la anterior se elimina automáticamente de Cloudinary
- **Banners**: Al actualizar el banner, el anterior se elimina automáticamente
- **Credenciales de Socio**: Al editar la foto de un socio, la anterior se elimina

### 2. Gestión de Galerías de Empresas
- Cada empresa puede tener **entre 0 y 3 imágenes** en su galería
- No se permite agregar más de 3 imágenes
- Al actualizar una imagen de galería, la anterior se elimina automáticamente

### 3. Tipos de Imágenes
El campo `tipo` en `ImagenCatalogo` distingue entre:
- **PERFIL**: Foto de perfil/logo
- **BANNER**: Imagen de banner
- **GALERIA**: Imagen de galería (solo para empresas)

## Servicios Disponibles

### ArchivoService (Cloudinary Integration)

```java
// Subir una imagen
String publicId = archivoService.subirImagen(
    CloudinaryFolders.EMPRESA_LOGO,  // Carpeta
    multipartFile,                    // Archivo
    "empresa_123"                     // Public ID
);

// Eliminar una imagen
archivoService.eliminarImagen(publicId);

// Obtener detalles de una imagen
Map<String, Object> detalles = archivoService.obtenerDetallesImagen(publicId);

// Listar imágenes de una carpeta
Map<String, Object> recursos = archivoService.listarImagenesCarpeta(
    CloudinaryFolders.EMPRESA_GALERIA,
    50  // Máximo de resultados
);
```

### ImagenCatalogoService (Gestión de Imágenes de Catálogo)

```java
// Guardar una imagen (automáticamente sube a Cloudinary)
ImagenesCatalogoEntity imagen = imagenCatalogoService.guardarImagen(
    catalogoId,                       // ID del catálogo
    CloudinaryFolders.TIPO_PERFIL,   // Tipo de imagen
    multipartFile                     // Archivo
);

// Buscar imágenes por catálogo y tipo
List<ImagenesCatalogoEntity> imagenes = imagenCatalogoService.findByCatalogoAndTipo(
    catalogoId,
    CloudinaryFolders.TIPO_GALERIA
);

// Verificar si se pueden agregar más imágenes a la galería
boolean puedeAgregar = imagenCatalogoService.puedeAgregarImagenGaleria(catalogoId);

// Eliminar una imagen (automáticamente elimina de Cloudinary)
imagenCatalogoService.eliminarImagen(imagenId);
```

## Ejemplos de Uso

### Ejemplo 1: Actualizar Logo de Empresa

```java
@PostMapping("/empresa/{id}/logo")
public ResponseEntity<?> actualizarLogo(
    @PathVariable Integer id,
    @RequestParam("logo") MultipartFile logo
) {
    try {
        // El servicio automáticamente:
        // 1. Elimina el logo anterior de Cloudinary
        // 2. Sube el nuevo logo
        // 3. Actualiza la base de datos
        ImagenesCatalogoEntity imagen = imagenCatalogoService.guardarImagen(
            id,
            CloudinaryFolders.TIPO_PERFIL,
            logo
        );
        return ResponseEntity.ok(imagen);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
```

### Ejemplo 2: Agregar Imagen a Galería

```java
@PostMapping("/empresa/{id}/galeria")
public ResponseEntity<?> agregarImagenGaleria(
    @PathVariable Integer id,
    @RequestParam("imagen") MultipartFile imagen
) {
    try {
        // Verificar límite
        if (!imagenCatalogoService.puedeAgregarImagenGaleria(id)) {
            return ResponseEntity.badRequest()
                .body("No se pueden agregar más de 3 imágenes a la galería");
        }
        
        ImagenesCatalogoEntity nuevaImagen = imagenCatalogoService.guardarImagen(
            id,
            CloudinaryFolders.TIPO_GALERIA,
            imagen
        );
        return ResponseEntity.ok(nuevaImagen);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
```

### Ejemplo 3: Actualizar Banner de Socio

```java
@PostMapping("/socio/{id}/banner")
public ResponseEntity<?> actualizarBannerSocio(
    @PathVariable Integer id,
    @RequestParam("banner") MultipartFile banner
) {
    try {
        SocioEntity socio = socioRepository.findById(id)
            .orElseThrow(() -> new Exception("Socio no encontrado"));
        
        // Eliminar banner anterior si existe
        if (socio.getBanner() != null && !socio.getBanner().isEmpty()) {
            archivoService.eliminarImagen(socio.getBanner());
        }
        
        // Subir nuevo banner
        String publicId = archivoService.subirImagen(
            CloudinaryFolders.SOCIO_BANNER,
            banner,
            "socio_" + id + "_banner"
        );
        
        // Actualizar entidad
        socio.setBanner(publicId);
        socioRepository.save(socio);
        
        return ResponseEntity.ok(socio);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
```

### Ejemplo 4: Actualizar Código QR de Socio

```java
@PostMapping("/socio/{id}/qr")
public ResponseEntity<?> actualizarQRSocio(
    @PathVariable Integer id,
    @RequestParam("qr") MultipartFile qrImage
) {
    try {
        SocioEntity socio = socioRepository.findById(id)
            .orElseThrow(() -> new Exception("Socio no encontrado"));
        
        // Actualizar QR (elimina el anterior automáticamente)
        String newPublicId = socioImagenService.actualizarQR(
            id,
            socio.getQr(),  // Public ID anterior
            qrImage
        );
        
        // Actualizar entidad
        socio.setQr(newPublicId);
        socioRepository.save(socio);
        
        return ResponseEntity.ok(socio);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
```

## Cambios en la Base de Datos

### Tabla `imagencatalogo`

Se agregó el campo `tipo`:

```sql
ALTER TABLE imagencatalogo ADD COLUMN tipo VARCHAR(30);
```

Valores válidos:
- `'PERFIL'`: Foto de perfil/logo
- `'BANNER'`: Banner
- `'GALERIA'`: Imagen de galería

## Migración desde el Sistema Anterior

### Pasos para Migrar

1. **Subir imágenes existentes a Cloudinary**:
   - Leer imágenes del sistema de archivos local
   - Subirlas a Cloudinary usando `archivoService.subirImagen()`
   - Actualizar los registros en la base de datos con los `public_id` de Cloudinary

2. **Actualizar campo `tipo`**:
   ```sql
   UPDATE imagencatalogo SET tipo = 'PERFIL' WHERE <condición para fotos de perfil>;
   UPDATE imagencatalogo SET tipo = 'BANNER' WHERE <condición para banners>;
   UPDATE imagencatalogo SET tipo = 'GALERIA' WHERE <condición para galería>;
   ```

## Configuración Requerida

En `application.properties`:

```properties
# Cloudinary Configuration
cloudinary.cloud-name=tu-cloud-name
cloudinary.api-key=tu-api-key
cloudinary.api-secret=tu-api-secret
```

## Buenas Prácticas

1. **Siempre validar archivos antes de subir**:
   ```java
   if (archivo == null || archivo.isEmpty()) {
       throw new Exception("Archivo vacío");
   }
   ```

2. **Manejar errores de Cloudinary apropiadamente**:
   ```java
   try {
       archivoService.eliminarImagen(publicId);
   } catch (IOException e) {
       // Log error pero continuar
       logger.error("Error eliminando imagen: " + e.getMessage());
   }
   ```

3. **Usar nombres de `public_id` descriptivos**:
   ```java
   String publicId = "empresa_" + catalogoId + "_" + tipo + "_" + timestamp;
   ```

4. **Verificar límites antes de agregar imágenes**:
   ```java
   if (!imagenCatalogoService.puedeAgregarImagenGaleria(catalogoId)) {
       throw new Exception("Límite de galería alcanzado");
   }
   ```

## Constantes Disponibles

Usa la clase `CloudinaryFolders` para acceder a constantes:

```java
import app.common.util.CloudinaryFolders;

// Carpetas
CloudinaryFolders.EMPRESA_LOGO
CloudinaryFolders.EMPRESA_BANNER
CloudinaryFolders.EMPRESA_GALERIA
CloudinaryFolders.SOCIO_PERFIL
CloudinaryFolders.SOCIO_BANNER
CloudinaryFolders.SOCIO_LOGO
CloudinaryFolders.SOCIO_QR

// Tipos
CloudinaryFolders.TIPO_PERFIL
CloudinaryFolders.TIPO_BANNER
CloudinaryFolders.TIPO_GALERIA

// Límites
CloudinaryFolders.MAX_GALERIA_IMAGES
```

## Solución de Problemas

### Problema: Imagen no se elimina de Cloudinary

**Solución**: Verifica que el `public_id` incluya la carpeta:
```java
// Correcto
archivoService.eliminarImagen("EMPRESA_LOGO/empresa_123");

// Incorrecto
archivoService.eliminarImagen("empresa_123");
```

### Problema: Error al subir imagen grande

**Solución**: Cloudinary tiene límites de tamaño. Considera comprimir imágenes antes de subir o configurar transformaciones automáticas.

### Problema: No se pueden agregar más imágenes a la galería

**Solución**: Verifica el límite:
```java
if (!imagenCatalogoService.puedeAgregarImagenGaleria(catalogoId)) {
    // Eliminar una imagen existente primero
    imagenCatalogoService.eliminarImagen(imagenIdAEliminar);
    // Luego agregar la nueva
}
```

## Recursos Adicionales

- [Documentación de Cloudinary](https://cloudinary.com/documentation)
- [Cloudinary Java SDK](https://cloudinary.com/documentation/java_integration)
- [Transformaciones de Imágenes](https://cloudinary.com/documentation/image_transformations)
