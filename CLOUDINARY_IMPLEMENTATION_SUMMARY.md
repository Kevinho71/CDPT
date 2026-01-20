# Resumen de Cambios - Integración con Cloudinary

## 📋 Descripción General

Se ha implementado completamente la integración con **Cloudinary** para gestionar todas las imágenes del sistema (perfiles, banners, credenciales y galerías). El sistema ahora elimina automáticamente las imágenes anteriores cuando se reemplazan, gestiona límites de galería y organiza las imágenes en carpetas estructuradas.

## 🎯 Características Implementadas

### ✅ 1. Gestión Automática de Reemplazo de Imágenes
- **Socios (Miembros)**:
  - Foto de perfil: La anterior se elimina automáticamente al subir una nueva
  - Banner: La anterior se elimina automáticamente al actualizar
  - Foto de credencial: La anterior se elimina automáticamente al editar

- **Empresas (Catálogos)**:
  - Logo/Foto de perfil: La anterior se elimina automáticamente
  - Banner: La anterior se elimina automáticamente
  - Imágenes de galería: La imagen reemplazada se elimina automáticamente

### ✅ 2. Control de Límites de Galería
- Cada empresa puede tener **máximo 3 imágenes** en su galería
- Validación automática antes de agregar nuevas imágenes
- Sistema de conteo implementado en el repositorio

### ✅ 3. Organización en Carpetas de Cloudinary
Las imágenes se organizan en 6 carpetas:
- `EMPRESA_LOGO` - Logos/fotos de perfil de empresas
- `EMPRESA_BANNER` - Banners de empresas
- `EMPRESA_GALERIA` - Galería de empresas
- `SOCIO_PERFIL` - Fotos de perfil de socios
- `SOCIO_BANNER` - Banners de socios
- `SOCIO_LOGO` - Fotos para credenciales

## 📁 Archivos Creados/Modificados

### Archivos Nuevos
1. **CloudinaryFolders.java** - Constantes para carpetas y tipos de imágenes
2. **SocioImagenService.java** - Servicio helper para gestión de imágenes de socios
3. **CLOUDINARY_GUIDE.md** - Guía completa de uso de la integración
4. **database/cloudinary_migration.sql** - Script de migración de base de datos

### Archivos Modificados

#### Entidades
1. **ImagenesCatalogoEntity.java**
   - ✅ Agregado campo `tipo` (VARCHAR) para distinguir PERFIL, BANNER, GALERIA
   - ✅ Actualizados constructores y getters/setters

2. **CatalogoEntity.java**
   - ✅ Agregado campo transient `banner` (MultipartFile)
   - ✅ Actualizados getters/setters

#### Servicios
3. **ArchivoService.java** (Interface)
   - ✅ Reescrito completamente para Cloudinary
   - ✅ Métodos: `subirImagen()`, `eliminarImagen()`, `obtenerDetallesImagen()`, `listarImagenesCarpeta()`

4. **ArchivoServiceImpl.java**
   - ✅ Implementación completa con Cloudinary SDK
   - ✅ Gestión automática de folders y public_ids
   - ✅ Manejo robusto de errores

5. **ImagenCatalogoService.java** (Interface)
   - ✅ Agregados métodos: `findByCatalogoAndTipo()`, `guardarImagen()`, `eliminarImagen()`, `puedeAgregarImagenGaleria()`

6. **ImagenCatalogoServiceImpl.java**
   - ✅ Implementación completa con lógica de reemplazo automático
   - ✅ Validación de límites de galería
   - ✅ Integración con Cloudinary

#### Repositorio
7. **ImagenCatalogoRepository.java**
   - ✅ Query `findByCatalogoAndTipo()` - Buscar imágenes por catálogo y tipo
   - ✅ Query `countGaleriaImagesByCatalogo()` - Contar imágenes de galería

## 🗄️ Cambios en Base de Datos

### Tabla `imagencatalogo`
```sql
-- Campo agregado
tipo VARCHAR(30)  -- Valores: 'PERFIL', 'BANNER', 'GALERIA'
```

### Índices Creados
```sql
CREATE INDEX idx_imagencatalogo_tipo ON imagencatalogo(tipo);
CREATE INDEX idx_imagencatalogo_catalogo_tipo ON imagencatalogo(fk_catalogo, tipo) WHERE estado = 1;
```

### Triggers
- `check_galeria_limit()` - Función para validar límite de galería
- `enforce_galeria_limit` - Trigger que aplica la validación

### Vistas
- `v_catalogo_imagenes` - Vista de todas las imágenes por catálogo
- `v_catalogo_galeria_count` - Conteo de imágenes de galería por catálogo

## 🔧 Configuración Requerida

En `application.properties`:
```properties
cloudinary.cloud-name=tu-cloud-name
cloudinary.api-key=tu-api-key
cloudinary.api-secret=tu-api-secret
```

## 📝 Ejemplos de Uso

### Ejemplo 1: Guardar Logo de Empresa
```java
@Autowired
private ImagenCatalogoService imagenCatalogoService;

// Automáticamente elimina el logo anterior y sube el nuevo
ImagenesCatalogoEntity imagen = imagenCatalogoService.guardarImagen(
    empresaId,
    CloudinaryFolders.TIPO_PERFIL,
    logoFile
);
```

### Ejemplo 2: Actualizar Foto de Perfil de Socio
```java
@Autowired
private SocioImagenService socioImagenService;

// Elimina la foto anterior y sube la nueva
String newPublicId = socioImagenService.actualizarFotoPerfil(
    socioId,
    socio.getFotoPerfilPublicId(),  // Public ID anterior
    nuevaFotoFile
);

socio.setFotoPerfil(newPublicId);
```

### Ejemplo 3: Agregar Imagen a Galería
```java
// Verificar límite primero
if (!imagenCatalogoService.puedeAgregarImagenGaleria(empresaId)) {
    throw new Exception("No se pueden agregar más de 3 imágenes a la galería");
}

ImagenesCatalogoEntity imagen = imagenCatalogoService.guardarImagen(
    empresaId,
    CloudinaryFolders.TIPO_GALERIA,
    imagenFile
);
```

## 🚀 Pasos para Desplegar

1. **Actualizar dependencias**: Asegurarse de que Cloudinary SDK esté en el `pom.xml`
   ```xml
   <dependency>
       <groupId>com.cloudinary</groupId>
       <artifactId>cloudinary-http44</artifactId>
       <version>1.33.0</version>
   </dependency>
   ```

2. **Configurar Cloudinary**: Actualizar `application.properties` con credenciales

3. **Ejecutar migración**: Ejecutar el script `database/cloudinary_migration.sql`

4. **Migrar imágenes existentes**: (Opcional) Crear un script para subir imágenes locales a Cloudinary

5. **Desplegar código**: Desplegar la aplicación con los nuevos cambios

6. **Verificar**: Ejecutar las queries de verificación del script de migración

## 🔍 Verificaciones Post-Despliegue

```sql
-- Verificar que el campo tipo existe
SELECT column_name FROM information_schema.columns 
WHERE table_name = 'imagencatalogo' AND column_name = 'tipo';

-- Verificar distribución de tipos de imagen
SELECT tipo, COUNT(*) FROM imagencatalogo 
WHERE estado = 1 GROUP BY tipo;

-- Verificar que no hay catálogos con más de 3 imágenes de galería
SELECT fk_catalogo, COUNT(*) 
FROM imagencatalogo 
WHERE tipo = 'GALERIA' AND estado = 1 
GROUP BY fk_catalogo 
HAVING COUNT(*) > 3;
```

## 📚 Documentación Adicional

- **CLOUDINARY_GUIDE.md** - Guía detallada de uso con ejemplos
- **CloudinaryFolders.java** - Constantes y convenciones de nombres
- **database/cloudinary_migration.sql** - Script completo de migración

## ⚠️ Consideraciones Importantes

1. **Public IDs únicos**: Cada imagen usa un public_id único con timestamp
   - Formato: `{tipo}_{id}_{categoria}_{timestamp}`
   - Ejemplo: `socio_123_perfil_1642345678901`

2. **Eliminación automática**: Las imágenes antiguas se eliminan automáticamente de Cloudinary

3. **Límite de galería**: Máximo 3 imágenes por empresa, validado tanto en código como en base de datos

4. **Folders organizados**: Las imágenes se organizan automáticamente en las carpetas correspondientes

5. **Manejo de errores**: Si falla la eliminación en Cloudinary, se registra pero no interrumpe el flujo

## 🎨 Estructura de Public IDs

Los public IDs en Cloudinary siguen este patrón:
```
{CARPETA}/{tipo}_{id}_{categoria}_{timestamp}

Ejemplos:
EMPRESA_LOGO/empresa_42_perfil_1642345678901
EMPRESA_BANNER/empresa_42_banner_1642345678902
EMPRESA_GALERIA/empresa_42_galeria_1642345678903
SOCIO_PERFIL/socio_15_perfil_1642345678904
SOCIO_BANNER/socio_15_banner_1642345678905
SOCIO_LOGO/socio_15_credencial_1642345678906
```

## 📞 Soporte

Para cualquier duda sobre la implementación:
- Revisar **CLOUDINARY_GUIDE.md** para ejemplos detallados
- Consultar la documentación de [Cloudinary Java SDK](https://cloudinary.com/documentation/java_integration)
- Revisar los servicios implementados con comentarios detallados

## ✨ Mejoras Futuras Sugeridas

1. **Transformaciones automáticas**: Implementar redimensionamiento automático de imágenes
2. **CDN optimization**: Configurar optimizaciones de CDN en Cloudinary
3. **Lazy loading**: Implementar URLs de transformación para lazy loading
4. **Backup local**: Mantener respaldo local de imágenes críticas
5. **Analytics**: Integrar analytics de uso de imágenes desde Cloudinary

---

**Fecha de implementación**: Enero 2026  
**Versión**: 1.0.0  
**Estado**: ✅ Completado y listo para producción
