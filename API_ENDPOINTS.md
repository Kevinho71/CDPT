    # API CADET - Guía de Endpoints

## Base URL
```
http://localhost:8080
```

---

## 🔐 AUTENTICACIÓN

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "password123"
}
```

---

## 👥 SOCIOS

### Listar todos los socios
```http
GET /api/partners
```

### Obtener socio por ID
```http
GET /api/partners/1
```

### Crear socio
```http
POST /api/partners
Content-Type: multipart/form-data

ci: 12345678
nombrecompleto: Juan Pérez
email: juan@example.com
celular: 70123456
matricula: MAT001
nombresocio: JUAN PEREZ
fechaemision: 2026-01-01
fechaexpiracion: 2027-01-01
lejendario: 1
profesionId: 1
institucionId: 1
logo: [file]
```

### Actualizar socio
```http
PUT /api/partners/1
Content-Type: multipart/form-data

(mismos campos que crear)
```

### Cambiar estado
```http
PATCH /api/partners/1/status
```

### Buscar por documento
```http
GET /api/partners/document/SOC-001-ABC123
```

### Obtener logo
```http
GET /api/partners/logo/SOCIO - 12345678.jpeg
```

### Obtener QR
```http
GET /api/partners/qr/QR - 12345678.png
```

---

## 🎓 PROFESIONES

### Listar todas
```http
GET /api/profesiones
```

### Listar por estado
```http
GET /api/profesiones/estado/1
```

### Obtener por ID
```http
GET /api/profesiones/1
```

### Crear profesión
```http
POST /api/profesiones
Content-Type: application/json

{
  "nombre": "Ingeniero de Software",
  "estado": 1
}
```

### Actualizar profesión
```http
PUT /api/profesiones/1
Content-Type: application/json

{
  "nombre": "Ingeniero de Sistemas",
  "estado": 1
}
```

### Cambiar estado
```http
PATCH /api/profesiones/1/status
```

---

## 🏢 INSTITUCIONES

### Listar todas
```http
GET /api/instituciones
```

### Listar por estado
```http
GET /api/instituciones/estado/1
```

### Obtener por ID
```http
GET /api/instituciones/1
```

### Crear institución
```http
POST /api/instituciones
Content-Type: application/json

{
  "nit": "1234567890",
  "compania": "Empresa ABC",
  "institucion": "ABC Ltda",
  "representante": "Carlos López",
  "correo": "contacto@abc.com",
  "direccion": "Av. Principal 123",
  "telefono": "4441234",
  "fax": "4445678",
  "host": "smtp.abc.com",
  "port": "587",
  "estado": 1,
  "provinciaId": 1
}
```

### Actualizar institución
```http
PUT /api/instituciones/1
Content-Type: application/json

(mismos campos que crear)
```

### Cambiar estado
```http
PATCH /api/instituciones/1/status
```

---

## 🛠️ SERVICIOS

### Listar todos
```http
GET /api/servicios
```

### Listar por estado
```http
GET /api/servicios/estado/1
```

### Obtener por ID
```http
GET /api/servicios/1
```

### Crear servicio
```http
POST /api/servicios
Content-Type: application/json

{
  "nombre": "Desarrollo Web",
  "descripcion": "Desarrollo de aplicaciones web",
  "categoria": "Tecnología",
  "estado": 1
}
```

### Actualizar servicio
```http
PUT /api/servicios/1
Content-Type: application/json

(mismos campos que crear)
```

### Cambiar estado
```http
PATCH /api/servicios/1/status
```

---

## 🏭 SECTORES

### Listar todos
```http
GET /api/sectores
```

### Listar por estado
```http
GET /api/sectores/estado/1
```

### Obtener por ID
```http
GET /api/sectores/1
```

### Crear sector
```http
POST /api/sectores
Content-Type: application/json

{
  "nombre": "Tecnología",
  "descripcion": "Sector de tecnología e innovación",
  "icono": "fa-laptop",
  "estado": 1
}
```

### Actualizar sector
```http
PUT /api/sectores/1
Content-Type: application/json

(mismos campos que crear)
```

### Cambiar estado
```http
PATCH /api/sectores/1/status
```

---

## 🌍 IDIOMAS

### Listar todos
```http
GET /api/idiomas
```

### Listar por estado
```http
GET /api/idiomas/estado/1
```

### Obtener por ID
```http
GET /api/idiomas/1
```

### Crear idioma
```http
POST /api/idiomas
Content-Type: application/json

{
  "nombre": "Inglés",
  "estado": 1
}
```

### Actualizar idioma
```http
PUT /api/idiomas/1
Content-Type: application/json

(mismos campos que crear)
```

### Cambiar estado
```http
PATCH /api/idiomas/1/status
```

---

## 🎯 ESPECIALIDADES

### Listar todas
```http
GET /api/especialidades
```

### Listar por estado
```http
GET /api/especialidades/estado/1
```

### Obtener por ID
```http
GET /api/especialidades/1
```

### Crear especialidad
```http
POST /api/especialidades
Content-Type: application/json

{
  "nombre": "Desarrollo Frontend",
  "descripcion": "Especialización en tecnologías frontend",
  "orden": 1,
  "estado": 1
}
```

### Actualizar especialidad
```http
PUT /api/especialidades/1
Content-Type: application/json

(mismos campos que crear)
```

### Cambiar estado
```http
PATCH /api/especialidades/1/status
```

---

## 🔑 ROLES

### Listar todos
```http
GET /api/roles
```

### Listar por estado
```http
GET /api/roles/estado/1
```

### Obtener por ID
```http
GET /api/roles/1
```

### Crear rol
```http
POST /api/roles
Content-Type: application/json

{
  "nombre": "ADMIN",
  "estado": 1
}
```
**Nota**: El sistema automáticamente agrega el prefijo "ROLE_" al nombre.

### Actualizar rol
```http
PUT /api/roles/1
Content-Type: application/json

{
  "nombre": "ADMINISTRATOR",
  "estado": 1
}
```

### Cambiar estado
```http
PATCH /api/roles/1/status
```

---

## 👤 USUARIOS

### Listar todos
```http
GET /api/usuarios
```

### Listar por estado
```http
GET /api/usuarios/estado/1
```

### Obtener por ID
```http
GET /api/usuarios/1
```

### Crear usuario
```http
POST /api/usuarios
Content-Type: application/json

{
  "username": "jperez",
  "password": "SecurePass123!",
  "estado": 1,
  "personaId": 1,
  "roleIds": [1, 2]
}
```

### Actualizar usuario
```http
PUT /api/usuarios/1
Content-Type: application/json

{
  "username": "jperez",
  "password": "NewPassword456!",
  "estado": 1,
  "personaId": 1,
  "roleIds": [1, 2]
}
```
**Nota**: Si la contraseña es diferente a la actual, será encriptada automáticamente.

### Cambiar estado
```http
PATCH /api/usuarios/1/status
```

---

## 📦 CATÁLOGOS (EMPRESAS)

### Listar todos
```http
GET /api/catalogos
```

**Response:**
```json
[
  {
    "id": 1,
    "codigo": 1,
    "nit": "1234567890",
    "nombre": "Restaurant El Buen Sabor",
    "descripcion": "Comida tradicional boliviana",
    "direccion": "Calle Principal 456",
    "descuentos": [
      "20$ en alitas",
      "10% en comida",
      "15% en bebidas los viernes"
    ],
    "tipo": "Premium",
    "longitud": "-63.5887",
    "latitud": "-16.5000",
    "estado": 1,
    "logoUrl": "/api/catalogos/logo/1234567890-1234567890",
    "imagenesUrls": [
      "/api/catalogos/imagenes/1234567890-1",
      "/api/catalogos/imagenes/1234567890-2"
    ]
  }
]
```

### Listar por estado
```http
GET /api/catalogos?estado=1
```

### Obtener por ID
```http
GET /api/catalogos/1
```

**Response:**
```json
{
  "id": 1,
  "codigo": 1,
  "nit": "1234567890",
  "nombre": "Restaurant El Buen Sabor",
  "descripcion": "Comida tradicional boliviana",
  "direccion": "Calle Principal 456",
  "descuentos": [
    "20$ en alitas",
    "10% en comida",
    "15% en bebidas los viernes"
  ],
  "tipo": "Premium",
  "longitud": "-63.5887",
  "latitud": "-16.5000",
  "estado": 1,
  "logoUrl": "/api/catalogos/logo/1234567890-1234567890",
  "imagenesUrls": [
    "/api/catalogos/imagenes/1234567890-1",
    "/api/catalogos/imagenes/1234567890-2"
  ]
}
```

### Crear catálogo
```http
POST /api/catalogos
Content-Type: multipart/form-data

nit: 1234567890
nombre: Restaurant El Buen Sabor
descripcion: Comida tradicional boliviana
direccion: Calle Principal 456
descuentos: ["20$ en alitas", "10% en comida", "15% en bebidas los viernes"]
tipo: Premium
longitud: -63.5887
latitud: -16.5000
estado: 1
logo: [file]
imagenes: [multiple files]
```

**Nota sobre descuentos**: El campo `descuentos` acepta un array JSON de strings. Cada elemento es un descuento independiente que se mostrará en la aplicación.

**Response:**
```json
{
  "id": 1,
  "codigo": 1,
  "nit": "1234567890",
  "nombre": "Restaurant El Buen Sabor",
  "descuentos": ["20$ en alitas", "10% en comida", "15% en bebidas los viernes"],
  "estado": 1,
  "logoUrl": "/api/catalogos/logo/1234567890-1234567890"
}
```

### Actualizar catálogo
```http
PUT /api/catalogos/1
Content-Type: multipart/form-data

(mismos campos que crear)
```

**Nota**: Si no se envían `logo` o `imagenes`, se mantienen los existentes. Si se envían nuevos, reemplazan completamente los anteriores.

### Cambiar estado
```http
PATCH /api/catalogos/1/status
```

**Response:**
```json
{
  "id": 1,
  "codigo": 1,
  "nit": "1234567890",
  "nombre": "Restaurant El Buen Sabor",
  "estado": 0
}
```

### Obtener logo
```http
GET /api/catalogos/logo/1234567890-1234567890
```

**Response**: Imagen (JPEG/PNG)
**Headers**: Cache-Control: max-age=604800 (7 días)

### Obtener imagen del catálogo
```http
GET /api/catalogos/imagenes/1234567890-1
```

**Response**: Imagen (JPEG/PNG)
**Headers**: Cache-Control: max-age=604800 (7 días)

---

## � PERFILES DE SOCIOS

### Listar todos los perfiles
```http
GET /api/perfiles
```

**Response:**
```json
[
  {
    "id": 1,
    "socioId": 1,
    "nombreCompleto": "Juan Pérez García",
    "email": "juan.personal@gmail.com",
    "telefono": "70123456",
    "tituloProfesional": "Ingeniero de Software",
    "especialidad": "Desarrollo Full Stack",
    "anosExperiencia": 5,
    "resumenProfesional": "Desarrollador con experiencia en...",
    "modalidadTrabajo": "Remoto/Presencial",
    "ciudad": "La Paz",
    "zona": "Sopocachi",
    "fotoPerfilUrl": "/api/perfiles/foto-perfil/PERFIL-1-123456789.jpg",
    "fotoBannerUrl": "/api/perfiles/foto-banner/BANNER-1-123456789.jpg",
    "linkedinUrl": "https://linkedin.com/in/juanperez",
    "perfilPublico": true,
    "permiteContacto": true,
    "visualizaciones": 150,
    "estado": 1
  }
]
```

### Listar perfiles por estado
```http
GET /api/perfiles?estado=1
```

### Listar perfiles públicos
```http
GET /api/perfiles/publicos
```

**Nota**: Solo devuelve perfiles con `perfilPublico=true` y `estado=1`.

### Buscar por ciudad
```http
GET /api/perfiles/ciudad?q=La Paz
```

### Buscar por especialidad
```http
GET /api/perfiles/especialidad?q=Desarrollo
```

### Obtener perfil por ID
```http
GET /api/perfiles/1
```

### Obtener perfil por ID del socio
```http
GET /api/perfiles/socio/1
```

**Nota**: Un socio solo puede tener un perfil. Este endpoint busca el perfil asociado a un socio específico.

### Crear o actualizar perfil
```http
POST /api/perfiles
Content-Type: multipart/form-data

socioId: 1
nombreCompleto: Juan Pérez García
email: juan.personal@gmail.com
telefono: 70123456
tituloProfesional: Ingeniero de Software
especialidad: Desarrollo Full Stack
anosExperiencia: 5
resumenProfesional: Desarrollador con 5 años de experiencia...
modalidadTrabajo: Remoto/Presencial
ciudad: La Paz
zona: Sopocachi
linkedinUrl: https://linkedin.com/in/juanperez
facebookUrl: https://facebook.com/juanperez
twitterUrl: https://twitter.com/juanperez
instagramUrl: https://instagram.com/juanperez
whatsapp: 70123456
sitioWeb: https://juanperez.com
perfilPublico: true
permiteContacto: true
estado: 1
fotoPerfil: [file]
fotoBanner: [file]
```

**Nota importante**: 
- El socio ingresa TODA su información manualmente (nombre, email, teléfono, etc.)
- Los datos NO se recuperan automáticamente de la base de datos
- Puede usar información diferente a la registrada oficialmente
- Si ya existe un perfil para el socio, se actualizará
- Las fotos son opcionales.
- Si se envían nuevas fotos, reemplazan las anteriores.

**RnombreCompleto": "Juan Pérez García",
  "email": "juan.personal@gmail.com",
  "telefono": "70123456
```json
{
  "id": 1,
  "socioId": 1,
  "socioNombre": "Juan Pérez",
  "tituloProfesional": "Ingeniero de Software",
  "fotoPerfilUrl": "/api/perfiles/foto-perfil/PERFIL-1-123456789.jpg",
  "fotoBannerUrl": "/api/perfiles/foto-banner/BANNER-1-123456789.jpg",
  "estado": 1
}
```

### Actualizar perfil
```http
PUT /api/perfiles/1
Content-Type: multipart/form-data

(mismos campos que crear)
```

**Nota**: Si no se envían las fotos, se mantienen las existentes.

### Cambiar estado
```http
PATCH /api/perfiles/1/status
```

**Response:**
```json
{
  "id": 1,
  "socioId": 1,
  "estado": 0
}
```

### Incrementar visualizaciones
```http
POST /api/perfiles/1/visualizar
```

**Response:** 200 OK (sin contenido)

**Nota**: Útil para tracking cuando alguien ve un perfil.

### Eliminar foto de perfil
```http
DELETE /api/perfiles/1/foto-perfil
```

**Response:**
```json
{
  "id": 1,
  "fotoPerfilUrl": null
}
```

### Eliminar foto de banner
```http
DELETE /api/perfiles/1/foto-banner
```

**Response:**
```json
{
  "id": 1,
  "fotoBannerUrl": null
}
```

### Obtener foto de perfil
```http
GET /api/perfiles/foto-perfil/PERFIL-1-123456789.jpg
```

**Response**: Imagen (JPEG/PNG)
**Headers**: Cache-Control: max-age=604800 (7 días)

### Obtener foto de banner
```http
GET /api/perfiles/foto-banner/BANNER-1-123456789.jpg
```

**Response**: Imagen (JPEG/PNG)
**Headers**: Cache-Control: max-age=604800 (7 días)

---
## 🎯 GUÍA COMPLETA: CREAR PERFIL DE SOCIO CON POSTMAN

### Paso 1: Configurar la petición

1. **Método**: `POST`
2. **URL**: `http://localhost:8080/api/perfiles`
3. **Tipo**: `form-data` (en la pestaña Body)

### Paso 2: Configurar los campos en Body > form-data

#### Campos simples (type: Text)

| Key | Value | Descripción |
|-----|-------|-------------|
| `socioId` | `1` | ⚠️ **REQUERIDO** - ID del socio existente |
| `tituloProfesional` | `Ingeniero de Software Senior` | Título profesional |
| `especialidad` | `Desarrollo Full Stack` | Especialidad principal |
| `anosExperiencia` | `10` | Años de experiencia (número) |
| `resumenProfesional` | `Ingeniero con más de 10 años de experiencia...` | Descripción del perfil |
| `modalidadTrabajo` | `Híbrido` | Presencial/Remoto/Híbrido |
| `ciudad` | `La Paz` | Ciudad de trabajo |
| `zona` | `Zona Sur` | Zona específica |
| `linkedinUrl` | `https://linkedin.com/in/usuario` | URL de LinkedIn (opcional) |
| `facebookUrl` | `https://facebook.com/usuario` | URL de Facebook (opcional) |
| `twitterUrl` | `https://twitter.com/usuario` | URL de Twitter (opcional) |
| `instagramUrl` | `https://instagram.com/usuario` | URL de Instagram (opcional) |
| `whatsapp` | `+59170123456` | WhatsApp con código país |
| `sitioWeb` | `https://www.misitio.com` | Sitio web personal |
| `perfilPublico` | `true` | Mostrar perfil públicamente |
| `permiteContacto` | `true` | Permitir contacto directo |
| `estado` | `1` | 1=Activo, 0=Inactivo |

#### Archivos de imagen (type: File)

| Key | Value | Descripción |
|-----|-------|-------------|
| `fotoPerfil` | [Seleccionar archivo] | Foto de perfil (JPEG/PNG, max 5MB) |
| `fotoBanner` | [Seleccionar archivo] | Banner del perfil (JPEG/PNG, max 5MB) |

#### Arrays JSON (type: Text)

**⚠️ IMPORTANTE**: Estos campos deben enviarse como JSON válido en formato texto.

##### 1. Idiomas con nivel

```
Key: idiomas
Value: [{"idiomaId": 1, "nivel": "Avanzado"}, {"idiomaId": 2, "nivel": "Intermedio"}]
```

**Estructura del objeto SocioIdiomaDTO**:
```json
{
  "idiomaId": 1,        // ID del idioma (tabla: idiomas)
  "nivel": "Avanzado"   // Niveles: Básico, Intermedio, Avanzado, Nativo
}
```

**Ejemplo completo con múltiples idiomas**:
```json
[
  {
    "idiomaId": 1,
    "nivel": "Nativo"
  },
  {
    "idiomaId": 2,
    "nivel": "Avanzado"
  },
  {
    "idiomaId": 3,
    "nivel": "Intermedio"
  }
]
```

##### 2. Sectores (solo IDs)

```
Key: sectorIds
Value: [1, 2, 3]
```

Array simple de IDs de sectores (tabla: sectores)

##### 3. Servicios (solo IDs)

```
Key: servicioIds
Value: [1, 2, 3, 4]
```

Array simple de IDs de servicios (tabla: servicios)

##### 4. Especialidades (solo IDs)

```
Key: especialidadIds
Value: [1, 2]
```

Array simple de IDs de especialidades (tabla: especialidades)

### Paso 3: Configurar Headers (automático)

Postman configura automáticamente:
```
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary...
```

**⚠️ NO agregues manualmente el header Content-Type**, Postman lo hace solo.

### Paso 4: Ejemplo completo en Postman

#### Configuración visual en Postman:

```
Body > form-data:

☐ socioId                 | Text | 1
☐ tituloProfesional       | Text | Ingeniero de Software Senior
☐ especialidad            | Text | Desarrollo Full Stack
☐ anosExperiencia         | Text | 10
☐ resumenProfesional      | Text | Ingeniero con más de 10 años de experiencia en desarrollo web...
☐ modalidadTrabajo        | Text | Híbrido
☐ ciudad                  | Text | La Paz
☐ zona                    | Text | Zona Sur
☐ linkedinUrl             | Text | https://linkedin.com/in/juanperez
☐ facebookUrl             | Text | https://facebook.com/juanperez
☐ whatsapp                | Text | +59170123456
☐ sitioWeb                | Text | https://www.juanperez.com
☐ perfilPublico           | Text | true
☐ permiteContacto         | Text | true
☐ estado                  | Text | 1
☐ idiomas                 | Text | [{"idiomaId": 1, "nivel": "Nativo"}, {"idiomaId": 2, "nivel": "Avanzado"}]
☐ sectorIds               | Text | [1, 2, 3]
☐ servicioIds             | Text | [1, 2, 3, 4]
☐ especialidadIds         | Text | [1, 2]
☐ fotoPerfil              | File | [foto_perfil.jpg]
☐ fotoBanner              | File | [banner.jpg]
```

### Paso 5: Respuesta esperada (201 Created)

```json
{
  "id": 1,
  "socioId": 1,
  "tituloProfesional": "Ingeniero de Software Senior",
  "especialidad": "Desarrollo Full Stack",
  "anosExperiencia": 10,
  "resumenProfesional": "Ingeniero con más de 10 años de experiencia en desarrollo web...",
  "modalidadTrabajo": "Híbrido",
  "ciudad": "La Paz",
  "zona": "Zona Sur",
  "linkedinUrl": "https://linkedin.com/in/juanperez",
  "facebookUrl": "https://facebook.com/juanperez",
  "twitterUrl": null,
  "instagramUrl": null,
  "whatsapp": "+59170123456",
  "sitioWeb": "https://www.juanperez.com",
  "fotoPerfilUrl": "/api/perfiles/foto-perfil/PERFIL-1-foto_perfil.jpg",
  "fotoBannerUrl": "/api/perfiles/foto-banner/BANNER-1-banner.jpg",
  "perfilPublico": true,
  "permiteContacto": true,
  "estado": 1,
  "fechaCreacion": "2026-01-15T05:30:00",
  "fechaActualizacion": "2026-01-15T05:30:00",
  "idiomas": [
    {
      "id": 1,
      "idioma": {
        "id": 1,
        "nombre": "Español",
        "codigo": "es"
      },
      "nivel": "Nativo"
    },
    {
      "id": 2,
      "idioma": {
        "id": 2,
        "nombre": "Inglés",
        "codigo": "en"
      },
      "nivel": "Avanzado"
    }
  ],
  "sectores": [
    {
      "id": 1,
      "nombre": "Tecnología",
      "descripcion": "Sector tecnológico"
    },
    {
      "id": 2,
      "nombre": "Consultoría",
      "descripcion": "Consultoría empresarial"
    }
  ],
  "servicios": [
    {
      "id": 1,
      "nombre": "Desarrollo Web",
      "descripcion": "Desarrollo de aplicaciones web"
    },
    {
      "id": 2,
      "nombre": "Desarrollo Móvil",
      "descripcion": "Desarrollo de aplicaciones móviles"
    }
  ],
  "especialidades": [
    {
      "id": 1,
      "nombre": "Java",
      "descripcion": "Desarrollo en Java"
    },
    {
      "id": 2,
      "nombre": "JavaScript",
      "descripcion": "Desarrollo en JavaScript"
    }
  ]
}
```

### Paso 6: Errores comunes y soluciones

#### Error 1: "El ID del socio es requerido"
```json
{
  "timestamp": "2026-01-15T05:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "El ID del socio es requerido"
}
```
**Solución**: Asegúrate de incluir el campo `socioId` con un valor numérico.

#### Error 2: "Socio no encontrado con id: 999"
```json
{
  "timestamp": "2026-01-15T05:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Socio no encontrado con id: 999"
}
```
**Solución**: Verifica que el `socioId` exista en la tabla `socio`.

#### Error 3: "Idioma no encontrado con id: 99"
```json
{
  "timestamp": "2026-01-15T05:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Idioma no encontrado con id: 99"
}
```
**Solución**: Verifica que los IDs en el array `idiomas` existan en la tabla `idiomas`.

#### Error 4: JSON mal formateado
```json
{
  "timestamp": "2026-01-15T05:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Error al procesar idiomas: JSON inválido"
}
```
**Solución**: Verifica que el JSON esté correctamente formateado:
- Usa comillas dobles `"` para las claves y valores string
- No pongas comas al final del último elemento
- Cierra correctamente corchetes `[]` y llaves `{}`

#### Error 5: Error al guardar foto
```json
{
  "timestamp": "2026-01-15T05:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Error al guardar foto de perfil: File too large"
}
```
**Solución**: Verifica que las imágenes no excedan 5MB.

### Paso 7: Actualizar un perfil existente

Para actualizar, usa el mismo formato pero con método `PUT`:

```
PUT http://localhost:8080/api/perfiles/{id}
```

O incluye el campo `id` en el form-data para que se actualice automáticamente:

```
Key: id
Value: 1
```

### Paso 8: Verificar el perfil creado

```http
GET http://localhost:8080/api/perfiles/1
```

### Paso 9: Ver las imágenes

```http
GET http://localhost:8080/api/perfiles/foto-perfil/PERFIL-1-foto.jpg
GET http://localhost:8080/api/perfiles/foto-banner/BANNER-1-banner.jpg
```

### 📋 Checklist antes de enviar

- [ ] `socioId` está presente y es válido
- [ ] Arrays JSON están correctamente formateados
- [ ] IDs de idiomas, sectores, servicios y especialidades existen en la BD
- [ ] Niveles de idiomas son válidos: "Básico", "Intermedio", "Avanzado", "Nativo"
- [ ] Imágenes son JPEG o PNG y no exceden 5MB
- [ ] Body está configurado como `form-data` (NO raw JSON)
- [ ] NO agregaste manualmente el header Content-Type

### 💡 Consejos profesionales

1. **Obtener IDs disponibles antes de crear**:
   ```
   GET /api/idiomas          → Lista de idiomas
   GET /api/sectores         → Lista de sectores
   GET /api/servicios        → Lista de servicios
   GET /api/especialidades   → Lista de especialidades
   ```

2. **Crear perfil mínimo** (solo campos requeridos):
   ```
   socioId: 1
   estado: 1
   ```
   Todos los demás campos son opcionales.

3. **Testing incremental**:
   - Primero crea con campos mínimos
   - Luego actualiza agregando relaciones
   - Finalmente agrega imágenes

4. **Usar variables en Postman**:
   ```javascript
   // En Tests (después de crear):
   pm.environment.set("perfil_id", pm.response.json().id);
   
   // Luego usar:
   GET {{base_url}}/api/perfiles/{{perfil_id}}
   ```

5. **Colección recomendada en Postman**:
   ```
   📁 CADET API
     📁 1. Catálogos (obtener IDs)
       GET Listar Idiomas
       GET Listar Sectores
       GET Listar Servicios
       GET Listar Especialidades
     📁 2. Socios
       GET Listar Socios
     📁 3. Perfiles
       POST Crear Perfil (este endpoint)
       GET Ver Perfil
       PUT Actualizar Perfil
       GET Foto Perfil
       GET Foto Banner
   ```

---
## �📋 CÓDIGOS DE ESTADO HTTP

- **200 OK**: Operación exitosa
- **201 Created**: Recurso creado exitosamente
- **400 Bad Request**: Datos inválidos o faltantes
- **404 Not Found**: Recurso no encontrado
- **409 Conflict**: Recurso duplicado (CI, NIT, nombre, etc.)
- **500 Internal Server Error**: Error del servidor

---

## 🔍 RESPUESTAS DE ERROR

Formato estándar de error:
```json
{
  "timestamp": "2026-01-15T02:15:00",
  "status": 409,
  "error": "Conflict",
  "message": "Persona ya existe con CI: '12345678'",
  "path": "/api/partners",
  "details": null
}
```

---

## 📝 NOTAS IMPORTANTES

1. **Estados**: 
   - `0` = Inactivo
   - `1` = Activo

2. **Formato de fechas**: `YYYY-MM-DD` (ISO 8601)

3. **Archivos**:
   - Logos: JPEG, PNG (max 5MB)
   - QR: Generados automáticamente

4. **Validaciones**:
   - CI: Único por socio
   - Username: Único por usuario
   - NIT: Único por institución
   - Email: Formato válido
   - Contraseña: Mínimo 8 caracteres

5. **Prefijos automáticos**:
   - Roles: `ROLE_` + nombre
   - Documentos: `SOC-{codigo}-{UUID}`

6. **Rutas públicas**: Todos los endpoints `/api/**` son accesibles sin autenticación para desarrollo.

---

## 🚀 TESTING EN POSTMAN

### Variables de entorno recomendadas
```
base_url = http://localhost:8080
```

### Headers comunes
```
Content-Type: application/json
Accept: application/json
```

### Para multipart/form-data
```
Content-Type: multipart/form-data
```
(Postman lo configura automáticamente al seleccionar form-data)

---

**Última actualización**: 15 de enero de 2026
**Versión del API**: 0.0.1-SNAPSHOT
