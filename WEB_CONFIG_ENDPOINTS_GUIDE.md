# API Endpoints - Web Config Module

## 📋 Endpoints Públicos (Sin autenticación)

### **WebConfig - Configuración Web**

```bash
# Obtener configuraciones por grupo
GET /api/public/webconfig/grupo/{grupo}
GET /public/webconfig/grupo/{grupo}

# Obtener configuración por clave
GET /api/public/webconfig/clave/{clave}
GET /public/webconfig/clave/{clave}

# Obtener configuraciones como Map (útil para frontend)
GET /api/public/webconfig/grupo/{grupo}/map
GET /public/webconfig/grupo/{grupo}/map
```

**Ejemplo:**
```javascript
// Obtener todas las configuraciones del HOME
fetch('/public/webconfig/grupo/HOME')
  .then(res => res.json())
  .then(configs => console.log(configs));

// Obtener como mapa clave-valor
fetch('/public/webconfig/grupo/HOME/map')
  .then(res => res.json())
  .then(configMap => {
    // { "HERO_TITLE": "Bienvenidos", "HERO_SUBTITLE": "..." }
  });
```

### **WebSeccionItem - Items de Secciones**

```bash
# Obtener items ACTIVOS de un grupo (ordenados)
GET /api/public/websecciones/grupo/{grupo}
GET /public/websecciones/grupo/{grupo}

# Obtener item específico (solo si está activo)
GET /api/public/websecciones/{id}
GET /public/websecciones/{id}
```

**Ejemplo:**
```javascript
// Obtener beneficios activos
fetch('/public/websecciones/grupo/BENEFICIOS')
  .then(res => res.json())
  .then(items => console.log(items));

// Obtener directiva activa
fetch('/public/websecciones/grupo/DIRECTIVA')
  .then(res => res.json())
  .then(directiva => console.log(directiva));
```

---

## 🔒 Endpoints Administrativos (Requiere autenticación ADMIN)

### **WebConfig - Configuración Web**

```bash
# CRUD completo
GET    /api/admin/webconfig                    # Listar todas
GET    /api/admin/webconfig/grupo/{grupo}      # Listar por grupo
GET    /api/admin/webconfig/{id}               # Obtener por ID
GET    /api/admin/webconfig/clave/{clave}      # Obtener por clave
POST   /api/admin/webconfig                    # Crear nueva
PUT    /api/admin/webconfig/{id}               # Actualizar completa
PATCH  /api/admin/webconfig/clave/{clave}      # Actualizar solo valor
DELETE /api/admin/webconfig/{id}               # Eliminar (lógico)
```

**Crear configuración:**
```json
POST /api/admin/webconfig
{
  "clave": "HERO_TITLE",
  "valor": "Bienvenidos al Colegio de Administradores",
  "grupo": "HOME"
}
```

**Actualizar configuración:**
```json
PUT /api/admin/webconfig/123
{
  "valor": "Nuevo título",
  "grupo": "HOME"
}
```

**Actualizar solo valor:**
```json
PATCH /api/admin/webconfig/clave/HERO_TITLE
{
  "valor": "Nuevo título"
}
```

### **WebSeccionItem - Items de Secciones**

```bash
# CRUD completo
GET    /api/admin/websecciones                        # Listar todos
GET    /api/admin/websecciones/grupo/{grupo}          # Listar por grupo
GET    /api/admin/websecciones/{id}                   # Obtener por ID
POST   /api/admin/websecciones                        # Crear nuevo
PUT    /api/admin/websecciones/{id}                   # Actualizar
DELETE /api/admin/websecciones/{id}                   # Eliminar (lógico + Cloudinary)

# Operaciones especiales
PATCH  /api/admin/websecciones/{id}/toggle-activo     # Activar/desactivar
PUT    /api/admin/websecciones/grupo/{grupo}/reordenar # Reordenar items

# Gestión de imágenes (DEPRECATED - usar Base64)
POST   /api/admin/websecciones/upload/imagen          # Subir imagen
DELETE /api/admin/websecciones/upload                 # Eliminar imagen
```

**Crear item (con Base64 - RECOMENDADO):**
```json
POST /api/admin/websecciones
{
  "grupo": "BENEFICIOS",
  "titulo": "Descuentos Especiales",
  "descripcion": "Accede a descuentos exclusivos",
  "imagenBase64": "data:image/png;base64,iVBORw0KGg...",
  "datoExtra": null,
  "enlaceUrl": "/beneficios/descuentos",
  "orden": 1,
  "activo": true
}
```

**Crear item de Directiva:**
```json
POST /api/admin/websecciones
{
  "grupo": "DIRECTIVA",
  "titulo": "Juan Pérez",
  "descripcion": "Ingeniero Comercial con 15 años de experiencia",
  "imagenBase64": "data:image/jpeg;base64,/9j/4AAQ...",
  "datoExtra": "2024-2026",  // ← Período
  "enlaceUrl": null,
  "orden": 1,
  "activo": true
}
```

**Actualizar item:**
```json
PUT /api/admin/websecciones/456
{
  "titulo": "Nuevo título",
  "descripcion": "Nueva descripción",
  "imagenBase64": "data:image/png;base64,..."  // Nueva imagen
}
```

**Toggle activo/inactivo:**
```json
PATCH /api/admin/websecciones/456/toggle-activo
{
  "activo": false
}
```

**Reordenar items:**
```json
PUT /api/admin/websecciones/grupo/BENEFICIOS/reordenar
{
  "ids": [3, 1, 2, 4]  // Nuevo orden
}
```

---

## 🎯 Grupos Predefinidos Sugeridos

### **WebConfig (Configuración)**
- `GLOBAL` - Configuración general del sitio
- `HOME` - Textos y configuración de la página principal
- `NOSOTROS` - Textos de "Acerca de"
- `AFILIACION` - Textos de proceso de afiliación
- `CONTACTO` - Textos y datos de contacto

**Ejemplos de claves:**
```
GLOBAL:
  - SITE_NAME
  - SITE_DESCRIPTION
  - LOGO_URL
  - FOOTER_TEXT

HOME:
  - HERO_TITLE
  - HERO_SUBTITLE
  - HERO_BACKGROUND_URL
  - CTA_BUTTON_TEXT

CONTACTO:
  - EMAIL_PRINCIPAL
  - TELEFONO_PRINCIPAL
  - DIRECCION
  - MAPA_URL
```

### **WebSeccionItem (Items Repetitivos)**
- `BENEFICIOS` - Lista de beneficios con iconos
- `PASOS_AFILIACION` - Pasos del proceso de afiliación
- `DIRECTIVA` - Miembros de la directiva
- `MENU_PRINCIPAL` - Items del menú principal
- `MENU_FOOTER` - Links del footer
- `REDES_SOCIALES` - Links a redes sociales

**Estructura sugerida por grupo:**

```javascript
// BENEFICIOS
{
  titulo: "Descuentos Especiales",
  descripcion: "Hasta 30% en comercios afiliados",
  imagenUrl: "https://...",  // Icono
  enlaceUrl: "/beneficios",
  orden: 1
}

// DIRECTIVA
{
  titulo: "Juan Pérez",
  descripcion: "Ingeniero Comercial, MBA",
  imagenUrl: "https://...",  // Foto
  datoExtra: "2024-2026",    // Período
  enlaceUrl: null,
  orden: 1
}

// PASOS_AFILIACION
{
  titulo: "Registra tus datos",
  descripcion: "Completa el formulario online",
  imagenUrl: "https://...",  // Icono de paso
  datoExtra: "1",            // Número de paso
  enlaceUrl: "/registro",
  orden: 1
}
```

---

## 🔐 Seguridad

### **Rutas Públicas (permitAll):**
- `/public/webconfig/**`
- `/api/public/webconfig/**`
- `/public/websecciones/**`
- `/api/public/websecciones/**`

### **Rutas Protegidas (ROLE_ADMIN):**
- `/api/admin/webconfig/**`
- `/api/admin/websecciones/**`

### **Headers Requeridos (Admin):**
```
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

---

## 📝 Códigos de Respuesta

- `200 OK` - Operación exitosa
- `201 Created` - Recurso creado
- `204 No Content` - Eliminación exitosa
- `400 Bad Request` - Datos inválidos
- `401 Unauthorized` - No autenticado
- `403 Forbidden` - Sin permisos
- `404 Not Found` - Recurso no encontrado
- `500 Internal Server Error` - Error del servidor

---

## ✅ Módulo Completo Implementado

**Total: 18 archivos**
- ✅ 2 Entities
- ✅ 2 Repositories
- ✅ 4 Services (2 interfaces + 2 implementaciones)
- ✅ 6 DTOs
- ✅ 4 Controllers (2 admin + 2 public)

**Compilación exitosa:** 190 archivos Java compilados
