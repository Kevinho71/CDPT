    INSERT INTO profesion (estado, nombre) VALUES 
    (1, "Administración de Empresas"),
    (1, "Ingenieria Comercial"),
    (1, "Ingenieria Financiera"),
    (1, "Economía"),
    (1, "Banca y Finanzas")

    INSERT INTO pais (estado, nombre) VALUES
    (1, "BOLIVIA"),
    (1, "PERÚ"),
    (1, "CHILE"),
    (1, "ARGENTINA"),
    (1, "COLOMBIA")

     -- Asumiendo que la tabla se llama 'departamento'
INSERT INTO departamento (estado, nombre, abreviacion, fk_pais) VALUES 
    -- BOLIVIA (ID 1)
    (1, 'TARIJA', 'TJA', 1),
    (1, 'SANTA CRUZ', 'SCZ', 1),
    (1, 'BENI', 'BE', 1),
    (1, 'CHUQUISACA', 'CH', 1),
    (1, 'COCHABAMBA', 'CBBA', 1),
    (1, 'LA PAZ', 'LPZ', 1),
    (1, 'ORURO', 'OR', 1),
    (1, 'PANDO', 'PA', 1),
    (1, 'POTOSÍ', 'PTS', 1),

    -- PERÚ (ID 2)
    (1, 'LIMA', 'LIM', 2),
    (1, 'CUSCO', 'CUS', 2),
    (1, 'AREQUIPA', 'ARE', 2),
    (1, 'PIURA', 'PIU', 2),
    (1, 'LA LIBERTAD', 'LAL', 2),

    -- CHILE (ID 3)
    (1, 'METROPOLITANA DE SANTIAGO', 'RM', 3),
    (1, 'VALPARAÍSO', 'VS', 3),
    (1, 'ANTOFAGASTA', 'AN', 3),
    (1, 'BIOBÍO', 'BI', 3),

    -- ARGENTINA (ID 4)
    (1, 'BUENOS AIRES', 'BA', 4),
    (1, 'CÓRDOBA', 'CBA', 4),
    (1, 'SANTA FE', 'SF', 4),
    (1, 'MENDOZA', 'MZ', 4),
    (1, 'TUCUMÁN', 'TM', 4),

    -- COLOMBIA (ID 5)
    (1, 'ANTIOQUIA', 'ANT', 5),
    (1, 'BOGOTÁ D.C.', 'DC', 5),
    (1, 'VALLE DEL CAUCA', 'VAC', 5),
    (1, 'CUNDINAMARCA', 'CUN', 5),
    (1, 'ATLÁNTICO', 'ATL', 5);

    INSERT INTO provincia (estado, nombre, fk_departamento) VALUES 
    -- ===========================
    -- BOLIVIA (IDs 1-9)
    -- ===========================
    (1, 'CERCADO', 1),         -- Tarija
    (1, 'ANDRÉS IBÁÑEZ', 2),   -- Santa Cruz
    (1, 'CERCADO', 3),         -- Beni
    (1, 'OROPEZA', 4),         -- Chuquisaca
    (1, 'CERCADO', 5),         -- Cochabamba
    (1, 'MURILLO', 6),         -- La Paz
    (1, 'CERCADO', 7),         -- Oruro
    (1, 'NICOLÁS SUÁREZ', 8),  -- Pando
    (1, 'TOMÁS FRÍAS', 9),     -- Potosí

    -- ===========================
    -- PERÚ (IDs 10-14)
    -- ===========================
    (1, 'LIMA', 10),           -- Lima
    (1, 'CUSCO', 11),          -- Cusco
    (1, 'AREQUIPA', 12),       -- Arequipa
    (1, 'PIURA', 13),          -- Piura (Faltaba en la lista anterior)
    (1, 'TRUJILLO', 14),       -- La Libertad (Faltaba en la lista anterior)

    -- ===========================
    -- CHILE (IDs 15-18)
    -- ===========================
    (1, 'SANTIAGO', 15),       -- Metropolitana
    (1, 'VALPARAÍSO', 16),     -- Valparaíso
    (1, 'ANTOFAGASTA', 17),    -- Antofagasta (Faltaba en la lista anterior)
    (1, 'CONCEPCIÓN', 18),     -- Biobío (Faltaba en la lista anterior)

    -- ===========================
    -- ARGENTINA (IDs 19-23)
    -- ===========================
    (1, 'LA PLATA', 19),       -- Buenos Aires
    (1, 'CAPITAL', 20),        -- Córdoba
    (1, 'ROSARIO', 21),        -- Santa Fe (Faltaba en la lista anterior)
    (1, 'CAPITAL', 22),        -- Mendoza (Faltaba en la lista anterior)
    (1, 'CAPITAL', 23),        -- Tucumán (Faltaba en la lista anterior)

    -- ===========================
    -- COLOMBIA (IDs 24-28)
    -- ===========================
    (1, 'MEDELLÍN', 24),       -- Antioquia
    (1, 'BOGOTÁ', 25),         -- Bogotá D.C.
    (1, 'CALI', 26),           -- Valle del Cauca
    (1, 'SOACHA', 27),         -- Cundinamarca (Faltaba en la lista anterior)
    (1, 'BARRANQUILLA', 28);   -- Atlántico (Faltaba en la lista anterior)



    INSERT INTO especialidades (nombre, descripcion, origen, orden) VALUES
    -- Finanzas
    ('Finanzas Corporativas', 'Gestión del valor y capital de la empresa', 'SISTEMA', 1),
    ('Auditoría y Control', 'Control interno y auditoría externa', 'SISTEMA', 2),
    ('Tributación y Fiscalidad', 'Gestión de impuestos y normativa fiscal', 'SISTEMA', 3),
    ('Banca y Seguros', 'Gestión bancaria y análisis de riesgos', 'SISTEMA', 4),
    ('Mercado de Valores', 'Inversiones y bolsa de valores', 'SISTEMA', 5),

    -- Recursos Humanos
    ('Gestión del Talento Humano', 'Reclutamiento, selección y retención', 'SISTEMA', 6),
    ('Compensaciones y Beneficios', 'Estructuras salariales y beneficios', 'SISTEMA', 7),
    ('Desarrollo Organizacional', 'Cultura, clima y cambio organizacional', 'SISTEMA', 8),
    ('Legislación Laboral', 'Normativa y derecho laboral aplicado', 'SISTEMA', 9),
    ('Coaching y Liderazgo', 'Desarrollo de habilidades blandas y directivas', 'SISTEMA', 10),

    -- Marketing y Ventas
    ('Marketing Estratégico', 'Planeación de mercado a largo plazo', 'SISTEMA', 11),
    ('Marketing Digital', 'Estrategias en medios digitales y redes', 'SISTEMA', 12),
    ('Investigación de Mercados', 'Análisis de consumidor y competencia', 'SISTEMA', 13),
    ('Gestión Comercial y Ventas', 'Dirección de fuerzas de ventas', 'SISTEMA', 14),
    ('Branding y Marca', 'Construcción y gestión de identidad de marca', 'SISTEMA', 15),

    -- Operaciones y Logística
    ('Supply Chain Management', 'Gestión de la cadena de suministro', 'SISTEMA', 16),
    ('Logística y Distribución', 'Almacenamiento y transporte', 'SISTEMA', 17),
    ('Gestión de Calidad (ISO)', 'Normas de calidad y mejora continua', 'SISTEMA', 18),
    ('Seguridad Industrial', 'Seguridad y salud ocupacional', 'SISTEMA', 19),
    ('Comercio Exterior', 'Importaciones, exportaciones y aduanas', 'SISTEMA', 20),

    -- Estrategia y Gestión
    ('Gerencia General', 'Dirección integral de organizaciones', 'SISTEMA', 21),
    ('Planificación Estratégica', 'Definición de objetivos a largo plazo', 'SISTEMA', 22),
    ('Gestión de Proyectos (PMO)', 'Administración de proyectos y portafolios', 'SISTEMA', 23),
    ('Responsabilidad Social (RSE)', 'Sostenibilidad y ética empresarial', 'SISTEMA', 24),
    ('Innovación Empresarial', 'Gestión de la innovación y creatividad', 'SISTEMA', 25),

    -- Tecnología
    ('Transformación Digital', 'Digitalización de procesos de negocio', 'SISTEMA', 26),
    ('Business Intelligence (BI)', 'Análisis de datos para toma de decisiones', 'SISTEMA', 27),
    ('E-Commerce', 'Comercio electrónico y negocios digitales', 'SISTEMA', 28);

    INSERT INTO servicios (nombre, categoria, descripcion, origen) VALUES
    -- Consultoría
    ('Elaboración de Planes de Negocio', 'Consultoría', 'Desarrollo integral de planes para startups o expansión', 'SISTEMA'),
    ('Valoración de Empresas', 'Finanzas', 'Determinación del valor económico de una organización', 'SISTEMA'),
    ('Reingeniería de Procesos', 'Procesos', 'Rediseño radical de procesos para mejorar productividad', 'SISTEMA'),
    ('Implementación de KPIs', 'Gestión', 'Diseño de tableros de control y métricas', 'SISTEMA'),
    ('Auditoría Administrativa', 'Auditoría', 'Evaluación de la eficiencia de la estructura organizacional', 'SISTEMA'),

    -- RRHH
    ('Headhunting y Selección', 'RRHH', 'Búsqueda de ejecutivos y personal clave', 'SISTEMA'),
    ('Medición de Clima Laboral', 'RRHH', 'Diagnóstico del ambiente de trabajo', 'SISTEMA'),
    ('Manuales de Funciones', 'RRHH', 'Redacción de manuales y procedimientos organizacionales', 'SISTEMA'),

    -- Marketing
    ('Plan de Marketing', 'Marketing', 'Estrategia de mercado y posicionamiento', 'SISTEMA'),
    ('Estudios de Mercado', 'Marketing', 'Investigación cualitativa y cuantitativa', 'SISTEMA'),
    ('Gestión de Redes Sociales', 'Marketing', 'Community management corporativo', 'SISTEMA'),

    -- Capacitación
    ('Capacitación In-Company', 'Formación', 'Cursos a medida para empresas', 'SISTEMA'),
    ('Talleres de Liderazgo', 'Formación', 'Desarrollo de habilidades directivas', 'SISTEMA'),
    ('Coaching Ejecutivo 1 a 1', 'Formación', 'Acompañamiento personalizado a gerentes', 'SISTEMA'),

    -- Legal / Tramites
    ('Constitución de Empresas', 'Legal', 'Trámites para fundar nuevas compañías', 'SISTEMA'),
    ('Asesoría Tributaria', 'Legal', 'Optimización y cumplimiento fiscal', 'SISTEMA');

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

    INSERT INTO rol(estado, nombre) VALUES
    (1, "ROLE_ADMIN"),
    (1, "ROLE_SOCIO")

    -- Insertar estadísticas base
    INSERT INTO estadisticas_publicas (clave, titulo, valor, descripcion, orden) VALUES
('total_socios', 'Socios Colegiados', '0', 'Total de administradores colegiados', 1),
('especialidades_disponibles', 'Especialidades', '8', 'Especialidades profesionales disponibles', 2),
('anios_trayectoria', 'Años de Historia', '25', 'Años de trayectoria institucional', 3),
('satisfaccion_clientes', 'Satisfacción', '98', 'Índice de satisfacción', 4);