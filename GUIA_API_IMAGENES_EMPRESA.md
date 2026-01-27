# Guía de API - Gestión de Imágenes de Empresa

## Conceptos Clave

### Sistema de Códigos para Imágenes
El sistema utiliza **códigos numéricos** y **tipos** para identificar las imágenes:

- **Banner**: `codigo=0`, `tipo=BANNER` → Máximo 1 por empresa
- **Galería 1**: `codigo=1`, `tipo=GALERIA`
- **Galería 2**: `codigo=2`, `tipo=GALERIA`
- **Galería 3**: `codigo=3`, `tipo=GALERIA` → Máximo 3 de galería

### Tipos de Imágenes

1. **Logo**: Imagen principal de la empresa (campo separado en `nombrelogo`)
2. **Banner**: Imagen destacada/portada (`codigo=0`, `tipo=BANNER`)
3. **Galería**: Hasta 3 imágenes adicionales (`codigo=1,2,3`, `tipo=GALERIA`)

---

## Lógica de Eliminación de Imágenes

**IMPORTANTE**: Para eliminar una imagen existente sin reemplazarla, el frontend debe enviar un archivo VACÍO (no null).

### Cómo enviar un archivo vacío desde el Frontend:

```javascript
// Para eliminar el logo
const formData = new FormData();
formData.append("logo", new File([], "", { type: "application/octet-stream" }));

// Para eliminar el banner
formData.append("banner", new File([], "", { type: "application/octet-stream" }));

// Para eliminar galería 1
formData.append("galeria1", new File([], "", { type: "application/octet-stream" }));
```

### Comportamiento del Backend:

1. **Campo no enviado** (`null`): No se modifica la imagen existente
2. **Campo con archivo**: Reemplaza la imagen existente (elimina la anterior de Cloudinary)
3. **Campo con archivo vacío**: Elimina la imagen existente y pone `null` en la BD

## Endpoints Disponibles

### 1. Crear Empresa (POST)

```http
POST /api/catalogos
Content-Type: multipart/form-data
```

#### Body (FormData)

Todos los campos de imágenes son **opcionales (nullable)**:

```javascript
{
  // Datos básicos (requeridos)
  "nit": "1029384751",
  "nombre": "AutoService Pro Solutions",
  "estado": 1,
  
  // Datos opcionales
  "descripcion": "Centro especializado en mecánica general...",
  "direccion": "Av. Los Motores #450, Zona Industrial Norte, Galpón 3",
  "tipo": "Automotriz / Taller Mecánico",
  "longitud": "2323",
  "latitud": "23223",
  "paisId": 1,              // Opcional
  "departamentoId": 1,      // Opcional
  "provinciaId": 1,         // Opcional
  "descuentos": ["5% de descuento en todo", "10% de descuento en autos"],
  
  // Imágenes (todas opcionales)
  "logo": [File],           // Logo de la empresa
  "banner": [File],         // Banner (codigo=0, tipo=BANNER)
  "galeria1": [File],       // Galería 1 (codigo=1, tipo=GALERIA)
  "galeria2": [File],       // Galería 2 (codigo=2, tipo=GALERIA)
  "galeria3": [File]        // Galería 3 (codigo=3, tipo=GALERIA)
}
```

#### Ejemplo JavaScript (Frontend)

```javascript
const formData = new FormData();

// Datos básicos
formData.append("nit", "1029384751");
formData.append("nombre", "AutoService Pro Solutions");
formData.append("estado", 1);

// Datos opcionales
formData.append("descripcion", "Centro especializado...");
formData.append("direccion", "Av. Los Motores #450...");
formData.append("tipo", "Automotriz / Taller Mecánico");
formData.append("longitud", "2323");
formData.append("latitud", "23223");
formData.append("paisId", 1);
formData.append("departamentoId", 1);
formData.append("provinciaId", 1);

// Descuentos (array como JSON string)
formData.append("descuentos", JSON.stringify([
  "5% de descuento en todo",
  "10% de descuento en autos"
]));

// Imágenes (solo si el usuario las seleccionó)
if (logoFile) formData.append("logo", logoFile);
if (bannerFile) formData.append("banner", bannerFile);
if (galeria1File) formData.append("galeria1", galeria1File);
if (galeria2File) formData.append("galeria2", galeria2File);
if (galeria3File) formData.append("galeria3", galeria3File);

// Enviar
fetch("/api/catalogos", {
  method: "POST",
  body: formData
})
  .then(response => response.json())
  .then(data => console.log(data));
```

---

### 2. Actualizar Empresa (PUT)

```http
PUT /api/catalogos/{id}
Content-Type: multipart/form-data
```

#### Body (FormData)

**Misma estructura que POST**. La diferencia clave está en la **lógica de reemplazo**:

- Si envías `logo` → Reemplaza el logo anterior
- Si envías `banner` → Busca la imagen con `codigo=0` y `tipo=BANNER`, la elimina y sube la nueva
- Si envías `galeria1` → Busca la imagen con `codigo=1` y `tipo=GALERIA`, la elimina y sube la nueva
- Si envías `galeria2` → Busca la imagen con `codigo=2` y `tipo=GALERIA`, la elimina y sube la nueva
- Si envías `galeria3` → Busca la imagen con `codigo=3` y `tipo=GALERIA`, la elimina y sube la nueva

**Importante**: Solo se reemplazan las imágenes que envíes. Si no envías `galeria2`, la galería 2 existente se mantiene.

#### Ejemplo JavaScript (Frontend)

```javascript
const formData = new FormData();

// Datos básicos (siempre incluir)
formData.append("nit", "1029384751");
formData.append("nombre", "AutoService Pro Solutions UPDATED");
formData.append("estado", 1);

// Actualizar solo el banner y galería 1
formData.append("banner", nuevoBannerFile);     // Reemplaza banner (codigo=0)
formData.append("galeria1", nuevaGaleria1File); // Reemplaza galería 1 (codigo=1)
// galeria2 y galeria3 no se envían, entonces se mantienen sin cambios

fetch("/api/catalogos/1", {
  method: "PUT",
  body: formData
})
  .then(response => response.json())
  .then(data => console.log(data));
```

---

## Respuesta del Backend (CatalogoResponseDTO)

```json
{
  "id": 1,
  "codigo": 1,
  "nit": "1029384751",
  "nombre": "AutoService Pro Solutions",
  "descripcion": "Centro especializado en mecánica general...",
  "direccion": "Av. Los Motores #450, Zona Industrial Norte, Galpón 3",
  "tipo": "Automotriz / Taller Mecánico",
  "longitud": "2323",
  "latitud": "23223",
  "estado": 1,
  "paisId": 1,
  "departamentoId": 1,
  "provinciaId": 1,
  "descuentos": ["5% de descuento en todo", "10% de descuento en autos"],
  "logourl": "https://res.cloudinary.com/.../EMPRESA_LOGO/empresa_1_logo_123.png",
  "bannerUrl": "https://res.cloudinary.com/.../EMPRESA_BANNER/empresa_1_banner_0_456.jpg",
  "galeriaUrls": [
    "https://res.cloudinary.com/.../EMPRESA_GALERIA/empresa_1_galeria_1_789.jpg",
    "https://res.cloudinary.com/.../EMPRESA_GALERIA/empresa_1_galeria_2_012.jpg",
    "https://res.cloudinary.com/.../EMPRESA_GALERIA/empresa_1_galeria_3_345.jpg"
  ]
}
```

### Campos Importantes

- **logourl**: URL directa del logo (puede ser `null`)
- **bannerUrl**: URL directa del banner con `codigo=0` (puede ser `null`)
- **galeriaUrls**: Array con hasta 3 URLs de galería `codigo=1,2,3` (puede ser `[]`)
- **paisId, departamentoId, provinciaId**: IDs de ubicación geográfica (pueden ser `null`)
- **descuentos**: Array de strings con ofertas/descuentos (puede ser `[]`)

---

## Lógica de Códigos y Tipos

### Al Crear (POST)

```
Si envías "banner" → Se crea con codigo=0, tipo=BANNER
Si envías "galeria1" → Se crea con codigo=1, tipo=GALERIA
Si envías "galeria2" → Se crea con codigo=2, tipo=GALERIA
Si envías "galeria3" → Se crea con codigo=3, tipo=GALERIA
```

### Al Actualizar (PUT)

```
Si envías "banner" → Busca imagen con codigo=0 y tipo=BANNER
                     → Si existe, la elimina de Cloudinary y BD
                     → Sube nueva imagen con codigo=0, tipo=BANNER

Si envías "galeria1" → Busca imagen con codigo=1 y tipo=GALERIA
                       → Si existe, la elimina de Cloudinary y BD
                       → Sube nueva imagen con codigo=1, tipo=GALERIA

Si envías "galeria2" → Busca imagen con codigo=2 y tipo=GALERIA
                       → Si existe, la elimina de Cloudinary y BD
                       → Sube nueva imagen con codigo=2, tipo=GALERIA

Si envías "galeria3" → Busca imagen con codigo=3 y tipo=GALERIA
                       → Si existe, la elimina de Cloudinary y BD
                       → Sube nueva imagen con codigo=3, tipo=GALERIA
```

**Regla clave**: Solo se reemplazan las imágenes que envíes en el FormData.

---

## Validaciones

### Logo
- **Límite**: 1 por empresa
- **Reemplazo**: Automático al enviar nuevo logo
- **Eliminación**: Al subir nuevo, el anterior se elimina de Cloudinary

### Banner
- **Límite**: 1 por empresa (`codigo=0`, `tipo=BANNER`)
- **Reemplazo**: Por código 0
- **Eliminación**: Automática al enviar nuevo banner

### Galería
- **Límite**: Máximo 3 imágenes (`codigo=1,2,3`, `tipo=GALERIA`)
- **Reemplazo**: Individual por código (1, 2 o 3)
- **Eliminación**: Automática al reemplazar por código

---

## Casos de Uso Comunes

### Caso 1: Crear empresa solo con datos básicos (sin imágenes)

```javascript
const formData = new FormData();
formData.append("nit", "1234567890");
formData.append("nombre", "Mi Empresa");
formData.append("estado", 1);

fetch("/api/catalogos", {
  method: "POST",
  body: formData
});
```

**Resultado**: Empresa creada sin logo, banner ni galería.

---

### Caso 2: Crear empresa con logo y banner

```javascript
const formData = new FormData();
formData.append("nit", "1234567890");
formData.append("nombre", "Mi Empresa");
formData.append("estado", 1);
formData.append("logo", logoFile);
formData.append("banner", bannerFile);

fetch("/api/catalogos", {
  method: "POST",
  body: formData
});
```

**Resultado**: Empresa con logo y banner (codigo=0). Sin galería.

---

### Caso 3: Actualizar solo el banner de una empresa

```javascript
const formData = new FormData();
formData.append("nit", "1234567890");
formData.append("nombre", "Mi Empresa");
formData.append("estado", 1);
formData.append("banner", nuevoBannerFile);

fetch("/api/catalogos/1", {
  method: "PUT",
  body: formData
});
```

**Resultado**: Banner reemplazado. Logo y galería sin cambios.

---

### Caso 4: Reemplazar galería 2, mantener galería 1 y 3

```javascript
const formData = new FormData();
formData.append("nit", "1234567890");
formData.append("nombre", "Mi Empresa");
formData.append("estado", 1);
formData.append("galeria2", nuevaGaleria2File); // Solo galeria2

fetch("/api/catalogos/1", {
  method: "PUT",
  body: formData
});
```

**Resultado**: Galería 2 (codigo=2) reemplazada. Galería 1 y 3 sin cambios.

---

### Caso 5: Agregar galería completa (3 imágenes)

```javascript
const formData = new FormData();
formData.append("nit", "1234567890");
formData.append("nombre", "Mi Empresa");
formData.append("estado", 1);
formData.append("galeria1", galeria1File);
formData.append("galeria2", galeria2File);
formData.append("galeria3", galeria3File);

fetch("/api/catalogos/1", {
  method: "PUT",
  body: formData
});
```

**Resultado**: 3 imágenes de galería creadas/reemplazadas con codigos 1, 2 y 3.

---

## Otros Endpoints

### Obtener todas las empresas

```http
GET /api/catalogos
```

### Obtener empresa por ID

```http
GET /api/catalogos/{id}
```

### Cambiar estado (activar/desactivar)

```http
PATCH /api/catalogos/{id}/status
```

---

## Notas Importantes

1. **FormData**: Siempre usar `Content-Type: multipart/form-data` al enviar archivos
2. **Códigos fijos**: Banner siempre es 0, galerías siempre 1, 2, 3
3. **Tipos fijos**: Solo "BANNER" y "GALERIA"
4. **URLs completas**: El backend siempre devuelve URLs absolutas de Cloudinary
5. **Reemplazo inteligente**: Solo se reemplazan las imágenes que envíes explícitamente
6. **Eliminación automática**: Al reemplazar, la imagen anterior se elimina de Cloudinary automáticamente
7. **Campos opcionales**: Todos los campos de imágenes y ubicación geográfica son opcionales
8. **Descuentos**: Enviar como array de strings en JSON
