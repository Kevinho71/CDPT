-- ============================================================================
-- ESQUEMA COMPLETO DE BASE DE DATOS - SISTEMA CADET
-- Versión: 3.1 (Con IF NOT EXISTS)
-- Fecha: 2026-01-17
-- ============================================================================

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
    lejendario INTEGER,
    linkqr VARCHAR(500),
    matricula VARCHAR(100),
    ntodocumento VARCHAR(100),
    nombresocio VARCHAR(255),
    qr VARCHAR(500),
    fk_institucion INTEGER,
    fk_persona INTEGER,
    fk_profesion INTEGER,
    CONSTRAINT fk_socio_institucion FOREIGN KEY (fk_institucion) REFERENCES institucion(id) ON DELETE SET NULL,
    CONSTRAINT fk_socio_persona FOREIGN KEY (fk_persona) REFERENCES persona(id) ON DELETE CASCADE,
    CONSTRAINT fk_socio_profesion FOREIGN KEY (fk_profesion) REFERENCES profesion(id) ON DELETE SET NULL
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
    visualizaciones INTEGER DEFAULT 0,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado INTEGER DEFAULT 1,
    CONSTRAINT fk_perfil_socio FOREIGN KEY (fk_socio) REFERENCES socio(id) ON DELETE CASCADE
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
-- 8. GESTIÓN DOCUMENTAL (NUEVO)
-- ============================================================================

CREATE TABLE IF NOT EXISTS documentos (
    id SERIAL PRIMARY KEY,
    fk_socio INTEGER NOT NULL,
    
    nombre VARCHAR(255) NOT NULL,
    tipo VARCHAR(50),
    
    archivo_url VARCHAR(500) NOT NULL,
    mime_type VARCHAR(100),
    
    estado INTEGER DEFAULT 1,
    fecha_subida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_documentos_socio FOREIGN KEY (fk_socio) REFERENCES socio(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_documentos_socio ON documentos(fk_socio);


-- ============================================================================
-- 10. FINANZAS (NUEVO - PARA DASHBOARD)
-- ============================================================================

CREATE TABLE IF NOT EXISTS pagos (
    id SERIAL PRIMARY KEY,
    fk_socio INTEGER NOT NULL,
    monto NUMERIC(10, 2) NOT NULL,
    moneda VARCHAR(10) DEFAULT 'BOB',
    concepto VARCHAR(255),
    metodo_pago VARCHAR(50),
    fecha_pago TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'aprobado',
    factura_url VARCHAR(500),
    CONSTRAINT fk_pagos_socio FOREIGN KEY (fk_socio) REFERENCES socio(id) ON DELETE CASCADE
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

CREATE TABLE IF NOT EXISTS consultas_contacto (
    id SERIAL PRIMARY KEY,
    fk_perfil_socio INTEGER NOT NULL,
    nombre_completo VARCHAR(255) NOT NULL,
    correo_electronico VARCHAR(255) NOT NULL,
    telefono VARCHAR(50),
    empresa VARCHAR(255),
    modalidad_preferida VARCHAR(20),
    horario_preferido VARCHAR(100),
    motivo_reunion TEXT NOT NULL,
    detalles_adicionales TEXT,
    estado VARCHAR(20) DEFAULT 'pendiente',
    fecha_contacto TIMESTAMP,
    notas_internas TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_consulta_perfil FOREIGN KEY (fk_perfil_socio) REFERENCES perfil_socio(id) ON DELETE CASCADE
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
-- ÍNDICES PARA OPTIMIZACIÓN DE POSTS
-- ============================================================================

-- Índices para posts
CREATE INDEX IF NOT EXISTS idx_posts_tipo ON posts(tipo);
CREATE INDEX IF NOT EXISTS idx_posts_publicado ON posts(publicado);
CREATE INDEX IF NOT EXISTS idx_posts_slug ON posts(slug);
CREATE INDEX IF NOT EXISTS idx_posts_usuario ON posts(fk_usuario);
CREATE INDEX IF NOT EXISTS idx_posts_created_at ON posts(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_posts_estado ON posts(estado);

-- Índices para post_secciones
CREATE INDEX IF NOT EXISTS idx_post_secciones_post ON post_secciones(fk_post);
CREATE INDEX IF NOT EXISTS idx_post_secciones_orden ON post_secciones(fk_post, orden);
CREATE INDEX IF NOT EXISTS idx_post_secciones_tipo ON post_secciones(tipo_seccion);
CREATE INDEX IF NOT EXISTS idx_post_secciones_estado ON post_secciones(estado);

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
-- ÍNDICES PARA OPTIMIZACIÓN DE TABLAS BASE
-- ============================================================================

-- Índices para tablas geográficas
CREATE INDEX IF NOT EXISTS idx_departamento_pais ON departamento(fk_pais);
CREATE INDEX IF NOT EXISTS idx_provincia_departamento ON provincia(fk_departamento);
CREATE INDEX IF NOT EXISTS idx_pais_estado ON pais(estado);
CREATE INDEX IF NOT EXISTS idx_departamento_estado ON departamento(estado);
CREATE INDEX IF NOT EXISTS idx_provincia_estado ON provincia(estado);

-- Índices para instituciones
CREATE INDEX IF NOT EXISTS idx_institucion_provincia ON institucion(fk_provincia);
CREATE INDEX IF NOT EXISTS idx_institucion_estado ON institucion(estado);
CREATE INDEX IF NOT EXISTS idx_anio_institucion ON anio(fk_institucion);

-- Índices para personas y usuarios
CREATE INDEX IF NOT EXISTS idx_persona_email ON persona(email);
CREATE INDEX IF NOT EXISTS idx_persona_ci ON persona(ci);
CREATE INDEX IF NOT EXISTS idx_persona_estado ON persona(estado);
CREATE INDEX IF NOT EXISTS idx_usuario_username ON usuario(username);
CREATE INDEX IF NOT EXISTS idx_usuario_persona ON usuario(fk_persona);
CREATE INDEX IF NOT EXISTS idx_usuario_estado ON usuario(estado);
CREATE INDEX IF NOT EXISTS idx_usuarios_roles_usuario ON usuarios_roles(usuario_id);
CREATE INDEX IF NOT EXISTS idx_usuarios_roles_rol ON usuarios_roles(rol_id);

-- Índices para socios (búsquedas frecuentes)
CREATE INDEX IF NOT EXISTS idx_socio_persona ON socio(fk_persona);
CREATE INDEX IF NOT EXISTS idx_socio_institucion ON socio(fk_institucion);
CREATE INDEX IF NOT EXISTS idx_socio_profesion ON socio(fk_profesion);
CREATE INDEX IF NOT EXISTS idx_socio_matricula ON socio(matricula);
CREATE INDEX IF NOT EXISTS idx_socio_codigo ON socio(codigo);
CREATE INDEX IF NOT EXISTS idx_socio_estado ON socio(estado);
CREATE INDEX IF NOT EXISTS idx_socio_nrodocumento ON socio(nrodocumento);

-- ============================================================================
-- ÍNDICES PARA OPTIMIZACIÓN DE IMÁGENES Y CATÁLOGOS
-- ============================================================================

-- Índices para catálogo (empresas)
CREATE INDEX IF NOT EXISTS idx_catalogo_tipo ON catalogo(tipo);
CREATE INDEX IF NOT EXISTS idx_catalogo_nit ON catalogo(nit);
CREATE INDEX IF NOT EXISTS idx_catalogo_estado ON catalogo(estado);
CREATE INDEX IF NOT EXISTS idx_catalogo_departamento ON catalogo(fk_departamento);
CREATE INDEX IF NOT EXISTS idx_catalogo_provincia ON catalogo(fk_provincia);
CREATE INDEX IF NOT EXISTS idx_catalogo_pais ON catalogo(fk_pais);

-- Índices CRÍTICOS para imágenes de catálogo (optimización principal)
CREATE INDEX IF NOT EXISTS idx_imagencatalogo_catalogo ON imagencatalogo(fk_catalogo);
CREATE INDEX IF NOT EXISTS idx_imagencatalogo_tipo ON imagencatalogo(tipo);
CREATE INDEX IF NOT EXISTS idx_imagencatalogo_estado ON imagencatalogo(estado);
CREATE INDEX IF NOT EXISTS idx_imagencatalogo_catalogo_tipo ON imagencatalogo(fk_catalogo, tipo);
CREATE INDEX IF NOT EXISTS idx_imagencatalogo_catalogo_estado ON imagencatalogo(fk_catalogo, estado);

-- Índice para relación socio-catálogo
CREATE INDEX IF NOT EXISTS idx_socio_catalogos_socio ON socio_catalogos(socio_id);
CREATE INDEX IF NOT EXISTS idx_socio_catalogos_catalogo ON socio_catalogos(catalogo_id);

-- ============================================================================
-- ÍNDICES PARA PERFILES PÚBLICOS Y BÚSQUEDA DE PROFESIONALES
-- ============================================================================

CREATE INDEX IF NOT EXISTS idx_perfil_socio_socio ON perfil_socio(fk_socio);
CREATE INDEX IF NOT EXISTS idx_perfil_socio_publico ON perfil_socio(perfil_publico);
CREATE INDEX IF NOT EXISTS idx_perfil_socio_estado ON perfil_socio(estado);
CREATE INDEX IF NOT EXISTS idx_perfil_socio_ciudad ON perfil_socio(ciudad);
CREATE INDEX IF NOT EXISTS idx_perfil_socio_titulo ON perfil_socio(titulo_profesional);
CREATE INDEX IF NOT EXISTS idx_perfil_socio_visualizaciones ON perfil_socio(visualizaciones DESC);
CREATE INDEX IF NOT EXISTS idx_perfil_socio_publico_estado ON perfil_socio(perfil_publico, estado);

-- Índices para especialidades y servicios
CREATE INDEX IF NOT EXISTS idx_especialidades_estado ON especialidades(estado);
CREATE INDEX IF NOT EXISTS idx_especialidades_origen ON especialidades(origen);
CREATE INDEX IF NOT EXISTS idx_socio_especialidades_perfil ON socio_especialidades(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_socio_especialidades_especialidad ON socio_especialidades(fk_especialidad);

CREATE INDEX IF NOT EXISTS idx_servicios_estado ON servicios(estado);
CREATE INDEX IF NOT EXISTS idx_servicios_categoria ON servicios(categoria);
CREATE INDEX IF NOT EXISTS idx_servicios_origen ON servicios(origen);
CREATE INDEX IF NOT EXISTS idx_socio_servicios_perfil ON socio_servicios(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_socio_servicios_servicio ON socio_servicios(fk_servicio);
CREATE INDEX IF NOT EXISTS idx_socio_servicios_destacado ON socio_servicios(destacado);

CREATE INDEX IF NOT EXISTS idx_sectores_estado ON sectores(estado);
CREATE INDEX IF NOT EXISTS idx_sectores_origen ON sectores(origen);
CREATE INDEX IF NOT EXISTS idx_socio_sectores_perfil ON socio_sectores(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_socio_sectores_sector ON socio_sectores(fk_sector);

-- ============================================================================
-- ÍNDICES PARA FORMACIÓN Y CERTIFICACIONES
-- ============================================================================

CREATE INDEX IF NOT EXISTS idx_formacion_perfil ON formacion(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_formacion_tipo ON formacion(tipo);
CREATE INDEX IF NOT EXISTS idx_formacion_estado ON formacion(estado);
CREATE INDEX IF NOT EXISTS idx_formacion_orden ON formacion(fk_perfil_socio, orden);

CREATE INDEX IF NOT EXISTS idx_certificaciones_perfil ON certificaciones(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_certificaciones_estado ON certificaciones(estado);
CREATE INDEX IF NOT EXISTS idx_certificaciones_orden ON certificaciones(fk_perfil_socio, orden);

CREATE INDEX IF NOT EXISTS idx_socio_idiomas_perfil ON socio_idiomas(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_socio_idiomas_idioma ON socio_idiomas(fk_idioma);

-- ============================================================================
-- ÍNDICES PARA GESTIÓN DOCUMENTAL
-- ============================================================================

CREATE INDEX IF NOT EXISTS idx_documentos_tipo ON documentos(tipo);
CREATE INDEX IF NOT EXISTS idx_documentos_estado ON documentos(estado);
CREATE INDEX IF NOT EXISTS idx_documentos_fecha ON documentos(fecha_subida DESC);

-- ============================================================================
-- ÍNDICES PARA PAGOS Y FINANZAS
-- ============================================================================

CREATE INDEX IF NOT EXISTS idx_pagos_socio ON pagos(fk_socio);
CREATE INDEX IF NOT EXISTS idx_pagos_estado ON pagos(estado);
CREATE INDEX IF NOT EXISTS idx_pagos_fecha ON pagos(fecha_pago DESC);
CREATE INDEX IF NOT EXISTS idx_pagos_metodo ON pagos(metodo_pago);

-- ============================================================================
-- ÍNDICES PARA AUTENTICACIÓN SOCIAL Y CONSULTAS
-- ============================================================================

CREATE INDEX IF NOT EXISTS idx_usuario_social_persona ON usuario_social(fk_persona);
CREATE INDEX IF NOT EXISTS idx_usuario_social_email ON usuario_social(email);
CREATE INDEX IF NOT EXISTS idx_usuario_social_provider ON usuario_social(provider);
CREATE INDEX IF NOT EXISTS idx_usuario_social_estado ON usuario_social(estado);

CREATE INDEX IF NOT EXISTS idx_consultas_perfil ON consultas_contacto(fk_perfil_socio);
CREATE INDEX IF NOT EXISTS idx_consultas_estado ON consultas_contacto(estado);
CREATE INDEX IF NOT EXISTS idx_consultas_fecha ON consultas_contacto(fecha_creacion DESC);

CREATE INDEX IF NOT EXISTS idx_estadisticas_orden ON estadisticas_publicas(orden);
CREATE INDEX IF NOT EXISTS idx_estadisticas_visible ON estadisticas_publicas(visible);

-- ============================================================================
-- ÍNDICES PARA CONTENIDO WEB ESTÁTICO
-- ============================================================================

CREATE INDEX IF NOT EXISTS idx_static_content_seccion ON web_static_content(seccion);
CREATE INDEX IF NOT EXISTS idx_static_content_tipo ON web_static_content(tipo_input);
CREATE INDEX IF NOT EXISTS idx_static_content_modificador ON web_static_content(fk_usuario_modificador);
