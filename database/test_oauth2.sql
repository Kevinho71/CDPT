-- ============================================================================
-- SCRIPT DE PRUEBA - OAUTH2 CON GOOGLE
-- Sistema CADET - Oficina Virtual
-- ============================================================================

-- ============================================================================
-- PASO 1: LIMPIAR DATOS DE PRUEBA ANTERIORES (OPCIONAL)
-- ============================================================================

-- Descomentar si quieres empezar desde cero
-- DELETE FROM usuario_social WHERE email LIKE '%@test.cadet.edu.bo';
-- DELETE FROM persona WHERE email LIKE '%@test.cadet.edu.bo';

-- ============================================================================
-- PASO 2: DATOS PARA CASO 1 - USUARIO RECURRENTE
-- ============================================================================

-- Insertar persona para usuario recurrente
INSERT INTO persona (ci, nombrecompleto, email, celular, estado) 
VALUES 
    ('1234567', 'Juan Recurrente López', 'juan.recurrente@test.cadet.edu.bo', 70123456, 1)
ON CONFLICT (email) DO NOTHING;

-- Insertar usuario social (simula que ya hizo login antes)
INSERT INTO usuario_social (
    provider, 
    provider_id, 
    email, 
    nombre_completo, 
    foto_perfil_url,
    fk_persona, 
    email_verificado,
    fecha_vinculacion,
    fecha_ultima_autenticacion,
    estado
)
VALUES (
    'GOOGLE',
    '100000000000000000001', -- Provider ID de prueba
    'juan.recurrente@test.cadet.edu.bo',
    'Juan Recurrente López',
    'https://lh3.googleusercontent.com/a/default-user',
    (SELECT id FROM persona WHERE email = 'juan.recurrente@test.cadet.edu.bo'),
    true,
    NOW() - INTERVAL '30 days', -- Vinculado hace 30 días
    NOW() - INTERVAL '2 days',  -- Último login hace 2 días
    1
)
ON CONFLICT (provider, provider_id) DO NOTHING;

-- ============================================================================
-- PASO 3: DATOS PARA CASO 2 - AUTO-LINKING (SOCIO EXISTENTE)
-- ============================================================================

-- Insertar persona que existe pero NO tiene OAuth2 vinculado
INSERT INTO persona (ci, nombrecompleto, email, celular, estado) 
VALUES 
    ('2345678', 'María AutoLink González', 'maria.autolink@test.cadet.edu.bo', 70234567, 1),
    ('3456789', 'Pedro Vinculado Sánchez', 'pedro.vinculado@test.cadet.edu.bo', 70345678, 1),
    ('4567890', 'Ana Linking Martínez', 'ana.linking@test.cadet.edu.bo', 70456789, 1)
ON CONFLICT (email) DO NOTHING;

-- NO insertar en usuario_social para estos usuarios
-- Esto simula que son socios registrados pero nunca han usado Google

-- ============================================================================
-- PASO 4: DATOS ADICIONALES (PERSONAS ACTIVAS E INACTIVAS)
-- ============================================================================

-- Persona inactiva (debe rechazar auto-linking)
INSERT INTO persona (ci, nombrecompleto, email, celular, estado) 
VALUES 
    ('5678901', 'Usuario Inactivo', 'inactivo@test.cadet.edu.bo', 70567890, 0)
ON CONFLICT (email) DO NOTHING;

-- Persona activa sin socio (para pruebas adicionales)
INSERT INTO persona (ci, nombrecompleto, email, celular, estado) 
VALUES 
    ('6789012', 'Socio Sin Perfil', 'sin.perfil@test.cadet.edu.bo', 70678901, 1)
ON CONFLICT (email) DO NOTHING;

-- ============================================================================
-- PASO 5: VERIFICAR DATOS INSERTADOS
-- ============================================================================

-- Ver usuarios con OAuth2
SELECT 
    '===== USUARIOS CON OAUTH2 VINCULADO =====' as seccion,
    us.id as usuario_social_id,
    us.provider,
    us.email,
    us.nombre_completo,
    p.nombrecompleto as persona_nombre,
    us.fecha_vinculacion,
    us.fecha_ultima_autenticacion,
    CASE WHEN us.estado = 1 THEN 'Activo' ELSE 'Inactivo' END as estado
FROM usuario_social us
JOIN persona p ON us.fk_persona = p.id
WHERE us.email LIKE '%@test.cadet.edu.bo'
ORDER BY us.fecha_vinculacion DESC;

-- Ver personas SIN OAuth2 (listas para auto-linking)
SELECT 
    '===== PERSONAS SIN OAUTH2 (LISTAS PARA AUTO-LINKING) =====' as seccion,
    p.id as persona_id,
    p.email,
    p.nombrecompleto,
    CASE WHEN p.estado = 1 THEN 'Activo' ELSE 'Inactivo' END as estado
FROM persona p
LEFT JOIN usuario_social us ON us.fk_persona = p.id
WHERE p.email LIKE '%@test.cadet.edu.bo'
  AND us.id IS NULL
ORDER BY p.id;

-- ============================================================================
-- PASO 6: QUERIES DE VERIFICACIÓN DURANTE PRUEBAS
-- ============================================================================

-- Verificar si un email ya tiene OAuth2
-- Usar: Reemplazar 'EMAIL_AQUI' con el email a verificar
-- SELECT * FROM usuario_social WHERE email = 'EMAIL_AQUI';

-- Verificar si un provider_id ya existe
-- Usar: Reemplazar 'PROVIDER_ID_AQUI' con el ID de Google
-- SELECT * FROM usuario_social WHERE provider = 'GOOGLE' AND provider_id = 'PROVIDER_ID_AQUI';

-- Ver último login de todos los usuarios OAuth2
-- SELECT email, fecha_ultima_autenticacion 
-- FROM usuario_social 
-- WHERE provider = 'GOOGLE' 
-- ORDER BY fecha_ultima_autenticacion DESC;

-- ============================================================================
-- PASO 7: LIMPIAR DATOS DE PRUEBA (EJECUTAR AL FINALIZAR)
-- ============================================================================

-- Descomentar para eliminar datos de prueba
/*
DELETE FROM usuario_social WHERE email LIKE '%@test.cadet.edu.bo';
DELETE FROM persona WHERE email LIKE '%@test.cadet.edu.bo';
*/

-- ============================================================================
-- INSTRUCCIONES DE USO
-- ============================================================================

/*
PARA PROBAR CASO 1 (Usuario Recurrente):
1. Ejecutar PASO 1-5
2. En Google Cloud Console, configurar una cuenta de prueba con email: juan.recurrente@test.cadet.edu.bo
3. Login con esa cuenta
4. Verificar que retorna isNewUser=false
5. Verificar que se actualiza fecha_ultima_autenticacion:
   SELECT fecha_ultima_autenticacion FROM usuario_social WHERE email = 'juan.recurrente@test.cadet.edu.bo';

PARA PROBAR CASO 2 (Auto-Linking):
1. Ejecutar PASO 1-5
2. Usar una cuenta de Google real con email: maria.autolink@test.cadet.edu.bo
3. Login con esa cuenta (primera vez)
4. Verificar que retorna isNewUser=true
5. Verificar que se creó registro en usuario_social:
   SELECT * FROM usuario_social WHERE email = 'maria.autolink@test.cadet.edu.bo';

PARA PROBAR CASO 3 (Acceso Denegado):
1. Usar una cuenta de Google con email NO registrado en persona
2. Login con esa cuenta
3. Verificar error: "El correo X no está registrado como socio activo"
4. Verificar que NO se creó ningún registro en usuario_social

PROBAR CON USUARIO INACTIVO:
1. Cambiar temporalmente el email de alguna cuenta Google a: inactivo@test.cadet.edu.bo
2. Intentar login
3. Verificar error: "La cuenta asociada al email ... no está activa"

LIMPIAR DATOS:
1. Al finalizar pruebas, descomentar y ejecutar PASO 7
2. Esto eliminará todos los datos de prueba (emails con @test.cadet.edu.bo)
*/

-- ============================================================================
-- QUERIES ADICIONALES PARA DEBUGGING
-- ============================================================================

-- Ver todos los providers disponibles
-- SELECT DISTINCT provider FROM usuario_social;

-- Contar usuarios por provider
-- SELECT provider, COUNT(*) as total 
-- FROM usuario_social 
-- GROUP BY provider;

-- Ver usuarios con múltiples vinculaciones (mismo email, diferentes providers)
-- SELECT email, COUNT(*) as providers_count 
-- FROM usuario_social 
-- GROUP BY email 
-- HAVING COUNT(*) > 1;

-- Ver últimos 10 logins OAuth2
-- SELECT email, provider, fecha_ultima_autenticacion 
-- FROM usuario_social 
-- ORDER BY fecha_ultima_autenticacion DESC 
-- LIMIT 10;
