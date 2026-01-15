# 🧪 Guía de Pruebas OAuth2 con Postman - CADET

## ⚠️ **IMPORTANTE: Limitación de Postman con OAuth2**

**NO es posible probar el flujo completo de Google OAuth2 con Postman** porque:
1. Google requiere que el usuario interactúe con su página de login (navegador real)
2. Postman no puede simular la interacción del usuario con Google
3. El flujo OAuth2 necesita redirecciones del navegador

**SIN EMBARGO**, puedes probar:
- ✅ Los endpoints de validación de token
- ✅ El health check
- ✅ Obtener información del usuario

---

## 📋 **Flujo de Prueba Recomendado**

### **Paso 1: Obtener un Token JWT (Usando Navegador)**

1. **Inicia tu aplicación:**
   ```bash
   mvn spring-boot:run
   ```

2. **Abre el navegador en:**
   ```
   http://localhost:8080/oauth2/authorization/google
   ```

3. **Proceso automático:**
   - Google te pedirá login
   - Después de autenticarte, serás redirigido a:
   ```
   http://localhost:3000/oauth2/callback?token=eyJhbGciOiJIUzI1NiJ9.eyJwZXJz...&personaId=123&email=tu@gmail.com&isNewUser=false
   ```

4. **Copia el token** del parámetro `token=` en la URL

---

## 🔧 **Configuración de Postman**

### **Crear Colección "CADET OAuth2"**

#### **1. Variable de Entorno**

Crea un Environment llamado `CADET Local` con:

| Variable | Initial Value | Current Value |
|----------|---------------|---------------|
| `base_url` | `http://localhost:8080` | `http://localhost:8080` |
| `token` | (vacío - lo pegarás después) | `eyJhbGciOiJIUzI1NiJ9...` |

---

## 📡 **Endpoints para Probar en Postman**

### **1️⃣ Health Check (Sin autenticación)**

**Propósito**: Verificar que la API está funcionando.

**Request:**
```
GET {{base_url}}/api/auth/health
```

**Headers:**
```
(ninguno necesario)
```

**Expected Response (200 OK):**
```json
{
  "status": "OK",
  "message": "OAuth2 API funcionando correctamente",
  "timestamp": 1705286400000
}
```

---

### **2️⃣ Validar Token JWT**

**Propósito**: Verificar que un token es válido.

**Request:**
```
POST {{base_url}}/api/auth/validate
```

**Headers:**
```
Authorization: Bearer {{token}}
```

**Expected Response (200 OK):**
```json
{
  "valid": true,
  "email": "juan@gmail.com",
  "personaId": 123
}
```

**Error Response (401 Unauthorized):**
```json
{
  "error": "Token expirado"
}
```

---

### **3️⃣ Obtener Usuario Actual**

**Propósito**: Obtener información del usuario autenticado.

**Request:**
```
GET {{base_url}}/api/auth/me
```

**Headers:**
```
Authorization: Bearer {{token}}
```

**Expected Response (200 OK):**
```json
{
  "email": "juan@gmail.com",
  "personaId": 123
}
```

**Error Response (401 Unauthorized):**
```json
{
  "error": "Token inválido"
}
```

---

## 🎯 **Pruebas Paso a Paso en Postman**

### **Test 1: Verificar que el Backend está corriendo**

1. Abre Postman
2. Crea una nueva request: `GET {{base_url}}/api/auth/health`
3. Click en **Send**
4. Deberías ver `200 OK` con el mensaje de salud

**Si falla:**
- Verifica que el backend esté corriendo (`mvn spring-boot:run`)
- Verifica que el puerto sea 8080

---

### **Test 2: Obtener Token con Google (Navegador)**

1. **En el navegador**, ve a:
   ```
   http://localhost:8080/oauth2/authorization/google
   ```

2. **Opciones según tu caso:**

   **Caso A: Usuario ya registrado en la BD**
   - Email en tabla `persona`: `juan@gmail.com`
   - Primera vez con Google: Crea registro en `usuario_social`
   - Login exitoso: Token en URL

   **Caso B: Usuario recurrente con Google**
   - Ya existe en `usuario_social`
   - Login inmediato: Token en URL

   **Caso C: Email NO registrado**
   - Google login exitoso pero backend rechaza
   - Error en URL: `?error=El correo no está registrado como socio activo`

3. **Copia el token** de la URL (después de `token=`)

---

### **Test 3: Guardar Token en Postman**

1. En Postman, ve a **Environments** → `CADET Local`
2. En la variable `token`, pega el token copiado
3. Click en **Save**

---

### **Test 4: Validar el Token**

1. Crea una nueva request: `POST {{base_url}}/api/auth/validate`
2. Ve a **Headers** y agrega:
   ```
   Authorization: Bearer {{token}}
   ```
3. Click en **Send**
4. Deberías ver:
   ```json
   {
     "valid": true,
     "email": "tu@gmail.com",
     "personaId": 123
   }
   ```

**Tests automáticos de Postman (opcional):**

En la pestaña **Tests**, agrega:
```javascript
pm.test("Status code es 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Token es válido", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.valid).to.eql(true);
});

pm.test("Email existe", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.email).to.exist;
});
```

---

### **Test 5: Obtener Usuario Actual**

1. Crea una nueva request: `GET {{base_url}}/api/auth/me`
2. Ve a **Headers** y agrega:
   ```
   Authorization: Bearer {{token}}
   ```
3. Click en **Send**
4. Deberías ver los datos del usuario autenticado

---

## 🔍 **Pruebas de Escenarios Negativos**

### **A. Token Inválido**

1. Modifica manualmente el token en Environment
2. Cambia algunos caracteres
3. Intenta `POST /api/auth/validate`
4. Deberías obtener `401 Unauthorized`:
   ```json
   {
     "error": "Token inválido: ..."
   }
   ```

---

### **B. Token Expirado**

1. Espera 24 horas (o cambia `jwt.expiration` en `application.properties` a 60000 = 1 minuto)
2. Intenta `POST /api/auth/validate`
3. Deberías obtener:
   ```json
   {
     "error": "Token expirado"
   }
   ```

---

### **C. Sin Token (Missing Authorization)**

1. Quita el header `Authorization`
2. Intenta `GET /api/auth/me`
3. Deberías obtener `400 Bad Request`:
   ```json
   {
     "error": "Token inválido"
   }
   ```

---

## 📊 **Verificar en Base de Datos**

Después de hacer login con Google por primera vez, verifica en PostgreSQL:

```sql
-- Ver usuarios sociales creados
SELECT * FROM usuario_social ORDER BY fecha_vinculacion DESC;

-- Ver última autenticación
SELECT 
    us.provider,
    us.email,
    us.nombre_completo,
    us.fecha_vinculacion,
    us.fecha_ultima_autenticacion,
    p.nombrecompleto as persona_nombre
FROM usuario_social us
JOIN persona p ON us.fk_persona = p.id
WHERE us.estado = 1;

-- Verificar vinculación automática
SELECT 
    p.email,
    p.nombrecompleto,
    us.provider,
    us.provider_id,
    us.fecha_vinculacion
FROM persona p
LEFT JOIN usuario_social us ON p.id = us.fk_persona
WHERE p.email = 'tu@gmail.com';
```

---

## 🚀 **Colección Completa de Postman (JSON)**

Importa esta colección en Postman:

```json
{
  "info": {
    "name": "CADET OAuth2 API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Health Check",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "{{base_url}}/api/auth/health",
          "host": ["{{base_url}}"],
          "path": ["api", "auth", "health"]
        }
      }
    },
    {
      "name": "Validate Token",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{token}}",
            "type": "text"
          }
        ],
        "url": {
          "raw": "{{base_url}}/api/auth/validate",
          "host": ["{{base_url}}"],
          "path": ["api", "auth", "validate"]
        }
      }
    },
    {
      "name": "Get Current User",
      "request": {
        "method": "GET",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{token}}",
            "type": "text"
          }
        ],
        "url": {
          "raw": "{{base_url}}/api/auth/me",
          "host": ["{{base_url}}"],
          "path": ["api", "auth", "me"]
        }
      }
    }
  ]
}
```

**Para importar:**
1. Postman → **Import** → Pega el JSON anterior
2. Configura el Environment `CADET Local` con `base_url` y `token`

---

## 🧪 **Alternativa: Probar con cURL (Terminal)**

Si prefieres la terminal en lugar de Postman:

### **1. Health Check**
```bash
curl http://localhost:8080/api/auth/health
```

### **2. Validar Token**
```bash
curl -X POST http://localhost:8080/api/auth/validate \
  -H "Authorization: Bearer TU_TOKEN_AQUI"
```

### **3. Obtener Usuario**
```bash
curl http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer TU_TOKEN_AQUI"
```

---

## 📝 **Checklist de Prueba Completa**

Usa esta lista para verificar que todo funciona:

- [ ] ✅ Backend corriendo (`mvn spring-boot:run`)
- [ ] ✅ Health check responde OK
- [ ] ✅ Google Cloud Console configurado (CLIENT_ID, SECRET)
- [ ] ✅ Variables en `.env` configuradas
- [ ] ✅ Login con Google en navegador funciona
- [ ] ✅ Token JWT recibido en URL callback
- [ ] ✅ Token copiado a Postman Environment
- [ ] ✅ Validación de token exitosa en Postman
- [ ] ✅ Endpoint `/api/auth/me` retorna datos correctos
- [ ] ✅ Token inválido retorna error 401
- [ ] ✅ Registro en `usuario_social` creado en BD
- [ ] ✅ `fecha_ultima_autenticacion` se actualiza en logins posteriores

---

## 🐛 **Troubleshooting**

### **Error: "redirect_uri_mismatch"**
**Solución**: Agrega `http://localhost:8080/login/oauth2/code/google` en Google Cloud Console → URIs de redirección.

### **Error: "Token inválido" inmediatamente después de login**
**Solución**: Verifica que `JWT_SECRET` en `.env` no haya cambiado entre el login y la validación.

### **Error: "El correo no está registrado como socio activo"**
**Solución**: 
```sql
-- Registra el socio primero en la BD
INSERT INTO persona (id, nombrecompleto, email, ci, estado) 
VALUES (1, 'Juan Pérez', 'juan@gmail.com', '12345678', 1);
```

### **Error: "CORS policy"** (desde frontend React)
**Solución**: Agrega configuración CORS en `SecurityConfig.java`:
```java
.cors(cors -> cors.configurationSource(request -> {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:3000"));
    config.setAllowedMethods(List.of("*"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);
    return config;
}))
```

---

## 📞 **Contacto y Soporte**

Si tienes problemas:
1. Revisa los logs del backend en la consola
2. Verifica que las 3 variables de entorno estén configuradas
3. Confirma que la configuración de Google Cloud coincida con esta guía
4. Revisa la base de datos para ver si el registro se creó

---

**Recuerda**: El flujo OAuth2 completo REQUIERE navegador. Postman solo sirve para probar los endpoints posteriores al login.
