-- Eliminar la columna fk_socio que no debería existir en socio_especialidades
-- Esta columna es incorrecta porque perfil_socio ya tiene la relación con socio

-- Primero, eliminar la constraint de FK si existe
ALTER TABLE socio_especialidades 
DROP CONSTRAINT IF EXISTS fk_socio_especialidades_socio;

-- Luego, eliminar la columna fk_socio
ALTER TABLE socio_especialidades 
DROP COLUMN IF EXISTS fk_socio;

-- Verificar que la estructura sea correcta
-- La tabla solo debe tener: id, fk_perfil_socio, fk_especialidad, fecha_creacion
