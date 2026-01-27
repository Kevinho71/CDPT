# Sistema de Posts (Noticias y Eventos) - CADET

## Resumen de Implementación Completa

### 📁 Estructura de Archivos Creados

```
app/posts/
├── dto/
│   ├── PostSummaryDTO.java          # DTO ligero para listados
│   ├── PostDetailDTO.java           # DTO completo con secciones
│   ├── PostSeccionDTO.java          # DTO para secciones
│   ├── PostCreateDTO.java           # DTO para crear posts
│   ├── PostUpdateDTO.java           # DTO para actualizar (campos opcionales)
│   └── PostSeccionCreateDTO.java    # DTO para crear/actualizar secciones
├── entity/
│   ├── PostEntity.java              # Entidad principal (ya existía, mejorada)
│   └── PostSeccionEntity.java       # Entidad de secciones (ya existía, mejorada)
├── repository/
│   ├── PostRepository.java          # Queries JPQL optimizadas
│   └── PostSeccionRepository.java   # Repository de secciones
├── service/
│   ├── PostService.java             # Interfaz del servicio
│   └── PostServiceImpl.java         # Implementación completa
└── controller/
    ├── PostPublicController.java    # Endpoints públicos (sin auth)
    └── PostAdminController.java     # Endpoints admin (con auth)
```

---

## 🎯 Características Principales

### 1. **DTOs Optimizados**
- **PostSummaryDTO**: Ligero para listados (NO incluye secciones)
- **PostDetailDTO**: Completo para vista de detalle (incluye secciones)
- **PostCreateDTO**: Validaciones completas para creación
- **PostUpdateDTO**: Campos opcionales para actualizaciones parciales
- **PostSeccionDTO**: Representación de secciones individuales

### 2. **Repository con JPQL Optimizado**
✅ Constructor Expressions para proyecciones a DTO  
✅ LEFT JOIN FETCH para cargar secciones eficientemente  
✅ Filtros por tipo (NOTICIA/EVENTO)  
✅ Validación de slugs únicos  
✅ **SIN native queries innecesarias**

### 3. **Service Layer - Lógica de Negocio**

#### Generación de Slugs
```java
"Nueva Directiva 2026" → "nueva-directiva-2026"
```
- Convierte a minúsculas
- Reemplaza espacios por guiones
- Elimina acentos y caracteres especiales
- Garantiza unicidad (añade sufijo numérico si existe)

#### Gestión de Imágenes (Cloudinary)
- **Carpetas**: `POST_PORTADA` y `POST_SECCION`
- Upload automático con IDs únicos
- Eliminación al actualizar/borrar posts
- Soporte para portada + múltiples imágenes de sección

#### Actualización de Secciones
- **Estrategia**: Clear + Add (orphanRemoval automático)
- Las secciones viejas se borran de BD automáticamente
- Las nuevas se crean con IDs consecutivos

---

## 🌐 API Endpoints

### **API Pública** (`/api/public/posts`)

#### 1. Listar Posts Publicados
```http
GET /api/public/posts
GET /api/public/posts?tipo=NOTICIA
GET /api/public/posts?tipo=EVENTO
```
**Respuesta**: Lista de `PostSummaryDTO`

#### 2. Obtener Detalle por Slug
```http
GET /api/public/posts/{slug}
Ejemplo: GET /api/public/posts/nueva-directiva-2026
```
**Respuesta**: `PostDetailDTO` con todas las secciones

#### 3. Últimos N Posts
```http
GET /api/public/posts/latest/5
```

#### 4. Atajos
```http
GET /api/public/posts/noticias  # Solo noticias
GET /api/public/posts/eventos   # Solo eventos
```

---

### **API Administrativa** (`/api/admin/posts`) 🔒

#### 1. Listar Todos (incluye borradores)
```http
GET /api/admin/posts
GET /api/admin/posts?tipo=NOTICIA
```

#### 2. Obtener por ID o Slug
```http
GET /api/admin/posts/{id}
GET /api/admin/posts/slug/{slug}
```

#### 3. Crear Post
```http
POST /api/admin/posts
Content-Type: application/json

{
  "titulo": "Nueva Directiva 2026",
  "intro": "Presentamos la nueva junta directiva...",
  "autor": "La Directiva",
  "portadaUrl": "https://res.cloudinary.com/...",
  "tipo": "NOTICIA",
  "publicado": false,
  "secciones": [
    {
      "orden": 0,
      "tipoSeccion": "ESTANDAR",
      "subtitulo": "Introducción",
      "contenido": "<p>Contenido HTML...</p>",
      "imagenUrl": "https://res.cloudinary.com/..."
    },
    {
      "orden": 1,
      "tipoSeccion": "VIDEO",
      "subtitulo": "Video Presentación",
      "videoUrl": "https://youtube.com/watch?v=..."
    }
  ]
}
```

#### 4. Actualizar Post (campos opcionales)
```http
PUT /api/admin/posts/{id}
Content-Type: application/json

{
  "titulo": "Nuevo Título",
  "publicado": true,
  "secciones": [...] // Opcional, reemplaza todas si se envía
}
```

#### 5. Eliminar Post (borrado lógico)
```http
DELETE /api/admin/posts/{id}
```
⚠️ También elimina imágenes de Cloudinary

#### 6. Publicar/Despublicar
```http
PATCH /api/admin/posts/{id}/publicar
Content-Type: application/json

{
  "publicado": true
}
```

#### 7. Subir Imágenes
```http
POST /api/admin/posts/upload/portada
Content-Type: multipart/form-data

file: (archivo imagen)
```

```http
POST /api/admin/posts/upload/seccion
Content-Type: multipart/form-data

file: (archivo imagen)
```

**Respuesta**:
```json
{
  "success": true,
  "url": "https://res.cloudinary.com/POST_PORTADA/portada_1234567890"
}
```

#### 8. Eliminar Imagen de Cloudinary
```http
DELETE /api/admin/posts/upload/{publicId}
```

---

## 📊 Modelo de Datos

### PostEntity
```
- id: Integer (PK)
- estado: Integer (1=activo, 0=eliminado)
- fk_usuario: Integer (FK → usuario)
- titulo: String
- slug: String (UNIQUE, URL-friendly)
- intro: Text
- portadaUrl: String (Cloudinary)
- autor: String
- tipo: String ('NOTICIA' | 'EVENTO')
- publicado: Boolean
- createdAt: LocalDateTime
- fechaEvento: LocalDateTime (nullable)
- lugarEvento: String (nullable)
- direccionEvento: String (nullable)
- secciones: List<PostSeccionEntity> (OneToMany)
```

### PostSeccionEntity
```
- id: Integer (PK)
- estado: Integer
- fk_post: Integer (FK → posts, CASCADE)
- orden: Integer
- tipoSeccion: String ('ESTANDAR' | 'VIDEO' | 'CITA')
- subtitulo: String
- contenido: Text (HTML)
- imagenUrl: String (Cloudinary)
- videoUrl: String (YouTube)
```

---

## 🔍 Queries Optimizadas

### Para Listados (Landing)
```java
// Constructor Expression - NO carga secciones
@Query("SELECT new app.posts.dto.PostSummaryDTO(...) FROM PostEntity p ...")
```

### Para Detalle
```java
// LEFT JOIN FETCH - carga secciones en una sola query
@Query("SELECT p FROM PostEntity p LEFT JOIN FETCH p.secciones WHERE p.slug = :slug")
```

---

## 🔐 Seguridad

- **Público**: Acceso sin autenticación
- **Admin**: Requiere `@PreAuthorize` con roles (implementar en configuración)
- Usuario autenticado obtenido de `SecurityContextHolder`

---

## ✅ Validaciones

### PostCreateDTO
- `@NotBlank` en: titulo, intro, autor, tipo
- `@Size` en todos los strings
- `@Valid` en lista de secciones

### PostUpdateDTO
- Todos los campos opcionales
- Validaciones de tamaño cuando se proporcionan

---

## 🎨 Integración Cloudinary

### Carpetas
- `POST_PORTADA`: Imágenes principales
- `POST_SECCION`: Imágenes de contenido

### Nombrado
```
portada_1234567890
seccion_1234567890
```

### Eliminación Automática
- Al actualizar portada → elimina anterior
- Al actualizar secciones → elimina imágenes de secciones viejas
- Al borrar post → elimina todas las imágenes

---

## 🚀 Flujo de Trabajo Recomendado

### Crear Noticia
1. **Upload portada**: `POST /api/admin/posts/upload/portada`
2. **Upload imágenes secciones**: `POST /api/admin/posts/upload/seccion`
3. **Crear post**: `POST /api/admin/posts` (usar URLs de paso 1 y 2)
4. **Publicar**: `PATCH /api/admin/posts/{id}/publicar`

### Actualizar Noticia
1. **Upload nuevas imágenes** (si aplica)
2. **Update post**: `PUT /api/admin/posts/{id}` (campos modificados)

### Eliminar Noticia
1. **Delete**: `DELETE /api/admin/posts/{id}` (elimina todo automáticamente)

---

## 📝 Notas Importantes

1. **Slugs**: Se generan automáticamente del título, garantizando unicidad
2. **Secciones**: El orden importa (campo `orden` define la secuencia)
3. **Tipos de Sección**:
   - `ESTANDAR`: Subtítulo + Contenido HTML + Imagen (opcional)
   - `VIDEO`: Subtítulo + URL de YouTube
   - `CITA`: Contenido como texto destacado

4. **Borrado**: Lógico (estado=0), no físico
5. **Cascada**: Al borrar post, se borran secciones automáticamente (orphanRemoval)

---

## 🛠️ Próximos Pasos

1. Configurar seguridad en endpoints admin (Spring Security)
2. Implementar paginación para listados grandes
3. Agregar búsqueda por texto (título, intro, contenido)
4. Crear endpoint para estadísticas (total noticias/eventos)
5. Implementar caché para posts publicados frecuentes
