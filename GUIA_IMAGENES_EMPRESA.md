# Guía de Imágenes de Empresa

## Tipos de Imágenes

### 1. **Logo de Empresa**
- **Propósito**: Imagen principal/identificador de la empresa
- **Cantidad**: 1 por empresa
- **Campo**: `logo` (MultipartFile)
- **Carpeta Cloudinary**: `EMPRESA_LOGO`
- **Campo en DB**: `nombrelogo` (URL de Cloudinary)

### 2. **Banner**
- **Propósito**: Imagen destacada/portada de la empresa
- **Cantidad**: 1 por empresa (reemplaza el anterior)
- **Campo**: Tabla `imagenes_catalogos` con `tipo = "BANNER"`
- **Carpeta Cloudinary**: `EMPRESA_BANNER`
- **Validación**: Solo puede existir 1 banner activo

### 3. **Galería**
- **Propósito**: Imágenes adicionales de la empresa/productos
- **Cantidad**: Máximo 3 por empresa
- **Campo**: Tabla `imagenes_catalogos` con `tipo = "GALERIA"`
- **Carpeta Cloudinary**: `EMPRESA_GALERIA`
- **Validación**: No se pueden agregar más de 3 imágenes

## Endpoints Disponibles

### Crear/Actualizar Empresa (con logo)
```
POST/PUT /api/catalogos/{id}
Content-Type: multipart/form-data

Body (FormData):
{
  "nit": "1029384751",
  "nombre": "AutoService Pro Solutions",
  "descripcion": "Centro especializado...",
  "direccion": "Av. Los Motores #450...",
  "tipo": "Automotriz / Taller Mecánico",
  "longitud": "2323",
  "latitud": "23223",
  "paisId": 1,
  "departamentoId": 1,
  "provinciaId": 1,
  "estado": 1,
  "descuentos": ["5% de descuento en todo", "10% en autos"],
  "logo": [File] // Archivo de imagen
}
```

### Actualizar/Agregar Banner
```
PUT /api/catalogos/{id}/banner
Content-Type: multipart/form-data

Body (FormData):
{
  "banner": [File] // Archivo de imagen
}

Lógica:
- Si existe un banner anterior, se elimina de Cloudinary
- Se sube el nuevo banner
- Solo puede haber 1 banner por empresa
```

### Agregar Imagen a Galería
```
POST /api/catalogos/{id}/galeria
Content-Type: multipart/form-data

Body (FormData):
{
  "imagen": [File] // Archivo de imagen
}

Lógica:
- Valida que no existan 3 imágenes ya
- Si hay 3, devuelve error 400
- Si hay menos de 3, sube la nueva imagen
```

### Eliminar Imagen de Galería
```
DELETE /api/catalogos/{catalogoId}/galeria/{imagenId}

Lógica:
- Solo elimina imágenes tipo "GALERIA"
- Si intentas eliminar el banner, devuelve error
- Elimina de Cloudinary y de la base de datos
```

## Respuesta del Backend (CatalogoResponseDTO)

```json
{
  "id": 1,
  "codigo": 1,
  "nit": "1029384751",
  "nombre": "AutoService Pro Solutions",
  "descripcion": "Centro especializado...",
  "direccion": "Av. Los Motores #450...",
  "tipo": "Automotriz / Taller Mecánico",
  "longitud": "2323",
  "latitud": "23223",
  "estado": 1,
  "paisId": 1,
  "departamentoId": 1,
  "provinciaId": 1,
  "descuentos": ["5% de descuento en todo", "10% en autos"],
  "logourl": "https://res.cloudinary.com/.../EMPRESA_LOGO/empresa_1_logo_123.png",
  "bannerUrl": "https://res.cloudinary.com/.../EMPRESA_BANNER/empresa_1_banner_456.jpg",
  "galeriaUrls": [
    "https://res.cloudinary.com/.../EMPRESA_GALERIA/empresa_1_galeria_1_789.jpg",
    "https://res.cloudinary.com/.../EMPRESA_GALERIA/empresa_1_galeria_2_012.jpg"
  ]
}
```

## Lógica del Frontend

### Crear/Editar Empresa
1. Crear FormData
2. Agregar todos los campos como texto/números
3. Agregar archivo del logo: `formData.append("logo", fileInput.files[0])`
4. Enviar con `Content-Type: multipart/form-data`

### Mostrar Imágenes
- **Logo**: Usar `logourl` directamente en `<img src={logourl} />`
- **Banner**: Usar `bannerUrl` si existe, sino mostrar placeholder
- **Galería**: Mapear `galeriaUrls` array y mostrar cada imagen

### Actualizar Banner
1. Crear FormData
2. `formData.append("banner", bannerFile)`
3. `PUT /api/catalogos/{id}/banner`

### Agregar a Galería
1. Validar que `galeriaUrls.length < 3`
2. Si es válido, crear FormData
3. `formData.append("imagen", imagenFile)`
4. `POST /api/catalogos/{id}/galeria`

### Eliminar de Galería
1. Obtener el `imagenId` de la imagen a eliminar
2. `DELETE /api/catalogos/{catalogoId}/galeria/{imagenId}`
3. Actualizar el estado local eliminando la URL

## Consideraciones Importantes

1. **URLs Absolutas**: Todas las imágenes se devuelven como URLs completas de Cloudinary, listas para usar
2. **Sin campos opcionales**: Si no hay banner o galería, los campos vienen como `null` o `[]`
3. **Validaciones**: El backend valida límites (1 banner, 3 galería)
4. **Eliminación automática**: Al actualizar logo/banner, el anterior se elimina de Cloudinary automáticamente
5. **Tipo de contenido**: Siempre usar `multipart/form-data` para archivos
6. **IDs geográficos**: Los campos `paisId`, `departamentoId`, `provinciaId` son opcionales (pueden ser null)
