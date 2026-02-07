-- ============================================================================
-- 1. PROFESIONES (Adaptado para Psicólogos)
-- ============================================================================
INSERT INTO profesion (estado, nombre) SELECT 1, 'Licenciatura en Psicología' WHERE NOT EXISTS (SELECT 1 FROM profesion WHERE nombre = 'Licenciatura en Psicología');
INSERT INTO profesion (estado, nombre) SELECT 1, 'Psicopedagogía' WHERE NOT EXISTS (SELECT 1 FROM profesion WHERE nombre = 'Psicopedagogía');
INSERT INTO profesion (estado, nombre) SELECT 1, 'Psicología Clínica' WHERE NOT EXISTS (SELECT 1 FROM profesion WHERE nombre = 'Psicología Clínica');
INSERT INTO profesion (estado, nombre) SELECT 1, 'Psicología' WHERE NOT EXISTS (SELECT 1 FROM profesion WHERE nombre = 'Psicología');
INSERT INTO profesion (estado, nombre) SELECT 1, 'Neuropsicología' WHERE NOT EXISTS (SELECT 1 FROM profesion WHERE nombre = 'Neuropsicología');

-- ============================================================================
-- 2. PAISES (Se mantiene igual)
-- ============================================================================
INSERT INTO pais (estado, nombre) SELECT 1, 'BOLIVIA' WHERE NOT EXISTS (SELECT 1 FROM pais WHERE nombre = 'BOLIVIA');
INSERT INTO pais (estado, nombre) SELECT 1, 'PERÚ' WHERE NOT EXISTS (SELECT 1 FROM pais WHERE nombre = 'PERÚ');
INSERT INTO pais (estado, nombre) SELECT 1, 'CHILE' WHERE NOT EXISTS (SELECT 1 FROM pais WHERE nombre = 'CHILE');
INSERT INTO pais (estado, nombre) SELECT 1, 'ARGENTINA' WHERE NOT EXISTS (SELECT 1 FROM pais WHERE nombre = 'ARGENTINA');
INSERT INTO pais (estado, nombre) SELECT 1, 'COLOMBIA' WHERE NOT EXISTS (SELECT 1 FROM pais WHERE nombre = 'COLOMBIA');

-- ============================================================================
-- 3. DEPARTAMENTOS (Se mantiene igual)
-- ============================================================================
-- BOLIVIA (1)
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'TARIJA', 'TJA', 1 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'TARIJA');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'SANTA CRUZ', 'SCZ', 1 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'SANTA CRUZ');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'BENI', 'BE', 1 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'BENI');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'CHUQUISACA', 'CH', 1 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'CHUQUISACA');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'COCHABAMBA', 'CBBA', 1 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'COCHABAMBA');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'LA PAZ', 'LPZ', 1 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'LA PAZ');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'ORURO', 'OR', 1 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'ORURO');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'PANDO', 'PA', 1 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'PANDO');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'POTOSÍ', 'PTS', 1 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'POTOSÍ');

-- PERÚ (2)
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'LIMA', 'LIM', 2 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'LIMA');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'CUSCO', 'CUS', 2 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'CUSCO');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'AREQUIPA', 'ARE', 2 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'AREQUIPA');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'PIURA', 'PIU', 2 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'PIURA');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'LA LIBERTAD', 'LAL', 2 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'LA LIBERTAD');

-- CHILE (3)
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'METROPOLITANA DE SANTIAGO', 'RM', 3 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'METROPOLITANA DE SANTIAGO');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'VALPARAÍSO', 'VS', 3 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'VALPARAÍSO');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'ANTOFAGASTA', 'AN', 3 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'ANTOFAGASTA');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'BIOBÍO', 'BI', 3 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'BIOBÍO');

-- ARGENTINA (4)
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'BUENOS AIRES', 'BA', 4 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'BUENOS AIRES');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'CÓRDOBA', 'CBA', 4 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'CÓRDOBA');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'SANTA FE', 'SF', 4 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'SANTA FE');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'MENDOZA', 'MZ', 4 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'MENDOZA');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'TUCUMÁN', 'TM', 4 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'TUCUMÁN');

-- COLOMBIA (5)
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'ANTIOQUIA', 'ANT', 5 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'ANTIOQUIA');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'BOGOTÁ D.C.', 'DC', 5 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'BOGOTÁ D.C.');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'VALLE DEL CAUCA', 'VAC', 5 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'VALLE DEL CAUCA');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'CUNDINAMARCA', 'CUN', 5 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'CUNDINAMARCA');
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) SELECT 1, 'ATLÁNTICO', 'ATL', 5 WHERE NOT EXISTS (SELECT 1 FROM departamento WHERE nombre = 'ATLÁNTICO');


-- ============================================================================
-- 4. PROVINCIAS (Se mantiene igual)
-- ============================================================================
-- BOLIVIA
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CERCADO', 1 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CERCADO' AND fk_departamento = 1);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'ANDRÉS IBÁÑEZ', 2 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'ANDRÉS IBÁÑEZ' AND fk_departamento = 2);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CERCADO', 3 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CERCADO' AND fk_departamento = 3);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'OROPEZA', 4 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'OROPEZA' AND fk_departamento = 4);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CERCADO', 5 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CERCADO' AND fk_departamento = 5);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'MURILLO', 6 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'MURILLO' AND fk_departamento = 6);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CERCADO', 7 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CERCADO' AND fk_departamento = 7);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'NICOLÁS SUÁREZ', 8 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'NICOLÁS SUÁREZ' AND fk_departamento = 8);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'TOMÁS FRÍAS', 9 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'TOMÁS FRÍAS' AND fk_departamento = 9);

-- PERU, CHILE, ARGENTINA, COLOMBIA (Se mantienen igual para no perder data)
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'LIMA', 10 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'LIMA' AND fk_departamento = 10);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CUSCO', 11 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CUSCO' AND fk_departamento = 11);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'AREQUIPA', 12 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'AREQUIPA' AND fk_departamento = 12);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'PIURA', 13 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'PIURA' AND fk_departamento = 13);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'TRUJILLO', 14 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'TRUJILLO' AND fk_departamento = 14);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'SANTIAGO', 15 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'SANTIAGO' AND fk_departamento = 15);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'VALPARAÍSO', 16 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'VALPARAÍSO' AND fk_departamento = 16);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'ANTOFAGASTA', 17 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'ANTOFAGASTA' AND fk_departamento = 17);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CONCEPCIÓN', 18 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CONCEPCIÓN' AND fk_departamento = 18);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'LA PLATA', 19 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'LA PLATA' AND fk_departamento = 19);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CAPITAL', 20 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CAPITAL' AND fk_departamento = 20);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'ROSARIO', 21 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'ROSARIO' AND fk_departamento = 21);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CAPITAL', 22 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CAPITAL' AND fk_departamento = 22);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CAPITAL', 23 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CAPITAL' AND fk_departamento = 23);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'MEDELLÍN', 24 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'MEDELLÍN' AND fk_departamento = 24);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'BOGOTÁ', 25 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'BOGOTÁ' AND fk_departamento = 25);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CALI', 26 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CALI' AND fk_departamento = 26);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'SOACHA', 27 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'SOACHA' AND fk_departamento = 27);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'BARRANQUILLA', 28 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'BARRANQUILLA' AND fk_departamento = 28);


-- ============================================================================
-- 5. ESPECIALIDADES (Adaptado a Psicología)
-- ============================================================================
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Psicología Clínica', 'Evaluación, diagnóstico y tratamiento de trastornos mentales', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Psicología Clínica');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Psicología Educativa', 'Procesos de aprendizaje y desarrollo en el ámbito escolar', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Psicología Educativa');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Psicología Organizacional', 'Comportamiento humano en el ámbito laboral y empresas', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Psicología Organizacional');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Neuropsicología', 'Relación entre el cerebro y la conducta humana', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Neuropsicología');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Psicología Forense', 'Aplicación de la psicología en el ámbito legal y jurídico', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Psicología Forense');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Psicología Social', 'Estudio de las relaciones sociales y comportamiento grupal', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Psicología Social');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Psicología del Deporte', 'Rendimiento y bienestar mental en deportistas', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Psicología del Deporte');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Terapia Cognitivo-Conductual', 'Enfoque terapéutico basado en el pensamiento y comportamiento', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Terapia Cognitivo-Conductual');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Psicoanálisis', 'Estudio del inconsciente y la estructura de la personalidad', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Psicoanálisis');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Terapia Sistémica Familiar', 'Abordaje de problemas desde la dinámica familiar', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Terapia Sistémica Familiar');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Psicooncología', 'Apoyo psicológico a pacientes con cáncer y sus familias', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Psicooncología');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Psicogerontología', 'Atención psicológica para el adulto mayor', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Psicogerontología');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Psicometría', 'Medición de variables psicológicas y uso de test', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Psicometría');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Intervención en Crisis', 'Atención inmediata ante eventos traumáticos', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Intervención en Crisis');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Sexología Clínica', 'Tratamiento de disfunciones y educación sexual', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Sexología Clínica');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Psicología de la Salud', 'Promoción de estilos de vida saludables y prevención', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Psicología de la Salud');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Gestión del Talento Humano', 'Reclutamiento y selección desde perspectiva psicológica', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Gestión del Talento Humano');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Estimulación Temprana', 'Potenciación del desarrollo infantil', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Estimulación Temprana');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Psicología Comunitaria', 'Trabajo con comunidades para el cambio social', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Psicología Comunitaria');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Trastornos del Espectro Autista', 'Especialización en diagnóstico e intervención en TEA', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Trastornos del Espectro Autista');


-- ============================================================================
-- 6. SERVICIOS (Adaptado a Psicología)
-- ============================================================================
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Psicoterapia Individual', 'Consulta', 'Sesiones terapéuticas para tratamiento personal', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Psicoterapia Individual');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Terapia de Pareja', 'Consulta', 'Resolución de conflictos y mejora de la relación', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Terapia de Pareja');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Terapia Familiar', 'Consulta', 'Intervención con el núcleo familiar completo', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Terapia Familiar');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Evaluación Psicológica', 'Evaluación', 'Aplicación de test y diagnóstico clínico', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Evaluación Psicológica');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Evaluación Neuropsicológica', 'Evaluación', 'Diagnóstico de funciones cognitivas y daño cerebral', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Evaluación Neuropsicológica');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Orientación Vocacional', 'Educativo', 'Asesoramiento para elección de carrera profesional', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Orientación Vocacional');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Psicodiagnóstico Infantil', 'Evaluación', 'Evaluación del desarrollo y problemas en niños', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Psicodiagnóstico Infantil');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Selección de Personal', 'Empresarial', 'Reclutamiento y pruebas psicométricas laborales', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Selección de Personal');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Clima Laboral', 'Empresarial', 'Diagnóstico e intervención en bienestar organizacional', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Clima Laboral');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Peritaje Forense', 'Legal', 'Informes psicológicos para procesos judiciales', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Peritaje Forense');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Talleres de Manejo de Estrés', 'Talleres', 'Técnicas de relajación y afrontamiento', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Talleres de Manejo de Estrés');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Escuela de Padres', 'Educativo', 'Orientación para la crianza de los hijos', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Escuela de Padres');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Rehabilitación Cognitiva', 'Tratamiento', 'Recuperación de funciones mentales superiores', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Rehabilitación Cognitiva');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Consultoría Organizacional', 'Empresarial', 'Asesoramiento a empresas en gestión humana', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Consultoría Organizacional');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Evaluación para Licencia de Conducir', 'Certificación', 'Test psicotécnicos para conductores', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Evaluación para Licencia de Conducir');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Atención en Adicciones', 'Tratamiento', 'Terapia para consumo de sustancias y dependencias', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Atención en Adicciones');


-- ============================================================================
-- 7. SECTORES (Donde trabajan los psicólogos)
-- ============================================================================
INSERT INTO sectores (nombre, icono, origen) SELECT 'Consulta Privada', 'fa-user-md', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Consulta Privada');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Salud y Hospitales', 'fa-hospital', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Salud y Hospitales');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Educación y Colegios', 'fa-school', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Educación y Colegios');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Universidades e Investigación', 'fa-graduation-cap', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Universidades e Investigación');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Recursos Humanos (Empresas)', 'fa-building', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Recursos Humanos (Empresas)');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Ámbito Jurídico y Forense', 'fa-gavel', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Ámbito Jurídico y Forense');
INSERT INTO sectores (nombre, icono, origen) SELECT 'ONGs y Fundaciones', 'fa-hand-holding-heart', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'ONGs y Fundaciones');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Sector Público / Gobierno', 'fa-landmark', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Sector Público / Gobierno');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Deporte y Alto Rendimiento', 'fa-running', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Deporte y Alto Rendimiento');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Centros de Rehabilitación', 'fa-clinic-medical', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Centros de Rehabilitación');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Consultoría Externa', 'fa-briefcase', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Consultoría Externa');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Atención de Emergencias', 'fa-ambulance', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Atención de Emergencias');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Trabajo Comunitario', 'fa-users', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Trabajo Comunitario');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Gerontología y Asilos', 'fa-blind', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Gerontología y Asilos');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Seguridad Policial/Militar', 'fa-shield-alt', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Seguridad Policial/Militar');


-- ============================================================================
-- 8. IDIOMAS (Se mantienen igual)
-- ============================================================================
INSERT INTO idiomas (nombre, estado) SELECT 'Español', 1 WHERE NOT EXISTS (SELECT 1 FROM idiomas WHERE nombre = 'Español');
INSERT INTO idiomas (nombre, estado) SELECT 'Inglés', 1 WHERE NOT EXISTS (SELECT 1 FROM idiomas WHERE nombre = 'Inglés');
INSERT INTO idiomas (nombre, estado) SELECT 'Portugués', 1 WHERE NOT EXISTS (SELECT 1 FROM idiomas WHERE nombre = 'Portugués');
INSERT INTO idiomas (nombre, estado) SELECT 'Francés', 1 WHERE NOT EXISTS (SELECT 1 FROM idiomas WHERE nombre = 'Francés');
INSERT INTO idiomas (nombre, estado) SELECT 'Alemán', 1 WHERE NOT EXISTS (SELECT 1 FROM idiomas WHERE nombre = 'Alemán');
INSERT INTO idiomas (nombre, estado) SELECT 'Italiano', 1 WHERE NOT EXISTS (SELECT 1 FROM idiomas WHERE nombre = 'Italiano');
INSERT INTO idiomas (nombre, estado) SELECT 'Quechua', 1 WHERE NOT EXISTS (SELECT 1 FROM idiomas WHERE nombre = 'Quechua');
INSERT INTO idiomas (nombre, estado) SELECT 'Aymara', 1 WHERE NOT EXISTS (SELECT 1 FROM idiomas WHERE nombre = 'Aymara');
INSERT INTO idiomas (nombre, estado) SELECT 'Guaraní', 1 WHERE NOT EXISTS (SELECT 1 FROM idiomas WHERE nombre = 'Guaraní');
INSERT INTO idiomas (nombre, estado) SELECT 'Chino Mandarín', 1 WHERE NOT EXISTS (SELECT 1 FROM idiomas WHERE nombre = 'Chino Mandarín');
INSERT INTO idiomas (nombre, estado) SELECT 'Japonés', 1 WHERE NOT EXISTS (SELECT 1 FROM idiomas WHERE nombre = 'Japonés');
INSERT INTO idiomas (nombre, estado) SELECT 'Ruso', 1 WHERE NOT EXISTS (SELECT 1 FROM idiomas WHERE nombre = 'Ruso');

-- ============================================================================
-- 9. ROLES
-- ============================================================================
INSERT INTO rol (estado, nombre) SELECT 1, 'ROLE_ADMIN' WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre = 'ROLE_ADMIN');
INSERT INTO rol (estado, nombre) SELECT 1, 'ROLE_SOCIO' WHERE NOT EXISTS (SELECT 1 FROM rol WHERE nombre = 'ROLE_SOCIO');

-- ============================================================================
-- 10. ESTADISTICAS (Descripciones adaptadas)
-- ============================================================================
INSERT INTO estadisticas_publicas (clave, titulo, valor, descripcion) SELECT 'total_socios', 'Psicólogos Colegiados', '0', 'Total de profesionales psicólogos colegiados' WHERE NOT EXISTS (SELECT 1 FROM estadisticas_publicas WHERE clave = 'total_socios');
INSERT INTO estadisticas_publicas (clave, titulo, valor, descripcion) SELECT 'especialidades_disponibles', 'Especialidades', '8', 'Áreas de especialización psicológica disponibles' WHERE NOT EXISTS (SELECT 1 FROM estadisticas_publicas WHERE clave = 'especialidades_disponibles');
INSERT INTO estadisticas_publicas (clave, titulo, valor, descripcion) SELECT 'anios_trayectoria', 'Años de Historia', '25', 'Años de trayectoria institucional en Tarija' WHERE NOT EXISTS (SELECT 1 FROM estadisticas_publicas WHERE clave = 'anios_trayectoria');
INSERT INTO estadisticas_publicas (clave, titulo, valor, descripcion) SELECT 'satisfaccion_clientes', 'Satisfacción', '98', 'Índice de satisfacción de pacientes' WHERE NOT EXISTS (SELECT 1 FROM estadisticas_publicas WHERE clave = 'satisfaccion_clientes');

-- ============================================================================
-- 11. PUBLICO OBJETIVO (Ya estaba correcto para Psicología)
-- ============================================================================
INSERT INTO publico_objetivo (nombre, descripcion) SELECT 'Niños', 'Atención psicológica infantil (3 a 12 años).' WHERE NOT EXISTS (SELECT 1 FROM publico_objetivo WHERE nombre = 'Niños');
INSERT INTO publico_objetivo (nombre, descripcion) SELECT 'Adolescentes', 'Atención a jóvenes en etapa de pubertad y adolescencia.' WHERE NOT EXISTS (SELECT 1 FROM publico_objetivo WHERE nombre = 'Adolescentes');
INSERT INTO publico_objetivo (nombre, descripcion) SELECT 'Adultos', 'Psicoterapia para personas mayores de edad.' WHERE NOT EXISTS (SELECT 1 FROM publico_objetivo WHERE nombre = 'Adultos');
INSERT INTO publico_objetivo (nombre, descripcion) SELECT 'Adultos Mayores', 'Gerontopsicología y atención a la tercera edad.' WHERE NOT EXISTS (SELECT 1 FROM publico_objetivo WHERE nombre = 'Adultos Mayores');
INSERT INTO publico_objetivo (nombre, descripcion) SELECT 'Parejas', 'Resolución de conflictos y orientación matrimonial.' WHERE NOT EXISTS (SELECT 1 FROM publico_objetivo WHERE nombre = 'Parejas');
INSERT INTO publico_objetivo (nombre, descripcion) SELECT 'Familias', 'Terapia sistémica para el núcleo familiar.' WHERE NOT EXISTS (SELECT 1 FROM publico_objetivo WHERE nombre = 'Familias');
INSERT INTO publico_objetivo (nombre, descripcion) SELECT 'Grupos', 'Terapia grupal para temáticas específicas.' WHERE NOT EXISTS (SELECT 1 FROM publico_objetivo WHERE nombre = 'Grupos');
INSERT INTO publico_objetivo (nombre, descripcion) SELECT 'Empresas', 'Consultoría organizacional y salud laboral.' WHERE NOT EXISTS (SELECT 1 FROM publico_objetivo WHERE nombre = 'Empresas');
INSERT INTO publico_objetivo (nombre, descripcion) SELECT 'Deportistas', 'Psicología de alto rendimiento.' WHERE NOT EXISTS (SELECT 1 FROM publico_objetivo WHERE nombre = 'Deportistas');
INSERT INTO publico_objetivo (nombre, descripcion) SELECT 'Comunidad LGBTIQ+', 'Atención especializada e inclusiva.' WHERE NOT EXISTS (SELECT 1 FROM publico_objetivo WHERE nombre = 'Comunidad LGBTIQ+');