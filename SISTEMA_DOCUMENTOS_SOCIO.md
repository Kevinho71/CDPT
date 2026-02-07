# Sistema de Gestión de Documentos Profesionales del Socio

## 📋 Descripción General

Este sistema permite a los psicólogos socios gestionar sus documentos profesionales (certificados, diplomas, títulos) desde su perfil público. Los visitantes pueden ver los documentos que el psicólogo decide mostrar, ordenados según su preferencia.

## 🏗️ Arquitectura

### Base de Datos

**Tabla: `documento_profesional`**
- Almacena la información del archivo (título, descripción, URL, tipo)
- Es el repositorio físico del documento

**Tabla: `socio_documentos`**
- Tabla intermedia que vincula el perfil del socio con sus documentos
- Controla la visibilidad (`es_visible`) y el orden (`orden`)
- Permite ocultar documentos sin eliminarlos

### Clases Principales

#### DTOs
- **`SocioDocumentoUploadDTO`**: Para subir nuevos documentos (incluye MultipartFile)
- **`SocioDocumentoEditDTO`**: Para actualizar título, descripción, visibilidad y orden
- **`SocioDocumentoCompleteDTO`**: Respuesta completa con toda la información del documento

#### Services
- **`SocioDocumentoService`**: Interface con métodos de gestión
- **`SocioDocumentoServiceImpl`**: Implementación que maneja la lógica de negocio
- **`ArchivoService`**: Maneja la subida de archivos a Cloudinary

#### Controllers
- **`SocioPerfilDocumentoController`**: Endpoints para el socio y públicos

## 📡 API Endpoints

### Endpoints Privados (Dashboard del Socio)

#### 1. Subir Nuevo Documento
```
POST /api/socio/perfil/{perfilId}/documentos
Content-Type: multipart/form-data

Parámetros:
- titulo (String, requerido): "Diplomado en Terapia Cognitiva"
- descripcion (String, opcional): "Universidad X, 200 horas"
- archivo (File, requerido): PDF o imagen (JPG/PNG)
- orden (Integer, opcional): 0, 1, 2... (default: 0)
- esVisible (Boolean, opcional): true/false (default: true)

Response: SocioDocumentoCompleteDTO (HTTP 201)
```

**Ejemplo con cURL:**
```bash
curl -X POST "http://localhost:8080/api/socio/perfil/1/documentos" \
  -F "titulo=Diplomado en Terapia Cognitiva" \
  -F "descripcion=Universidad X, 200 horas académicas" \
  -F "archivo=@certificado.pdf" \
  -F "orden=1" \
  -F "esVisible=true"
```

#### 2. Listar Mis Documentos
```
GET /api/socio/perfil/{perfilId}/documentos

Response: List<SocioDocumentoCompleteDTO>
```

Retorna **TODOS** los documentos del socio (visibles y ocultos), ordenados por el campo `orden`.

#### 3. Actualizar Documento
```
PUT /api/socio/perfil/{perfilId}/documentos/{docId}
Content-Type: application/json

Body:
{
  "titulo": "Nuevo título",
  "descripcion": "Nueva descripción",
  "esVisible": false,
  "orden": 5
}

Response: SocioDocumentoCompleteDTO
```

#### 4. Eliminar Documento
```
DELETE /api/socio/perfil/{perfilId}/documentos/{docId}

Response: HTTP 204 No Content
```

Elimina el documento de la base de datos y el archivo de Cloudinary.

### Endpoints Públicos (Landing Page)

#### Ver Documentos Visibles
```
GET /api/publico/perfil/{perfilId}/documentos

Response: List<SocioDocumentoCompleteDTO>
```

Retorna **SOLO** los documentos con `es_visible = true`, ordenados por el campo `orden`.

## 🔄 Flujo de Trabajo

### Paso 1: Subir Documento
1. El socio va a "Mi Perfil Público" → "Mis Documentos"
2. Hace clic en "Agregar Nuevo"
3. Completa el formulario:
   - Título: "Diplomado en Terapia Cognitiva"
   - Descripción (opcional): "Universidad X, 200 horas"
   - Archivo: Sube el PDF o imagen
4. El sistema:
   - Sube el archivo a Cloudinary → carpeta `SOCIO_DOCUMENTOS_PERFIL`
   - Crea el registro en `documento_profesional`
   - Vincula el documento al perfil en `socio_documentos`

### Paso 2: Gestionar Visibilidad y Orden
1. El socio ve una lista de sus documentos
2. Puede:
   - **Ocultar/Mostrar**: Toggle para cambiar `es_visible`
   - **Reordenar**: Drag & Drop para cambiar el campo `orden`
   - **Editar**: Cambiar título y descripción

### Paso 3: Visualización Pública
1. Un visitante entra a: `cadet.bo/perfil/juan-perez`
2. El frontend hace: `GET /api/publico/perfil/1/documentos`
3. El sistema retorna solo documentos con `es_visible = true`
4. Se muestran ordenados según el campo `orden`

## 📁 Almacenamiento de Archivos

### Cloudinary
- **Carpeta**: `SOCIO_DOCUMENTOS_PERFIL`
- **Formatos soportados**: PDF, JPG, PNG, GIF
- **Nombre del archivo**: `{timestamp}_{nombre_original}`
- **URL de ejemplo**: `https://res.cloudinary.com/.../SOCIO_DOCUMENTOS_PERFIL/1738904567_diploma.pdf`

### Detección de Tipo de Archivo
El sistema detecta automáticamente el tipo de archivo:
- `application/pdf` → "PDF"
- `image/jpeg` → "JPG"
- `image/png` → "PNG"
- Otros → Por extensión del archivo

## 🔒 Validaciones

### Backend
- ✅ El archivo es obligatorio al subir
- ✅ El título es obligatorio
- ✅ Validación de que el documento pertenece al perfil del socio
- ✅ El perfil del socio debe existir

### Tipos de archivo permitidos
- PDF (recomendado para certificados)
- JPG/JPEG (imágenes)
- PNG (imágenes)
- GIF (imágenes)

## 🎨 Ejemplo de Respuesta JSON

```json
{
  "id": 15,
  "documentoId": 42,
  "titulo": "Diplomado en Terapia Cognitiva",
  "descripcion": "Universidad X, 200 horas académicas",
  "archivoUrl": "https://res.cloudinary.com/.../SOCIO_DOCUMENTOS_PERFIL/1738904567_diploma.pdf",
  "tipoArchivo": "PDF",
  "orden": 1,
  "esVisible": true,
  "fechaSubida": "2026-02-07T10:30:00",
  "fechaAsociacion": "2026-02-07T10:30:00"
}
```

## 🛠️ Testing con Postman

### 1. Subir Documento (form-data)
```
POST http://localhost:8080/api/socio/perfil/1/documentos

Body (form-data):
- titulo: "Maestría en Psicología Clínica"
- descripcion: "Universidad Católica Boliviana - 2024"
- archivo: [Seleccionar archivo PDF]
- orden: 1
- esVisible: true
```

### 2. Listar Documentos
```
GET http://localhost:8080/api/socio/perfil/1/documentos
```

### 3. Actualizar Visibilidad (JSON)
```
PUT http://localhost:8080/api/socio/perfil/1/documentos/15

Body (raw JSON):
{
  "esVisible": false
}
```

### 4. Eliminar Documento
```
DELETE http://localhost:8080/api/socio/perfil/1/documentos/15
```

### 5. Ver Documentos Públicos
```
GET http://localhost:8080/api/publico/perfil/1/documentos
```

## 🚨 Manejo de Errores

### Errores Comunes

**404 - Perfil no encontrado**
```json
{
  "error": "Perfil socio no encontrado con ID: 999"
}
```

**400 - Documento no pertenece al perfil**
```json
{
  "error": "El documento no pertenece al perfil especificado"
}
```

**400 - Archivo vacío**
```json
{
  "error": "El archivo es obligatorio"
}
```

## 📊 Estructura de la Base de Datos

```sql
-- Documento profesional (el archivo)
CREATE TABLE documento_profesional (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    archivo_url VARCHAR(500) NOT NULL,
    tipo_archivo VARCHAR(50),
    estado INT DEFAULT 1,
    fecha_subida DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Relación socio-documento (control de visibilidad)
CREATE TABLE socio_documentos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fk_perfil_socio INT NOT NULL,
    fk_documento INT NOT NULL,
    orden INT DEFAULT 0,
    es_visible BOOLEAN DEFAULT TRUE,
    fecha_asociacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id),
    FOREIGN KEY (fk_documento) REFERENCES documento_profesional(id)
);
```

## 💡 Notas Importantes

1. **Sin límite de documentos**: Un socio puede subir tantos documentos como desee
2. **Soft delete en visibilidad**: Ocultar un documento no lo elimina, solo cambia `es_visible = false`
3. **Hard delete al eliminar**: Borrar un documento elimina el archivo de Cloudinary y los registros de BD
4. **Ordenamiento flexible**: El campo `orden` permite drag & drop en el frontend
5. **Validación de propiedad**: Al actualizar/eliminar se valida que el documento pertenezca al perfil

## 🔗 Archivos Creados/Modificados

### Nuevos Archivos
- ✅ `SocioDocumentoUploadDTO.java`
- ✅ `SocioDocumentoEditDTO.java`
- ✅ `SocioDocumentoCompleteDTO.java`
- ✅ `SocioPerfilDocumentoController.java`

### Archivos Modificados
- ✅ `ArchivoService.java` (agregado método `uploadFile`)
- ✅ `ArchivoServiceImpl.java` (implementación de `uploadFile`)
- ✅ `SocioDocumentoService.java` (agregados métodos de gestión)
- ✅ `SocioDocumentoServiceImpl.java` (implementación completa)
- ✅ `SocioDocumentoRepository.java` (agregadas queries de ordenamiento)

## 🎯 Checklist de Implementación Frontend

- [ ] Formulario de subida con drag & drop
- [ ] Lista de documentos con reordenamiento
- [ ] Toggle de visibilidad
- [ ] Modal de previsualización (PDF/Imagen)
- [ ] Confirmación de eliminación
- [ ] Vista pública (landing page del psicólogo)
