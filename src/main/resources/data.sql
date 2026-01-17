-- 1. PROFESIONES
INSERT INTO profesion (estado, nombre) VALUES 
(1, 'Administración de Empresas'),
(1, 'Ingenieria Comercial'),
(1, 'Ingenieria Financiera'),
(1, 'Economía'),
(1, 'Banca y Finanzas');

-- 2. PAISES
INSERT INTO pais (estado, nombre) VALUES
(1, 'BOLIVIA'),
(1, 'PERÚ'),
(1, 'CHILE'),
(1, 'ARGENTINA'),
(1, 'COLOMBIA');

-- 3. DEPARTAMENTOS
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) VALUES 
(1, 'TARIJA', 'TJA', 1),
(1, 'SANTA CRUZ', 'SCZ', 1),
(1, 'BENI', 'BE', 1),
(1, 'CHUQUISACA', 'CH', 1),
(1, 'COCHABAMBA', 'CBBA', 1),
(1, 'LA PAZ', 'LPZ', 1),
(1, 'ORURO', 'OR', 1),
(1, 'PANDO', 'PA', 1),
(1, 'POTOSÍ', 'PTS', 1),
(1, 'LIMA', 'LIM', 2),
(1, 'CUSCO', 'CUS', 2),
(1, 'AREQUIPA', 'ARE', 2),
(1, 'PIURA', 'PIU', 2),
(1, 'LA LIBERTAD', 'LAL', 2),
(1, 'METROPOLITANA DE SANTIAGO', 'RM', 3),
(1, 'VALPARAÍSO', 'VS', 3),
(1, 'ANTOFAGASTA', 'AN', 3),
(1, 'BIOBÍO', 'BI', 3),
(1, 'BUENOS AIRES', 'BA', 4),
(1, 'CÓRDOBA', 'CBA', 4),
(1, 'SANTA FE', 'SF', 4),
(1, 'MENDOZA', 'MZ', 4),
(1, 'TUCUMÁN', 'TM', 4),
(1, 'ANTIOQUIA', 'ANT', 5),
(1, 'BOGOTÁ D.C.', 'DC', 5),
(1, 'VALLE DEL CAUCA', 'VAC', 5),
(1, 'CUNDINAMARCA', 'CUN', 5),
(1, 'ATLÁNTICO', 'ATL', 5);

-- 4. PROVINCIAS
INSERT INTO provincia (estado, nombre, fk_departamento) VALUES 
(1, 'CERCADO', 1),
(1, 'ANDRÉS IBÁÑEZ', 2),
(1, 'CERCADO', 3),
(1, 'OROPEZA', 4),
(1, 'CERCADO', 5),
(1, 'MURILLO', 6),
(1, 'CERCADO', 7),
(1, 'NICOLÁS SUÁREZ', 8),
(1, 'TOMÁS FRÍAS', 9),
(1, 'LIMA', 10),
(1, 'CUSCO', 11),
(1, 'AREQUIPA', 12),
(1, 'PIURA', 13),
(1, 'TRUJILLO', 14),
(1, 'SANTIAGO', 15),
(1, 'VALPARAÍSO', 16),
(1, 'ANTOFAGASTA', 17),
(1, 'CONCEPCIÓN', 18),
(1, 'LA PLATA', 19),
(1, 'CAPITAL', 20),
(1, 'ROSARIO', 21),
(1, 'CAPITAL', 22),
(1, 'CAPITAL', 23),
(1, 'MEDELLÍN', 24),
(1, 'BOGOTÁ', 25),
(1, 'CALI', 26),
(1, 'SOACHA', 27),
(1, 'BARRANQUILLA', 28);

-- 5. ESPECIALIDADES (Se eliminó columna 'orden')
INSERT INTO especialidades (nombre, descripcion, origen) VALUES
('Finanzas Corporativas', 'Gestión del valor y capital de la empresa', 'SISTEMA'),
('Auditoría y Control', 'Control interno y auditoría externa', 'SISTEMA'),
('Tributación y Fiscalidad', 'Gestión de impuestos y normativa fiscal', 'SISTEMA'),
('Banca y Seguros', 'Gestión bancaria y análisis de riesgos', 'SISTEMA'),
('Mercado de Valores', 'Inversiones y bolsa de valores', 'SISTEMA'),
('Gestión del Talento Humano', 'Reclutamiento, selección y retención', 'SISTEMA'),
('Compensaciones y Beneficios', 'Estructuras salariales y beneficios', 'SISTEMA'),
('Desarrollo Organizacional', 'Cultura, clima y cambio organizacional', 'SISTEMA'),
('Legislación Laboral', 'Normativa y derecho laboral aplicado', 'SISTEMA'),
('Coaching y Liderazgo', 'Desarrollo de habilidades blandas y directivas', 'SISTEMA'),
('Marketing Estratégico', 'Planeación de mercado a largo plazo', 'SISTEMA'),
('Marketing Digital', 'Estrategias en medios digitales y redes', 'SISTEMA'),
('Investigación de Mercados', 'Análisis de consumidor y competencia', 'SISTEMA'),
('Gestión Comercial y Ventas', 'Dirección de fuerzas de ventas', 'SISTEMA'),
('Branding y Marca', 'Construcción y gestión de identidad de marca', 'SISTEMA'),
('Supply Chain Management', 'Gestión de la cadena de suministro', 'SISTEMA'),
('Logística y Distribución', 'Almacenamiento y transporte', 'SISTEMA'),
('Gestión de Calidad (ISO)', 'Normas de calidad y mejora continua', 'SISTEMA'),
('Seguridad Industrial', 'Seguridad y salud ocupacional', 'SISTEMA'),
('Comercio Exterior', 'Importaciones, exportaciones y aduanas', 'SISTEMA'),
('Gerencia General', 'Dirección integral de organizaciones', 'SISTEMA'),
('Planificación Estratégica', 'Definición de objetivos a largo plazo', 'SISTEMA'),
('Gestión de Proyectos (PMO)', 'Administración de proyectos y portafolios', 'SISTEMA'),
('Responsabilidad Social (RSE)', 'Sostenibilidad y ética empresarial', 'SISTEMA'),
('Innovación Empresarial', 'Gestión de la innovación y creatividad', 'SISTEMA'),
('Transformación Digital', 'Digitalización de procesos de negocio', 'SISTEMA'),
('Business Intelligence (BI)', 'Análisis de datos para toma de decisiones', 'SISTEMA'),
('E-Commerce', 'Comercio electrónico y negocios digitales', 'SISTEMA');

-- 6. SERVICIOS
INSERT INTO servicios (nombre, categoria, descripcion, origen) VALUES
('Elaboración de Planes de Negocio', 'Consultoría', 'Desarrollo integral de planes para startups o expansión', 'SISTEMA'),
('Valoración de Empresas', 'Finanzas', 'Determinación del valor económico de una organización', 'SISTEMA'),
('Reingeniería de Procesos', 'Procesos', 'Rediseño radical de procesos para mejorar productividad', 'SISTEMA'),
('Implementación de KPIs', 'Gestión', 'Diseño de tableros de control y métricas', 'SISTEMA'),
('Auditoría Administrativa', 'Auditoría', 'Evaluación de la eficiencia de la estructura organizacional', 'SISTEMA'),
('Headhunting y Selección', 'RRHH', 'Búsqueda de ejecutivos y personal clave', 'SISTEMA'),
('Medición de Clima Laboral', 'RRHH', 'Diagnóstico del ambiente de trabajo', 'SISTEMA'),
('Manuales de Funciones', 'RRHH', 'Redacción de manuales y procedimientos organizacionales', 'SISTEMA'),
('Plan de Marketing', 'Marketing', 'Estrategia de mercado y posicionamiento', 'SISTEMA'),
('Estudios de Mercado', 'Marketing', 'Investigación cualitativa y cuantitativa', 'SISTEMA'),
('Gestión de Redes Sociales', 'Marketing', 'Community management corporativo', 'SISTEMA'),
('Capacitación In-Company', 'Formación', 'Cursos a medida para empresas', 'SISTEMA'),
('Talleres de Liderazgo', 'Formación', 'Desarrollo de habilidades directivas', 'SISTEMA'),
('Coaching Ejecutivo 1 a 1', 'Formación', 'Acompañamiento personalizado a gerentes', 'SISTEMA'),
('Constitución de Empresas', 'Legal', 'Trámites para fundar nuevas compañías', 'SISTEMA'),
('Asesoría Tributaria', 'Legal', 'Optimización y cumplimiento fiscal', 'SISTEMA');

-- 7. SECTORES
INSERT INTO sectores (nombre, icono, origen) VALUES
('Banca y Finanzas', 'fa-university', 'SISTEMA'),
('Seguros', 'fa-shield-alt', 'SISTEMA'),
('Educación y Universidades', 'fa-graduation-cap', 'SISTEMA'),
('Salud y Hospitales', 'fa-hospital', 'SISTEMA'),
('Construcción e Inmobiliaria', 'fa-building', 'SISTEMA'),
('Minería e Hidrocarburos', 'fa-hammer', 'SISTEMA'),
('Agroindustria', 'fa-tractor', 'SISTEMA'),
('Retail y Comercio Minorista', 'fa-shopping-cart', 'SISTEMA'),
('Consumo Masivo', 'fa-box-open', 'SISTEMA'),
('Tecnología y Software', 'fa-laptop-code', 'SISTEMA'),
('Telecomunicaciones', 'fa-broadcast-tower', 'SISTEMA'),
('Transporte y Logística', 'fa-truck', 'SISTEMA'),
('Turismo y Hotelería', 'fa-plane', 'SISTEMA'),
('Gastronomía y Restaurantes', 'fa-utensils', 'SISTEMA'),
('Sector Público / Gobierno', 'fa-landmark', 'SISTEMA'),
('ONGs y Fundaciones', 'fa-hand-holding-heart', 'SISTEMA'),
('Manufactura e Industria', 'fa-industry', 'SISTEMA'),
('Automotriz', 'fa-car', 'SISTEMA'),
('Legal y Jurídico', 'fa-gavel', 'SISTEMA'),
('Energía y Medio Ambiente', 'fa-solar-panel', 'SISTEMA');

-- 8. IDIOMAS
INSERT INTO idiomas (nombre, estado) VALUES 
('Español', 1), 
('Inglés', 1), 
('Portugués', 1), 
('Francés', 1), 
('Alemán', 1), 
('Italiano', 1), 
('Quechua', 1), 
('Aymara', 1), 
('Guaraní', 1),
('Chino Mandarín', 1), 
('Japonés', 1), 
('Ruso', 1);

-- 9. ROLES
INSERT INTO rol (estado, nombre) VALUES
(1, 'ROLE_ADMIN'),
(1, 'ROLE_SOCIO');

-- 10. ESTADISTICAS (Se eliminó columna 'orden')
INSERT INTO estadisticas_publicas (clave, titulo, valor, descripcion) VALUES
('total_socios', 'Socios Colegiados', '0', 'Total de administradores colegiados'),
('especialidades_disponibles', 'Especialidades', '8', 'Especialidades profesionales disponibles'),
('anios_trayectoria', 'Años de Historia', '25', 'Años de trayectoria institucional'),
('satisfaccion_clientes', 'Satisfacción', '98', 'Índice de satisfacción');