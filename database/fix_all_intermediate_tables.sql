-- Script para verificar y corregir TODAS las tablas intermedias de perfil_socio
-- Ninguna debería tener fk_socio porque perfil_socio ya tiene esa relación

-- 1. socio_especialidades
ALTER TABLE socio_especialidades 
DROP CONSTRAINT IF EXISTS fk_socio_especialidades_socio;
ALTER TABLE socio_especialidades 
DROP COLUMN IF EXISTS fk_socio;

-- 2. socio_idiomas
ALTER TABLE socio_idiomas 
DROP CONSTRAINT IF EXISTS fk_socio_idiomas_socio;
ALTER TABLE socio_idiomas 
DROP COLUMN IF EXISTS fk_socio;

-- 3. socio_sectores
ALTER TABLE socio_sectores 
DROP CONSTRAINT IF EXISTS fk_socio_sectores_socio;
ALTER TABLE socio_sectores 
DROP COLUMN IF EXISTS fk_socio;

-- 4. socio_servicios
ALTER TABLE socio_servicios 
DROP CONSTRAINT IF EXISTS fk_socio_servicios_socio;
ALTER TABLE socio_servicios 
DROP COLUMN IF EXISTS fk_socio;

-- Verificar estructura final de cada tabla
SELECT 'socio_especialidades' as tabla, column_name, data_type, is_nullable
FROM information_schema.columns 
WHERE table_name = 'socio_especialidades'
ORDER BY ordinal_position;

SELECT 'socio_idiomas' as tabla, column_name, data_type, is_nullable
FROM information_schema.columns 
WHERE table_name = 'socio_idiomas'
ORDER BY ordinal_position;

SELECT 'socio_sectores' as tabla, column_name, data_type, is_nullable
FROM information_schema.columns 
WHERE table_name = 'socio_sectores'
ORDER BY ordinal_position;

SELECT 'socio_servicios' as tabla, column_name, data_type, is_nullable
FROM information_schema.columns 
WHERE table_name = 'socio_servicios'
ORDER BY ordinal_position;
