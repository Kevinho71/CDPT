# Configuración de Google OAuth2 - CADET

## Descripción del Flujo Implementado

Este sistema implementa autenticación OAuth2 con Google siguiendo un flujo de 3 pasos:

### 1️⃣ **Búsqueda Directa (Usuario Recurrente)**
- Busca en `usuario_social` por `provider_id` y `provider = 'GOOGLE'`
- Si existe: actualiza `fecha_ultima_autenticacion` y genera JWT
- **Resultado**: Login exitoso inmediato

### 2️⃣ **Auto-Linking (Usuario Existente, Primera vez con Google)**
- Si no existe en `usuario_social`, busca en `persona` por `email`
- Si el email coincide: crea registro en `usuario_social` vinculado a esa persona
- **Resultado**: Cuentas vinculadas automáticamente + JWT

### 3️⃣ **Rechazo (Usuario Desconocido)**
- Si el email no existe en `persona`: **rechaza el acceso**
- Mensaje: "El correo no está registrado como socio activo"
- **Resultado**: Usuario debe registrarse manualmente primero

---

## Configuración de Google Cloud Console

### Paso 1: Crear Proyecto en Google Cloud
1. Ve a [Google Cloud Console](https://console.cloud.google.com/)
2. Crea un nuevo proyecto o selecciona uno existente
3. Nombre del proyecto: `CADET-OAuth`

### Paso 2: Habilitar Google+ API
1. En el menú lateral, ve a **APIs y servicios** > **Biblioteca**
2. Busca "Google+ API"
3. Haz clic en **Habilitar**

### Paso 3: Crear Credenciales OAuth2
1. Ve a **APIs y servicios** > **Credenciales**
2. Clic en **+ CREAR CREDENCIALES** > **ID de cliente de OAuth 2.0**
3. Si es la primera vez, configura la **Pantalla de consentimiento de OAuth**:
   - Tipo de usuario: **Externo**
   - Nombre de la aplicación: `CADET - Oficina Virtual`
   - Correo de asistencia del usuario: tu email
   - Logotipo (opcional)
   - Dominios autorizados: `localhost` (desarrollo)
   - Ámbitos: `email`, `profile`, `openid`

### Paso 4: Configurar URIs de Redirección
En la configuración del cliente OAuth2:

**Tipo de aplicación**: Aplicación web

**URIs de redirección autorizados**:
```
http://localhost:8080/login/oauth2/code/google
http://localhost:3000/oauth2/callback
```

**Orígenes de JavaScript autorizados**:
```
http://localhost:8080
http://localhost:3000
```

### Paso 5: Obtener Credenciales
Una vez creado, verás:
- **ID de cliente**: `123456789-xxxxxx.apps.googleusercontent.com`
- **Secreto del cliente**: `GOCSPX-xxxxxxxxxxxx`

---

## Configuración del Backend (Spring Boot)

### Opción 1: Variables de Entorno (.env)
Crea o edita el archivo `.env` en la raíz del proyecto:

```properties
# Google OAuth2 Credentials
GOOGLE_CLIENT_ID=tu-client-id-aqui.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=tu-client-secret-aqui

# JWT Configuration
JWT_SECRET=cadet_super_secret_key_2026_change_in_production
```

### Opción 2: application.properties (No recomendado para producción)
Edita `src/main/resources/application.properties`:

```properties
spring.security.oauth2.client.registration.google.client-id=tu-client-id
spring.security.oauth2.client.registration.google.client-secret=tu-client-secret
```

---

## Configuración del Frontend (React)

### 1. Instalar Dependencias
```bash
npm install axios js-cookie
```

### 2. Crear Servicio de Autenticación

**`src/services/authService.js`**:
```javascript
import axios from 'axios';
import Cookies from 'js-cookie';

const API_URL = 'http://localhost:8080';

export const authService = {
  // Inicia el flujo de login con Google
  loginWithGoogle() {
    window.location.href = `${API_URL}/oauth2/authorization/google`;
  },

  // Procesa el callback después del login con Google
  handleOAuth2Callback(params) {
    if (params.error) {
      throw new Error(params.error);
    }

    const { token, personaId, email, isNewUser } = params;
    
    // Guardar token en cookie (seguro, httpOnly)
    Cookies.set('cadet_token', token, { expires: 1 }); // 1 día
    
    // Guardar info del usuario en localStorage
    localStorage.setItem('user', JSON.stringify({
      personaId,
      email,
      isNewUser
    }));

    return { token, personaId, email, isNewUser };
  },

  // Obtiene el token actual
  getToken() {
    return Cookies.get('cadet_token');
  },

  // Verifica si el usuario está autenticado
  isAuthenticated() {
    return !!this.getToken();
  },

  // Cierra sesión
  logout() {
    Cookies.remove('cadet_token');
    localStorage.removeItem('user');
  },

  // Valida el token con el backend
  async validateToken() {
    const token = this.getToken();
    if (!token) return false;

    try {
      const response = await axios.post(
        `${API_URL}/api/auth/validate`,
        {},
        {
          headers: { Authorization: `Bearer ${token}` }
        }
      );
      return response.data.valid;
    } catch (error) {
      return false;
    }
  }
};
```

### 3. Crear Componente de Login

**`src/components/LoginButton.jsx`**:
```jsx
import React from 'react';
import { authService } from '../services/authService';

function LoginButton() {
  const handleGoogleLogin = () => {
    authService.loginWithGoogle();
  };

  return (
    <button 
      onClick={handleGoogleLogin}
      className="btn-google-login"
    >
      <img src="/google-icon.svg" alt="Google" />
      Iniciar sesión con Google
    </button>
  );
}

export default LoginButton;
```

### 4. Crear Página de Callback

**`src/pages/OAuth2Callback.jsx`**:
```jsx
import React, { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { authService } from '../services/authService';

function OAuth2Callback() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const [error, setError] = useState(null);

  useEffect(() => {
    const processCallback = async () => {
      try {
        // Obtener parámetros de la URL
        const params = {
          token: searchParams.get('token'),
          personaId: searchParams.get('personaId'),
          email: searchParams.get('email'),
          isNewUser: searchParams.get('isNewUser') === 'true',
          error: searchParams.get('error')
        };

        // Procesar el callback
        const userData = authService.handleOAuth2Callback(params);

        // Redirigir al dashboard
        if (userData.isNewUser) {
          navigate('/welcome'); // Primera vez con Google
        } else {
          navigate('/dashboard'); // Usuario recurrente
        }

      } catch (err) {
        setError(err.message);
      }
    };

    processCallback();
  }, [searchParams, navigate]);

  if (error) {
    return (
      <div className="error-container">
        <h2>Error de Autenticación</h2>
        <p>{error}</p>
        <button onClick={() => navigate('/login')}>
          Volver al Login
        </button>
      </div>
    );
  }

  return (
    <div className="loading-container">
      <p>Procesando autenticación...</p>
    </div>
  );
}

export default OAuth2Callback;
```

### 5. Configurar Rutas (React Router)

**`src/App.jsx`**:
```jsx
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginButton from './components/LoginButton';
import OAuth2Callback from './pages/OAuth2Callback';
import Dashboard from './pages/Dashboard';
import ProtectedRoute from './components/ProtectedRoute';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginButton />} />
        <Route path="/oauth2/callback" element={<OAuth2Callback />} />
        <Route 
          path="/dashboard" 
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          } 
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
```

### 6. Crear Ruta Protegida

**`src/components/ProtectedRoute.jsx`**:
```jsx
import React from 'react';
import { Navigate } from 'react-router-dom';
import { authService } from '../services/authService';

function ProtectedRoute({ children }) {
  if (!authService.isAuthenticated()) {
    return <Navigate to="/login" replace />;
  }

  return children;
}

export default ProtectedRoute;
```

---

## Endpoints de la API

### 1. **Inicio de Sesión con Google**
```
GET http://localhost:8080/oauth2/authorization/google
```
Redirige al usuario a Google para autenticarse.

### 2. **Callback de Google (automático)**
```
GET http://localhost:8080/login/oauth2/code/google
```
Spring Security procesa automáticamente la respuesta de Google.

### 3. **Validar Token**
```
POST http://localhost:8080/api/auth/validate
Headers: Authorization: Bearer {token}
```
Respuesta exitosa:
```json
{
  "valid": true,
  "email": "juan@gmail.com",
  "personaId": 123
}
```

### 4. **Obtener Usuario Actual**
```
GET http://localhost:8080/api/auth/me
Headers: Authorization: Bearer {token}
```
Respuesta:
```json
{
  "email": "juan@gmail.com",
  "personaId": 123
}
```

### 5. **Health Check**
```
GET http://localhost:8080/api/auth/health
```
Respuesta:
```json
{
  "status": "OK",
  "message": "OAuth2 API funcionando correctamente",
  "timestamp": 1705286400000
}
```

---

## Estructura de la Base de Datos

### Tabla `usuario_social`
```sql
CREATE TABLE usuario_social (
    id SERIAL PRIMARY KEY,
    provider VARCHAR(20) NOT NULL DEFAULT 'GOOGLE',
    provider_id VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    nombre_completo VARCHAR(255),
    foto_perfil_url VARCHAR(500),
    fk_persona INTEGER NOT NULL,
    access_token TEXT,
    refresh_token TEXT,
    token_expires_at TIMESTAMP,
    email_verificado BOOLEAN DEFAULT true,
    fecha_vinculacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_ultima_autenticacion TIMESTAMP,
    estado INTEGER DEFAULT 1,
    CONSTRAINT fk_usuario_social_persona 
        FOREIGN KEY (fk_persona) REFERENCES persona(id) ON DELETE CASCADE,
    CONSTRAINT uk_provider_id UNIQUE (provider, provider_id)
);
```

---

## Pruebas

### 1. Probar Health Check
```bash
curl http://localhost:8080/api/auth/health
```

### 2. Iniciar Login con Google (desde navegador)
```
http://localhost:8080/oauth2/authorization/google
```

### 3. Validar Token (después de login)
```bash
curl -X POST http://localhost:8080/api/auth/validate \
  -H "Authorization: Bearer {tu-token-jwt}"
```

---

## Solución de Problemas Comunes

### Error: "redirect_uri_mismatch"
**Causa**: La URI de redirección no está autorizada en Google Cloud Console.

**Solución**:
1. Ve a Google Cloud Console > Credenciales
2. Edita tu ID de cliente OAuth 2.0
3. Agrega exactamente: `http://localhost:8080/login/oauth2/code/google`

### Error: "El correo no está registrado como socio activo"
**Causa**: El email de Google no existe en la tabla `persona`.

**Solución**:
1. Registra manualmente el socio en la base de datos primero
2. Asegúrate que el campo `correo` en `persona` sea igual al Gmail

### Error: "Token inválido o expirado"
**Causa**: El JWT ha expirado (24 horas por defecto).

**Solución**:
- Relogin con Google
- O ajusta `jwt.expiration` en application.properties

---

## Seguridad en Producción

### 1. Cambiar JWT Secret
Genera una clave segura:
```bash
openssl rand -base64 64
```

### 2. Configurar HTTPS
En producción, cambia:
- `http://localhost` → `https://tudominio.com`
- Actualiza URIs en Google Cloud Console

### 3. Variables de Entorno
Nunca commits credenciales. Usa:
- `.env` para desarrollo (añadir a `.gitignore`)
- Variables de entorno del servidor en producción

### 4. CORS
Configura CORS en SecurityConfig:
```java
.cors(cors -> cors.configurationSource(request -> {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("https://tudominio.com"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    return config;
}))
```

---

## Diagrama del Flujo Completo

```
┌─────────────┐
│   Frontend  │
│  (React)    │
└──────┬──────┘
       │ 1. Click "Login con Google"
       │ GET /oauth2/authorization/google
       ▼
┌─────────────┐
│   Backend   │
│ (Spring)    │──────► 2. Redirect a Google
└──────┬──────┘
       │
       │ 3. Usuario autentica en Google
       ▼
┌─────────────┐
│   Google    │
│  OAuth2     │──────► 4. Devuelve código de autorización
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Backend   │──────► 5. Intercambia código por tokens
│ (Spring)    │
└──────┬──────┘
       │ 6. Lógica de negocio:
       │    A. Buscar en usuario_social
       │    B. Si no existe, buscar en persona
       │    C. Vincular o rechazar
       ▼
┌─────────────┐
│  JWT Token  │──────► 7. Generar token CADET
└──────┬──────┘
       │
       │ 8. Redirect a /oauth2/callback?token=xxx
       ▼
┌─────────────┐
│   Frontend  │──────► 9. Guardar token y redirigir
│  (React)    │
└─────────────┘
```

---

## Contacto y Soporte

Para problemas o dudas:
- Documentación oficial: [Spring Security OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- Google OAuth2: [Guía de Google](https://developers.google.com/identity/protocols/oauth2)
