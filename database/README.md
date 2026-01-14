# 📊 Base de Datos - Sistema CADET

## 🗂️ Archivos

- **`schema_completo.sql`** - Esquema completo de la base de datos (tablas existentes + nuevas funcionalidades)

## ✨ Mejoras Implementadas

### 1. **Reorganización de Estructura**
- ✅ Eliminados todos los `ALTER TABLE` innecesarios
- ✅ Las Foreign Keys se definen directamente en la creación de cada tabla
- ✅ Orden lógico: primero las tablas sin dependencias, luego las que dependen de otras

### 2. **Índices Agregados**
Se agregaron índices útiles para optimizar las búsquedas más comunes:

#### Tablas Base:
- `idx_pais_nombre` - Búsqueda por nombre de país
- `idx_departamento_nombre` - Búsqueda por departamento
- `idx_departamento_pais` - Relación departamento-país
- `idx_provincia_nombre` - Búsqueda por provincia
- `idx_profesion_nombre` - Búsqueda por profesión
- `idx_institucion_nit` - Búsqueda por NIT
- `idx_institucion_correo` - Búsqueda por email institucional
- `idx_institucion_compania` - Búsqueda por nombre de institución
- `idx_anio_institucion` - Relación año-institución
- `idx_anio_nombre` - Búsqueda por año
- `idx_persona_ci` - Búsqueda por CI
- `idx_persona_nombrecompleto` - Búsqueda por nombre
- `idx_persona_departamento` - Relación persona-departamento
- `idx_rol_nombre` - Búsqueda por rol
- `idx_usuario_persona` - Relación usuario-persona
- `idx_socio_matricula` - Búsqueda por matrícula (IMPORTANTE)
- `idx_socio_nombresocio` - Búsqueda por nombre de socio
- `idx_socio_profesion` - Filtro por profesión
- `idx_socio_institucion` - Filtro por institución
- `idx_socio_fechaexpiracion` - Para alertas de vencimiento
- `idx_catalogo_nit` - Búsqueda por NIT de empresa
- `idx_catalogo_nombre` - Búsqueda por nombre de empresa
- `idx_catalogo_tipo` - Filtro por tipo de catálogo
- `idx_catalogo_departamento` - Filtro por ubicación

#### Tablas Nuevas (Perfiles Públicos):
- `idx_perfil_socio_publico` - Filtro de perfiles públicos/privados
- `idx_perfil_socio_especialidad` - Búsqueda por especialidad
- `idx_perfil_socio_modalidad` - Filtro por modalidad de trabajo
- `idx_perfil_socio_ciudad` - Búsqueda por ciudad
- `idx_especialidades_nombre` - Búsqueda en catálogo de especialidades
- `idx_especialidades_origen` - Filtro por origen (SISTEMA/USUARIO)
- `idx_servicios_nombre` - Búsqueda de servicios
- `idx_servicios_categoria` - Filtro por categoría de servicio
- `idx_socio_servicios_destacado` - Servicios destacados del socio
- `idx_sectores_nombre` - Búsqueda de sectores
- `idx_metodologias_nombre` - Búsqueda de metodologías
- `idx_formacion_tipo` - Filtro por tipo de formación
- `idx_idiomas_nombre` - Búsqueda de idiomas
- `idx_usuario_social_provider` - Filtro por proveedor OAuth2
- `idx_usuario_social_estado` - Cuentas sociales activas
- `idx_consultas_estado` - Filtro por estado de consulta
- `idx_consultas_fecha` - Ordenamiento por fecha (DESC)
- `idx_consultas_correo` - Búsqueda por email del contacto
- `idx_estadisticas_orden` - Ordenamiento de estadísticas

### 3. **Comentarios Agregados**
- ✅ `COMMENT ON TABLE` en todas las tablas principales
- ✅ `COMMENT ON COLUMN` en columnas clave que necesitan explicación
- ✅ Comentarios inline con detalles de valores válidos
- ✅ Sección de flujo de autenticación OAuth2 documentada

### 4. **Sintaxis Mejorada**
- ✅ Consistencia en nombres de constraints (`fk_*`, `uk_*`, `idx_*`)
- ✅ Columnas `NOT NULL` marcadas explícitamente donde corresponde
- ✅ Columnas `UNIQUE` marcadas explícitamente
- ✅ Valores `DEFAULT` claramente especificados
- ✅ Formateo consistente y legible

### 5. **Secciones Organizadas**
```
SECCIÓN 1: TABLAS BASE DEL SISTEMA (existentes)
  ├── Geográficas: pais, departamento, provincia
  ├── Catálogos: profesion, anio
  ├── Entidades: institucion, persona
  ├── Seguridad: rol, usuario, usuarios_roles
  └── Core: socio, catalogo, imagencatalogo, socio_catalogos

SECCIÓN 2: NUEVAS TABLAS - PERFILES PÚBLICOS
  ├── perfil_socio (perfil extendido)
  ├── Catálogos Sugeridos:
  │   ├── especialidades, servicios, sectores
  │   ├── metodologias, idiomas
  │   └── Relaciones N:M correspondientes
  ├── Formación: formacion, certificaciones
  ├── OAuth2: usuario_social
  ├── Contacto: consultas_contacto
  └── Landing: estadisticas_publicas

SECCIÓN 3: DOCUMENTACIÓN
  ├── Orden de inserción de datos
  ├── Índices para búsquedas públicas
  └── Comandos para ejecutar el script
```

## 📋 Estrategia de Catálogos (Opción A)

**Para especialidades, servicios, sectores, metodologías:**
- El socio usa **campos libres** en sus tablas (ej: `perfil_socio.especialidad`)
- Las tablas de catálogo son **SUGERENCIAS** para dropdowns
- El socio NO está obligado a elegir del catálogo
- Máxima flexibilidad sin restricciones

**Para idiomas:**
- Catálogo sugerido con los idiomas más comunes
- Se puede agregar más idiomas según necesidad

## 🔐 OAuth2 - Multi-Cuenta

### Diseño
- Tabla `usuario_social` permite **N cuentas Google : 1 persona**
- NO modifica la tabla `usuario` existente
- FK hacia `persona` (identidad central)

### Flujo de Autenticación
1. **Login con Google** → Callback con `provider_id` (sub) y `email`
2. **Verificación Directa**: Buscar por `provider_id` en `usuario_social`
   - ✅ **Existe**: Autenticar → Obtener `fk_persona` → Crear sesión
   - ❌ **No existe**: Ir a paso 3
3. **Auto-Linking**: Buscar por `email` en tabla `persona`
   - ✅ **Coincide**: INSERT en `usuario_social` con ese `fk_persona`
   - ❌ **No coincide**: Denegar acceso (no crear usuarios fantasma)
4. **Vinculación Manual**: Usuario autenticado puede vincular nueva cuenta Google

## 🚀 Comandos de Ejecución

### Crear base de datos y ejecutar schema:
```powershell
# Crear base de datos
createdb -U kevin -h localhost -p 5433 cadet_bd

# Ejecutar schema completo
psql -U kevin -h localhost -p 5433 -d cadet_bd -f database/schema_completo.sql
```

### Recrear desde cero (¡BORRA TODOS LOS DATOS!):
```powershell
dropdb -U kevin -h localhost -p 5433 cadet_bd
createdb -U kevin -h localhost -p 5433 cadet_bd
psql -U kevin -h localhost -p 5433 -d cadet_bd -f database/schema_completo.sql
```

### Verificar tablas creadas:
```sql
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public' 
ORDER BY table_name;
```

## 📊 Datos Iniciales

El schema incluye INSERTs automáticos para:
- ✅ 8 Especialidades predefinidas
- ✅ 10 Servicios predefinidos
- ✅ 10 Sectores predefinidos
- ✅ 10 Metodologías predefinidas
- ✅ 8 Idiomas comunes
- ✅ 4 Estadísticas base para landing page

## 🔍 Búsquedas Optimizadas

### Perfiles públicos activos:
```sql
SELECT * FROM perfil_socio 
WHERE perfil_publico = true AND estado = 1;
-- Usa: idx_perfil_socio_publico, idx_perfil_socio_estado
```

### Por especialidad:
```sql
SELECT * FROM perfil_socio 
WHERE especialidad ILIKE '%marketing%' AND perfil_publico = true;
-- Usa: idx_perfil_socio_especialidad, idx_perfil_socio_publico
```

### Por ciudad y modalidad:
```sql
SELECT * FROM perfil_socio 
WHERE ciudad = 'Tarija' AND modalidad_trabajo = 'hibrido';
-- Usa: idx_perfil_socio_ciudad, idx_perfil_socio_modalidad
```

### Socios por matrícula:
```sql
SELECT * FROM socio WHERE matricula = 'MAT-001';
-- Usa: idx_socio_matricula (RÁPIDO)
```

## 📝 Notas Importantes

1. **NO MODIFICAR** las tablas ni campos existentes
2. Todos los índices son **adicionales** para optimizar búsquedas
3. Los comentarios ayudan a entender el propósito de cada tabla/columna
4. El orden de tablas respeta las dependencias de Foreign Keys
5. La estrategia de catálogos permite máxima flexibilidad al socio

## 🏗️ Próximos Pasos

1. ✅ Schema SQL completo → Listo
2. ⏳ Crear entidades JPA correspondientes en Java
3. ⏳ Implementar repositorios y servicios
4. ⏳ Configurar Spring Security con OAuth2
5. ⏳ Desarrollar API REST para perfiles públicos
6. ⏳ Crear landing page y sistema de búsqueda
