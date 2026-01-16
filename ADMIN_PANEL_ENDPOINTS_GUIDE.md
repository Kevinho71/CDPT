# 📚 Guía Completa de Endpoints para Panel de Administración

**Base URL**: `http://localhost:8080`

---

## 📋 Tabla de Contenidos
1. [Gestión de Socios](#-gestión-de-socios)
2. [Gestión de Usuarios](#-gestión-de-usuarios)
3. [Gestión de Empresas/Catálogos](#-gestión-de-empresascatálogos)
4. [Gestión de Roles](#-gestión-de-roles)
5. [Gestión de Profesiones e Instituciones](#-gestión-de-profesiones-e-instituciones)

---

## 🧑‍💼 Gestión de Socios

### 1. Listar Todos los Socios (Completo)
**Endpoint**: `GET /api/partners/complete`  
**Descripción**: Obtiene lista completa de socios incluyendo usuario y empresas asociadas  
**Headers**: 
```
Authorization: Bearer {token}
```

**Response Success (200)**:
```json
[
  {
    "id": 3,
    "codigo": 3,
    "nrodocumento": "SOC-3-ABC123",
    "matricula": "MAT-001",
    "nombresocio": "kevin",
    "fechaemision": "2026-02-01",
    "fechaexpiracion": "2027-03-01",
    "estado": 1,
    "imagen": "logo-socio-3.png",
    
    "personaId": 6,
    "personaCi": "125945",
    "personaNombre": "kevin ady guzman marca",
    "personaEmail": "kevin.guzman@ucb.edu.bo",
    
    "profesion": "ADMINISTRADOR DE EMPRESAS",
    "institucion": "COLEGIO DE ADMINISTRADOR DE EMPRESAS DE TARIJA",
    
    "usuario": {
      "id": 2,
      "username": "ramiro.c",
      "estado": 1,
      "roles": ["ROLE_SOCIO", "ROLE_ADMIN"]
    },
    
    "empresas": [
      {
        "id": 1,
        "codigo": 1,
        "nit": "1234567890",
        "nombre": "Restaurante el buen saber",
        "descripcion": "Breve descripción de la empresa...",
        "direccion": "Calle Principal #123",
        "tipo": "Gastronomía",
        "nombrelogo": "logo-empresa-1.png",
        "estado": 1
      }
    ]
  }
]
```

---

### 2. Listar Socios (Simple)
**Endpoint**: `GET /api/partners`  
**Descripción**: Lista básica de socios sin usuario ni empresas  

**Response Success (200)**:
```json
[
  {
    "id": 3,
    "codigo": 3,
    "nrodocumento": "SOC-3-ABC123",
    "matricula": "MAT-001",
    "nombresocio": "kevin",
    "fechaemision": "2026-02-01",
    "fechaexpiracion": "2027-03-01",
    "lejendario": 0,
    "estado": 1,
    "personaId": 6,
    "ci": "125945",
    "nombrecompleto": "kevin ady guzman marca",
    "email": "kevin.guzman@ucb.edu.bo",
    "celular": 76543210,
    "profesionId": 1,
    "profesionNombre": "ADMINISTRADOR DE EMPRESAS",
    "institucionId": 1,
    "institucionNombre": "COLEGIO DE ADMINISTRADOR DE EMPRESAS DE TARIJA",
    "logoUrl": "/api/partners/logo/logo-socio-3.png",
    "qrUrl": "/api/partners/qr/QR-socio-3.png"
  }
]
```

---

### 3. Obtener Socio por ID
**Endpoint**: `GET /api/partners/{id}`  
**Descripción**: Obtiene información detallada de un socio  
**Parámetros**: 
- `id` (path): ID del socio

**Response Success (200)**: Igual a item de lista simple

---

### 4. Crear Nuevo Socio
**Endpoint**: `POST /api/partners`  
**Content-Type**: `multipart/form-data`  
**Descripción**: Crea un nuevo socio con datos de persona

**Body (form-data)**:
```
ci: "12345678"
nombrecompleto: "Juan Pérez García"
email: "juan.perez@email.com"
celular: 76543210
matricula: "MAT-025"
nombresocio: "Juan"
fechaemision: "2026-01-16"
fechaexpiracion: "2027-01-16"
profesionId: 1
institucionId: 1
logo: [archivo] (opcional)
```

**Response Success (201)**:
```json
{
  "id": 7,
  "codigo": 7,
  "nrodocumento": "SOC-7-ABCD1234",
  "matricula": "MAT-025",
  "nombresocio": "Juan",
  "fechaemision": "2026-01-16",
  "fechaexpiracion": "2027-01-16",
  "estado": 1,
  "personaId": 10,
  "ci": "12345678",
  "nombrecompleto": "Juan Pérez García",
  "email": "juan.perez@email.com",
  "logoUrl": "/api/partners/logo/logo-socio-7.png",
  "qrUrl": "/api/partners/qr/QR-socio-7.png"
}
```

---

### 5. Actualizar Socio
**Endpoint**: `PUT /api/partners/{id}`  
**Content-Type**: `multipart/form-data`  
**Descripción**: Actualiza información completa del socio

**Body (form-data)**: Mismos campos que crear (todos requeridos)

**Response Success (200)**: Socio actualizado

---

### 6. Cambiar Estado del Socio
**Endpoint**: `PATCH /api/partners/{id}/status`  
**Content-Type**: `application/json`  
**Descripción**: Activa o desactiva un socio

**Body**:
```json
{
  "estado": 1  // 1 = Activo, 0 = Inactivo
}
```

**Response Success (200)**: Socio con estado actualizado

---

### 7. Renovar Código QR
**Endpoint**: `POST /api/partners/{id}/qr/renew`  
**Descripción**: Genera un nuevo código QR para el socio

**Response Success (200)**:
```json
{
  "id": 3,
  "qrUrl": "/api/partners/qr/QR-socio-3-NEW.png",
  ...
}
```

---

### 8. Asignar/Quitar Empresas a Socio
**Endpoint**: `PATCH /api/partners/{id}/catalogs`  
**Content-Type**: `application/json`  
**Descripción**: Actualiza la lista completa de empresas asociadas al socio

**Body**:
```json
{
  "catalogIds": [1, 3, 5]  // IDs de empresas a asociar
}
```

**Notas**:
- ✅ **Asignar**: Incluir el ID de la empresa en el array
- ❌ **Quitar**: Omitir el ID de la empresa del array
- 🔄 **Reemplazar**: El array enviado reemplaza completamente las asociaciones actuales

**Ejemplo - Asignar empresas 1, 3, 5**:
```json
{
  "catalogIds": [1, 3, 5]
}
```

**Ejemplo - Quitar empresa 3 (dejar solo 1 y 5)**:
```json
{
  "catalogIds": [1, 5]
}
```

**Ejemplo - Quitar todas las empresas**:
```json
{
  "catalogIds": []
}
```

**Response Success (200)**: Socio con empresas actualizadas

---

### 9. Obtener Logo de Socio
**Endpoint**: `GET /api/partners/logo/{filename}`  
**Descripción**: Descarga/visualiza el logo del socio  
**Response**: Imagen (PNG/JPG)

---

### 10. Obtener QR de Socio
**Endpoint**: `GET /api/partners/qr/{filename}`  
**Descripción**: Descarga/visualiza el código QR del socio  
**Response**: Imagen PNG

---

## 👤 Gestión de Usuarios

### 1. Listar Todos los Usuarios
**Endpoint**: `GET /api/usuarios`  
**Descripción**: Obtiene lista de todos los usuarios con roles

**Response Success (200)**:
```json
[
  {
    "id": 2,
    "username": "ramiro.c",
    "estado": 1,
    "personaId": 7,
    "personaCi": "392843",
    "personaNombre": "Ramiro Caucota",
    "personaEmail": "kevin4000100@gmail.com",
    "roles": [
      {
        "id": 2,
        "nombre": "ROLE_SOCIO"
      }
    ]
  }
]
```

---

### 2. Listar Usuarios por Estado
**Endpoint**: `GET /api/usuarios?estado=1`  
**Descripción**: Filtra usuarios por estado  
**Query Params**: 
- `estado`: 1 (activos) o 0 (inactivos)

**Response Success (200)**: Array de usuarios filtrados

---

### 3. Obtener Usuario por ID
**Endpoint**: `GET /api/usuarios/{id}`  
**Descripción**: Obtiene información de un usuario específico  

**Response Success (200)**: Usuario con roles

---

### 4. Obtener Usuario por Persona ID
**Endpoint**: `GET /api/usuarios/persona/{personaId}`  
**Descripción**: Busca el usuario asociado a una persona específica  
**Uso**: Útil para verificar si un socio tiene usuario creado

**Response Success (200)**: Usuario con roles

**Response Error (404)**: 
```json
{
  "message": "Usuario no encontrado para persona con ID: 8",
  "status": 404
}
```

**Ejemplo de uso**: Verificar si socio tiene usuario antes de crear uno nuevo

---

### 5. Crear Usuario para Socio
**Endpoint**: `POST /api/usuarios`  
**Content-Type**: `application/json`  
**Descripción**: Crea un usuario para un socio existente

**Body**:
```json
{
  "username": "mauricio.g",
  "password": "Password123!",
  "estado": 1,
  "personaId": 8,
  "roleIds": [2]
}
```

**Notas**:
- `personaId`: ID de la persona asociada al socio
- `roleIds`: Array de IDs de roles
  - `1`: ROLE_ADMIN
  - `2`: ROLE_SOCIO
  - `3`: ROLE_USER (si existe)

**Response Success (201)**:
```json
{
  "id": 5,
  "username": "mauricio.g",
  "estado": 1,
  "personaId": 8,
  "personaCi": "12334454",
  "personaNombre": "Mauricio Guzman Marca",
  "personaEmail": "mauricio@email.com",
  "roles": [
    {
      "id": 2,
      "nombre": "ROLE_SOCIO"
    }
  ]
}
```

---

### 6. Actualizar Usuario
**Endpoint**: `PUT /api/usuarios/{id}`  
**Content-Type**: `application/json`  
**Descripción**: Actualiza credenciales y roles del usuario

**Body**:
```json
{
  "username": "ramiro.c",
  "password": "NuevaPassword123!",
  "estado": 1,
  "personaId": 7,
  "roleIds": [2, 1]
}
```

**Notas**:
- ✅ **Cambiar password**: Enviar nueva contraseña
- ⏭️ **Mantener password actual**: Dejar vacío `password: ""`
- 🔄 **Actualizar roles**: Enviar nuevos roleIds

**Response Success (200)**: Usuario actualizado

---

### 7. Cambiar Estado de Usuario (Activar/Desactivar)
**Endpoint**: `PATCH /api/usuarios/{id}/status`  
**Descripción**: Alterna el estado del usuario (activo ↔ inactivo)

**Response Success (200)**:
```json
{
  "id": 2,
  "username": "ramiro.c",
  "estado": 0,  // Desactivado
  ...
}
```

**Notas**:
- 🔄 **Toggle automático**: Si está activo (1) → desactiva (0), si está inactivo (0) → activa (1)
- 🚫 **Acceso bloqueado**: Usuario inactivo no puede hacer login

## 🏢 Gestión de Empresas/Catálogos

### 1. Listar Todas las Empresas
**Endpoint**: `GET /api/catalogos`  
**Descripción**: Obtiene lista de todas las empresas/catálogos

**Response Success (200)**:
```json
[
  {
    "id": 1,
    "codigo": 1,
    "nit": "1234567890",
    "nombre": "Restaurante el buen saber",
    "descripcion": "Breve descripción de la empresa y sus servicios...",
    "direccion": "Av. Principal #123",
    "descuentos": [
      {
        "porcentaje": "15",
        "descripcion": "Descuento en menú ejecutivo"
      }
    ],
    "tipo": "Gastronomía",
    "nombrelogo": "logo-empresa-1.png",
    "longitud": "-64.7465",
    "latitud": "-21.5355",
    "estado": 1,
    "logoUrl": "/api/catalogos/logo/logo-empresa-1.png",
    "imagenes": [
      {
        "id": 1,
        "nombreimagen": "imagen-1.jpg",
        "imageUrl": "/api/catalogos/imagenes/imagen-1.jpg"
      }
    ]
  }
]
```

---

### 2. Listar Empresas por Estado
**Endpoint**: `GET /api/catalogos?estado=1`  
**Query Params**: 
- `estado`: 1 (activas) o 0 (inactivas)

**Response Success (200)**: Array de empresas filtradas

---

### 3. Obtener Empresa por ID
**Endpoint**: `GET /api/catalogos/{id}`  
**Descripción**: Obtiene información detallada de una empresa

**Response Success (200)**: Empresa con imágenes

---

### 4. Crear Nueva Empresa
**Endpoint**: `POST /api/catalogos`  
**Content-Type**: `multipart/form-data`  
**Descripción**: Crea nueva empresa con logo e imágenes

**Body (form-data)**:
```
nit: "9876543210"
nombre: "The cake"
descripcion: "Pastelería artesanal con recetas tradicionales"
direccion: "Calle Comercio #456"
descuentos: [{"porcentaje":"10","descripcion":"Descuento en tortas"}]
tipo: "negocio"
longitud: "-64.7500"
latitud: "-21.5400"
estado: 1
logo: [archivo] (opcional)
imagenes: [archivo1, archivo2, archivo3] (opcional, múltiples)
```

**Notas**:
- `descuentos`: Array JSON como string
- `logo`: 1 archivo de imagen
- `imagenes`: Hasta N archivos de imágenes

**Response Success (201)**: Empresa creada con URLs de recursos

---

### 5. Actualizar Empresa
**Endpoint**: `PUT /api/catalogos/{id}`  
**Content-Type**: `multipart/form-data`  
**Descripción**: Actualiza información completa de la empresa

**Body (form-data)**: Mismos campos que crear

**Notas**:
- 🔄 **Actualizar logo**: Enviar nuevo archivo en `logo`
- ⏭️ **Mantener logo actual**: Omitir campo `logo`
- ➕ **Agregar imágenes**: Enviar nuevos archivos en `imagenes`
- ⚠️ **Las imágenes nuevas se AGREGAN** a las existentes

**Response Success (200)**: Empresa actualizada

---

### 6. Cambiar Estado de Empresa
**Endpoint**: `PATCH /api/catalogos/{id}/status`  
**Descripción**: Alterna el estado de la empresa (activa ↔ inactiva)

**Response Success (200)**: Empresa con estado actualizado

---

### 7. Obtener Logo de Empresa
**Endpoint**: `GET /api/catalogos/logo/{filename}`  
**Descripción**: Descarga/visualiza el logo de la empresa  
**Response**: Imagen (PNG/JPG)

---

### 8. Obtener Imagen de Empresa
**Endpoint**: `GET /api/catalogos/imagenes/{filename}`  
**Descripción**: Descarga/visualiza una imagen de la galería  
**Response**: Imagen (PNG/JPG)

---

## 🔐 Gestión de Roles

### 1. Listar Roles Disponibles
**Endpoint**: `GET /api/roles`  
**Descripción**: Obtiene lista de todos los roles del sistema

**Response Success (200)**:
```json
[
  {
    "id": 1,
    "nombre": "ROLE_ADMIN",
    "descripcion": "Administrador con acceso completo"
  },
  {
    "id": 2,
    "nombre": "ROLE_SOCIO",
    "descripcion": "Socio con acceso a panel y empresas"
  }
]
```

---

## 🎓 Gestión de Profesiones e Instituciones

### 1. Listar Profesiones
**Endpoint**: `GET /api/profesiones`  
**Descripción**: Obtiene lista de profesiones disponibles

**Response Success (200)**:
```json
[
  {
    "id": 1,
    "nombre": "ADMINISTRADOR DE EMPRESAS"
  },
  {
    "id": 2,
    "nombre": "INGENIERO CIVIL"
  }
]
```

---

### 2. Listar Instituciones
**Endpoint**: `GET /api/instituciones`  
**Descripción**: Obtiene lista de instituciones/colegios profesionales

**Response Success (200)**:
```json
[
  {
    "id": 1,
    "institucion": "COLEGIO DE ADMINISTRADOR DE EMPRESAS DE TARIJA",
    "sigla": "CADET",
    "host": "https://cadet.org.bo"
  }
]
```

---

## 🔄 Flujo de Trabajo Completo

### **Escenario 1: Crear Socio con Usuario**

#### Paso 1: Crear Socio
```
POST /api/partners
```
Form-data con datos del socio → Obtener `personaId` del response

#### Paso 2: Crear Usuario para el Socio
```
POST /api/usuarios
```
Body:
```json
{
  "username": "nuevo.usuario",
  "password": "Password123!",
  "estado": 1,
  "personaId": 10,  // Del response anterior
  "roleIds": [2]
}
```

#### Paso 3: Asignar Empresas
```
PATCH /api/partners/{socioId}/catalogs
```
Body:
```json
{
  "catalogIds": [1, 2, 3]
}
```

---

### **Escenario 2: Editar Información de Socio**

#### Paso 1: Obtener datos actuales
```
GET /api/partners/{id}
```

#### Paso 2: Actualizar datos
```
PUT /api/partners/{id}
```
Form-data con todos los campos

#### Paso 3 (opcional): Cambiar foto
```
PUT /api/partners/{id}
```
Incluir nuevo archivo en campo `logo`

---

### **Escenario 3: Gestionar Usuario de Socio**

#### Ver usuario actual:
```
GET /api/partners/complete
```
Revisar campo `usuario` (puede ser `null` si no tiene)

#### Crear usuario (si no tiene):
```
POST /api/usuarios
```

#### Editar usuario (cambiar password/roles):
```
PUT /api/usuarios/{usuarioId}
```

#### Desactivar acceso:
```
PATCH /api/usuarios/{usuarioId}/status
```

---

### **Escenario 4: Gestionar Empresas de Socio**

#### Ver empresas actuales:
```
GET /api/partners/complete
```
Revisar array `empresas`

#### Agregar empresa:
```
PATCH /api/partners/{socioId}/catalogs
```
Body: Array con IDs actuales + nuevo ID

#### Quitar empresa:
```
PATCH /api/partners/{socioId}/catalogs
```
Body: Array con IDs actuales - ID a quitar

---

## ⚠️ Códigos de Error Comunes

| Código | Descripción | Solución |
|--------|-------------|----------|
| 400 | Bad Request - Datos inválidos | Verificar formato del body |
| 401 | No autorizado | Incluir token JWT válido |
| 403 | Acceso denegado | Usuario no tiene permisos |
| 404 | Recurso no encontrado | Verificar ID existe |
| 409 | Conflicto - Duplicado | Username/CI/Email ya existe |
| 500 | Error del servidor | Contactar soporte |

---

## 📝 Notas Importantes

### Headers Requeridos
```
Authorization: Bearer {jwt_token}
Content-Type: application/json  // Para JSON
Content-Type: multipart/form-data  // Para archivos
```

### Formatos de Fecha
```
YYYY-MM-DD  // Ejemplo: "2026-01-16"
```

### Estados
```
1 = Activo
0 = Inactivo
```

### Multipart Form-Data
Cuando se envían archivos, usar `Content-Type: multipart/form-data` y construir FormData:

```javascript
const formData = new FormData();
formData.append('nit', '1234567890');
formData.append('nombre', 'Mi Empresa');
formData.append('estado', '1');
formData.append('logo', file);  // File object
formData.append('imagenes', image1);
formData.append('imagenes', image2);
```

---

## 🚀 Testing con Postman/Thunder Client

### 1. Autenticación
```
POST http://localhost:8080/api/auth/login
Body: {
  "username": "admin",
  "password": "admin123"
}
```
Guardar el `token` del response

### 2. Usar Token en Requests
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## 📚 Documentación Adicional

- [FRONTEND_ROLES_GUIDE.md](./FRONTEND_ROLES_GUIDE.md) - Manejo de roles y autenticación
- [SOCIOID_JWT_SOLUTION.md](./SOCIOID_JWT_SOLUTION.md) - Estructura del JWT token
- [OAUTH2_FRONTEND_GUIDE.md](./OAUTH2_FRONTEND_GUIDE.md) - Login con Google

---

**Última actualización**: 16 de enero de 2026  
**Backend Version**: 0.0.1-SNAPSHOT
