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

    -- Insertar estadísticas base
    INSERT INTO estadisticas_publicas (clave, titulo, valor, descripcion, orden) VALUES
('total_socios', 'Socios Colegiados', '0', 'Total de administradores colegiados', 1),
('especialidades_disponibles', 'Especialidades', '8', 'Especialidades profesionales disponibles', 2),
('anios_trayectoria', 'Años de Historia', '25', 'Años de trayectoria institucional', 3),
('satisfaccion_clientes', 'Satisfacción', '98', 'Índice de satisfacción', 4);