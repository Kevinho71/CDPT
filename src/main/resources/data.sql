-- ============================================================================
-- 1. PROFESIONES
-- ============================================================================
INSERT INTO profesion (estado, nombre) SELECT 1, 'Administración de Empresas' WHERE NOT EXISTS (SELECT 1 FROM profesion WHERE nombre = 'Administración de Empresas');
INSERT INTO profesion (estado, nombre) SELECT 1, 'Ingenieria Comercial' WHERE NOT EXISTS (SELECT 1 FROM profesion WHERE nombre = 'Ingenieria Comercial');
INSERT INTO profesion (estado, nombre) SELECT 1, 'Ingenieria Financiera' WHERE NOT EXISTS (SELECT 1 FROM profesion WHERE nombre = 'Ingenieria Financiera');
INSERT INTO profesion (estado, nombre) SELECT 1, 'Economía' WHERE NOT EXISTS (SELECT 1 FROM profesion WHERE nombre = 'Economía');
INSERT INTO profesion (estado, nombre) SELECT 1, 'Banca y Finanzas' WHERE NOT EXISTS (SELECT 1 FROM profesion WHERE nombre = 'Banca y Finanzas');

-- ============================================================================
-- 2. PAISES
-- ============================================================================
INSERT INTO pais (estado, nombre) SELECT 1, 'BOLIVIA' WHERE NOT EXISTS (SELECT 1 FROM pais WHERE nombre = 'BOLIVIA');
INSERT INTO pais (estado, nombre) SELECT 1, 'PERÚ' WHERE NOT EXISTS (SELECT 1 FROM pais WHERE nombre = 'PERÚ');
INSERT INTO pais (estado, nombre) SELECT 1, 'CHILE' WHERE NOT EXISTS (SELECT 1 FROM pais WHERE nombre = 'CHILE');
INSERT INTO pais (estado, nombre) SELECT 1, 'ARGENTINA' WHERE NOT EXISTS (SELECT 1 FROM pais WHERE nombre = 'ARGENTINA');
INSERT INTO pais (estado, nombre) SELECT 1, 'COLOMBIA' WHERE NOT EXISTS (SELECT 1 FROM pais WHERE nombre = 'COLOMBIA');

-- ============================================================================
-- 3. DEPARTAMENTOS
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
-- 4. PROVINCIAS
-- ============================================================================
-- OJO: Aqui validamos por Nombre Y por Departamento, porque "CERCADO" se repite.

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

-- PERU
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'LIMA', 10 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'LIMA' AND fk_departamento = 10);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CUSCO', 11 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CUSCO' AND fk_departamento = 11);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'AREQUIPA', 12 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'AREQUIPA' AND fk_departamento = 12);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'PIURA', 13 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'PIURA' AND fk_departamento = 13);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'TRUJILLO', 14 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'TRUJILLO' AND fk_departamento = 14);

-- CHILE
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'SANTIAGO', 15 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'SANTIAGO' AND fk_departamento = 15);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'VALPARAÍSO', 16 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'VALPARAÍSO' AND fk_departamento = 16);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'ANTOFAGASTA', 17 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'ANTOFAGASTA' AND fk_departamento = 17);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CONCEPCIÓN', 18 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CONCEPCIÓN' AND fk_departamento = 18);

-- ARGENTINA
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'LA PLATA', 19 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'LA PLATA' AND fk_departamento = 19);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CAPITAL', 20 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CAPITAL' AND fk_departamento = 20);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'ROSARIO', 21 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'ROSARIO' AND fk_departamento = 21);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CAPITAL', 22 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CAPITAL' AND fk_departamento = 22);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CAPITAL', 23 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CAPITAL' AND fk_departamento = 23);

-- COLOMBIA
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'MEDELLÍN', 24 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'MEDELLÍN' AND fk_departamento = 24);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'BOGOTÁ', 25 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'BOGOTÁ' AND fk_departamento = 25);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'CALI', 26 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'CALI' AND fk_departamento = 26);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'SOACHA', 27 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'SOACHA' AND fk_departamento = 27);
INSERT INTO provincia (estado, nombre, fk_departamento) SELECT 1, 'BARRANQUILLA', 28 WHERE NOT EXISTS (SELECT 1 FROM provincia WHERE nombre = 'BARRANQUILLA' AND fk_departamento = 28);


-- ============================================================================
-- 5. ESPECIALIDADES
-- ============================================================================
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Finanzas Corporativas', 'Gestión del valor y capital de la empresa', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Finanzas Corporativas');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Auditoría y Control', 'Control interno y auditoría externa', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Auditoría y Control');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Tributación y Fiscalidad', 'Gestión de impuestos y normativa fiscal', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Tributación y Fiscalidad');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Banca y Seguros', 'Gestión bancaria y análisis de riesgos', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Banca y Seguros');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Mercado de Valores', 'Inversiones y bolsa de valores', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Mercado de Valores');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Gestión del Talento Humano', 'Reclutamiento, selección y retención', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Gestión del Talento Humano');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Compensaciones y Beneficios', 'Estructuras salariales y beneficios', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Compensaciones y Beneficios');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Desarrollo Organizacional', 'Cultura, clima y cambio organizacional', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Desarrollo Organizacional');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Legislación Laboral', 'Normativa y derecho laboral aplicado', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Legislación Laboral');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Coaching y Liderazgo', 'Desarrollo de habilidades blandas y directivas', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Coaching y Liderazgo');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Marketing Estratégico', 'Planeación de mercado a largo plazo', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Marketing Estratégico');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Marketing Digital', 'Estrategias en medios digitales y redes', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Marketing Digital');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Investigación de Mercados', 'Análisis de consumidor y competencia', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Investigación de Mercados');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Gestión Comercial y Ventas', 'Dirección de fuerzas de ventas', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Gestión Comercial y Ventas');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Branding y Marca', 'Construcción y gestión de identidad de marca', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Branding y Marca');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Supply Chain Management', 'Gestión de la cadena de suministro', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Supply Chain Management');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Logística y Distribución', 'Almacenamiento y transporte', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Logística y Distribución');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Gestión de Calidad (ISO)', 'Normas de calidad y mejora continua', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Gestión de Calidad (ISO)');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Seguridad Industrial', 'Seguridad y salud ocupacional', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Seguridad Industrial');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Comercio Exterior', 'Importaciones, exportaciones y aduanas', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Comercio Exterior');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Gerencia General', 'Dirección integral de organizaciones', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Gerencia General');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Planificación Estratégica', 'Definición de objetivos a largo plazo', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Planificación Estratégica');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Gestión de Proyectos (PMO)', 'Administración de proyectos y portafolios', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Gestión de Proyectos (PMO)');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Responsabilidad Social (RSE)', 'Sostenibilidad y ética empresarial', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Responsabilidad Social (RSE)');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Innovación Empresarial', 'Gestión de la innovación y creatividad', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Innovación Empresarial');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Transformación Digital', 'Digitalización de procesos de negocio', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Transformación Digital');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'Business Intelligence (BI)', 'Análisis de datos para toma de decisiones', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'Business Intelligence (BI)');
INSERT INTO especialidades (nombre, descripcion, origen) SELECT 'E-Commerce', 'Comercio electrónico y negocios digitales', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM especialidades WHERE nombre = 'E-Commerce');


-- ============================================================================
-- 6. SERVICIOS
-- ============================================================================
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Elaboración de Planes de Negocio', 'Consultoría', 'Desarrollo integral de planes para startups o expansión', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Elaboración de Planes de Negocio');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Valoración de Empresas', 'Finanzas', 'Determinación del valor económico de una organización', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Valoración de Empresas');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Reingeniería de Procesos', 'Procesos', 'Rediseño radical de procesos para mejorar productividad', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Reingeniería de Procesos');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Implementación de KPIs', 'Gestión', 'Diseño de tableros de control y métricas', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Implementación de KPIs');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Auditoría Administrativa', 'Auditoría', 'Evaluación de la eficiencia de la estructura organizacional', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Auditoría Administrativa');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Headhunting y Selección', 'RRHH', 'Búsqueda de ejecutivos y personal clave', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Headhunting y Selección');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Medición de Clima Laboral', 'RRHH', 'Diagnóstico del ambiente de trabajo', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Medición de Clima Laboral');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Manuales de Funciones', 'RRHH', 'Redacción de manuales y procedimientos organizacionales', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Manuales de Funciones');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Plan de Marketing', 'Marketing', 'Estrategia de mercado y posicionamiento', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Plan de Marketing');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Estudios de Mercado', 'Marketing', 'Investigación cualitativa y cuantitativa', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Estudios de Mercado');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Gestión de Redes Sociales', 'Marketing', 'Community management corporativo', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Gestión de Redes Sociales');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Capacitación In-Company', 'Formación', 'Cursos a medida para empresas', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Capacitación In-Company');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Talleres de Liderazgo', 'Formación', 'Desarrollo de habilidades directivas', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Talleres de Liderazgo');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Coaching Ejecutivo 1 a 1', 'Formación', 'Acompañamiento personalizado a gerentes', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Coaching Ejecutivo 1 a 1');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Constitución de Empresas', 'Legal', 'Trámites para fundar nuevas compañías', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Constitución de Empresas');
INSERT INTO servicios (nombre, categoria, descripcion, origen) SELECT 'Asesoría Tributaria', 'Legal', 'Optimización y cumplimiento fiscal', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM servicios WHERE nombre = 'Asesoría Tributaria');


-- ============================================================================
-- 7. SECTORES
-- ============================================================================
INSERT INTO sectores (nombre, icono, origen) SELECT 'Banca y Finanzas', 'fa-university', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Banca y Finanzas');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Seguros', 'fa-shield-alt', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Seguros');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Educación y Universidades', 'fa-graduation-cap', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Educación y Universidades');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Salud y Hospitales', 'fa-hospital', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Salud y Hospitales');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Construcción e Inmobiliaria', 'fa-building', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Construcción e Inmobiliaria');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Minería e Hidrocarburos', 'fa-hammer', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Minería e Hidrocarburos');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Agroindustria', 'fa-tractor', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Agroindustria');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Retail y Comercio Minorista', 'fa-shopping-cart', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Retail y Comercio Minorista');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Consumo Masivo', 'fa-box-open', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Consumo Masivo');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Tecnología y Software', 'fa-laptop-code', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Tecnología y Software');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Telecomunicaciones', 'fa-broadcast-tower', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Telecomunicaciones');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Transporte y Logística', 'fa-truck', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Transporte y Logística');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Turismo y Hotelería', 'fa-plane', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Turismo y Hotelería');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Gastronomía y Restaurantes', 'fa-utensils', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Gastronomía y Restaurantes');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Sector Público / Gobierno', 'fa-landmark', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Sector Público / Gobierno');
INSERT INTO sectores (nombre, icono, origen) SELECT 'ONGs y Fundaciones', 'fa-hand-holding-heart', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'ONGs y Fundaciones');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Manufactura e Industria', 'fa-industry', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Manufactura e Industria');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Automotriz', 'fa-car', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Automotriz');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Legal y Jurídico', 'fa-gavel', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Legal y Jurídico');
INSERT INTO sectores (nombre, icono, origen) SELECT 'Energía y Medio Ambiente', 'fa-solar-panel', 'SISTEMA' WHERE NOT EXISTS (SELECT 1 FROM sectores WHERE nombre = 'Energía y Medio Ambiente');

-- ============================================================================
-- 8. IDIOMAS
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
-- 10. ESTADISTICAS
-- ============================================================================
INSERT INTO estadisticas_publicas (clave, titulo, valor, descripcion) SELECT 'total_socios', 'Socios Colegiados', '0', 'Total de administradores colegiados' WHERE NOT EXISTS (SELECT 1 FROM estadisticas_publicas WHERE clave = 'total_socios');
INSERT INTO estadisticas_publicas (clave, titulo, valor, descripcion) SELECT 'especialidades_disponibles', 'Especialidades', '8', 'Especialidades profesionales disponibles' WHERE NOT EXISTS (SELECT 1 FROM estadisticas_publicas WHERE clave = 'especialidades_disponibles');
INSERT INTO estadisticas_publicas (clave, titulo, valor, descripcion) SELECT 'anios_trayectoria', 'Años de Historia', '25', 'Años de trayectoria institucional' WHERE NOT EXISTS (SELECT 1 FROM estadisticas_publicas WHERE clave = 'anios_trayectoria');
INSERT INTO estadisticas_publicas (clave, titulo, valor, descripcion) SELECT 'satisfaccion_clientes', 'Satisfacción', '98', 'Índice de satisfacción' WHERE NOT EXISTS (SELECT 1 FROM estadisticas_publicas WHERE clave = 'satisfaccion_clientes');