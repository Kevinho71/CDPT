-- ============================================================================
-- ESQUEMA COMPLETO DE BASE DE DATOS - SISTEMA CADET
-- Versión: 3.1 (Con IF NOT EXISTS)
-- Fecha: 2026-01-17
-- ============================================================================



-- ============================================================================
-- 1. FASE 1: LEADS Y CONTACTO INICIAL
-- ============================================================================
CREATE TABLE IF NOT EXISTS solicitud_inicial_afiliacion (
    id SERIAL PRIMARY KEY,
    nombre_completo VARCHAR(255) NOT NULL,
    celular VARCHAR(20) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    
    -- El token para enviarle por WhatsApp y que acceda al formulario completo
    token_acceso VARCHAR(100) UNIQUE,
    token_expiracion TIMESTAMP,
    
    estado VARCHAR(20) DEFAULT 'PENDIENTE', -- 'PENDIENTE', 'CONTACTADO', 'LINK_ENVIADO', 'FINALIZADO'
    fecha_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notas_admin TEXT -- Para que el admin anote "Le escribí por WhatsApp a tal hora"
);

-- ============================================================================
-- 2. FASE 2: DATOS DE AFILIACIÓN (FORMULARIO LARGO)
-- ============================================================================
CREATE TABLE IF NOT EXISTS solicitud_afiliacion (
    id SERIAL PRIMARY KEY,
    fk_solicitud_inicial_afiliacion INTEGER UNIQUE, -- Vincula con el Lead original
    
    -- DATOS PERSONALES DUROS (Base para crear tabla 'persona')
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    ci VARCHAR(20) NOT NULL,
    ci_expedido VARCHAR(10),
    fecha_nacimiento DATE,
    genero VARCHAR(20),
    direccion_domicilio TEXT,
    
    -- DOCUMENTOS ADMINISTRATIVOS (Privados, para validación)
    url_foto_carnet_anverso VARCHAR(500),
    url_foto_carnet_reverso VARCHAR(500),
    url_foto_titulo_provisicion VARCHAR(500),
    url_cv VARCHAR(500),
    
    
    -- ESTADOS DEL PROCESO
    estado_afiliacion VARCHAR(30) DEFAULT 'EN_REVISION', 
    -- 'EN_REVISION': El usuario ya llenó, el admin está viendo
    -- 'OBSERVADO': Algo está mal, se le pide corregir
    -- 'APROBADO': Se crea el socio automáticamente
    -- 'RECHAZADO': No cumple requisitos

    fecha_revision TIMESTAMP,    
    CONSTRAINT fk_afiliacion_contacto FOREIGN KEY (fk_solicitud_inicial_afiliacion) REFERENCES solicitud_inicial_afiliacion(id) ON DELETE SET NULL
);
-- ============================================================================
-- 1. TABLAS BASE GEOGRÁFICAS Y CATÁLOGOS GLOBALES
-- ============================================================================

CREATE TABLE IF NOT EXISTS pais (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS departamento (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    nombre VARCHAR(255) NOT NULL,
    abreviacion VARCHAR(10),
    fk_pais INTEGER,
    CONSTRAINT fk_departamento_pais FOREIGN KEY (fk_pais) REFERENCES pais(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS provincia (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    nombre VARCHAR(255) NOT NULL,
    fk_departamento INTEGER,
    CONSTRAINT fk_provincia_departamento FOREIGN KEY (fk_departamento) REFERENCES departamento(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS profesion (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    nombre VARCHAR(255) NOT NULL
);

-- ============================================================================
-- 2. INSTITUCIONES Y AÑOS
-- ============================================================================

CREATE TABLE IF NOT EXISTS institucion (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    compania VARCHAR(255),
    institucion VARCHAR(255),
    correo VARCHAR(255),
    direccion VARCHAR(500),
    fax VARCHAR(50),
    host VARCHAR(255),
    nit VARCHAR(50),
    port VARCHAR(10),
    representante VARCHAR(255),
    telefono VARCHAR(50),
    fk_provincia INTEGER,
    CONSTRAINT fk_institucion_provincia FOREIGN KEY (fk_provincia) REFERENCES provincia(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS anio (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    nombre VARCHAR(50),
    fk_institucion INTEGER,
    CONSTRAINT fk_anio_institucion FOREIGN KEY (fk_institucion) REFERENCES institucion(id) ON DELETE SET NULL
);

-- ============================================================================
-- 3. NÚCLEO DE USUARIOS Y SOCIOS
-- ============================================================================

CREATE TABLE IF NOT EXISTS persona (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    celular INTEGER,
    ci VARCHAR(50),
    email VARCHAR(255),
    nombrecompleto VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS rol (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuario (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    password VARCHAR(500),
    username VARCHAR(100) UNIQUE,
    fk_persona INTEGER UNIQUE,
    CONSTRAINT fk_usuario_persona FOREIGN KEY (fk_persona) REFERENCES persona(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS usuarios_roles (
    usuario_id INTEGER NOT NULL,
    rol_id INTEGER NOT NULL,
    PRIMARY KEY (usuario_id, rol_id),
    CONSTRAINT fk_usuarios_roles_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    CONSTRAINT fk_usuarios_roles_rol FOREIGN KEY (rol_id) REFERENCES rol(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS socio (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    codigo INTEGER,
    nrodocumento VARCHAR(30),
    fechaemision DATE,
    fechaexpiracion DATE,
    imagen VARCHAR(500),
    matricula VARCHAR(100),
    nombresocio VARCHAR(255),
    fk_institucion INTEGER,
    fk_persona INTEGER,
    fk_profesion INTEGER,
    fk_solicitud_afiliacion INTEGER UNIQUE,
    CONSTRAINT fk_socio_institucion FOREIGN KEY (fk_institucion) REFERENCES institucion(id) ON DELETE SET NULL,
    CONSTRAINT fk_socio_persona FOREIGN KEY (fk_persona) REFERENCES persona(id) ON DELETE CASCADE,
    CONSTRAINT fk_socio_profesion FOREIGN KEY (fk_profesion) REFERENCES profesion(id) ON DELETE SET NULL,
    CONSTRAINT fk_socio_origen_afiliacion FOREIGN KEY (fk_solicitud_afiliacion) REFERENCES solicitud_afiliacion(id) ON DELETE SET NULL
);

-- ============================================================================
-- 4. CATÁLOGOS EXTERNOS (EMPRESAS)
-- ============================================================================

CREATE TABLE IF NOT EXISTS catalogo (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    codigo INTEGER,
    
    -- Campos básicos
    nit VARCHAR(50),
    nombre VARCHAR(255),
    descripcion VARCHAR(800),
    direccion VARCHAR(500),
    dominio VARCHAR(255),
    
    -- El campo que te faltaba (JSONB para Postgres)
    descuento JSONB,
    
    tipo VARCHAR(100),
    nombrelogo VARCHAR(255),
    
    -- Coordenadas: Las pongo VARCHAR porque en tu Java son String
    latitud VARCHAR(50),
    longitud VARCHAR(50),
    
    -- Relaciones geográficas
    fk_departamento INTEGER,
    fk_pais INTEGER,
    fk_provincia INTEGER,
    
    CONSTRAINT fk_catalogo_departamento FOREIGN KEY (fk_departamento) REFERENCES departamento(id) ON DELETE SET NULL,
    CONSTRAINT fk_catalogo_pais FOREIGN KEY (fk_pais) REFERENCES pais(id) ON DELETE SET NULL,
    CONSTRAINT fk_catalogo_provincia FOREIGN KEY (fk_provincia) REFERENCES provincia(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS imagencatalogo (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,
    codigo INTEGER,
    imagen VARCHAR(500),
    tipo VARCHAR(30),
    fk_catalogo INTEGER,
    CONSTRAINT fk_imagencatalogo_catalogo FOREIGN KEY (fk_catalogo) REFERENCES catalogo(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS socio_catalogos (
    socio_id INTEGER NOT NULL,
    catalogo_id INTEGER NOT NULL,
    PRIMARY KEY (socio_id, catalogo_id),
    CONSTRAINT fk_socio_catalogos_socio FOREIGN KEY (socio_id) REFERENCES socio(id) ON DELETE CASCADE,
    CONSTRAINT fk_socio_catalogos_catalogo FOREIGN KEY (catalogo_id) REFERENCES catalogo(id) ON DELETE CASCADE
);

-- ============================================================================
-- 5. PERFILES PÚBLICOS
-- ============================================================================

CREATE TABLE IF NOT EXISTS perfil_socio (
    id SERIAL PRIMARY KEY,
    fk_socio INTEGER NOT NULL UNIQUE,
    nombre_completo VARCHAR(200),
    email VARCHAR(150),
    telefono VARCHAR(20),
    titulo_profesional VARCHAR(100),
    anos_experiencia INTEGER,
    resumen_profesional TEXT,
    modalidad_trabajo VARCHAR(50),
    institucion VARCHAR(50),
    ciudad VARCHAR(100),
    zona VARCHAR(200),
    foto_perfil VARCHAR(500),
    foto_banner VARCHAR(500),
    linkedin_url VARCHAR(300),
    facebook_url VARCHAR(300),
    twitter_url VARCHAR(300),
    instagram_url VARCHAR(300),
    whatsapp VARCHAR(20),
    sitio_web VARCHAR(300),
    perfil_publico BOOLEAN DEFAULT true,
    permite_contacto BOOLEAN DEFAULT true,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado INTEGER DEFAULT 1,
    CONSTRAINT fk_perfil_socio FOREIGN KEY (fk_socio) REFERENCES socio(id) ON DELETE CASCADE
);

-- ============================================================================
-- 1.4 SOLICITUDES DE CITA (Bandeja de Entrada / Leads de Pacientes)
-- ============================================================================
CREATE TABLE IF NOT EXISTS solicitud_cita (
    id SERIAL PRIMARY KEY,
    
    -- RELACIÓN CORRECTA: Apunta al Perfil Público
    fk_perfil_socio INTEGER NOT NULL, 
    
    -- Datos del Interesado (Visitante)
    nombre_paciente VARCHAR(150) NOT NULL,
    celular VARCHAR(50) NOT NULL, -- Fundamental para el botón de WhatsApp
    email VARCHAR(150),
    
    -- Detalles de la intención
    motivo_consulta TEXT,
    modalidad VARCHAR(20), -- 'VIRTUAL', 'PRESENCIAL'
    
    -- Gestión del Lead
    estado VARCHAR(30) DEFAULT 'PENDIENTE', 
    -- 'PENDIENTE': El psicólogo no lo ha visto
    -- 'CONTACTADO': El psicólogo ya abrió el WhatsApp
    -- 'CONVERTIDO': Se volvió una cita real
    -- 'AGENDADO': No contestó o no interesó
    
    nota_interna TEXT, -- "Le escribí y quedamos en hablar el lunes"
    
    fecha_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP,
    
    CONSTRAINT fk_sol_cita_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE
);
-- ============================================================================
-- 6. CATÁLOGOS EXTENSIBLES (Estrategia 1: SISTEMA/USUARIO)
-- ============================================================================

CREATE TABLE IF NOT EXISTS especialidades (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL UNIQUE,
    descripcion TEXT,
    origen VARCHAR(20) DEFAULT 'SISTEMA',
    estado INTEGER DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS socio_especialidades (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    fk_especialidad INTEGER NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_socio_esp_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE,
    CONSTRAINT fk_socio_esp_especialidad FOREIGN KEY (fk_especialidad) REFERENCES especialidades(id) ON DELETE CASCADE,
    CONSTRAINT uk_socio_especialidad UNIQUE (fk_perfil_socio, fk_especialidad)
);

CREATE TABLE IF NOT EXISTS servicios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    categoria VARCHAR(100),
    origen VARCHAR(20) DEFAULT 'SISTEMA',
    estado INTEGER DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS socio_servicios (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    fk_servicio INTEGER NOT NULL,
    destacado BOOLEAN DEFAULT false,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_socio_serv_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE,
    CONSTRAINT fk_socio_serv_servicio FOREIGN KEY (fk_servicio) REFERENCES servicios(id) ON DELETE CASCADE,
    CONSTRAINT uk_socio_servicio UNIQUE (fk_perfil_socio, fk_servicio)
);

CREATE TABLE IF NOT EXISTS sectores (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL UNIQUE,
    descripcion TEXT,
    icono VARCHAR(100),
    origen VARCHAR(20) DEFAULT 'SISTEMA',
    estado INTEGER DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS socio_sectores (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    fk_sector INTEGER NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_socio_sec_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE,
    CONSTRAINT fk_socio_sec_sector FOREIGN KEY (fk_sector) REFERENCES sectores(id) ON DELETE CASCADE,
    CONSTRAINT uk_socio_sector UNIQUE (fk_perfil_socio, fk_sector)
);

-- ============================================================================
-- 7. DETALLES DE FORMACIÓN (Hijos de Perfil)
-- ============================================================================

CREATE TABLE IF NOT EXISTS formacion (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    titulo VARCHAR(300) NOT NULL,
    institucion VARCHAR(300),
    pais VARCHAR(100),
    fecha_inicio DATE,
    fecha_fin DATE,
    en_curso BOOLEAN DEFAULT false,
    descripcion TEXT,
    orden INTEGER DEFAULT 0,
    estado INTEGER DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_formacion_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS certificaciones (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    nombre VARCHAR(300) NOT NULL,
    institucion_emisora VARCHAR(300),
    url_verificacion VARCHAR(500),
    orden INTEGER DEFAULT 0,
    estado INTEGER DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_certificacion_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS idiomas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    estado INTEGER DEFAULT 1,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS socio_idiomas (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    fk_idioma INTEGER NOT NULL,
    nivel VARCHAR(20) NOT NULL,
    estado INTEGER,
    fecha_creacion TIMESTAMP,
    CONSTRAINT fk_socio_idioma_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE,
    CONSTRAINT fk_socio_idioma_idioma FOREIGN KEY (fk_idioma) REFERENCES idiomas(id) ON DELETE CASCADE,
    CONSTRAINT uk_socio_idioma UNIQUE (fk_perfil_socio, fk_idioma)
);


-- ============================================================================
-- 11. AUTENTICACIÓN Y LANDING
-- ============================================================================

CREATE TABLE IF NOT EXISTS usuario_social (
    id SERIAL PRIMARY KEY,
    provider VARCHAR(20) NOT NULL DEFAULT 'GOOGLE',
    provider_id VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    nombre_completo VARCHAR(255),
    foto_perfil_url VARCHAR(500),
    fk_persona INTEGER NOT NULL,
    access_token TEXT,
    refresh_token TEXT,
    token_expires_at TIMESTAMP,
    email_verificado BOOLEAN DEFAULT true,
    fecha_vinculacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_ultima_autenticacion TIMESTAMP,
    estado INTEGER DEFAULT 1,
    CONSTRAINT fk_usuario_social_persona FOREIGN KEY (fk_persona) REFERENCES persona(id) ON DELETE CASCADE,
    CONSTRAINT uk_provider_id UNIQUE (provider, provider_id)
);



CREATE TABLE IF NOT EXISTS estadisticas_publicas (
    id SERIAL PRIMARY KEY,
    clave VARCHAR(100) NOT NULL UNIQUE,
    titulo VARCHAR(100) NOT NULL,
    valor VARCHAR(500),
    icono VARCHAR(100),
    descripcion TEXT,
    orden INTEGER DEFAULT 0,
    visible BOOLEAN DEFAULT true,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado INTEGER DEFAULT 1
);

-- ============================================================================
-- 12. SISTEMA DE PUBLICACIONES (POSTS: NOTICIAS Y EVENTOS)
-- ============================================================================

-- TABLA PRINCIPAL (Gestiona Noticias y Eventos)
CREATE TABLE IF NOT EXISTS posts (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,

    -- RELACIÓN CON USUARIO (Auditoría)
    fk_usuario INTEGER,
    CONSTRAINT fk_posts_usuario FOREIGN KEY (fk_usuario) REFERENCES usuario(id) ON DELETE SET NULL,

    -- DATOS OBLIGATORIOS COMUNES
    titulo VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    intro TEXT NOT NULL,
    portada_url VARCHAR(500),
    autor VARCHAR(100) NOT NULL,
    
    -- CLASIFICACIÓN Y ESTADO
    tipo VARCHAR(20) NOT NULL,
    publicado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- DATOS EXCLUSIVOS DE EVENTOS (Nullables)
    fecha_evento TIMESTAMP,
    lugar_evento VARCHAR(255),
    direccion_evento VARCHAR(500)
);

-- TABLA DE SECCIONES (El contenido dinámico)
CREATE TABLE IF NOT EXISTS post_secciones (
    id SERIAL PRIMARY KEY,
    estado INTEGER DEFAULT 1,

    -- RELACIÓN CON EL POST PADRE
    fk_post INTEGER NOT NULL,
    CONSTRAINT fk_post_secciones_post FOREIGN KEY (fk_post) REFERENCES posts(id) ON DELETE CASCADE,

    -- ORDENAMIENTO
    orden INTEGER NOT NULL,

    -- TIPO DE CONTENIDO
    tipo_seccion VARCHAR(20) DEFAULT 'ESTANDAR',

    -- CAMPOS DE CONTENIDO
    subtitulo VARCHAR(255), 
    contenido TEXT,
    imagen_url VARCHAR(500),
    video_url VARCHAR(500)
);



-- ============================================================================
-- 13. SISTEMA DE GESTIÓN DE CONTENIDOS ESTÁTICOS (CMS SIMPLIFICADO)
-- ============================================================================

-- TABLA ÚNICA: DICCIONARIO DE CONTENIDOS (Arquitectura Clave-Valor)
-- Almacena todo el contenido editable de la Landing Page
CREATE TABLE IF NOT EXISTS web_static_content (
    clave VARCHAR(100) PRIMARY KEY, -- ID único (ej: 'home_hero_titulo', 'footer_copy')
    
    -- CONTENIDO EDITABLE
    valor TEXT, -- El contenido (Texto, URL imagen, Link)
    
    -- AGRUPACIÓN Y TIPO
    seccion VARCHAR(50), -- Agrupador para el Panel Admin (ej: 'HOME', 'CONTACTO', 'GLOBAL')
    tipo_input VARCHAR(20) DEFAULT 'TEXT', -- Instrucción de renderizado para el Admin:
                                           -- 'TEXT' (Input simple)
                                           -- 'TEXTAREA' (Caja grande)
                                           -- 'IMAGE' (Uploader)
                                           -- 'LINK' (Input para URL externa)
    
    -- AUDITORÍA (Solo se guarda quién modificó y cuándo)
    fk_usuario_modificador INTEGER,
    fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_static_content_modificador FOREIGN KEY (fk_usuario_modificador) REFERENCES usuario(id) ON DELETE SET NULL
);

-- ============================================================================
-- 1. MÓDULO CLÍNICO (Agenda y Pacientes)
-- ============================================================================

-- 1.1 PACIENTES
-- Registro privado de cada psicólogo. Un mismo paciente real podría estar
-- registrado dos veces si va con dos psicólogos diferentes (privacidad de datos).
CREATE TABLE IF NOT EXISTS paciente (
    id SERIAL PRIMARY KEY,
    fk_socio INTEGER NOT NULL, -- El psicólogo dueño del registro
    
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    ci VARCHAR(20),
    fecha_nacimiento DATE,
    genero VARCHAR(20), -- 'M', 'F', 'Otro'
    telefono VARCHAR(50),
    email VARCHAR(150),
    direccion VARCHAR(255),
    ocupacion VARCHAR(100),
    estado_civil VARCHAR(50),
    
    nombre_contacto_emergencia VARCHAR(150),
    telefono_contacto_emergencia VARCHAR(50),
    
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado INTEGER DEFAULT 1, -- 1: Activo, 0: Inactivo/Archivado
    
    CONSTRAINT fk_paciente_socio FOREIGN KEY (fk_socio) REFERENCES socio(id) ON DELETE CASCADE
);

-- ============================================================================
-- 1.2 CITAS (La Agenda)
-- ============================================================================
CREATE TABLE IF NOT EXISTS cita (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    
    -- El "Titular" de la cita (quien reservó o quien paga). 
    -- Para ver quiénes asistieron realmente, se usa 'cita_participantes'.
    fk_paciente INTEGER NOT NULL,
    
    fecha_cita DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    
    modalidad VARCHAR(20), -- 'PRESENCIAL', 'VIRTUAL', 'DOMICILIO'
    tipo_sesion VARCHAR(50), -- 'INDIVIDUAL', 'PAREJA', 'FAMILIA', 'GRUPO'
    
    estado_cita VARCHAR(20) DEFAULT 'PROGRAMADA',
    
    motivo_breve VARCHAR(255),
    notas_internas TEXT,
    
    monto_acordado NUMERIC(10, 2), -- Precio negociado para esta sesión específica
    
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_cita_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE,
    CONSTRAINT fk_cita_paciente FOREIGN KEY (fk_paciente) REFERENCES paciente(id) ON DELETE CASCADE
);

-- ============================================================================
-- 1.2.1 PARTICIPANTES DE LA CITA (Tabla Nueva - Soporte Multicliente)
-- ============================================================================
CREATE TABLE IF NOT EXISTS cita_participantes (
    id SERIAL PRIMARY KEY,
    fk_cita INTEGER NOT NULL,
    fk_paciente INTEGER NOT NULL,
    
    -- Rol en la sesión: 'TITULAR', 'PAREJA', 'HIJO', 'PADRE', 'OBSERVADOR'
    tipo_participacion VARCHAR(50) DEFAULT 'PACIENTE',
    
    fecha_agregado TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_cp_cita FOREIGN KEY (fk_cita) REFERENCES cita(id) ON DELETE CASCADE,
    CONSTRAINT fk_cp_paciente FOREIGN KEY (fk_paciente) REFERENCES paciente(id) ON DELETE CASCADE,
    
    -- Evita duplicados: Un paciente no puede estar 2 veces en la misma lista de la misma cita
    CONSTRAINT uk_cita_participante UNIQUE (fk_cita, fk_paciente)
);

-- ============================================================================
-- 1.3 HISTORIA CLÍNICA (Modificada para Enfoque Sistémico)
-- ============================================================================
CREATE TABLE IF NOT EXISTS historia_clinica (
    id SERIAL PRIMARY KEY,
    
    -- CAMBIO IMPORTANTE:fk_paciente ahora puede ser NULL.
    -- Si la nota es de una sesión familiar, se vincula solo a fk_cita.
    -- Si es una nota suelta (llamada telefónica, análisis individual), se usa fk_paciente.
    fk_paciente INTEGER, 
    
    -- En este modelo, fk_cita es el conector principal para ver el historial
    fk_cita INTEGER, 
    
    fecha_consulta DATE NOT NULL,
    
    motivo_consulta TEXT,
    observaciones TEXT,
    diagnostico TEXT,
    tratamiento_plan TEXT,
    evolucion TEXT, -- Aquí se escribe lo que pasó con la pareja/familia
    
    archivos_adjuntos VARCHAR(1000),
    
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_hc_paciente FOREIGN KEY (fk_paciente) REFERENCES paciente(id) ON DELETE CASCADE,
    CONSTRAINT fk_hc_cita FOREIGN KEY (fk_cita) REFERENCES cita(id) ON DELETE SET NULL
);


-- ============================================================================
-- NUEVO: AMBIENTES Y RESERVAS
-- ============================================================================

-- Definición del espacio físico
CREATE TABLE IF NOT EXISTS ambiente (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL, -- Ej: 'Consultorio 1'
    descripcion TEXT,
    precio_hora NUMERIC(10, 2) NOT NULL, -- Monto fijo por la hora
    estado INTEGER DEFAULT 1
);
CREATE TABLE IF NOT EXISTS reserva_ambiente (
    id SERIAL PRIMARY KEY,
    fk_ambiente INTEGER NOT NULL,
    fk_socio INTEGER NOT NULL, -- El socio que reserva
    
    fecha_reserva DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    
    monto_total NUMERIC(10, 2), -- Se guarda aquí como histórico
    
    -- El estado de pago real se consulta en 'estado_cuenta_socio', 
    -- pero mantenemos este campo como 'espejo' para pintar el calendario rápido.
    estado_pago VARCHAR(20) DEFAULT 'PENDIENTE', 
    estado_reserva VARCHAR(20) DEFAULT 'CONFIRMADA', -- 'CONFIRMADA', 'CANCELADA'
    
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_res_ambiente FOREIGN KEY (fk_ambiente) REFERENCES ambiente(id) ON DELETE RESTRICT,
    CONSTRAINT fk_res_socio FOREIGN KEY (fk_socio) REFERENCES socio(id) ON DELETE CASCADE
);  

-- ============================================================================
-- 2. MÓDULO ADMINISTRATIVO DE PAGOS (Complejo)
-- ============================================================================

-- 2.1 CONFIGURACIÓN DE TARIFAS ANUALES
-- Permite que los precios cambien año tras año sin afectar registros antiguos.
CREATE TABLE IF NOT EXISTS configuracion_cuotas (
    id SERIAL PRIMARY KEY,
    gestion INTEGER NOT NULL UNIQUE, -- Ej: 2025, 2026
    monto_matricula NUMERIC(10, 2) NOT NULL, -- Ej: 500.00
    monto_mensualidad NUMERIC(10, 2) NOT NULL, -- Ej: 100.00
    dia_limite_pago INTEGER DEFAULT 10, -- Ej: Se debe pagar hasta el 10 de cada mes
    estado INTEGER DEFAULT 1
);

-- ============================================================================
-- 2.2 ESTADO DE CUENTA (MODIFICADA PARA SOPORTAR RESERVAS)
-- ============================================================================
CREATE TABLE IF NOT EXISTS estado_cuenta_socio (
    id SERIAL PRIMARY KEY,
    fk_socio INTEGER NOT NULL,
    
    -- Tipos: 'MATRICULA', 'MENSUALIDAD', 'ALQUILER', 'MULTA', 'CERTIFICADO'
    tipo_obligacion VARCHAR(50) NOT NULL, 
    
    gestion INTEGER NOT NULL, -- 2026
    mes INTEGER, -- 1 para Enero. Puede ser NULL si es un cargo anual o eventual.
    
    -- NUEVO: Para saber qué se está cobrando exactamente
    -- Ej: "Cuota Enero 2026" o "Reserva Consultorio 1 - 20/Feb"
    concepto VARCHAR(255), 
    
    -- NUEVO: Vinculación con la reserva (Opcional, solo si es Alquiler)
    fk_reserva INTEGER, 

    monto_original NUMERIC(10, 2) NOT NULL,
    fecha_emision DATE DEFAULT CURRENT_DATE,
    fecha_vencimiento DATE NOT NULL,
    
    estado_pago VARCHAR(20) DEFAULT 'PENDIENTE', -- 'PENDIENTE', 'PAGADO', 'PARCIAL', 'VENCIDO'
    monto_pagado_acumulado NUMERIC(10, 2) DEFAULT 0,
    
    CONSTRAINT fk_estadocuenta_socio FOREIGN KEY (fk_socio) REFERENCES socio(id) ON DELETE CASCADE
    
    -- NOTA: La FK a reserva se agrega al final con ALTER TABLE para evitar errores de orden
);

-- 2.3 TRANSACCIONES DE PAGO (El Dinero Real)
-- Registro de cuando el psicólogo paga.
CREATE TABLE IF NOT EXISTS transaccion_pago (
    id SERIAL PRIMARY KEY,
    fk_socio INTEGER NOT NULL,
    fk_usuario_admin INTEGER, -- Si un admin registra el pago manualmente (opcional)
    
    monto_total NUMERIC(10, 2) NOT NULL,
    metodo_pago VARCHAR(50), -- 'QR', 'TRANSFERENCIA', 'EFECTIVO'
    comprobante_url VARCHAR(500), -- Foto del voucher
    referencia_bancaria VARCHAR(100),
    
    fecha_pago TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observaciones TEXT,
    
    estado VARCHAR(20) DEFAULT 'APROBADO', -- 'EN_REVISION', 'APROBADO', 'RECHAZADO' (Si requieren validación)
    
    CONSTRAINT fk_transaccion_socio FOREIGN KEY (fk_socio) REFERENCES socio(id) ON DELETE CASCADE,
    CONSTRAINT fk_transaccion_admin FOREIGN KEY (fk_usuario_admin) REFERENCES usuario(id) ON DELETE SET NULL
);

-- 2.4 DETALLE DEL PAGO (Tabla Pivote)
-- Relaciona UN pago con UNA O VARIAS deudas. 
-- Ejemplo: Un pago de 200bs puede cubrir "Enero" (100bs) y "Febrero" (100bs).
CREATE TABLE IF NOT EXISTS detalle_pago_deuda (
    id SERIAL PRIMARY KEY,
    fk_transaccion INTEGER NOT NULL,
    fk_estado_cuenta INTEGER NOT NULL,
    
    monto_aplicado NUMERIC(10, 2) NOT NULL, -- Cuánto de este pago se fue a esta deuda
    
    CONSTRAINT fk_det_transaccion FOREIGN KEY (fk_transaccion) REFERENCES transaccion_pago(id) ON DELETE CASCADE,
    CONSTRAINT fk_det_deuda FOREIGN KEY (fk_estado_cuenta) REFERENCES estado_cuenta_socio(id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS publico_objetivo (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    estado INTEGER DEFAULT 1, -- 1: Activo, 0: Inactivo
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS socio_publico (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    fk_publico_objetivo INTEGER NOT NULL,
    
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Relaciones (Foreign Keys)
    CONSTRAINT fk_socio_pub_perfil FOREIGN KEY (fk_perfil_socio) 
        REFERENCES perfil_socio(id) ON DELETE CASCADE,
        
    CONSTRAINT fk_socio_pub_objetivo FOREIGN KEY (fk_publico_objetivo) 
        REFERENCES publico_objetivo(id) ON DELETE CASCADE,
        
    -- Restricción Única: Evita que asignen el mismo público dos veces al mismo socio
    CONSTRAINT uk_socio_publico UNIQUE (fk_perfil_socio, fk_publico_objetivo)
);

-- 2.1 TABLA DE DOCUMENTOS (El archivo en sí)
CREATE TABLE IF NOT EXISTS documento_profesional (
    id SERIAL PRIMARY KEY,
    
    titulo VARCHAR(255) NOT NULL, -- Ej: "Título de Maestría en Clínica"
    descripcion TEXT,
    
    archivo_url VARCHAR(500) NOT NULL, -- URL de Cloudinary o local
    tipo_archivo VARCHAR(50), -- 'PDF', 'JPG', 'PNG'
    
    estado INTEGER DEFAULT 1,
    fecha_subida TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2.2 TABLA INTERMEDIA: SOCIO <-> DOCUMENTO
-- Aquí defines qué documentos muestra cada perfil
CREATE TABLE IF NOT EXISTS socio_documentos (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    fk_documento INTEGER NOT NULL,
    
    orden INTEGER DEFAULT 0, -- Para que el socio decida cuál sale primero
    es_visible BOOLEAN DEFAULT true, -- Permite ocultarlo sin borrar la relación
    
    fecha_asociacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_pd_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE,
    CONSTRAINT fk_pd_documento FOREIGN KEY (fk_documento) REFERENCES documento_profesional(id) ON DELETE CASCADE,
    
    CONSTRAINT uk_perfil_documento UNIQUE (fk_perfil_socio, fk_documento)
);


END $$;
-- ============================================================================
-- ÍNDICES PARA OPTIMIZACIÓN DE TABLAS BASE
-- ============================================================================
-- ============================================================================
-- ÍNDICES DE BASE (Geografía, Instituciones, Personas) - SE MANTIENEN
-- ============================================================================
CREATE INDEX IF NOT EXISTS idx_departamento_pais ON departamento(fk_pais);
CREATE INDEX IF NOT EXISTS idx_provincia_departamento ON provincia(fk_departamento);
CREATE INDEX IF NOT EXISTS idx_pais_estado ON pais(estado);
CREATE INDEX IF NOT EXISTS idx_departamento_estado ON departamento(estado);
CREATE INDEX IF NOT EXISTS idx_provincia_estado ON provincia(estado);

CREATE INDEX IF NOT EXISTS idx_institucion_provincia ON institucion(fk_provincia);
CREATE INDEX IF NOT EXISTS idx_institucion_estado ON institucion(estado);
CREATE INDEX IF NOT EXISTS idx_anio_institucion ON anio(fk_institucion);

CREATE INDEX IF NOT EXISTS idx_persona_email ON persona(email);
CREATE INDEX IF NOT EXISTS idx_persona_ci ON persona(ci);
CREATE INDEX IF NOT EXISTS idx_persona_estado ON persona(estado);
CREATE INDEX IF NOT EXISTS idx_usuario_username ON usuario(username);
CREATE INDEX IF NOT EXISTS idx_usuario_persona ON usuario(fk_persona);
CREATE INDEX IF NOT EXISTS idx_usuario_estado ON usuario(estado);
CREATE INDEX IF NOT EXISTS idx_usuarios_roles_usuario ON usuarios_roles(usuario_id);
CREATE INDEX IF NOT EXISTS idx_usuarios_roles_rol ON usuarios_roles(rol_id);

CREATE INDEX IF NOT EXISTS idx_socio_persona ON socio(fk_persona);
CREATE INDEX IF NOT EXISTS idx_socio_institucion ON socio(fk_institucion);
CREATE INDEX IF NOT EXISTS idx_socio_profesion ON socio(fk_profesion);
CREATE INDEX IF NOT EXISTS idx_socio_matricula ON socio(matricula);
CREATE INDEX IF NOT EXISTS idx_socio_codigo ON socio(codigo);
CREATE INDEX IF NOT EXISTS idx_socio_estado ON socio(estado);
CREATE INDEX IF NOT EXISTS idx_socio_nrodocumento ON socio(nrodocumento);
-- NUEVO: Índice para buscar socios por su solicitud de origen
CREATE INDEX IF NOT EXISTS idx_socio_solicitud ON socio(fk_solicitud_afiliacion);

-- ============================================================================
-- ÍNDICES PARA CATÁLOGOS (Empresas) - SE MANTIENEN
-- ============================================================================
CREATE INDEX IF NOT EXISTS idx_catalogo_tipo ON catalogo(tipo);
CREATE INDEX IF NOT EXISTS idx_catalogo_nit ON catalogo(nit);
CREATE INDEX IF NOT EXISTS idx_catalogo_estado ON catalogo(estado);
CREATE INDEX IF NOT EXISTS idx_catalogo_departamento ON catalogo(fk_departamento);
CREATE INDEX IF NOT EXISTS idx_catalogo_provincia ON catalogo(fk_provincia);
CREATE INDEX IF NOT EXISTS idx_catalogo_pais ON catalogo(fk_pais);

CREATE INDEX IF NOT EXISTS idx_imagencatalogo_catalogo ON imagencatalogo(fk_catalogo);
CREATE INDEX IF NOT EXISTS idx_imagencatalogo_tipo ON imagencatalogo(tipo);
CREATE INDEX IF NOT EXISTS idx_imagencatalogo_estado ON imagencatalogo(estado);
CREATE INDEX IF NOT EXISTS idx_imagencatalogo_catalogo_tipo ON imagencatalogo(fk_catalogo, tipo);

CREATE INDEX IF NOT EXISTS idx_socio_catalogos_socio ON socio_catalogos(socio_id);
CREATE INDEX IF NOT EXISTS idx_socio_catalogos_catalogo ON socio_catalogos(catalogo_id);

-- ============================================================================
-- ÍNDICES PARA PERFILES PÚBLICOS (Optimización de búsqueda de psicólogos)
-- ============================================================================
CREATE INDEX IF NOT EXISTS idx_perfil_socio_socio ON perfil_socio(fk_socio);
CREATE INDEX IF NOT EXISTS idx_perfil_socio_publico ON perfil_socio(perfil_publico);
CREATE INDEX IF NOT EXISTS idx_perfil_socio_estado ON perfil_socio(estado);
CREATE INDEX IF NOT EXISTS idx_perfil_socio_ciudad ON perfil_socio(ciudad);
CREATE INDEX IF NOT EXISTS idx_perfil_socio_titulo ON perfil_socio(titulo_profesional);
-- Índice compuesto para filtrar "Mostrar solo públicos y activos"
CREATE INDEX IF NOT EXISTS idx_perfil_socio_publico_estado ON perfil_socio(perfil_publico, estado);

-- Especialidades, Servicios, Sectores
CREATE INDEX IF NOT EXISTS idx_especialidades_estado ON especialidades(estado);
CREATE INDEX IF NOT EXISTS idx_socio_especialidades_perfil ON socio_especialidades(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_socio_especialidades_especialidad ON socio_especialidades(fk_especialidad);

CREATE INDEX IF NOT EXISTS idx_servicios_estado ON servicios(estado);
CREATE INDEX IF NOT EXISTS idx_socio_servicios_perfil ON socio_servicios(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_socio_servicios_servicio ON socio_servicios(fk_servicio);
CREATE INDEX IF NOT EXISTS idx_socio_servicios_destacado ON socio_servicios(destacado);

CREATE INDEX IF NOT EXISTS idx_sectores_estado ON sectores(estado);
CREATE INDEX IF NOT EXISTS idx_socio_sectores_perfil ON socio_sectores(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_socio_sectores_sector ON socio_sectores(fk_sector);

-- Formación y Certificaciones
CREATE INDEX IF NOT EXISTS idx_formacion_perfil ON formacion(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_formacion_orden ON formacion(fk_perfil_socio, orden);
CREATE INDEX IF NOT EXISTS idx_certificaciones_perfil ON certificaciones(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_certificaciones_orden ON certificaciones(fk_perfil_socio, orden);
CREATE INDEX IF NOT EXISTS idx_socio_idiomas_perfil ON socio_idiomas(fk_perfil_socio);

-- ============================================================================
-- ÍNDICES NUEVOS: MÓDULO DE SOLICITUDES Y AFILIACIÓN
-- ============================================================================

-- Fase 1: Leads
CREATE INDEX IF NOT EXISTS idx_sol_contacto_token ON solicitud_inicial_afiliacion(token_acceso); -- Rápido acceso al link
CREATE INDEX IF NOT EXISTS idx_sol_contacto_estado ON solicitud_inicial_afiliacion(estado);
CREATE INDEX IF NOT EXISTS idx_sol_contacto_fecha ON solicitud_inicial_afiliacion(fecha_solicitud DESC);

-- Fase 2: Afiliación Completa
CREATE INDEX IF NOT EXISTS idx_sol_afiliacion_contacto ON solicitud_afiliacion(fk_solicitud_inicial_afiliacion);
CREATE INDEX IF NOT EXISTS idx_sol_afiliacion_estado ON solicitud_afiliacion(estado_afiliacion);
CREATE INDEX IF NOT EXISTS idx_sol_afiliacion_ci ON solicitud_afiliacion(ci); -- Para evitar duplicados manuales

-- ============================================================================
-- ÍNDICES NUEVOS: MÓDULO CLÍNICO (PACIENTES Y CITAS)
-- ============================================================================

-- Solicitudes de Cita (Bandeja de Entrada)
CREATE INDEX IF NOT EXISTS idx_sol_cita_perfil ON solicitud_cita(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_sol_cita_estado ON solicitud_cita(estado); -- Filtrar "Pendientes"
CREATE INDEX IF NOT EXISTS idx_sol_cita_fecha ON solicitud_cita(fecha_solicitud DESC);

-- Pacientes
CREATE INDEX IF NOT EXISTS idx_paciente_socio ON paciente(fk_socio);
CREATE INDEX IF NOT EXISTS idx_paciente_ci ON paciente(ci);
CREATE INDEX IF NOT EXISTS idx_paciente_nombres ON paciente(nombres, apellidos); -- Búsqueda por nombre

-- Citas (Agenda)
CREATE INDEX IF NOT EXISTS idx_cita_perfil ON cita(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_cita_paciente ON cita(fk_paciente);
CREATE INDEX IF NOT EXISTS idx_cita_fecha ON cita(fecha_cita); -- Para mostrar calendario
CREATE INDEX IF NOT EXISTS idx_cita_estado ON cita(estado_cita);
-- Índice compuesto para validar cruce de horarios rápidamente
CREATE INDEX IF NOT EXISTS idx_cita_horario ON cita(fk_perfil_socio, fecha_cita, hora_inicio);

-- Historia Clínica
CREATE INDEX IF NOT EXISTS idx_hc_paciente ON historia_clinica(fk_paciente);
CREATE INDEX IF NOT EXISTS idx_hc_cita ON historia_clinica(fk_cita);
CREATE INDEX IF NOT EXISTS idx_hc_fecha ON historia_clinica(fecha_consulta DESC);

-- ============================================================================
-- ÍNDICES NUEVOS: MÓDULO FINANCIERO Y ADMINISTRATIVO
-- ============================================================================

-- Estado de Cuenta (Deudas)
CREATE INDEX IF NOT EXISTS idx_estadocuenta_socio ON estado_cuenta_socio(fk_socio);
CREATE INDEX IF NOT EXISTS idx_estadocuenta_estado ON estado_cuenta_socio(estado_pago);
CREATE INDEX IF NOT EXISTS idx_estadocuenta_gestion ON estado_cuenta_socio(gestion);
CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_mensualidad 
ON estado_cuenta_socio (fk_socio, tipo_obligacion, gestion, mes) 
WHERE tipo_obligacion IN ('MENSUALIDAD', 'MATRICULA');
-- Para buscar morosos rápidamente
CREATE INDEX IF NOT EXISTS idx_estadocuenta_morosos ON estado_cuenta_socio(fecha_vencimiento) WHERE estado_pago != 'PAGADO';

-- Transacciones (Pagos Reales)
CREATE INDEX IF NOT EXISTS idx_transaccion_socio ON transaccion_pago(fk_socio);
CREATE INDEX IF NOT EXISTS idx_transaccion_fecha ON transaccion_pago(fecha_pago DESC);
CREATE INDEX IF NOT EXISTS idx_transaccion_estado ON transaccion_pago(estado); -- Filtrar "En Revisión"

-- Reservas de Ambientes
CREATE INDEX IF NOT EXISTS idx_reserva_ambiente ON reserva_ambiente(fk_ambiente);
CREATE INDEX IF NOT EXISTS idx_reserva_socio ON reserva_ambiente(fk_socio);
CREATE INDEX IF NOT EXISTS idx_reserva_fecha ON reserva_ambiente(fecha_reserva); -- Calendario de reservas
CREATE INDEX IF NOT EXISTS idx_reserva_estado_pago ON reserva_ambiente(estado_pago);

-- ============================================================================
-- ÍNDICES NUEVOS: DOCUMENTOS Y PÚBLICO
-- ============================================================================

-- Público Objetivo
CREATE INDEX IF NOT EXISTS idx_socio_publico_perfil ON socio_publico(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_socio_publico_objetivo ON socio_publico(fk_publico_objetivo);

-- Documentos Profesionales (Tabla Intermedia)
CREATE INDEX IF NOT EXISTS idx_socio_docs_perfil ON socio_documentos(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_socio_docs_doc ON socio_documentos(fk_documento);
CREATE INDEX IF NOT EXISTS idx_socio_docs_visible ON socio_documentos(es_visible);

-- ============================================================================
-- ÍNDICES SOCIAL Y CMS - SE MANTIENEN
-- ============================================================================
CREATE INDEX IF NOT EXISTS idx_usuario_social_persona ON usuario_social(fk_persona);
CREATE INDEX IF NOT EXISTS idx_usuario_social_email ON usuario_social(email);

CREATE INDEX IF NOT EXISTS idx_estadisticas_orden ON estadisticas_publicas(orden);

CREATE INDEX IF NOT EXISTS idx_static_content_seccion ON web_static_content(seccion);

-- Posts
CREATE INDEX IF NOT EXISTS idx_posts_tipo ON posts(tipo);
CREATE INDEX IF NOT EXISTS idx_posts_publicado ON posts(publicado);
CREATE INDEX IF NOT EXISTS idx_posts_slug ON posts(slug);
CREATE INDEX IF NOT EXISTS idx_posts_created_at ON posts(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_post_secciones_post ON post_secciones(fk_post);

-- Para buscar rápido quiénes fueron a la Cita #100
CREATE INDEX IF NOT EXISTS idx_cita_participantes_cita ON cita_participantes(fk_cita);

-- Para buscar rápido el historial de 'Carlos' (en qué citas participó)
CREATE INDEX IF NOT EXISTS idx_cita_participantes_paciente ON cita_participantes(fk_paciente);