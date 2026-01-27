# ============================================================================
# DOKPLOY DEPLOYMENT - CONFIGURACIÓN RÁPIDA
# ============================================================================

## 📦 Archivos Creados

✅ `Dockerfile` - Imagen Docker multi-stage optimizada
✅ `docker-compose.prod.yml` - Configuración de producción
✅ `.env.production` - Template de variables de entorno
✅ `application-prod.properties` - Configuración Spring Boot para producción
✅ `DEPLOYMENT_GUIDE.md` - Guía completa de deployment

---

## 🚀 Pasos Rápidos para Deploy

### 1. Variables de Entorno Obligatorias en Dokploy

```env
# Base de Datos (Dokploy puede crear automáticamente)
DB_DEV_URL=jdbc:postgresql://db:5432/cadet_db
DB_DEV_USER=cadet_user
DB_DEV_PASSWORD=[GENERAR_PASSWORD_SEGURO]
DB_DEV_NAME=cadet_db

# Seguridad (IMPORTANTE: Cambiar estos valores)
JWT_SECRET=[GENERAR_CON: openssl rand -base64 64]
USER_ADMIN_PASSWORD=[PASSWORD_SEGURO_ADMIN]

# Google OAuth2 (Obtener de Google Cloud Console)
GOOGLE_CLIENT_ID=tu-client-id.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=GOCSPX-tu-client-secret

# Cloudinary (Obtener de cloudinary.com)
CLOUDINARY_CLOUD_NAME=tu-cloud-name
CLOUDINARY_API_KEY=tu-api-key
CLOUDINARY_API_SECRET=tu-api-secret

# Dominio
APP_DOMAIN=https://tu-dominio.com
```

### 2. Configurar en Dokploy

1. **Crear Proyecto**: New Project → Docker Compose
2. **Conectar Repo**: Seleccionar tu repositorio Git
3. **Build Config**: 
   - Dockerfile path: `/Dockerfile`
   - Build command: `docker-compose -f docker-compose.prod.yml up -d`
4. **Environment**: Pegar las variables de arriba
5. **Database**: Crear PostgreSQL 17 en Dokploy
6. **Deploy**: Click en Deploy

### 3. Verificar Deployment

```bash
# Health check
curl https://tu-dominio.com/actuator/health

# Debería responder:
{"status":"UP"}
```

---

## 🔑 Credenciales a Obtener ANTES de Deploy

### Google OAuth2
1. Ir a: https://console.cloud.google.com/
2. Crear proyecto
3. APIs & Services → Credentials → Create OAuth 2.0 Client ID
4. URIs autorizados:
   - `https://tu-dominio.com/login/oauth2/code/google`
   - `https://tu-dominio.com/oauth2/callback`

### Cloudinary
1. Crear cuenta: https://cloudinary.com/
2. Dashboard → Account Details → Copiar credenciales

---

## ⚠️ IMPORTANTE - Seguridad

### Generar JWT Secret Seguro
```bash
openssl rand -base64 64
```

### Nunca Commitear
- ❌ `.env` con valores reales
- ❌ Passwords en código
- ❌ API keys en repositorio

---

## 📚 Documentación Completa

Ver `DEPLOYMENT_GUIDE.md` para:
- Troubleshooting
- Configuración avanzada
- SSL/HTTPS
- Monitoreo
- Backups

---

## ✅ Checklist Pre-Deploy

- [ ] Código en repositorio Git (GitHub/GitLab)
- [ ] Credenciales de Google OAuth2 obtenidas
- [ ] Cuenta de Cloudinary creada
- [ ] Dominio configurado (DNS apuntando al servidor)
- [ ] Variables de entorno preparadas
- [ ] Base de datos configurada en Dokploy

---

## 🆘 Problemas Comunes

**App no inicia:**
→ Ver logs en Dokploy Panel → Logs
→ Verificar variables de entorno

**No conecta a DB:**
→ Verificar que DB_DEV_URL use `db:5432` (nombre del servicio)
→ Verificar credenciales

**OAuth2 no funciona:**
→ Verificar URIs de redireccionamiento en Google Console
→ Deben coincidir exactamente con tu dominio

---

## 📞 Soporte

- Dokploy Docs: https://docs.dokploy.com
- Spring Boot Docs: https://spring.io/guides
- Este proyecto: Ver `DEPLOYMENT_GUIDE.md`
