# 🚀 Guía de Deployment en Dokploy

## 📋 Índice
1. [Pre-requisitos](#pre-requisitos)
2. [Configuración de Dokploy](#configuración-de-dokploy)
3. [Variables de Entorno](#variables-de-entorno)
4. [Deployment](#deployment)
5. [Post-Deployment](#post-deployment)
6. [Troubleshooting](#troubleshooting)

---

## 📦 Pre-requisitos

### 1. Cuenta de Dokploy
- Tener acceso a un servidor con Dokploy instalado
- URL del panel de administración de Dokploy

### 2. Repositorio Git
- Código subido a GitHub/GitLab/Bitbucket
- Branch de producción configurado (main/master)

### 3. Credenciales Necesarias

#### Google Cloud (OAuth2)
1. Ir a [Google Cloud Console](https://console.cloud.google.com/)
2. Crear un proyecto o usar uno existente
3. Habilitar Google+ API
4. Crear credenciales OAuth 2.0:
   - Tipo: Aplicación web
   - URIs de redireccionamiento autorizados:
     ```
     https://tu-dominio.com/login/oauth2/code/google
     https://tu-dominio.com/oauth2/callback
     ```
5. Obtener Client ID y Client Secret

#### Cloudinary (Almacenamiento de Imágenes)
1. Crear cuenta en [Cloudinary](https://cloudinary.com/)
2. Obtener credenciales del Dashboard:
   - Cloud Name
   - API Key
   - API Secret

#### Base de Datos
- Dokploy puede crear automáticamente una base de datos PostgreSQL
- O puedes usar una externa (AWS RDS, DigitalOcean, etc.)

---

## ⚙️ Configuración de Dokploy

### Paso 1: Crear Nuevo Proyecto

1. Acceder al panel de Dokploy
2. Click en **"New Project"**
3. Nombre: `cadet-backend`
4. Seleccionar tipo: **"Docker Compose"** o **"Dockerfile"**

### Paso 2: Conectar Repositorio

1. **Source**: Seleccionar Git Provider (GitHub/GitLab)
2. **Repository**: Elegir el repositorio
3. **Branch**: `main` o `master`
4. **Build Path**: `/` (raíz del proyecto)

### Paso 3: Configurar Build

**Opción A: Usando docker-compose.prod.yml**
```yaml
Build Command: docker-compose -f docker-compose.prod.yml up -d
```

**Opción B: Usando Dockerfile directamente**
```yaml
Build Command: docker build -t cadet-backend .
Run Command: docker run -p 8080:8080 cadet-backend
```

---

## 🔐 Variables de Entorno

### En el Panel de Dokploy

Ir a la sección **Environment Variables** y agregar:

```bash
# Base de Datos
DB_DEV_URL=jdbc:postgresql://db:5432/cadet_db
DB_DEV_USER=cadet_user
DB_DEV_PASSWORD=TuPasswordSeguro123!
DB_DEV_NAME=cadet_db

# Seguridad
JWT_SECRET=UnStringMuyLargoYAleatorioDeAlMenos64CaracteresParaProduccion123456
USER_ADMIN_PASSWORD=AdminPass123!Secure

# Google OAuth2
GOOGLE_CLIENT_ID=123456789-abcdefghijklmnop.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=GOCSPX-abcdefghijklmnopqrstuvwxyz

# Cloudinary
CLOUDINARY_CLOUD_NAME=tu-cloud-name
CLOUDINARY_API_KEY=123456789012345
CLOUDINARY_API_SECRET=abcdefghijklmnopqrstuvwxyz

# Aplicación
APP_DOMAIN=https://tu-dominio.com
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8080

# JVM (Opcional - optimización)
JAVA_OPTS=-Xms512m -Xmx1024m -XX:+UseG1GC
```

### Generar JWT Secret Seguro

```bash
# En tu terminal local
openssl rand -base64 64
```

---

## 🗄️ Configuración de Base de Datos

### Opción A: Base de Datos Interna de Dokploy

1. En Dokploy, ir a **"Add Database"**
2. Seleccionar **PostgreSQL 17**
3. Configurar:
   - Name: `cadet_db`
   - Username: `cadet_user`
   - Password: (generar una segura)
4. Dokploy creará automáticamente el contenedor

### Opción B: Base de Datos Externa

Modificar `DB_DEV_URL` en las variables de entorno:
```bash
DB_DEV_URL=jdbc:postgresql://tu-servidor.com:5432/cadet_db
```

---

## 🚀 Deployment

### 1. Verificar Configuración

Antes de deployar, verificar:
- ✅ Todas las variables de entorno están configuradas
- ✅ El Dockerfile existe en la raíz del proyecto
- ✅ Base de datos está creada y accesible
- ✅ Credenciales de Google OAuth2 configuradas
- ✅ Cloudinary configurado

### 2. Deploy

1. En Dokploy, click en **"Deploy"**
2. Dokploy ejecutará:
   - Git clone del repositorio
   - Build de la imagen Docker (puede tardar 3-5 minutos)
   - Creación de contenedores
   - Inicialización de la base de datos

### 3. Monitorear Logs

Ver logs en tiempo real:
```bash
# En Dokploy panel
Ir a "Logs" > Ver build logs y runtime logs
```

---

## 🔄 Post-Deployment

### 1. Verificar Estado de la Aplicación

Acceder a:
```
https://tu-dominio.com/actuator/health
```

Debería responder:
```json
{
  "status": "UP"
}
```

### 2. Acceder al Sistema

**Panel de Administración:**
```
https://tu-dominio.com/login
Usuario: admin
Password: (el configurado en USER_ADMIN_PASSWORD)
```

**API Documentation (Swagger):**
```
https://tu-dominio.com/swagger-ui.html
```

### 3. Verificar Base de Datos

Conectarse a la base de datos y verificar que las tablas se crearon:
```sql
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public';
```

### 4. Configurar SSL/HTTPS

Dokploy puede configurar automáticamente SSL con Let's Encrypt:
1. Ir a **Settings** > **SSL**
2. Activar **"Auto SSL"**
3. Ingresar tu dominio

---

## 🌐 Configuración de Dominio

### 1. DNS Records

Agregar en tu proveedor de DNS:

```
Tipo: A
Nombre: @
Valor: [IP de tu servidor Dokploy]

Tipo: A  
Nombre: www
Valor: [IP de tu servidor Dokploy]
```

### 2. Configurar en Dokploy

1. Ir a **Domains**
2. Agregar dominio: `tu-dominio.com`
3. Dokploy configurará automáticamente el proxy inverso

---

## 🔍 Troubleshooting

### Problema: La aplicación no inicia

**Síntoma:** Container se reinicia constantemente

**Solución:**
1. Ver logs: `docker logs cadet_app_prod`
2. Verificar variables de entorno
3. Verificar conectividad con la base de datos:
   ```bash
   docker exec -it cadet_app_prod sh
   wget db:5432
   ```

### Problema: Error de conexión a base de datos

**Síntoma:** `Connection refused` o `Unknown host`

**Solución:**
1. Verificar que el contenedor de DB está corriendo
2. Verificar `DB_DEV_URL` apunta a `db:5432` (nombre del servicio)
3. Verificar credenciales de DB

### Problema: OAuth2 Google no funciona

**Síntoma:** Error al hacer login con Google

**Solución:**
1. Verificar URIs de redireccionamiento en Google Console
2. Debe incluir: `https://tu-dominio.com/login/oauth2/code/google`
3. Verificar `GOOGLE_CLIENT_ID` y `GOOGLE_CLIENT_SECRET`

### Problema: Imágenes no se suben

**Síntoma:** Error al subir imágenes a Cloudinary

**Solución:**
1. Verificar credenciales de Cloudinary
2. Verificar límites de la cuenta gratuita
3. Ver logs de la aplicación

### Problema: Out of Memory

**Síntoma:** Aplicación se detiene por falta de memoria

**Solución:**
1. Ajustar `JAVA_OPTS`:
   ```bash
   JAVA_OPTS=-Xms256m -Xmx512m
   ```
2. O aumentar recursos del servidor en Dokploy

---

## 📊 Monitoreo y Mantenimiento

### Health Checks

La aplicación incluye health checks automáticos:
```
/actuator/health
```

### Logs

Ver logs en Dokploy:
```
Panel > Tu Proyecto > Logs
```

O por SSH:
```bash
docker logs -f cadet_app_prod
```

### Backups de Base de Datos

Configurar backups automáticos en Dokploy:
1. Ir a **Database** > **Backups**
2. Configurar frecuencia (diario recomendado)
3. Configurar retención (7-30 días)

### Actualización de la Aplicación

1. Push cambios a tu repositorio Git
2. En Dokploy: Click en **"Redeploy"**
3. Dokploy automáticamente:
   - Hace pull de los cambios
   - Reconstruye la imagen
   - Reinicia la aplicación con zero-downtime

---

## 🔒 Mejores Prácticas de Seguridad

1. **Nunca commitear `.env` con valores reales**
2. **Usar passwords fuertes** (mínimo 16 caracteres)
3. **Rotar JWT_SECRET** periódicamente
4. **Activar HTTPS** (SSL) siempre
5. **Limitar acceso a la base de datos** (firewall)
6. **Hacer backups regulares**
7. **Monitorear logs** por actividad sospechosa

---

## 📞 Soporte

Para problemas específicos:
- Documentación de Dokploy: https://docs.dokploy.com
- Spring Boot: https://spring.io/projects/spring-boot
- PostgreSQL: https://www.postgresql.org/docs/

---

## ✅ Checklist de Deployment

- [ ] Código pusheado a repositorio Git
- [ ] Dockerfile creado y probado localmente
- [ ] Variables de entorno configuradas en Dokploy
- [ ] Base de datos creada
- [ ] Credenciales de Google OAuth2 obtenidas
- [ ] Cuenta de Cloudinary creada
- [ ] Dominio apuntando al servidor
- [ ] SSL/HTTPS configurado
- [ ] Primera prueba de deployment exitosa
- [ ] Health check respondiendo
- [ ] Login funcionando
- [ ] Upload de imágenes funcionando
- [ ] Backups configurados

---

**¡Listo para producción! 🎉**
