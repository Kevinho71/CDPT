-- ⚠️ ADVERTENCIA: ESTO BORRARÁ TODA LA BASE DE DATOS Y SUS DATOS
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO public;
-- Si tu usuario de conexión es 'kevin', descomenta la siguiente línea:
GRANT ALL ON SCHEMA public TO kevin;